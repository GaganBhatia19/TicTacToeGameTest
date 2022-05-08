package com.example.tictactoegametest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MultiplayerScreen extends AppCompatActivity {

    private Button hostBtn, joinBtn;
    private EditText userNameET, roomNameET, roomPassET;

    private static String ROOM_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_screen);

        hostBtn = findViewById(R.id.hostBtnMP);
        joinBtn = findViewById(R.id.joinBtnMP);
        userNameET = findViewById(R.id.userNameEditTextMP);
        roomNameET = findViewById(R.id.roomNameEditTextMP);
        roomPassET = findViewById(R.id.roomPasswordMP);

        hostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    /*
                String username = userNameET.getText().toString();
                String roomName = roomNameET.getText().toString();
                String roomPassword = roomPassET.getText().toString();
                
                if(username.isEmpty() || roomName.isEmpty() || roomPassword.isEmpty()){
                    Toast.makeText(MultiplayerScreen.this, "Oops! something missing", Toast.LENGTH_SHORT).show();
                } else {
//                    */
                    String roomUniqueName = roomName.concat(roomPassword);
//                    ROOM_NAME = roomUniqueName;
                    ///////////////////////////////////////
//                    String roomUniqueName = "testRoom";
//                    String username = "host";
                    ///////////////////////////////////////
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("GameRooms");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.child(roomUniqueName).exists()) {
                                databaseReference.child(roomUniqueName).child("player1_host").setValue(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(MultiplayerScreen.this, "Room created successfully", Toast.LENGTH_SHORT).show();
                                        databaseReference.child(roomUniqueName).child("player_count").setValue(1);
                                        DatabaseReference playerCountReference = databaseReference.child(roomUniqueName).child("player_count");

                                        playerCountReference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                            Log.d("playerCountDataChange",snapshot.getValue(Integer.class).toString());
                                                if(snapshot.getValue(Integer.class)==2){
                                                    // setting random number from host to database
                                                    int randomNumber = new Random().nextInt(2)+1;
                                                    databaseReference.child(roomUniqueName).child("current_player_turn").setValue(randomNumber==1?"HOST":"JOINED");
                                                    // do next
                                                    Intent intent = new Intent(MultiplayerScreen.this,MultiplayerGamePlayScreen.class);
                                                    intent.putExtra("room_name_key",roomUniqueName);
                                                    intent.putExtra("player_role","HOST");
                                                    intent.putExtra("random_number",randomNumber);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    ProgressDialog progressDialog = new ProgressDialog(MultiplayerScreen.this);
                                                    progressDialog.setMessage("Please wait while the second player joins game");
                                                    progressDialog.setCancelable(false);
                                                    if(!isFinishing()) progressDialog.show();
                                                    else progressDialog.dismiss();
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {}
                                        });
                                    }
                                });
                            } else {
                                Toast.makeText(MultiplayerScreen.this, "Room already exist, try changing name", Toast.LENGTH_SHORT).show();
                            }
                        }
                        // check if the room already exist/// if already exist then message user to change the name
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MultiplayerScreen.this, "Error occured while creating room", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } //
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    /*
                String username = userNameET.getText().toString();
                String roomName = roomNameET.getText().toString();
                String roomPassword = roomPassET.getText().toString();

                if(username.isEmpty() || roomName.isEmpty()|| roomPassword.isEmpty()){
                    Toast.makeText(MultiplayerScreen.this, "Oops! something missing", Toast.LENGTH_SHORT).show();
                } else {
//                     */
                    String roomUniqueName = roomName.concat(roomPassword);
//                    String roomUniqueName = "testRoom"; //
//                    String username = "joiner"; //
                    DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference("GameRooms");
                    rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Log.d("databaseSnapshot", "onDataChange: "+(snapshot.getValue()!=null));
                            if(snapshot.child(roomUniqueName).getValue()!=null){
                                if(snapshot.child(roomUniqueName).child("player_count").getValue(Integer.class)==1){
                                    // if the particular reference is not null the add the joined person into room
                                    rootReference.child(roomUniqueName).child("player2_joined").setValue(username);
                                    rootReference.child(roomUniqueName).child("player_count").setValue(2); // setting player_count = 2

                                    Toast.makeText(MultiplayerScreen.this, "Room joined successfully", Toast.LENGTH_SHORT).show();
                                    // starting the new screen
                                    Intent intent = new Intent(MultiplayerScreen.this,MultiplayerGamePlayScreen.class);
                                    intent.putExtra("room_name_key",roomUniqueName);
                                    intent.putExtra("player_role","JOINED");
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MultiplayerScreen.this, "Room is full", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MultiplayerScreen.this, "Room doesn't exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            } //
        });
    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GameRooms").child(ROOM_NAME);
        if(reference!=null){
            reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(MultiplayerScreen.this, "Room removed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MultiplayerScreen.this, "Room not removed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
 */
}