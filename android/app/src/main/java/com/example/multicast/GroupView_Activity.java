package com.example.multicast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class GroupView_Activity extends AppCompatActivity {

    ListView lvGroupChat;
    ArrayList<GroupChat> groupChatArrayList;
    GroupChatAdapter groupChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);

//        AnhXa();
        onRenderGroupChat();


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

    private void onRenderGroupChat() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,GroupModel> map = (Map<String, GroupModel>)dataSnapshot.getValue();
                if(map != null) {
                    final List<GroupModel> groups = new ArrayList<>(map.values());
                    groupChatAdapter = new GroupChatAdapter(GroupView_Activity.this,R.layout.line_group_chat, groups);
                    lvGroupChat = (ListView) findViewById(R.id.listviewGroupChat);
                    lvGroupChat.setAdapter(groupChatAdapter);
                    lvGroupChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            GroupModel groupModel = new GroupModel((HashMap<String, String>) ((Object)groups.get(position)));
                            if(!groupModel.isJoined) {
                                Intent intent = new Intent(GroupView_Activity.this,ConversationActivity.class);
                                intent.putExtra("data",groupModel);
                                startActivity(intent);
                            }
                            Toast.makeText(GroupView_Activity.this, "start activity chat group!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    lvGroupChat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                            GroupModel groupModel = new GroupModel((HashMap<String, String>) ((Object)groups.get(position)));
                            if(groupModel.isJoined == false){
                                ConfirmJoinGroup(groupModel);
                            }else {
                                Toast.makeText(GroupView_Activity.this, "You joined this group!", Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        }
                    });

                } else {
                    Log.d("TAG", "onDataChange: Failed");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", "onDataChange: " + databaseError.getMessage());
            }
        });
    }

    private void ConfirmJoinGroup(final GroupModel groupModel){
        AlertDialog.Builder builder = new AlertDialog.Builder(GroupView_Activity.this);

        builder.setTitle("Confirm join group");
        builder.setMessage("Are you sure you want to join the group?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                groupModel.isJoined = true;
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

        // login api - find by username;
        final String username = "username1";
        Query query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("username").equalTo(username);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Map<String,UserModel> map = (Map<String, UserModel>)dataSnapshot.getValue();
                if(map != null) {
                    Log.d("TAG", "onDataChange: Login Success");
                } else {
                    Log.d("TAG", "onDataChange: Failed");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", "onDataChange: " + databaseError.getMessage());
            }
        });


        // findAllGroups
        FirebaseDatabase.getInstance().getReference().child("groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,GroupModel> map = (Map<String, GroupModel>)dataSnapshot.getValue();
                if(map != null) {
                    Log.d("TAG", "onDataChange: has group model");
                } else {
                    Log.d("TAG", "onDataChange: Failed");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", "onDataChange: " + databaseError.getMessage());
            }
        });

                    // findGroupUserByUserId
        final String userId="f808116a-49df-473c-8421-380420a0e99d"; // user 1
        FirebaseDatabase.getInstance().getReference().child("user_group_ref").orderByChild("userId").equalTo(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, UserGroupModel> map = (Map<String, UserGroupModel>) dataSnapshot.getValue();
                        if (map != null) {
                            Log.d("TAG", "onDataChange: has list group id user"); // so sanh voi list group bang dau de xac nhan group nao da dc user join !?
                        } else {
                            Log.d("TAG", "onDataChange: Failed");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("TAG", "onDataChange: " + databaseError.getMessage());
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
