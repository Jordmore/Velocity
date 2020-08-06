package uk.ac.tees.honeycomb.velocity.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import uk.ac.tees.honeycomb.velocity.R;
import uk.ac.tees.honeycomb.velocity.behaviours.BusStopTimetable;
import uk.ac.tees.honeycomb.velocity.behaviours.QRScanner;

public class QRCameraFragment extends Fragment {
    SurfaceView cameraPreview;
    public QRCameraFragment()
    {
    // Required empty public constructor
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scanner, container,false);

cameraPreview = view.findViewById(R.id.cameraPreview);
createCameraSource();
        return view;
    }
    private void createCameraSource()
    {
        BarcodeDetector barcodedetect = new BarcodeDetector.Builder(getActivity()).build();
final CameraSource camSource = new CameraSource.Builder(getActivity(),barcodedetect)
        .setAutoFocusEnabled(true)
        .setRequestedPreviewSize(1600,1024)
        .build();
cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camSource.start(cameraPreview.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
camSource.stop();
    }
});

barcodedetect.setProcessor(new Detector.Processor<Barcode>() {
    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections<Barcode> detections) {
final SparseArray<Barcode> barcodes=detections.getDetectedItems();
if(barcodes.size()>0){

    Intent intent = new Intent();
    intent.putExtra("barcode",barcodes.valueAt(0));

}
    }
});
    }




}
