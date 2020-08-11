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


import java.text.SimpleDateFormat;
import java.util.Date;

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
        result =  parentView.findViewById(R.id.textView4);
       openQR.setOnClickListener((view1) -> open(view1));

        refresh.setOnClickListener((view1) -> qrDetailsPopUp(view1));
    }



    private void open(View view) {

        Intent intent = new Intent(view.getContext(), QRActivity.class);
        view.getContext().startActivity(intent);





    }

    public void qrDetailsPopUp(View view1) {
        QRCodeData qr =QRCodeData.instance();;

        final Dialog dialog = new Dialog(view1.getContext());
        dialog.setContentView(R.layout.qrcodedetails_inputwindow);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        Button save = (Button) dialog.findViewById(R.id.save);
        ImageView qrImage = (ImageView) dialog.findViewById(R.id.qrimage);
        TextView inputqrName = (TextView) dialog.findViewById(R.id.inputQRName);
        TextView inputStartDate = (TextView) dialog.findViewById(R.id.inputStartDate);
        TextView inputEndDate = (TextView) dialog.findViewById(R.id.inputEndDate);
        // if button is clicked, close the custom dialog

        QRGEncoder qrgEncoder = new QRGEncoder(qr.getRawjson(), null, QRGContents.Type.TEXT,150);
Bitmap QRImage = qrgEncoder.getBitmap();
        qrImage.setImageBitmap(QRImage);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
            }
        }); dialog.show();
        save.setOnClickListener(new View.OnClickListener() {

            

            @Override
            public void onClick(View v) {


                if(inputqrName.getText() == null || inputStartDate.getText() == null ||  inputEndDate.getText() == null){
                    inputqrName.setText("");
                    inputStartDate.setText("");
                    inputEndDate.setText("");

                    inputqrName.setHintTextColor(Color.RED);
                    inputStartDate.setHintTextColor(Color.RED);
                    inputEndDate.setHintTextColor(Color.RED);}
                else if(new Date().after((Date)inputStartDate.getText()))
                {
                    inputStartDate.setText("");
                    inputEndDate.setText("");
                    inputStartDate.setHintTextColor(Color.RED);
                    inputEndDate.setHintTextColor(Color.RED);}

                else
                {
                    qr.setImage(QRImage);
                    qr.setName(inputqrName.getText().toString());

                    qr.setStart((Date)inputStartDate.getText());
                    qr.setExpire((Date)inputEndDate.getText());

                    result.setText(qr.getName());
                }





            }


        }); dialog.show();
    }








        }











