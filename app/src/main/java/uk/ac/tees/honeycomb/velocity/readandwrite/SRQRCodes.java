package uk.ac.tees.honeycomb.velocity.readandwrite;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import uk.ac.tees.honeycomb.velocity.qrc.QRCodeItem;

/**
 *  @author Jordon
 */
public class SRQRCodes {

    public void writeJson(ArrayList qr1, Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("QRLibrary", 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();

        String serializedObject = gson.toJson(qr1);

        sharedPreferencesEditor.putString("serial", serializedObject);
        sharedPreferencesEditor.apply();
        sharedPreferencesEditor.commit();


    }

    public ArrayList readJson(Context context) {


        SharedPreferences sharedPreferences = context.getSharedPreferences("QRLibrary", 0);
        if (sharedPreferences.contains("serial")) {
            Type type = new TypeToken<ArrayList<QRCodeItem>>() {
            }.getType();
            //Type type = new TypeToken<List<Student>>(){}.getType();
            final Gson gson = new Gson();


            return gson.fromJson(sharedPreferences.getString("serial", ""), type);
        }
        return null;
    }
}
