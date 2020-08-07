package uk.ac.tees.honeycomb.velocity;

import android.Manifest;

import android.content.Intent;

import android.content.pm.PackageManager;



import android.os.Bundle;

import android.util.SparseArray;

import android.view.SurfaceHolder;

import android.view.SurfaceView;

import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.CameraSource;

import com.google.android.gms.vision.Detector;

import com.google.android.gms.vision.barcode.Barcode;

import com.google.android.gms.vision.barcode.BarcodeDetector;



import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import uk.ac.tees.honeycomb.velocity.behaviours.QRScanner;
import uk.ac.tees.honeycomb.velocity.qrc.QRCode;


public class QRActivity extends AppCompatActivity implements Serializable {



    SurfaceView cameraView;

    BarcodeDetector barcode;

    CameraSource cameraSource;

    SurfaceHolder holder;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qr);


        cameraView = (SurfaceView) findViewById(R.id.cameraView);

        cameraView.setZOrderMediaOverlay(true);

        holder = cameraView.getHolder();

        barcode = new BarcodeDetector.Builder(this)

                .setBarcodeFormats(Barcode.QR_CODE)

                .build();


        if(!barcode.isOperational()){

            Toast.makeText(getApplicationContext(), "Sorry, Couldn't setup the detector", Toast.LENGTH_LONG).show();

            this.finish();

        }

        cameraSource = new CameraSource.Builder(this, barcode)

                .setFacing(CameraSource.CAMERA_FACING_BACK)

                .setRequestedFps(24)

                .setAutoFocusEnabled(true)

                .setRequestedPreviewSize(1920,1024)

                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override

            public void surfaceCreated(SurfaceHolder holder) {

                try{

                    if(ContextCompat.checkSelfPermission(QRActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

                        cameraSource.start(cameraView.getHolder());

                    }

                }

                catch (IOException e){

                    e.printStackTrace();

                }

            }



            @Override

            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {



            }



            @Override

            public void surfaceDestroyed(SurfaceHolder holder) {



            }

        });

        barcode.setProcessor(new Detector.Processor<Barcode>() {

            @Override

            public void release() {



            }



            @Override

            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes =  detections.getDetectedItems();

                if(barcodes.size() > 0){

                    Intent intent = new Intent();

                    intent.putExtra("barcode", barcodes.valueAt(0));

                    setResult(RESULT_OK, intent);

QRCode qr =QRCode.instance();;

qr.setName("TEST");


                    finish();

                }

            }

        });

    }

}