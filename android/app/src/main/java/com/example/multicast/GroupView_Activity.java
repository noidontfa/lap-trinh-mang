package com.example.multicast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GroupView_Activity extends AppCompatActivity {

    ListView lvGroupChat;
    ArrayList<GroupChat> groupChatArrayList;
    ArrayList<UserGroup> userGroupArrayList;
    GroupChatAdapter groupChatAdapter;
    String textname;
    FirebaseUser userCurrent;
    UserGroup userGroup;
    GroupChat groupChat;
    String groupid;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);
        getSupportActionBar().show();

        userCurrent = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lvGroupChat = findViewById(R.id.listviewGroupChat);
        groupChatArrayList = new ArrayList<>();
        userGroupArrayList = new ArrayList<>();
        groupChatAdapter = new GroupChatAdapter(this, R.layout.line_group_chat, groupChatArrayList);
        lvGroupChat.setAdapter(groupChatAdapter);

        mDatabase.child("user_group_ref").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserGroup usergroup = dataSnapshot.getValue(UserGroup.class);
                userGroupArrayList.add(new UserGroup(usergroup.getGroupid(), usergroup.getUserid()));
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.child("groups").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    GroupChat group = dataSnapshot.getValue(GroupChat.class);

//                    groupChatAdapter.setStatus(false);
                    for(int i=0; i<userGroupArrayList.size(); i++){
                        if(userGroupArrayList.get(i).getUserid() == userCurrent.getUid()) {
                            if (userGroupArrayList.get(i).getGroupid() == group.getId()) {
                                groupChatAdapter.setStatus(true);
                                groupChatAdapter.notifyDataSetChanged();
                                Toast.makeText(GroupView_Activity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    groupChatArrayList.add(new GroupChat(group.getId(), group.getIp(), group.getName()));
                    groupChatAdapter.notifyDataSetChanged();
//                    for(int i=0; i<userGroupArrayList.size(); i++){
//                        if(userGroupArrayList.get(i).getUserid() == userCurrent.getUid()) {
//                            if (userGroupArrayList.get(i).getGroupid() == group.getId()) {
//                                groupChatAdapter.setStatus(true);
//                                groupChatAdapter.notifyDataSetChanged();
//                            } else {
//                                groupChatAdapter.setStatus(false);
//                                groupChatAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    }


            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        lvGroupChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                mDatabase.child("user_group_ref").addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        userGroup = dataSnapshot.getValue(UserGroup.class);
//                        if(userGroup.getUserid() == userCurrent.getUid()){
//                            if(userGroup.getGroupid() == groupChatArrayList.get(position).getId()){
//                                Toast.makeText(GroupView_Activity.this, "start activity chat group!", Toast.LENGTH_SHORT).show();
//                            } else{
//                                Toast.makeText(GroupView_Activity.this, "You don't join the group.", Toast.LENGTH_SHORT).show();
//                            }
//                        }else {
//
//                        }
//                        groupChatAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });
//
//
//        lvGroupChat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                mDatabase.child("user_group_ref").addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                            userGroup = dataSnapshot.getValue(UserGroup.class);
//                            if(userGroup.getUserid() == userCurrent.getUid()){
//                                if(userGroup.getGroupid() == groupChatArrayList.get(position).getId()){
//                                    Toast.makeText(GroupView_Activity.this, "You joined the group!", Toast.LENGTH_SHORT).show();
//                                    groupChatAdapter.JoinGroup(true);
//                                } else{
//                                    ConfirmJoinGroup(position);
//                                }
//                            }
//                            groupChatAdapter.notifyDataSetChanged();
//                        }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//                return false;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_group_view, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuCreGroup:
                DialogCreateGroup();
                break;
            case R.id.menuExit:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(GroupView_Activity.this, MainActivity.class));
                Toast.makeText(GroupView_Activity.this, "You signed out.", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogCreateGroup(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_create_group);

        final EditText edtNameGroup = (EditText) dialog.findViewById(R.id.editTextNameGroup);
        Button btnOk = (Button) dialog.findViewById(R.id.buttonOk);
        Button btnCancel = (Button) dialog.findViewById(R.id.buttonCancel);

        //set size of dialog
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width)/7, (2 * height)/5);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtNameGroup.getText().toString().equals("")){
                    Toast.makeText(GroupView_Activity.this, "your name group be empty", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(GroupView_Activity.this, "You created a new group", Toast.LENGTH_SHORT).show();
                    textname = edtNameGroup.getText().toString();
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    groupid = mDatabase.push().getKey();
                    addNewGroup(groupChatArrayList, textname);
                    mDatabase = FirebaseDatabase.getInstance().getReference("user_group_ref");
                    mDatabase.child(groupChat.getId()).setValue(new UserGroup(userCurrent.getUid(), groupChat.getId()));
                }
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void ConfirmJoinGroup(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(GroupView_Activity.this);

        builder.setTitle("Confirm join group");
        builder.setMessage("Are you sure you want to join the group?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(GroupView_Activity.this, "Already join group!", Toast.LENGTH_SHORT).show();
                mDatabase = FirebaseDatabase.getInstance().getReference("user_group_ref");
                mDatabase.child(mDatabase.push().getKey()).setValue(new UserGroup(groupChatArrayList.get(position).getId(), userCurrent.getUid()));
                groupChatAdapter.setStatus(true);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                groupChatAdapter.setStatus(false);
                Toast.makeText(GroupView_Activity.this, "You are not join group!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void addNewGroup(ArrayList<GroupChat> List,String nameGroup) {
        groupChat = new GroupChat(groupid, "this is ip", nameGroup);
        List.add(groupChat);
        mDatabase = FirebaseDatabase.getInstance().getReference("groups");
        mDatabase.child(groupid).setValue(groupChat);
    }
}
