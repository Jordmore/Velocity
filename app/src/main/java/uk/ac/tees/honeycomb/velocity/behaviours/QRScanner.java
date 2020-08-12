package uk.ac.tees.honeycomb.velocity.behaviours;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import uk.ac.tees.honeycomb.velocity.QRActivity;
import uk.ac.tees.honeycomb.velocity.R;
import uk.ac.tees.honeycomb.velocity.qrc.QRCodeData;


public class QRScanner extends AppCompatActivity implements Behaviour {
    public static final int PERMISSION_REQUEST = 200;
    Context context;
    public static final int REQUEST_CODE = 100;
    private View parentView;

    TextView result;

    public QRScanner(View parentView) {
        this.parentView = parentView;


        createListeners(parentView);

    }


    private void createListeners(View view) {
        Button openQR = parentView.findViewById(R.id.Scan);
        Button refresh = parentView.findViewById(R.id.refresh);
        result = parentView.findViewById(R.id.textView4);
        openQR.setOnClickListener((view1) -> open(view1));

        refresh.setOnClickListener((view1) -> qrDetailsPopUp(view1));
    }


    private void open(View view) {

        Intent intent = new Intent(view.getContext(), QRActivity.class);
        view.getContext().startActivity(intent);


    }

    public void qrDetailsPopUp(View view1) {
        QRCodeData qr = QRCodeData.instance();
        ;

        final Dialog dialog = new Dialog(view1.getContext());
        dialog.setContentView(R.layout.qrcodedetails_inputwindow);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        Button save = (Button) dialog.findViewById(R.id.save);
        ImageView qrImage = (ImageView) dialog.findViewById(R.id.qrimage);
        TextView inputqrName = (TextView) dialog.findViewById(R.id.inputQRName);
        TextView inputStartDate = (TextView) dialog.findViewById(R.id.inputStartDate);
        TextView inputEndDate = (TextView) dialog.findViewById(R.id.inputEndDate);
        // if button is clicked, close the custom dialog

        QRGEncoder qrgEncoder = new QRGEncoder(qr.getRawjson(), null, QRGContents.Type.TEXT, 150);
        Bitmap QRImage = qrgEncoder.getBitmap();
        qrImage.setImageBitmap(QRImage);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });
        dialog.show();
        save.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
boolean validation = false;
                Date start = new Date();
                Date end = new Date();

                if (inputqrName.getText().toString().trim() == null) {
                    inputqrName.setText("");
                    inputqrName.setHintTextColor(Color.RED);
                    validation = true;
                } else if (inputStartDate.getText().toString().trim() == null) {
                    inputStartDate.setText("");
                    inputStartDate.setHintTextColor(Color.RED);
                    validation = true;

                } else if (inputEndDate.getText().toString().trim() == null) {
                    inputEndDate.setText("");
                    inputEndDate.setHintTextColor(Color.RED);
                    validation = true;
                }

if(checkdateisvalid(inputStartDate.getText().toString()) == true )
{
   inputStartDate.setHint("@string/incorrectDate");
   inputStartDate.setHintTextColor(Color.RED);
    validation = true;
}
else if(checkdateisvalid(inputEndDate.getText().toString()) == true )
{
    inputEndDate.setHint("@string/incorrectDate");
    inputEndDate.setHintTextColor(Color.RED);
    validation = true;
}
else
{
    try
    {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
df.setTimeZone(TimeZone.getTimeZone("GMT"));
        StringBuffer startSB = new StringBuffer(inputStartDate.getText().toString());
        StringBuffer endSB = new StringBuffer(inputEndDate.getText().toString());
        if(!startSB.toString().contains("/") ||!endSB.toString().contains("/") )
        {

                    startSB.insert(2,"/");
            startSB.insert(5,"/");
                    endSB.insert(2,"/");
            endSB.insert(5,"/");

String temp = df.format(startSB.toString());
            start = df.parse(temp);
temp  = df.format(endSB.toString());
            end = df.parse(temp);
        }



    }
    catch(ParseException e)
    {
        e.printStackTrace();
        inputStartDate.setHint("Something went wrong!\n Please try again!");
        inputStartDate.setHintTextColor(Color.RED);
        inputEndDate.setHint("Something went wrong!\n Please try again!");
        inputEndDate.setHintTextColor(Color.RED);

validation = true;
    }


}

if(validation == false) {
    qr.setImage(QRImage);
    qr.setName(inputqrName.getText().toString());

  //  qr.setStart(start);
  //  qr.setExpire(end);

    dialog.dismiss();

    result.setText(qr.getName() + " Start: " +qr.getStart() + " End: " + qr.getExpire()
    + " RAW: " + qr.getRawjson());
}


            }


        });


        dialog.show();
    }


    private boolean checkdateisvalid(String date) {
        if (date.matches("[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                Date d1 = sdf.parse(date);
                return true;
            } catch (ParseException e) {
                return false;
            }
        } else {

            return false;
        }
    }
}













