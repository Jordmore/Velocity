package uk.ac.tees.honeycomb.velocity.qrc;

import android.graphics.Bitmap;

import java.time.LocalDateTime;
import java.util.Date;

public  class QRCodeData {


   private String name;


   private LocalDateTime start;


    private LocalDateTime expire;


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

    public void setStart(LocalDateTime date)
    {
        start = date;
    }

    public void setExpire(LocalDateTime date) {expire = date; }

    public void setImage(Bitmap image) {this.image = image;}

    public String getName() { return name; }

    public LocalDateTime getStart() { return start; }

    public LocalDateTime getExpire() { return expire;}

    public Bitmap getImage() {return image;}

    public String getRawjson() {return rawjson;}

}
