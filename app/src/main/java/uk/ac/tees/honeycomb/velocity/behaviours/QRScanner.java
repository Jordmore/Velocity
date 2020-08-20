package uk.ac.tees.honeycomb.velocity.behaviours;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import uk.ac.tees.honeycomb.velocity.MainActivity;
import uk.ac.tees.honeycomb.velocity.QRActivity;
import uk.ac.tees.honeycomb.velocity.R;

import uk.ac.tees.honeycomb.velocity.qrc.QRCodeAdapter;
import uk.ac.tees.honeycomb.velocity.qrc.QRCodeData;
import uk.ac.tees.honeycomb.velocity.qrc.QRCodeItem;
import uk.ac.tees.honeycomb.velocity.readandwrite.SRQRCodes;


/**
 * This class displays QR Data, Acts as a hub from which you can Scan QR Codes or display them.
 * @author Jordon
 */
public class QRScanner extends AppCompatActivity implements Behaviour {
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    //QR Code Scanner Acivity requirements.

    Context context;
    private View parentView;

   //QRCodeAdapter variables for displaying QR Codes and "creating QR Codes"
    ArrayList qrCodeAdapterData = new ArrayList<QRCodeItem>();
    QRCodeAdapter adapter;

    SRQRCodes SR = new SRQRCodes();


    public QRScanner(View parentView) {
        this.parentView = parentView;

context = parentView.getContext();
adapter = new QRCodeAdapter(qrCodeAdapterData,context);
        createListeners(parentView);

    }


    private void createListeners(View view) {
        RecyclerView recyclerView;




        Button openQR = parentView.findViewById(R.id.Scan);
        Button addToList = parentView.findViewById(R.id.addqr);

        recyclerView = parentView.findViewById(R.id.qrRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        if(SR.readJson(context) != null) {
            ArrayList li = new ArrayList<QRCodeItem>();


           li = SR.readJson(context);

          qrCodeAdapterData.addAll(li);
            adapter.notifyDataSetChanged();
        }
        openQR.setOnClickListener((view1) -> open(view1));

        addToList.setOnClickListener((view1) -> qrDetailsPopUp(view1));
    }


    private void open(View view) {

        Intent intent = new Intent(view.getContext(), QRActivity.class);
        view.getContext().startActivity(intent);


    }

    public void qrDetailsPopUp(View view1) {

        QRCodeData qr = QRCodeData.instance();

        if(!qr.isPopulated())
        {
            Toast.makeText(parentView.getContext(), "QR Has Not Been Scanned!", Toast.LENGTH_LONG).show();
            return;
        }
        final Dialog dialog = new Dialog(view1.getContext());
        dialog.setContentView(R.layout.qrcodedetails_inputwindow);
        //Components
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        Button save = (Button) dialog.findViewById(R.id.save);

        ImageView qrImage = (ImageView) dialog.findViewById(R.id.qrimage);

qrImage.setImageBitmap(createImage(qr.getRawjson()));
        TextView inputqrName = (TextView) dialog.findViewById(R.id.inputQRName);
        TextView inputStartDate = (TextView) dialog.findViewById(R.id.inputStartDate);
        TextView inputEndDate = (TextView) dialog.findViewById(R.id.inputEndDate);
        // if button is clicked, close the custom dialog





        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                qr.discard();
            }
        });
        dialog.show();
        save.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                boolean validation = false;


                if (inputqrName.getText().toString().trim() == "") {
                    inputqrName.setText("");
                    inputqrName.setHintTextColor(Color.RED);
                    validation = true;
                } else if (inputStartDate.getText().toString().trim() == "") {
                    inputStartDate.setText("");
                    inputStartDate.setHintTextColor(Color.RED);
                    validation = true;

                } else if (inputEndDate.getText().toString().trim() == "") {
                    inputEndDate.setText("");
                    inputEndDate.setHintTextColor(Color.RED);
                    validation = true;
                }

                if (!checkdateisvalid(inputStartDate.getText().toString())) {
                    inputStartDate.setHint(R.string.incorrectDate);
                    inputStartDate.setHintTextColor(Color.RED);
                    validation = true;
                }

                if (!checkdateisvalid(inputEndDate.getText().toString())) {
                    inputEndDate.setHint(R.string.incorrectDate);
                    inputEndDate.setHintTextColor(Color.RED);
                    validation = true;
                }

                    StringBuffer startSB = new StringBuffer(inputStartDate.getText().toString());
                    StringBuffer endSB = new StringBuffer(inputEndDate.getText().toString());
                    if (!validation && !startSB.toString().contains("/") || !validation && !endSB.toString().contains("/")) {

                        startSB.insert(2, "/");
                        startSB.insert(5, "/");
                        endSB.insert(2, "/");
                        endSB.insert(5, "/");
                        if (Integer.parseInt(inputStartDate.getText().toString().substring(0, 2)) > Integer.parseInt(inputEndDate.getText().toString().substring(0, 2)))
                        {
                            validation = false;
                        }
                        else
                        {
                            if (Integer.parseInt(inputStartDate.getText().toString().substring(2, 4)) > Integer.parseInt(inputEndDate.getText().toString().substring(2, 4)))
                            {
                                validation = false;
                            }
                            else
                            {
                                if (Integer.parseInt(inputStartDate.getText().toString().substring(4, 8)) > Integer.parseInt(inputEndDate.getText().toString().substring(4, 8)))
                                {
                                    validation = false;
                                }
                            }
                        }

                    }






                if (validation == false){
                    qr.setStart(startSB.toString());
                    qr.setExpire(endSB.toString());


                    qr.setName(inputqrName.getText().toString());



                    dialog.dismiss();


                    qrCodeAdapterData.add(new QRCodeItem(qr.getName(), qr.getStart(), qr.getExpire(),qr.getRawjson()));


                    adapter.notifyDataSetChanged();
                    SRQRCodes SR = new SRQRCodes();
                    SR.writeJson(qrCodeAdapterData,context);
                    qr.discard();


                }


            }


        });


        dialog.show();
    }


    private boolean checkdateisvalid(String date) {


        if (date.length() != 8) {
            return false;
        } else {
            if (date.matches("[0-9]{2}[0-9]{2}[0-9]{4}")) {

                if (Integer.parseInt(date.substring(0, 2)) > 31) {
                    return false;
                } else if (Integer.parseInt(date.substring(2, 4)) > 12) {
                    return false;
                } else {
                    return true;
                }

            }
        }
        return false;
    }


    private Bitmap createImage(String compressedBit)
    {
        QRGEncoder qrgEncoder = new QRGEncoder( compressedBit, null, QRGContents.Type.TEXT, 150);

        return qrgEncoder.getBitmap();
    }



}


















