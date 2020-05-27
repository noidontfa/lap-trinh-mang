package com.example.multicast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUp_Activity extends AppCompatActivity {
    private TextView tvToSignIn;
    private Button btnSignUp;
    private EditText s1_email, s2_password, s3_username;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;
    User userCurrent;
    private DatabaseReference mData;
    ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_sign_up_);
        tvToSignIn = (TextView)findViewById(R.id.tvToSignIn);
        s1_email=(EditText)findViewById(R.id.txtEmail);
        s2_password=(EditText)findViewById(R.id.txtPass);
        s3_username=(EditText)findViewById(R.id.txtName);
        btnSignUp=(Button)findViewById(R.id.btnSignUp);
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

        userArrayList = new ArrayList<User>();

        tvToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp_Activity.this,SignIn_Activity.class);
                startActivity(i);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    protected  void signUpUser(){
        dialog.setMessage("Registering. Please wait.");
        dialog.show();

        if(s3_username.getText().toString().equals("") || s1_email.getText().toString().equals("") || s2_password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(s1_email.getText().toString(),s2_password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                dialog.hide();
                                FirebaseUser user = mAuth.getCurrentUser();
                                userCurrent = new User(user.getUid(),s2_password.getText().toString(),user.getEmail(),R.drawable.ic_person_black_24dp);
                                userArrayList.add(userCurrent);
                                updateUI(user);
                                finish();
                            } else{
                                dialog.hide();
                                Toast.makeText(getApplicationContext(),"Authentication failed.",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
        }
    }

    private void writeNewUser(){
//        mData = FirebaseDatabase.getInstance().getReference();
//        mData.child(s1_email.getText().toString()).setValue(userCurrent);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child(s3_username.getText().toString()).setValue(userCurrent);
    }

    protected void updateUI(FirebaseUser account){
        if(account != null){
            writeNewUser();
            Toast.makeText(this,"You Signed up successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(SignUp_Activity.this,SignIn_Activity.class));
        }else {
            Toast.makeText(this,"You didn't signed up",Toast.LENGTH_LONG).show();
        }
    }
}
