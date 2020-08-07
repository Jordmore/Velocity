package uk.ac.tees.honeycomb.velocity.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import java.util.Date;

import uk.ac.tees.honeycomb.velocity.QRActivity;
import uk.ac.tees.honeycomb.velocity.R;
import uk.ac.tees.honeycomb.velocity.behaviours.QRScanner;

public class QRLibraryFragment extends Fragment {
   private QRScanner behaviour;

 public QRLibraryFragment(){
     // Required empty public constructor
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
