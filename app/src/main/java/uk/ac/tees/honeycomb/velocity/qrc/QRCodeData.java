package uk.ac.tees.honeycomb.velocity.qrc;




/**
 * Used for one passing a Raw String value from the QRAcivity to the QRLibrary
 * And used for 1 time population of fields to pass data from the pop up to the QRCodeAdapter.
 */
public  class QRCodeData {


    /**
     * The given name for a QR Code. Holds no greater purpose other than to be displayed.
     */
   private String name;


    /**
     * When the user said what date the QR Code was created.
     * Not when the ticket (actually) was created.
     */
   private String start;

    /**
     * When the user said what date the QR Code will expire.
     * Not when the ticket (actually) expires.
     */
    private String expire;


    /**
     * The raw data of the scanned QR Code.
     * Used for generating a Bitmap of the scanned QR Code.
     */
   private String rawjson;

    /**
     * Used for quick verifacation whether data is indeed present for transfer.
     */
   private boolean populated;

    private static QRCodeData qr = new QRCodeData( );

    /**
     * "Default" constructor.
     */
    private QRCodeData()
    {
        this.name = "CODE_BLANK";
        this.start = null;
        this.expire = null;

        this.rawjson = "";
        this.populated = false;
    }

/**
 * gets the only instance for singleton class to transfer data.
 **/
public static QRCodeData instance()
{
    return qr;
}

    /**
     * Setter for name.
     * @param sname
     */

    public void setName(String sname) {
       name = sname;
    }

    /**
     * Setter for raw Json.
     * @param raw
     */
    public void setRawJson(String raw){rawjson = raw;}

    /**
     * setter for the start date.
     * @param date
     */
    public void setStart(String date)
    {
        start = date;
    }

    /**
     * Setter for the expiry date.
     * @param date
     */
    public void setExpire(String date) {expire = date; }

    /**
     * setter for populated.
     * @param populated
     */
    public void setPopulated(boolean populated) {
        this.populated = populated;
    }
    /**
     * getter for name.
     * @return name.
     */
    public String getName() { return name; }

    /**
     * getter for start.
     * @return Start.
     */
    public String getStart() { return start; }

    /**
     * getter for expiry.
     * @return expiry.
     */
    public String getExpire() { return expire;}

    /**
     * getter raw json.
     * @return rawjson. Used for generating bitmaps as we have no reader.
     */
    public String getRawjson() {return rawjson;}

    /**
     * A void method for setting data to default. After the data has been transferred.
     */
    public void discard()
    {
        this.name = "CODE_BLANK";
        this.start = null;
        this.expire = null;
        this.rawjson = "";
        this.populated = false;
    }

    /**
     * returns a binary response on whether data is populated.
     * @return populated.
     */
    public boolean isPopulated() {
        return populated;
    }


}
