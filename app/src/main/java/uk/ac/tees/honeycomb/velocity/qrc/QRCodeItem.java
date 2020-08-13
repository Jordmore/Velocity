package uk.ac.tees.honeycomb.velocity.qrc;

import android.graphics.Bitmap;

import org.json.JSONObject;

public class QRCodeItem {

        private String name;


        private String start;

        private String position;

        private String expire;


        private Bitmap image;
        private JSONObject rawjson;

        public QRCodeItem()
        {
            this.name = "CODE_BLANK";
            this.start = null;
            this.expire = null;
            this.image= null;

        }


        public QRCodeItem(String name, String start, String expiry, Bitmap image)
        {
            this.name = name;
            this.start = start;
            this.expire = expiry;
            this.image= image;
        }



        public void setName(String sname) { name = sname; }

        public void setRawJson(JSONObject raw){rawjson = raw;}

        public void setStart(String date) { start = date; }

        public void setExpire(String date) {expire = date; }

        public void setImage(Bitmap image) {this.image = image;}

        public void setPosition(String position) { this.position = position; }

        public String getName() { return name; }

        public String getStart() { return start; }

        public String getExpire() { return expire;}

        public Bitmap getImage() {return image;}

        public JSONObject  getRawjson() {return rawjson;}

        public String getPosition() { return position; }


}

