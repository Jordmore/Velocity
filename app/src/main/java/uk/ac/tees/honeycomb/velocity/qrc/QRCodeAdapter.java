package uk.ac.tees.honeycomb.velocity.qrc;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import uk.ac.tees.honeycomb.velocity.R;

public class QRCodeAdapter extends RecyclerView.Adapter<QRCodeAdapter.QRCodeViewHolder> {

    private ArrayList<QRCodeItem> qrCodeItems;

public QRCodeAdapter(ArrayList<QRCodeItem> qrCodeItems)
{

    this.qrCodeItems = qrCodeItems;
}

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
    holder.qrCodeImage.setImageBitmap(createImage(qrListData.getRawjson()));



    }

    @Override
    public int getItemCount() {
        return qrCodeItems.size();
    }


    public static class QRCodeViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView start;
        private final TextView expiry;
        private final ImageView qrCodeImage;

        public QRCodeViewHolder(View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.name);
            start = itemView.findViewById(R.id.start);
            expiry = itemView.findViewById(R.id.expiry);
            qrCodeImage= itemView.findViewById(R.id.qrCodeImage);

        }


}



private Bitmap createImage(String compressedBit)
{
    QRGEncoder qrgEncoder = new QRGEncoder( compressedBit, null, QRGContents.Type.TEXT, 150);

    return qrgEncoder.getBitmap();
}

}
