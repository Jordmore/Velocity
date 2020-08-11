package uk.ac.tees.honeycomb.velocity.qrc;

import android.graphics.Bitmap;

import java.util.Date;

public  class QRCodeData {


   private String name;
   private Date start;
    private Date expire;
   private Bitmap image;
   private String rawjson;
    private static QRCodeData qr = new QRCodeData( );
    private QRCodeData()
    {
        this.name = "CODE_BLANK";
        this.start = null;
        this.expire = null;
        this.image= null;
        this.rawjson = "";
    }


public static QRCodeData instance()
{
    return qr;
}



    public void setName(String sname) {
       name = sname;
    }

    public void setRawJson(String raw){rawjson = raw;}

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

    public String getRawjson() {return rawjson;}

}
