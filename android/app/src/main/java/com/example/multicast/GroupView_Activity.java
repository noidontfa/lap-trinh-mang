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

import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.multicast.model.GroupModel;
import com.example.multicast.model.UserGroupModel;
import com.example.multicast.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupView_Activity extends AppCompatActivity {

    ListView lvGroupChat;
    ArrayList<GroupChat> groupChatArrayList;
    ArrayList<UserGroup> userGroupArrayList;
    GroupChatAdapter groupChatAdapter;
    String textname;
    String textip;
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
                    final GroupChat group = dataSnapshot.getValue(GroupChat.class);

//                    groupChatAdapter.setStatus(false);
//                    for(int i=0; i<userGroupArrayList.size(); i++){
//                        if(userGroupArrayList.get(i).getUserid() == userCurrent.getUid()) {
//                            if (userGroupArrayList.get(i).getGroupid() == group.getId()) {
//                                groupChatAdapter.setStatus(true);
//                                groupChatAdapter.notifyDataSetChanged();
//                                Toast.makeText(GroupView_Activity.this, "Success", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
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
                lvGroupChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                       Intent intent = new Intent(GroupView_Activity.this, ConversationActivity.class);
                       intent.putExtra("GROUP_IP", groupChatArrayList.get(position).getIp());
                       intent.putExtra("USER_ID", userCurrent.getUid());
                       intent.putExtra("USER_NAME", userCurrent.getEmail());
                       startActivity(intent);
                    }
                });


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

        lvGroupChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                mDatabase.child("user_group_ref").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        userGroup = dataSnapshot.getValue(UserGroup.class);
                        if(userGroup.getUserid() == userCurrent.getUid()){
                            if(userGroup.getGroupid() == groupChatArrayList.get(position).getId()){
                                Toast.makeText(GroupView_Activity.this, "start activity chat group!", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(GroupView_Activity.this, "You don't join the group.", Toast.LENGTH_SHORT).show();
                            }
                        }else {

                        }
                        groupChatAdapter.notifyDataSetChanged();
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
            }
        });


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
//                                    groupChatAdapter.setStatus(true);
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
        final EditText edtIpGroup = (EditText) dialog.findViewById(R.id.editTextIpGroup);
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
                if(edtNameGroup.getText().toString().equals("") || edtIpGroup.getText().toString().equals("")){
                    Toast.makeText(GroupView_Activity.this, "name or ip is empty", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(GroupView_Activity.this, "You created a new group", Toast.LENGTH_SHORT).show();
                    textname = edtNameGroup.getText().toString();
                    textip = edtIpGroup.getText().toString();
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    groupid = mDatabase.push().getKey();
                    addNewGroup(groupChatArrayList, textname, textip);
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
//        AnhXa();
//        onRenderGroupChat();


//        groupChatAdapter = new GroupChatAdapter(this, R.layout.line_group_chat, groupChatArrayList);
//        lvGroupChat.setAdapter(groupChatAdapter);
//
//        lvGroupChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(GroupView_Activity.this, "start activity chat group!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        lvGroupChat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                if(groupChatArrayList.get(position).isState() == false){
//                    ConfirmJoinGroup(position);
//                }else {
//                    Toast.makeText(GroupView_Activity.this, "You joined this group!", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//        });


    }

//    private void onRenderGroupChat() {
//
//        FirebaseDatabase.getInstance().getReference().child("groups").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Map<String,GroupModel> map = (Map<String, GroupModel>)dataSnapshot.getValue();
//                if(map != null) {
//                    final List<GroupModel> groups = new ArrayList<>(map.values());
//                    groupChatAdapter = new GroupChatAdapter(GroupView_Activity.this,R.layout.line_group_chat, groups);
//                    lvGroupChat = (ListView) findViewById(R.id.listviewGroupChat);
//                    lvGroupChat.setAdapter(groupChatAdapter);
//                    lvGroupChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            GroupModel groupModel = new GroupModel((HashMap<String, String>) ((Object)groups.get(position)));
//                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                            if(!groupModel.isJoined) {
//                                Intent intent = new Intent(GroupView_Activity.this,ConversationActivity.class);
//                                intent.putExtra("GROUP_IP",groupModel.ip);
//                                intent.putExtra("USER_ID",user.getUid());
//                                intent.putExtra("USER_NAME",user.getEmail());
//                                startActivity(intent);
//                            }
//                            Toast.makeText(GroupView_Activity.this, "start activity chat group!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    lvGroupChat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                        @Override
//                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                            GroupModel groupModel = new GroupModel((HashMap<String, String>) ((Object)groups.get(position)));
//                            if(groupModel.isJoined == false){
//                                ConfirmJoinGroup(groupModel);
//                            }else {
//                                Toast.makeText(GroupView_Activity.this, "You joined this group!", Toast.LENGTH_SHORT).show();
//                            }
//                            return false;
//                        }
//                    });
//
//                } else {
//                    Log.d("TAG", "onDataChange: Failed");
//                }
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d("TAG", "onDataChange: " + databaseError.getMessage());
//            }
//        });

//    }

//    private void ConfirmJoinGroup(final int){
//        AlertDialog.Builder builder = new AlertDialog.Builder(GroupView_Activity.this);
//
//        builder.setTitle("Confirm join group");
//        builder.setMessage("Are you sure you want to join the group?");
//
//        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialog, int which) {
//
//                // Do nothing but close the dialog
//                groupModel.isJoined = true;
//                Toast.makeText(GroupView_Activity.this, "Already join group!", Toast.LENGTH_SHORT).show();
//                mDatabase = FirebaseDatabase.getInstance().getReference("user_group_ref");
//                mDatabase.child(mDatabase.push().getKey()).setValue(new UserGroup(groupChatArrayList.get(position).getId(), userCurrent.getUid()));
//                groupChatAdapter.setStatus(true);
//                dialog.dismiss();
//            }
//        });
//
//        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                groupChatAdapter.setStatus(false);
//                Toast.makeText(GroupView_Activity.this, "You are not join group!", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//
//        AlertDialog alert = builder.create();
//        alert.show();
//    }

    private void addNewGroup(ArrayList<GroupChat> List,String nameGroup, String ip) {
        groupChat = new GroupChat(groupid, ip, nameGroup);
        List.add(groupChat);
        mDatabase = FirebaseDatabase.getInstance().getReference("groups");
        mDatabase.child(groupid).setValue(groupChat);




//        CoreAPI core = CoreAPI.getInstance();
//        UserModel userModel = new UserModel(UUID.randomUUID().toString(),"User 1","username1","123456");
//        UserModel userModel2 = new UserModel(UUID.randomUUID().toString(),"User 2","username2","123456");
//        UserModel userModel3 = new UserModel(UUID.randomUUID().toString(),"User 3","username3","123456");
//        UserModel userModel4 = new UserModel(UUID.randomUUID().toString(),"User 4","username4","123456");
//        UserModel userModel5 = new UserModel(UUID.randomUUID().toString(),"User 5","username5","123456");
//
//        core.writeDataUser(userModel).writeDataUser(userModel2).writeDataUser(userModel3).writeDataUser(userModel4)
//                .writeDataUser(userModel5);
//


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
