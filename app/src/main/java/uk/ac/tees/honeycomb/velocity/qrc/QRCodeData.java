package uk.ac.tees.honeycomb.velocity.qrc;

import android.graphics.Bitmap;



public  class QRCodeData {


   private String name;


   private String start;


    private String expire;


   private Bitmap image;
   private String rawjson;
   private boolean populated;
    private static QRCodeData qr = new QRCodeData( );
    private QRCodeData()
    {
        this.name = "CODE_BLANK";
        this.start = null;
        this.expire = null;
        this.image= null;
        this.rawjson = "";
        this.populated = false;
    }


public static QRCodeData instance()
{
    return qr;
}



    public void setName(String sname) {
       name = sname;
    }

    public void setRawJson(String raw){rawjson = raw;}

    public void setStart(String date)
    {
        start = date;
    }

    public void setExpire(String date) {expire = date; }

    public void setImage(Bitmap image) {this.image = image;}

    public String getName() { return name; }

    public String getStart() { return start; }

    public String getExpire() { return expire;}

    public Bitmap getImage() {return image;}

    public String getRawjson() {return rawjson;}

    public void discard()
    {
        this.name = "CODE_BLANK";
        this.start = null;
        this.expire = null;
        this.image= null;
        this.rawjson = "";
        this.populated = false;
    }

    public boolean isPopulated() {
        return populated;
    }

    public void setPopulated(boolean populated) {
        this.populated = populated;
    }
}
