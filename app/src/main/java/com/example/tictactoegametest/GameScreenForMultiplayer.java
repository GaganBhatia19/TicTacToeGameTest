package com.example.tictactoegametest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GameScreenForMultiplayer extends AppCompatActivity {

    private final static int ROW_SIDE_VICTORY = 0;
    private final static int COLUMN_SIDE_VICTORY = 1;
    private final static int DIAGONAL048_SIDE_VICTORY = 2;
    private final static int DIAGONAL246_SIDE_VICTORY = 3;


    private ImageButton imageButton1,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6,imageButton7,imageButton8,imageButton9;
    private TextView playerTurnTextView, player1NameTextView, player2NameTextView,winnerDisplayName;
    private LinearLayout player1LLayout, player2LLayout;
    private AppCompatButton restartButton;

    private ArrayList<ArrayList<ImageButton>> imageButtonArrayList;


    private String GAME_ROOM_NAME;
    // Database objects names
    private final static String GAME_OBJECTS = "GameObjects";
    private final static String boardStateName = "board_state";
    // Database references
    DatabaseReference roomDBref;
    private String[][] localBoardState;

    private String playerRole;
    private String current_player_turn = "";

    private String player1Name, player2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen_for_multiplayer);

        imageButton1 = findViewById(R.id.imageButton1GMP);
        imageButton2 = findViewById(R.id.imageButton2GMP);
        imageButton3 = findViewById(R.id.imageButton3GMP);
        imageButton4 = findViewById(R.id.imageButton4GMP);
        imageButton5 = findViewById(R.id.imageButton5GMP);
        imageButton6 = findViewById(R.id.imageButton6GMP);
        imageButton7 = findViewById(R.id.imageButton7GMP);
        imageButton8 = findViewById(R.id.imageButton8GMP);
        imageButton9 = findViewById(R.id.imageButton9GMP);

        restartButton = findViewById(R.id.restartGameBtnGMP);

        playerTurnTextView = findViewById(R.id.playerTurnTextViewGMP);
        winnerDisplayName = findViewById(R.id.winnerDisplayNameGMP2);
        player1NameTextView = findViewById(R.id.player1NameTextViewGMP);
        player2NameTextView = findViewById(R.id.player2NameTextViewGMP);

        player1LLayout = findViewById(R.id.player1LLayoutGMP);
        player2LLayout = findViewById(R.id.player2LLayoutGMP);

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

        GAME_ROOM_NAME = getIntent().getStringExtra("room_name_key");
        playerRole = getIntent().getStringExtra("player_role");

        // connecting to game room
        roomDBref = FirebaseDatabase.getInstance().getReference("GameRooms").child(GAME_ROOM_NAME);


        roomDBref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String player1Name = snapshot.child("player1_host").getValue(String.class);
                String player2Name = snapshot.child("player2_joined").getValue(String.class);
                current_player_turn = snapshot.child("current_player_turn").getValue(String.class);
                player1NameTextView.setText(player1Name);
                player2NameTextView.setText(player2Name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        roomDBref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                player1Name = snapshot.child("player1_host").getValue(String.class);
                player2Name = snapshot.child("player2_joined").getValue(String.class);
                current_player_turn = snapshot.child("current_player_turn").getValue(String.class);
                player1NameTextView.setText(player1Name);
                player2NameTextView.setText(player2Name);
                if(current_player_turn!=null){
                    if (current_player_turn.equals("HOST")) {
                        playerTurnTextView.setText(player1Name + " 's turn");
                        player1LLayout.setBackground(getDrawable(R.drawable.player_card_game_screen));
                        player2LLayout.setBackground(null);
                    } else {
                        playerTurnTextView.setText(player2Name + " 's turn");
                        player2LLayout.setBackground(getDrawable(R.drawable.player_card_game_screen));
                        player1LLayout.setBackground(null);
                    }
                } else {
                    current_player_turn = snapshot.child("current_player_turn").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        {
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
        }// image buttons click events

        localBoardState = new String[][]{{"","",""}, {"","",""}, {"","",""}};
        // setting board state in database
        for(int i=0;i< localBoardState.length;i++){
            for(int j=0;j<localBoardState[i].length;j++){
                roomDBref.child(GAME_OBJECTS).child(boardStateName).child(i+","+j).setValue(localBoardState[i][j]);
            }
        }

        roomDBref.child(GAME_OBJECTS).child(boardStateName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Log.d("snapshotValue",snapshot.getKey()); // get coordinate values eg. 0,0 from database
                String[] coordinateIJ = snapshot.getKey().split("[,]");
                int coordinateI = Integer.parseInt(coordinateIJ[0]);
                int coordinateJ = Integer.parseInt(coordinateIJ[1]);
                // updating localboardstate
                localBoardState[coordinateI][coordinateJ] = snapshot.getValue().toString();

                if(snapshot.getValue().toString().equals("1")){
                    imageButtonArrayList.get(coordinateI).get(coordinateJ).setImageResource(R.drawable.round_xml_cross);
                } else if(snapshot.getValue().toString().equals("2")) {
                    imageButtonArrayList.get(coordinateI).get(coordinateJ).setImageResource(R.drawable.round_circle_centre_hole);
                }

                // disabling the button
                imageButtonArrayList.get(coordinateI).get(coordinateJ).setEnabled(false);

                // checking if someone wins
                if(someoneWins()){
                    playerTurnTextView.setVisibility(View.GONE);
                    winnerDisplayName.setVisibility(View.VISIBLE);

                    if(current_player_turn.equals("HOST")){
                        winnerDisplayName.setText(player1Name+" wins!");
                        Toast.makeText(GameScreenForMultiplayer.this, player1Name+" wins!", Toast.LENGTH_SHORT).show();
                    } else {
                        winnerDisplayName.setText(player2Name+" wins!");
                        Toast.makeText(GameScreenForMultiplayer.this, player2Name+" wins!", Toast.LENGTH_SHORT).show();
                    }
                    disableButtonsAndRestartGame();
                } else if(checkDraw()){
                    playerTurnTextView.setVisibility(View.GONE);
                    winnerDisplayName.setVisibility(View.VISIBLE);

                    winnerDisplayName.setText("Match Draw!");
                    Toast.makeText(GameScreenForMultiplayer.this, "Game Draw", Toast.LENGTH_SHORT).show();
                    disableButtonsAndRestartGame();
                }

                if(current_player_turn.equals("HOST")){
                    current_player_turn = "JOINED";
                    roomDBref.child("current_player_turn").setValue(current_player_turn);
                } else {
                    current_player_turn = "HOST";
                    roomDBref.child("current_player_turn").setValue(current_player_turn);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        // will fire when a room is removed
        FirebaseDatabase.getInstance().getReference("GameRooms").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                roomRemoveToast();
                Intent intent = new Intent(GameScreenForMultiplayer.this,MultiplayerScreen.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean checkDraw(){
        for(String[]b:localBoardState){
            for(String s:b){
                if(s.isEmpty()) return false; //i.e there is some empty space left in the board;
            }
        }
        return true;
    }

    public boolean someoneWins(){
        return checkRowWise() ||
                checkColumnWise() ||
                checkDiagonal0() ||
                checkDiagonal2();
    }
    public boolean checkRowWise(){
        for(int i=0;i<3;i++){
            if(!localBoardState[i][0].isEmpty() && localBoardState[i][0].equals(localBoardState[i][1]) && localBoardState[i][1].equals(localBoardState[i][2])){
//                Log.d("winner", "checkRowWise: someone wins");
                paintWinBoxes(i,this.ROW_SIDE_VICTORY);
                return true;
            }
        }
        return false;
    }
    public boolean checkColumnWise(){
        for(int i=0;i<3;i++){
            if(!localBoardState[0][i].isEmpty() && localBoardState[0][i].equals(localBoardState[1][i]) && localBoardState[1][i].equals(localBoardState[2][i])){
//                Log.d("winner", "checkColumnWise: someone wins");
                paintWinBoxes(i,this.COLUMN_SIDE_VICTORY);
                return true;
            }
        }
        return false;
    }
    public boolean checkDiagonal0(){
        if(!localBoardState[0][0].isEmpty() && localBoardState[0][0].equals(localBoardState[1][1]) && localBoardState[1][1].equals(localBoardState[2][2])){
//            Log.d("winner", "checkDiagonal0: someone wins");
            paintWinBoxes(0,this.DIAGONAL048_SIDE_VICTORY);
            return true;
        }
        return false;
    }
    public boolean checkDiagonal2(){
        if(!localBoardState[0][2].isEmpty() && localBoardState[0][2].equals(localBoardState[1][1]) && localBoardState[1][1].equals(localBoardState[2][0])){
//            Log.d("winner", "checkDiagonal2: someone wins");
            paintWinBoxes(0,this.DIAGONAL246_SIDE_VICTORY);
            return true;
        }
        return false;
    }

    public void paintWinBoxes(int i,int whichSide){
        if(whichSide==this.ROW_SIDE_VICTORY){
            for(int j=0;j<3;j++){
                imageButtonArrayList.get(i).get(j).setBackground(getDrawable(R.drawable.player_card_winner));
            }
        } else if(whichSide==this.COLUMN_SIDE_VICTORY){
            for(int j=0;j<3;j++){
                imageButtonArrayList.get(j).get(i).setBackground(getDrawable(R.drawable.player_card_winner));
            }
        } else if(whichSide==this.DIAGONAL048_SIDE_VICTORY){
            for(int j=0;j<3;j++){
                imageButtonArrayList.get(j).get(j).setBackground(getDrawable(R.drawable.player_card_winner));
            }
        } else if(whichSide==this.DIAGONAL246_SIDE_VICTORY) {
            int k = 0;
            for(int j=2;j>=0;j--,k++){
                imageButtonArrayList.get(j).get(k).setBackground(getDrawable(R.drawable.player_card_winner));
            }
        }
    }

    public void disableButtonsAndRestartGame(){
        imageButton1.setEnabled(false);
        imageButton2.setEnabled(false);
        imageButton3.setEnabled(false);
        imageButton4.setEnabled(false);
        imageButton5.setEnabled(false);
        imageButton6.setEnabled(false);
        imageButton7.setEnabled(false);
        imageButton8.setEnabled(false);
        imageButton9.setEnabled(false);

        restartButton.setVisibility(View.VISIBLE);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameScreenForMultiplayer.this,MultiplayerScreen.class);
                startActivity(intent);
                finish();
                // removing room
                removeRoom();
            }
        });

    }

    private void removeRoom(){
        roomDBref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // show toast
                roomRemoveToast();
            }
        });
    }
    private void roomRemoveToast(){
        Toast.makeText(this, "ROOM DELETED!", Toast.LENGTH_SHORT).show();
    }

    private void playerClicks(ImageButton imageButton){
        String[] coordinateIJ = imageButton.getTag().toString().split("[,]");
        int coordinateI = Integer.parseInt(coordinateIJ[0]);
        int coordinateJ = Integer.parseInt(coordinateIJ[1]);

        if(playerRole.equals("HOST") && current_player_turn.equals("HOST")){
            imageButtonArrayList.get(coordinateI).get(coordinateJ).setImageResource(R.drawable.round_xml_cross);
            localBoardState[coordinateI][coordinateJ] = "1";
            roomDBref.child(GAME_OBJECTS).child(boardStateName).child(coordinateI+","+coordinateJ).setValue(localBoardState[coordinateI][coordinateJ]);
//            current_player_turn = "JOINED";
//            roomDBref.child("current_player_turn").setValue(current_player_turn);
        } else if(playerRole.equals("JOINED") && current_player_turn.equals("JOINED")){
            imageButtonArrayList.get(coordinateI).get(coordinateJ).setImageResource(R.drawable.round_circle_centre_hole);
            localBoardState[coordinateI][coordinateJ] = "2";
            roomDBref.child(GAME_OBJECTS).child(boardStateName).child(coordinateI+","+coordinateJ).setValue(localBoardState[coordinateI][coordinateJ]);
//            current_player_turn = "HOST";
//            roomDBref.child("current_player_turn").setValue(current_player_turn);
        }
    } // playerClicks method

}