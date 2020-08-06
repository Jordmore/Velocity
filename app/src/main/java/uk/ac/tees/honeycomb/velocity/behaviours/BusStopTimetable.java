package uk.ac.tees.honeycomb.velocity.behaviours;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import uk.ac.tees.honeycomb.velocity.R;
import uk.ac.tees.honeycomb.velocity.api.entities.endpoints.StopByName;
import uk.ac.tees.honeycomb.velocity.api.entities.endpoints.StopByPostcode;
import uk.ac.tees.honeycomb.velocity.api.entities.endpoints.StopTimetable;
import uk.ac.tees.honeycomb.velocity.api.entities.transportapi.Departure;
import uk.ac.tees.honeycomb.velocity.stops.NaptanBusStop;

/**
 * A fragment behaviour for the Bus Stop Timetable feature.
 *
 * @author Jordon
 * @since 11/2/20
 *
 * @author Aidan
 * @since 4/5/20
 */
public class BusStopTimetable implements Behaviour {

    private final View parentView;
    private HashMap<String, String> loadedStops = new HashMap<>();
    private ArrayList<String> atco = new ArrayList<>();
    private String cacheInput = null;

    public BusStopTimetable(View parentView){
        this.parentView = parentView;
        createListeners(parentView);
    }

    private void createListeners(View view){
        EditText edtTxt = parentView.findViewById(R.id.busStopInput);
        edtTxt.setText(cacheInput);
        Button confirmButton = view.findViewById(R.id.confirm_busstop);
        confirmButton.setOnClickListener((view1) -> redirectButton());
        setSpinnerVisibility(false);
        setProgressBarVisibility(false);
    }

    /**
     * Method ran when the "confirm" button is pressed which finds the bus stops from the text
     * inputted.
     */
    private void redirectButton() {
        if (isSpinnerVisible()){
            return; // Already searching.
        }
        final EditText busStopInput = parentView.findViewById(R.id.busStopInput);
        cacheInput = busStopInput.getText().toString();

        if (validatePostCode(busStopInput.getText().toString())) {
            showBusStopsViaPostCode();
        } else {
            showBusStopViaName();
        }
    }

    /**
     * A reference to the Context this object was instantiated with.
     * @return The parent view's Context.
     */
    public Context getViewContext(){
        return parentView.getContext();
    }

    private boolean validatePostCode(String postCode) {
        Pattern pattern = Pattern.compile("([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([A-Za-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9][A-Za-z]?))))\\s?[0-9][A-Za-z]{2})");
        return pattern.matcher(postCode).matches();

    }

    private void removeArrayElements()
    {
        atco.clear();
        loadedStops.clear();
    }

    private void showBusStopsViaPostCode() {
        removeArrayElements();
        setProgressBarVisibility(true);

        final Spinner choices = parentView.findViewById(R.id.spinner_busstop);

        final EditText busStopInput = parentView.findViewById(R.id.busStopInput);
        final String message = busStopInput.getText().toString();

        final StopByPostcode SBP = new StopByPostcode(getViewContext(), message);
        SBP.query(
                response -> {
                    final List<NaptanBusStop> stops = response.getMessage().getData();
                    if (stops == null) {
                        Log.d("Velocity", "No values found.");
                        setProgressBarVisibility(false);
                        TableLayout tb = parentView.findViewById(R.id.score_table);
                        tb.removeAllViews();
                        AddRow(getViewContext(),tb,"Apologies, No Bus Stops can be found with" +
                                "\nZIP Code");
                        clearSpinner();
                        return;
                    }

                    if (!loadedStops.isEmpty()) {
                        loadedStops.clear();
                    }
                    for (NaptanBusStop stop : stops) {
                        Log.d("Velocity Result", stop.getName());

                        loadedStops.put(stop.getAtcoCode().toLowerCase(), stop.getName() + " - " + stop.getLocality());
                        atco.add(stop.getAtcoCode().toLowerCase());
                    }
                    setProgressBarVisibility(false);
                    setSpinnerVisibility(true);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_spinner_item, new ArrayList<>(loadedStops.values()));
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    choices.setAdapter(arrayAdapter);
                    choices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view1, int position, long id) {
                            getTimesViaATCO(atco.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) { }
                    });

                },
                error -> { Log.e("Velocity", "Error" + error);
                    setProgressBarVisibility(false);

                    TableLayout tb = parentView.findViewById(R.id.score_table);
                    tb.removeAllViews();
                    AddRow(getViewContext(),tb,"Apologies, Service Appears To be Down." +
                            "\nTry Again Later! ");}
        );

    }


    private void showBusStopViaName() {
        removeArrayElements();
        setProgressBarVisibility(true);
        final Spinner choices = parentView.findViewById(R.id.spinner_busstop);

        final EditText busStopInput = parentView.findViewById(R.id.busStopInput);
        final String message = busStopInput.getText().toString();

        final StopByName SBN = new StopByName(getViewContext(), message);
        busStopInput.setHint("Loading");


        SBN.query(
                response -> {
                    Log.d("Velocity", response.getMessage().getDataSource());
                    busStopInput.setHint("Loaded Successfully");
                    final List<NaptanBusStop> stops = response.getMessage().getData();
                    if (stops == null || stops.isEmpty()) {
                        Log.d("Velocity", "No values found.");
                        TableLayout tb = parentView.findViewById(R.id.score_table);
                        tb.removeAllViews();
                        AddRow(getViewContext(),tb,"No Bus Stops Found with that Name.");
                        setProgressBarVisibility(false);
                        return;
                    }

                    if (!loadedStops.isEmpty()) {
                        loadedStops.clear();
                    }
                    for (NaptanBusStop stop : stops) {
                        Log.d("Velocity Result", stop.getName());

                        loadedStops.put(stop.getAtcoCode().toLowerCase(), stop.getName() + " - " + stop.getLocality());
                        atco.add(stop.getAtcoCode().toLowerCase());
                    }
                    setProgressBarVisibility(false);
                    setSpinnerVisibility(true);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_spinner_item, new ArrayList<>(loadedStops.values()));
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    choices.setAdapter(arrayAdapter);
                    choices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view1, int position, long id) {
                            String code = atco.get(position);
                            Log.d("ATCO Code:",code);
                            getTimesViaATCO(code);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) { }
                    });

                },
                error -> { Log.e("Velocity", "Error" + error);
                    setProgressBarVisibility(false);
                    TableLayout tb = parentView.findViewById(R.id.score_table);
                    tb.removeAllViews();
                    AddRow(getViewContext(),tb,"Apologies, Service Appears To be Down." +
                            "\nTry Again Later! ");}
        );

    }

    private void getTimesViaATCO(String atco) {

        final StopTimetable stopTimetable = new StopTimetable(getViewContext(), atco);

        stopTimetable.query(
                response -> {
                    ArrayList<Departure> departures = response.getMessage().getStopTimetable().getDepartures();
                    TableLayout tb = parentView.findViewById(R.id.score_table);
                    tb.removeAllViews();
                    if (departures == null || departures.isEmpty()){
                        AddRow(getViewContext(),tb,"No Departures Listed.");
                        return;
                    }

                    for(Departure departure : departures){
                        String operatorName = departure.getOperatorName() == null ? "No Name Specified" : departure.getOperatorName();
                        String ws1 ="";
                        String ws2 ="";

                        StringBuilder sb1 = new StringBuilder();
                        int t = departure.getLineName().length();
                        sb1.append(new String(new char[32]).replace('\0',' ').substring((t > 1) ? t * 2 : t));
                        String secondSpacing = new String(new char[15]).replace("\0"," ");
                        AddRow(getViewContext(), tb, departure.getLineName() + sb1.toString() + departure.getAimedDepartureTime() + secondSpacing + operatorName);
                    }
                },
                error -> { Log.e("Velocity", "Error" + error);
                    setProgressBarVisibility(false);
                    TableLayout tb = parentView.findViewById(R.id.score_table);
                    tb.removeAllViews();
                    AddRow(getViewContext(),tb,"Apologies, Service Appears To be Down." +
                            "\nTry Again Later! ");}
        );
    }

    /**
     * Changes the visibility of the spinner (drop down selection) for bus stop choices.
     * @param visible Whether or not to make the spinner visible.
     */
    private void setSpinnerVisibility(boolean visible)
    {
        final Spinner choices = parentView.findViewById(R.id.spinner_busstop);
        choices.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * Returns true if the spinner is visible. False otherwise.
     * @return
     */
    private boolean isSpinnerVisible(){
        final Spinner choices = parentView.findViewById(R.id.spinner_busstop);
        return choices.getVisibility() == View.VISIBLE;
    }

    /**
     * Changes the visibility of the progress bar (spinning wheel) for loading the bus stops
     * matching the name given.
     * @param visible Whether or not to make the progress bar visible.
     */
    private void setProgressBarVisibility(boolean visible)
    {
        final ProgressBar loadingBar = parentView.findViewById(R.id.progressBar);
        loadingBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void clearSpinner()
    {
        Spinner choices = parentView.findViewById(R.id.spinner_busstop);
        choices.removeAllViews();
    }

    /**
     * Adds a row to the given {@link TableLayout} with the text given.
     * @param context The context for this operation.
     * @param layout The layout to add a row to.
     * @param text The text to be added to the newly created row.
     */
    private void AddRow(Context context, TableLayout layout, String text){
        TableRow row = new TableRow(context);
        TextView textView = new TextView(context);
        textView.setText(text);
        row.addView(textView);
        layout.addView(row);
    }

}
