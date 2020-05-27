package com.example.multicast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GroupView_Activity extends AppCompatActivity {

    ListView lvGroupChat;
    ArrayList<GroupChat> groupChatArrayList;
    GroupChatAdapter groupChatAdapter;
    String textname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);

        getSupportActionBar().show();

        lvGroupChat = (ListView) findViewById(R.id.listviewGroupChat);
        groupChatArrayList = new ArrayList<>();
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
                    Toast.makeText(GroupView_Activity.this, "You joined the group!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
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
                    addNewGroup(groupChatArrayList, textname);
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

    private void addNewGroup(ArrayList<GroupChat> List,String nameGroup) {
        List.add(new GroupChat(nameGroup, "this is group: "+nameGroup, false, R.drawable.ic_group_black_24dp));
    }
}
