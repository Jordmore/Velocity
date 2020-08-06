package uk.ac.tees.honeycomb.velocity.dataTransfer;

import android.graphics.Bitmap;

import java.util.Date;

public interface Parsing {
    public String passDataString(String name);
    public Date passDataDate(Date date);
    public Bitmap passDataImage(Bitmap image);


}
