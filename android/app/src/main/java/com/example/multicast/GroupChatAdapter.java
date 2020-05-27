package com.example.multicast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GroupChatAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<GroupChat> groupChatList;

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
        TextView txtName, txtDescrip;
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
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //gan gia tri
        GroupChat groupChat = groupChatList.get(position);

        holder.txtName.setText(groupChat.getName());
        holder.txtDescrip.setText(groupChat.getDescription());
        holder.imgImage.setImageResource(groupChat.getImage());

        return convertView;
    }
}
