package uk.ac.tees.honeycomb.velocity.behaviours;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;*/

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import java.util.Date;

import uk.ac.tees.honeycomb.velocity.QRActivity;
import uk.ac.tees.honeycomb.velocity.R;
import uk.ac.tees.honeycomb.velocity.qrc.QRCode;

import static android.app.Activity.RESULT_OK;


public class QRScanner extends AppCompatActivity implements Behaviour {
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
        Button refresh = parentView.findViewById(R.id.refresh);
        result =  parentView.findViewById(R.id.textView4);
       openQR.setOnClickListener((view1) -> open(view1));

        refresh.setOnClickListener((view1) -> recieveData());
    }



    private void open(View view) {

        Intent intent = new Intent(view.getContext(), QRActivity.class);
        view.getContext().startActivity(intent);





    }

    public void recieveData() {
        QRCode qr =QRCode.instance();;
        if (!qr.name.equals("CODE_BLANK")) {
            result.setText(qr.name);
            qr.name ="CODE_BLANK";

            qr.image = null;
        }
        else
        {
//nohing
        }
    }

}








