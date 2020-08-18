package uk.ac.tees.honeycomb.velocity.qrc;

import android.graphics.Bitmap;



public class QRCodeItem {

        private String name;


        private String start;

        private String position;

        private String expire;


        private Bitmap image;
        private String encoded;

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



        public void setName(String sname) { name = sname; }

        public void setRawJson(String raw){rawjson = raw;}

        public void setStart(String date) { start = date; }

        public void setExpire(String date) {expire = date; }



        public void setPosition(String position) { this.position = position; }

        public String getName() { return name; }

        public String getStart() { return start; }

        public String getExpire() { return expire;}



        public String  getRawjson() {return rawjson;}

        public String getPosition() { return position; }


    public String getEncoded() {
        return encoded;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }
}

