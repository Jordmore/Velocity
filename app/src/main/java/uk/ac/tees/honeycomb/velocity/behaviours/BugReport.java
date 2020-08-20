package uk.ac.tees.honeycomb.velocity.behaviours;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Calendar;


import uk.ac.tees.honeycomb.velocity.R;

public class BugReport extends AppCompatActivity implements Behaviour {

    private View parentView;
Context context;

    public BugReport(View parentView,Context c) {
        this.parentView = parentView;
        context =c;
        createListeners(parentView);

    }


    private void createListeners(View view) {
        Button sendEmail = parentView.findViewById(R.id.sendbug);
        sendEmail.setOnClickListener((view1) -> sendReport());

    }


    private void sendReport()
    {

        EditText nameOfReporter= parentView.findViewById(R.id.inputBugReportName);
        EditText report= parentView.findViewById(R.id.inputBugReort);

        String velocity = "VelocityMaintenance@outlook.com";

        Intent sendBugReportIntent = new Intent(Intent.ACTION_SEND);

        sendBugReportIntent.setType("message/rfc822");
        sendBugReportIntent.putExtra(Intent.EXTRA_EMAIL  , new String[] {velocity});
        sendBugReportIntent.putExtra(Intent.EXTRA_SUBJECT, "Bug Report: " + Calendar.getInstance().getTime().toString() + "," + nameOfReporter.getText().toString());
        sendBugReportIntent.putExtra(Intent.EXTRA_TEXT   , report.getText().toString());



        context.startActivity(Intent.createChooser( sendBugReportIntent, "Select Your Proffered Email Service"));
/**
 * The context was null which made sending an email via an intent very difficult.
 */



    }
}
