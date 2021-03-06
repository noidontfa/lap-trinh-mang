package com.example.multicast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.multicast.model.GroupModel;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupChatAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<GroupChat> groupChatList;
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

//    private List<GroupModel> groupChatList;


    public GroupChatAdapter(Context context, int layout, List<GroupChat> groupChatList) {
        this.context = context;
        this.layout = layout;
        this.groupChatList = groupChatList;
    }

    @Override
    public int getCount() {
        return groupChatList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgImage;
        TextView txtName, txtDescrip, tvStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();

            //anh xa view
            holder.txtName = (TextView) convertView.findViewById(R.id.textviewName);
            holder.txtDescrip = (TextView) convertView.findViewById(R.id.textviewDescrip);
            holder.imgImage = (ImageView) convertView.findViewById(R.id.imageviewImage);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //gan gia tri

        GroupChat groupChat = groupChatList.get(position);

        holder.txtName.setText(groupChat.getName());
        holder.txtDescrip.setText(groupChat.getIp());
        holder.imgImage.setImageResource(R.drawable.ic_group_black_24dp);
        if(isStatus() == true){
            holder.tvStatus.setText("joined");
        }else {
            holder.tvStatus.setVisibility(View.GONE);
        }

//        Object groupChat = groupChatList.get(position);
//        GroupModel groupModel = new GroupModel((HashMap<String, String>) groupChat);
//        holder.txtName.setText(groupModel.name);
//        holder.txtDescrip.setText(groupModel.ip);
//        holder.imgImage.setImageResource(groupChat.getImage());
        return convertView;
    }
}
