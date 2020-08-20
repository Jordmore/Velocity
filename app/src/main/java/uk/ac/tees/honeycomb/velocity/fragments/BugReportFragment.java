package uk.ac.tees.honeycomb.velocity.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import uk.ac.tees.honeycomb.velocity.R;
import uk.ac.tees.honeycomb.velocity.behaviours.BugReport;
import uk.ac.tees.honeycomb.velocity.behaviours.BusStopTimetable;

public class BugReportFragment extends Fragment {
    private BugReport behaviour;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bug_report, container,false);
        behaviour = new BugReport(view);
        return view;
    }
}
