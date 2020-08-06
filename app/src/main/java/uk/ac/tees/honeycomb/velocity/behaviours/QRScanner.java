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

import androidx.fragment.app.Fragment;


import java.util.Date;

import uk.ac.tees.honeycomb.velocity.QRActivity;
import uk.ac.tees.honeycomb.velocity.R;
import uk.ac.tees.honeycomb.velocity.dataTransfer.Parsing;

public class QRScanner extends Fragment  implements Behaviour, Parsing {
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

        private String getdata()
        {
            if(!getArguments().equals("")) {
                String strtext =getArguments().toString();
                return strtext;
            }
            return "";
        }


    @Override
    public String passDataString(String name) {


        return name;
    }

    @Override
    public Date passDataDate(Date date) {

        return date;
    }

    @Override
    public Bitmap passDataImage(Bitmap image) {

        return image;
    }
}







