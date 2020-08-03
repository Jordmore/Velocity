package uk.ac.tees.honeycomb.velocity.behaviours;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import uk.ac.tees.honeycomb.velocity.R;

public class QRScanner implements Behaviour {
    private SurfaceView cameraView;
   private  TextView barcodeInfo;
    private final View parentView;

    public QRScanner(View parentView){
        this.parentView = parentView;
        createListeners(parentView);
    }


    private void createListeners(View view) {


      cameraView = parentView.findViewById(R.id.camera_view);
        barcodeInfo = parentView.findViewById(R.id.code_info);
    }



    private Context getViewContext(){
        return parentView.getContext();
    }

    private void camera()
    {
        BarcodeDetector barcodeDetector =
                new BarcodeDetector.Builder(getViewContext())
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            barcodeInfo.setText(    // Update the TextView
                                    barcodes.valueAt(0).displayValue
                            );
                        }
                    });
                    CameraSource cameraSource = new CameraSource
                            .Builder(getViewContext(), barcodeDetector)
                            .setRequestedPreviewSize(640, 480)
                            .build();
                    cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                        @Override
                        public void surfaceCreated(SurfaceHolder holder) {
                            try {
                                cameraSource.start(cameraView.getHolder());
                            } catch (IOException ie) {
                                Log.e("CAMERA SOURCE", ie.getMessage());
                            }
                        }

                        @Override
                        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                        }

                        @Override
                        public void surfaceDestroyed(SurfaceHolder holder) {
                            cameraSource.stop();
                        }
                    });
                }
            }
        });
    }
}

