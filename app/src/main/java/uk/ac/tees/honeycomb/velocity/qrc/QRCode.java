package uk.ac.tees.honeycomb.velocity.qrc;

import android.graphics.Bitmap;

import java.util.Date;

public  class QRCode {


   public  String name;
   public  Date start;
    public  Date finish;
   public Bitmap image;
    private static QRCode qr = new QRCode( );
    private QRCode()
    {
        this.name = "CODE_BLANK";
        this.start = null;
        this.finish = null;
        this.image= null;

    }


public static QRCode instance()
{
    return qr;
}


    public java.lang.String getName() {
        return name;
    }
    public void setName(String sname) {
       name = sname;
    }
    public void setStart(Date date)
    {
        start = date;
    }
    public void setFinish(Date date)
    {
        start = date;
    }
}
