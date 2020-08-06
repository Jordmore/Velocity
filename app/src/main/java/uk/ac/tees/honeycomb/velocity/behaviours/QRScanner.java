package uk.ac.tees.honeycomb.velocity.behaviours;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;*/

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.vision.barcode.Barcode;


import java.io.IOException;
import java.util.List;

import uk.ac.tees.honeycomb.velocity.R;
import uk.ac.tees.honeycomb.velocity.fragments.QRCameraFragment;

public class QRScanner implements Behaviour {

    private final View parentView;

    public QRScanner(View parentView) {
        this.parentView = parentView;
        createListeners(parentView);
    }


    private void createListeners(View view) {
        Button openQR = parentView.findViewById(R.id.Scan);

    }
}







