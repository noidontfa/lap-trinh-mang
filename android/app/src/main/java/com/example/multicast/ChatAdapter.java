package com.example.multicast;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multicast.model.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.RecylerViewHolder> {
    private Context context;
    private ArrayList<Message> messages;
    private String mId;

    public ChatAdapter(Context context, ArrayList<Message> messages, String mId) {
        this.context = context;
        this.messages = messages;
        this.mId = mId;
    }

    @NonNull
    @Override
    public RecylerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.simple_list_row,parent,false);
        return new RecylerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerViewHolder holder, int position) {
        Message message = messages.get(position);
        if(message.getId().equals(mId)) {
            holder.layoutSender.setVisibility(View.VISIBLE);
            holder.layoutReceiver.setVisibility(View.GONE);
            if(message.getImage() != null) {
                holder.imgSender.setVisibility(View.VISIBLE);
                Bitmap bmp = BitmapFactory.decodeByteArray(message.getImage(), 0, message.getImage().length);
                holder.imgSender.setImageBitmap(bmp);
            } else {
                holder.imgSender.setVisibility(View.GONE);
                holder.btnSave.setVisibility(View.GONE);
            }
            if(!message.getMessage().equals("")) {
                holder.txtSender.setVisibility(View.VISIBLE);
                holder.txtSender.setText(message.getMessage());
            } else {
                holder.txtSender.setVisibility(View.GONE);
            }
        } else {
            holder.layoutReceiver.setVisibility(View.VISIBLE);
            holder.layoutSender.setVisibility(View.GONE);
            holder.txtReceiverName.setVisibility(View.VISIBLE);
            holder.txtReceiverName.setText(message.getName());
            if(message.getImage() != null) {
                holder.btnSave.setVisibility(View.VISIBLE);
                holder.imgReceiver.setVisibility(View.VISIBLE);
                final Bitmap bmp = BitmapFactory.decodeByteArray(message.getImage(), 0, message.getImage().length);
                holder.imgReceiver.setImageBitmap(bmp);
                holder.btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(bmp != null) {
                            // Assume block needs to be inside a Try/Catch block.
                            String path = Environment.getExternalStorageDirectory().toString();
                            OutputStream fOut = null;
                            File file = new File(path, "socketapp"+Calendar.getInstance().getTimeInMillis()+".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
                            try {
                                fOut = new FileOutputStream(file);
                                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                                fOut.flush(); // Not really required
                                fOut.close(); // do not forget to close the stream
                                MediaStore.Images.Media.insertImage(context.getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
                                Toast.makeText(context,"Complete",Toast.LENGTH_SHORT).show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {

                            }
                        }
                    }
                });
            } else {
                holder.imgReceiver.setVisibility(View.GONE);
            }
            if(!message.getMessage().equals("")) {
                holder.txtReceiver.setVisibility(View.VISIBLE);
                holder.txtReceiver.setText(message.getMessage());
            } else {
                holder.txtReceiver.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class RecylerViewHolder extends RecyclerView.ViewHolder {
        private TextView txtReceiverName, txtSenderName, txtReceiver, txtSender;
        private ImageView imgReceiver, imgSender;
        private ImageButton btnSave;
        private ConstraintLayout layoutReceiver, layoutSender;

        public RecylerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtReceiver = (TextView) itemView.findViewById(R.id.main_txt_receiver);
            txtSender = (TextView) itemView.findViewById(R.id.main_txt_sender);
            txtReceiverName = (TextView) itemView.findViewById(R.id.main_txt_name_receiver);
            txtSenderName = (TextView) itemView.findViewById(R.id.main_txt_name_sender);
            imgReceiver = (ImageView) itemView.findViewById(R.id.main_img_receiver);
            imgSender = (ImageView) itemView.findViewById(R.id.main_img_sender);
            btnSave = (ImageButton) itemView.findViewById(R.id.main_btn_save);
            layoutReceiver = (ConstraintLayout) itemView.findViewById(R.id.main_layout_receiver);
            layoutSender = (ConstraintLayout) itemView.findViewById(R.id.main_layout_sender);
        }
    }
}
