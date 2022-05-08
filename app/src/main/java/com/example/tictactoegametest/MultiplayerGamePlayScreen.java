package com.example.tictactoegametest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MultiplayerGamePlayScreen extends AppCompatActivity {

    private ImageButton imageButton1,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6,imageButton7,imageButton8,imageButton9;
    private TextView playerTurnTextView, player1NameTextView, player2NameTextView;
    private LinearLayout player1LLayout, player2LLayout;

    private String player1Name = "", player2Name = "";
    private String roomName;

    private String[][]localBoardState;
    private ArrayList<ArrayList<ImageButton>> imageButtonArrayList;

    private int currentTurn;
    private String playerRole;
    private boolean isHost;
    private boolean isJoined;
    private String current_player_turn;

    private DatabaseReference dbBoardState,roomDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_game_play_screen);

        imageButton1 = findViewById(R.id.imageButton1MP);
        imageButton2 = findViewById(R.id.imageButton2MP);
        imageButton3 = findViewById(R.id.imageButton3MP);
        imageButton4 = findViewById(R.id.imageButton4MP);
        imageButton5 = findViewById(R.id.imageButton5MP);
        imageButton6 = findViewById(R.id.imageButton6MP);
        imageButton7 = findViewById(R.id.imageButton7MP);
        imageButton8 = findViewById(R.id.imageButton8MP);
        imageButton9 = findViewById(R.id.imageButton9MP);

        playerTurnTextView = findViewById(R.id.playerTurnTextViewMP);
        player1NameTextView = findViewById(R.id.player1NameTextViewMP);
        player2NameTextView = findViewById(R.id.player2NameTextViewMP);

        player1LLayout = findViewById(R.id.player1LLayoutMP);
        player2LLayout = findViewById(R.id.player2LLayoutMP);

        imageButtonArrayList = new ArrayList<>();

        ArrayList<ImageButton> row0List,row1List,row2List;

        row0List = new ArrayList<>();
        row1List = new ArrayList<>();
        row2List = new ArrayList<>();

        row0List.add(imageButton1);
        row0List.add(imageButton2);
        row0List.add(imageButton3);

        row1List.add(imageButton4);
        row1List.add(imageButton5);
        row1List.add(imageButton6);

        row2List.add(imageButton7);
        row2List.add(imageButton8);
        row2List.add(imageButton9);

        imageButtonArrayList.add(row0List);
        imageButtonArrayList.add(row1List);
        imageButtonArrayList.add(row2List);

        roomName = getIntent().getStringExtra("room_name_key"); // the name of the room

        roomDatabaseReference = FirebaseDatabase.getInstance().getReference("GameRooms").child(roomName);

        currentTurn = getIntent().getIntExtra("random_number",1);


        localBoardState = new String[][]{{"","",""}, {"","",""}, {"","",""}};
        // creating game objects inside room
        DatabaseReference gameObjectsDatabaseReference = roomDatabaseReference.child("GameObjects");
        dbBoardState = gameObjectsDatabaseReference.child("board_state");

        updateDatabaseBoardState(localBoardState); // when called this will insert the localBoardState values to the realtime-database
//        makeButtonsFunc(); // will set on click listener to every button

        playerRole = getIntent().getStringExtra("player_role");
//
//        isHost = playerRole.equals("HOST");
//        isJoined = playerRole.equals("JOINED");

        roomDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                player1Name = snapshot.child("player1_host").getValue(String.class);
                player2Name = snapshot.child("player2_joined").getValue(String.class);

                player1NameTextView.setText(player1Name);
                player2NameTextView.setText(player2Name);
//                currentTurn = snapshot.child("current_user_random_number").getValue(Integer.class);
                current_player_turn = snapshot.child("current_player_turn").getValue(String.class);

                if(current_player_turn.equals("HOST")){
                    playerTurnTextView.setText(player1Name+" 's turn");
                    player1LLayout.setBackground(getDrawable(R.drawable.player_card_game_screen));
                } else if(current_player_turn.equals("JOINED")) {
                    playerTurnTextView.setText(player2Name+" 's turn");
                    player2LLayout.setBackground(getDrawable(R.drawable.player_card_game_screen));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton1);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton2);
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton3);
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton4);
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton5);
            }
        });
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton6);
            }
        });
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton7);
            }
        });
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton8);
            }
        });
        imageButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton9);
            }
        });

        roomDatabaseReference.child("current_player_turn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                current_player_turn = snapshot.child("current_player_turn").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int coordinateI,coordinateJ;
    private String[]coordinateIJ;

    int counter = 0;
    private void playerClicks(ImageButton imageButton){

        coordinateIJ = imageButton.getTag().toString().split("[,]");
        coordinateI = Integer.parseInt(coordinateIJ[0]);
        coordinateJ = Integer.parseInt(coordinateIJ[1]);

        roomDatabaseReference.child("event_log").child(String.valueOf(counter++)).setValue(current_player_turn+" clicks button "+coordinateIJ[0]+","+coordinateIJ[1]);

        roomDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("GameObjects").child("board_state").child(coordinateIJ[0]+","+coordinateIJ[1]).toString().isEmpty()) {
                    if (current_player_turn.equals(playerRole) && snapshot.child("current_player_turn").getValue(String.class).equals(playerRole)) {
                        roomDatabaseReference.child("GameObjects").child("board_state").child(coordinateIJ[0] + "," + coordinateIJ[1]).setValue(current_player_turn);
                        imageButtonArrayList.get(coordinateI).get(coordinateJ).setImageResource(R.drawable.round_xml_cross);
                        current_player_turn = "JOINED";
                        roomDatabaseReference.child("current_player_turn").setValue(current_player_turn);
                        player1LLayout.setBackground(null);
                        player2LLayout.setBackground(getDrawable(R.drawable.player_card_game_screen));
                    } else {
                        roomDatabaseReference.child("GameObjects").child("board_state").child(coordinateIJ[0] + "," + coordinateIJ[1]).setValue(current_player_turn);
                        imageButtonArrayList.get(coordinateI).get(coordinateJ).setImageResource(R.drawable.round_circle_centre_hole);
                        current_player_turn = "HOST";
                        roomDatabaseReference.child("current_player_turn").setValue(current_player_turn);
                        player2LLayout.setBackground(null);
                        player1LLayout.setBackground(getDrawable(R.drawable.player_card_game_screen));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*
        if(localBoardState[coordinateI][coordinateJ]==0) {
            localBoardState[coordinateI][coordinateJ] = currentTurn;
            updateDatabaseBoardState(localBoardState);
            if(currentTurn==2){
                imageButton.setImageResource(R.drawable.round_circle_centre_hole);
                currentTurn = 1;
                roomDatabaseReference.child("current_turn").setValue(currentTurn);
                playerTurnTextView.setText(player1Name+" 's turn");
                player1LLayout.setBackground(getDrawable(R.drawable.player_card_game_screen));
                player2LLayout.setBackground(null);
                updateDatabaseBoardState(localBoardState);
            } else {
                imageButton.setImageResource(R.drawable.round_xml_cross);
                currentTurn = 2;
                roomDatabaseReference.child("current_turn").setValue(currentTurn);
                playerTurnTextView.setText(player2Name+" 's turn");
                player2LLayout.setBackground(getDrawable(R.drawable.player_card_game_screen));
                player1LLayout.setBackground(null);
                updateDatabaseBoardState(localBoardState);
            }
        }

         */
    }

    private void updateLLayout(){

    }

    private void updateDatabaseBoardState(String[][]currentBoardState){
        for(int i=0;i<currentBoardState.length;i++){
            for (int j=0;j<currentBoardState[i].length;j++){
                dbBoardState.child(i+","+j).setValue(currentBoardState[i][j]);
                //updating on UI - update it later
                ImageView imv = imageButtonArrayList.get(i).get(j);
                if(currentBoardState[i][j].equals("HOST")){
                    imv.setImageResource(R.drawable.round_xml_cross);
                } else if(currentBoardState[i][j].equals("JOINED")){
                    imv.setImageResource(R.drawable.round_circle_centre_hole);
                }
            }
        }
    }

    private void makeButtonsFunc(){
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton1);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton2);
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton3);
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton4);
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton5);
            }
        });
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton6);
            }
        });
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton7);
            }
        });
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton8);
            }
        });
        imageButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerClicks(imageButton9);
            }
        });
    }

    private void makeAllButtonEnable(boolean b){
        for(ArrayList<ImageButton>imgBtns:imageButtonArrayList){
            for(ImageButton imgB:imgBtns){
                imgB.setEnabled(b);
            }
        }
    }
}