package com.example.multicast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupView_Activity extends AppCompatActivity {

    ListView lvGroupChat;
    ArrayList<GroupChat> groupChatArrayList;
    GroupChatAdapter groupChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view_);

        AnhXa();
        groupChatAdapter = new GroupChatAdapter(this, R.layout.line_group_chat, groupChatArrayList);
        lvGroupChat.setAdapter(groupChatAdapter);

        lvGroupChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GroupView_Activity.this, "start activity chat group!", Toast.LENGTH_SHORT).show();
            }
        });


        lvGroupChat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if(groupChatArrayList.get(position).isState() == false){
                    ConfirmJoinGroup(position);
                }else {
                    Toast.makeText(GroupView_Activity.this, "You joined this group!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private void ConfirmJoinGroup(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(GroupView_Activity.this);

        builder.setTitle("Confirm join group");
        builder.setMessage("Are you sure you want to join the group?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                groupChatArrayList.get(position).setState(true);
                Toast.makeText(GroupView_Activity.this, "Already join group!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(GroupView_Activity.this, "You are not join group!", Toast.LENGTH_SHORT).show();
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void AnhXa() {
        lvGroupChat = (ListView) findViewById(R.id.listviewGroupChat);
        groupChatArrayList = new ArrayList<>();

        groupChatArrayList.add(new GroupChat("Nhom 1", "this is Nhom 1", false, R.drawable.ic_group_black_24dp));
        groupChatArrayList.add(new GroupChat("Nhom 2", "this is Nhom 2", false, R.drawable.ic_group_black_24dp));
        groupChatArrayList.add(new GroupChat("Nhom 3", "this is Nhom 3", false, R.drawable.ic_group_black_24dp));
        groupChatArrayList.add(new GroupChat("Nhom 4", "this is Nhom 4", false, R.drawable.ic_group_black_24dp));
        groupChatArrayList.add(new GroupChat("Nhom 5", "this is Nhom 5", false, R.drawable.ic_group_black_24dp));
        groupChatArrayList.add(new GroupChat("Nhom 6", "this is Nhom 6", false, R.drawable.ic_group_black_24dp));
        groupChatArrayList.add(new GroupChat("Nhom 7", "this is Nhom 7", false, R.drawable.ic_group_black_24dp));
        groupChatArrayList.add(new GroupChat("Nhom 8", "this is Nhom 8", false, R.drawable.ic_group_black_24dp));
        groupChatArrayList.add(new GroupChat("Nhom 9", "this is Nhom 9", false, R.drawable.ic_group_black_24dp));

    }
}
