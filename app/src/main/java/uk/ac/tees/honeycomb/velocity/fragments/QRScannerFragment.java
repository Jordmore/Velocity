package uk.ac.tees.honeycomb.velocity.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import uk.ac.tees.honeycomb.velocity.R;
import uk.ac.tees.honeycomb.velocity.behaviours.QRScanner;

public class QRScannerFragment extends Fragment {
   private QRScanner behaviour;

 public QRScannerFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qrlibrary, container,false);
       behaviour = new QRScanner(view);

     return view;
    }

}
