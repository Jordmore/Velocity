package uk.ac.tees.honeycomb.velocity.qrc;

import android.graphics.Bitmap;


/**
 *  @author Jordon
 * A class for populating a QR Code with data about said qr code.
 * We do not extract infomation from the raw input. we use that raw input to generate the image.
 */
public class QRCodeItem {


    /**
     * The given name for a QR Code. Holds no greater purpose other than to be displayed.
     */
    private String name;


    /**
     * When the user said what date the QR Code was created.
     * Not when the ticket (actually) was created.
     */
    private String start;

        private String position;

    /**
     * When the user said what date the QR Code will expire.
     * Not when the ticket (actually) expires.
     */
    private String expire;


    /**
     * Image of the QR Code
     */
    private Bitmap image;


    /**
     * RawJson used for generating a bitmap.
     * And in future could be used to extract data about the ticket.
     */
        private String rawjson;

        public QRCodeItem()
        {
            this.name = "CODE_BLANK";
            this.start = null;
            this.expire = null;
            this.image= null;

        }


        public QRCodeItem(String name, String start, String expiry,String raw)
        {
            this.name = name;
            this.start = start;
            this.expire = expiry;
            this.rawjson = raw;

        }


    /**
     *
     * @param sname input name to set the class name.
     */
        public void setName(String sname) { name = sname; }


        public String getName() { return name; }

        public String getStart() { return start; }

        public String getExpire() { return expire;}



        public String  getRawjson() {return rawjson;}






}

