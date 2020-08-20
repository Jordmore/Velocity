package uk.ac.tees.honeycomb.velocity.qrc;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import uk.ac.tees.honeycomb.velocity.R;
import uk.ac.tees.honeycomb.velocity.readandwrite.SRQRCodes;

public class QRCodeAdapter extends RecyclerView.Adapter<QRCodeAdapter.QRCodeViewHolder> {

    /**
     * qrCodeItems an ArrayList of object QRCodeItem which is a representation of a QR Ticket
     */
    private ArrayList<QRCodeItem> qrCodeItems;
    private Context context;

    /**
     * @param qrCodeItems ArrayList passed through the QRLibrary to QRCodeAdapter
     * @param context
     */
    public QRCodeAdapter(ArrayList<QRCodeItem> qrCodeItems, Context context) {
        this.context = context;
        this.qrCodeItems = qrCodeItems;
    }

    /**
     * A method to create views within the recycler view, once created data
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public QRCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qrcode_item, parent, false);
        return new QRCodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QRCodeViewHolder holder, int position) {
        final QRCodeItem qrListData = qrCodeItems.get(position);

        holder.name.setText(qrListData.getName());
        holder.start.setText(qrListData.getStart());
        holder.expiry.setText(qrListData.getExpire());
        holder.qrCodeImage.setImageBitmap(createImage(qrListData.getRawjson(),150));
        holder.qrCodeImage.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      final Dialog dialog = new Dialog(context);
                                                      dialog.setContentView(R.layout.enlargedqrcode);
                                                      ImageView enlargedQR = (ImageView) dialog.findViewById(R.id.enlargedQR);

                                                      enlargedQR.setImageBitmap(createImage(qrListData.getRawjson(),500));
                                                      dialog.show();
                                                  }
                                              });
        holder.removeQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrCodeItems.remove(qrListData);
                notifyDataSetChanged();
                SRQRCodes SR = new SRQRCodes();
                SR.writeJson(qrCodeItems, context);
                Toast.makeText(context, "QR Has Been Removed Forever!", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return qrCodeItems.size();
    }

    /**
     * A method for removing objects from the ArrayList
     * @param object ArrayList object of type QRCodeItems to be removed from adapter ArrayList/
     */
    public void removeItem(@NonNull Object object) {
        qrCodeItems.remove(object);
        notifyDataSetChanged();
    }

    /**
     * Defining of the recycler view item.
     * With initialisation of the items components to be used in populating the fields with data
     * provided by the user when accessing the qr popup details dialog feature.
     */
    public static class QRCodeViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView start;
        private final TextView expiry;
        private final ImageView qrCodeImage;
        private final ImageButton removeQR;

        public QRCodeViewHolder(View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.name);
            start = itemView.findViewById(R.id.start);
            expiry = itemView.findViewById(R.id.expiry);
            qrCodeImage = itemView.findViewById(R.id.qrCodeImage);
            removeQR = itemView.findViewById(R.id.removeQR);

        }
    }


    /**
     *
     * @param raw string value which is the raw scanner output of the scanned QR Code from QRActivity.
     * @return a bitmap created from the raw input of size 150x150.
     */
    private Bitmap createImage(String raw,int dimension) {
        QRGEncoder qrgEncoder = new QRGEncoder(raw, null, QRGContents.Type.TEXT, dimension);

        return qrgEncoder.getBitmap();
    }

}
