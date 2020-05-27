package com.example.multicast.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.multicast.model.GroupModel;
import com.example.multicast.model.UserGroupModel;
import com.example.multicast.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class CoreAPI {
    private static CoreAPI instance;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    public static CoreAPI getInstance() {
        if (instance == null) {
            instance = new CoreAPI();
        }
        return instance;
    }

    public CoreAPI writeDataGroup(GroupModel groupModel) {
        database.child("groups").child(groupModel.id).setValue(groupModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: !!!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });
        return instance;
    }

    public CoreAPI writeDataUser(UserModel userModel) {
        database.child("users").child(userModel.id).setValue(userModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: !!!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });
        return instance;
    }

    public CoreAPI writeUserGroupModel(UserGroupModel userGroupModel) {
        database.child("user_group_ref").child(UUID.randomUUID().toString()).setValue(userGroupModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: !!!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: " + e.getMessage());
                    }
                });

        return instance;
    }

//    public List<GroupModel> findAllGroupModel() throws InterruptedException {
//        final TaskCompletionSource<Map<String,GroupModel>> tcs = new TaskCompletionSource<>();
//        FirebaseDatabase.getInstance().getReference().child("groups").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Map<String,GroupModel> map = (Map<String,GroupModel>)dataSnapshot.getValue();
//                tcs.setResult(map);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                tcs.setException(databaseError.toException());
//            }
//        });
//        Task<Map<String,GroupModel>> t = tcs.getTask();
//        try {
//            Tasks.await(t);
//        } catch (ExecutionException | InterruptedException e) {
//            t = Tasks.forException(e);
//        }
//
//        if(t.isSuccessful()) {
//            Map<String,GroupModel> result = t.getResult();
//        }
//        return null;
//    }

    // findGroupByUserId;

}
