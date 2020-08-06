package uk.ac.tees.honeycomb.velocity.behaviours;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.vision.barcode.Barcode;


import java.io.IOException;
import java.util.List;

import uk.ac.tees.honeycomb.velocity.MainActivity;
import uk.ac.tees.honeycomb.velocity.QRActivity;
import uk.ac.tees.honeycomb.velocity.R;
import uk.ac.tees.honeycomb.velocity.fragments.QRCameraFragment;

import static android.app.Activity.RESULT_OK;

public class QRScanner extends Fragment  implements Behaviour {
    public static final int PERMISSION_REQUEST = 200;

    public static final int REQUEST_CODE = 100;
    private final View parentView;
    TextView result;
    public QRScanner(View parentView) {
        this.parentView = parentView;

        createListeners(parentView);

    }


    private void createListeners(View view) {
        Button openQR = parentView.findViewById(R.id.Scan);
        result =  parentView.findViewById(R.id.textView4);
       openQR.setOnClickListener((view1) -> open(view1));



    }

    private void open(View view)
    {

            Intent intent = new Intent(view.getContext(), QRActivity.class);
            view.getContext().startActivity(intent);
        }

    @Override

   public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            if (data != null) {

                final Barcode barcode = data.getParcelableExtra("barcode");

                result.post(new Runnable() {

                    @Override

                    public void run() {

                        result.setText(barcode.displayValue);

                    }

                });
            }
        }
    }
}







