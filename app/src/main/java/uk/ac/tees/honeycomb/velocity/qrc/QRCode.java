package uk.ac.tees.honeycomb.velocity.qrc;

import android.graphics.Bitmap;

import java.util.Date;

public  class QRCode {


   protected  String name;
   protected  Date start;
    protected  Date expire;
   protected Bitmap image;
    private static QRCode qr = new QRCode( );
    private QRCode()
    {
        this.name = "CODE_BLANK";
        this.start = null;
        this.expire = null;
        this.image= null;

    }


public static QRCode instance()
{
    return qr;
}



    public void setName(String sname) {
       name = sname;
    }

    public void setStart(Date date)
    {
        start = date;
    }

    public void setExpire(Date date) {expire = date; }

    public void setImage(Bitmap image) {this.image = image;}

    public String getName() { return name; }

    public Date getStart() { return start; }

    public Date getExpire() { return expire;}

    public Bitmap getImage() {return image;}
}
