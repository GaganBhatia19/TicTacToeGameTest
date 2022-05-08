package com.example.tictactoegametest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameScreen extends AppCompatActivity {

    private final static int ROW_SIDE_VICTORY = 0;
    private final static int COLUMN_SIDE_VICTORY = 1;
    private final static int DIAGONAL048_SIDE_VICTORY = 2;
    private final static int DIAGONAL246_SIDE_VICTORY = 3;

    private TextView playerTurnTextView,player1NameTextView,player2NameTextView;
    private ImageButton imageButton1,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6,imageButton7,imageButton8,imageButton9;
    private LinearLayout player1LLayout, player2LLayout;
    private String player1Name,player2Name;
    private int currentTurn;
    private AppCompatButton restartButton;

    private int[][]boardState;
    private ArrayList<ArrayList<ImageButton>> imageButtonArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        // getting data from intents
        Intent intent = getIntent();
        player1Name = intent.getStringExtra("playerName1Key");
        player2Name = intent.getStringExtra("playerName2Key");
        // initializing with ids
        player1NameTextView = findViewById(R.id.player1NameTextView);
        player2NameTextView = findViewById(R.id.player2NameTextView);
        playerTurnTextView = findViewById(R.id.playerTurnTextView);
        player1LLayout = findViewById(R.id.player1LLayout);
        player2LLayout = findViewById(R.id.player2LLayout);

        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        imageButton5 = findViewById(R.id.imageButton5);
        imageButton6 = findViewById(R.id.imageButton6);
        imageButton7 = findViewById(R.id.imageButton7);
        imageButton8 = findViewById(R.id.imageButton8);
        imageButton9 = findViewById(R.id.imageButton9);

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

        // setting names
        player1NameTextView.setText(player1Name);
        player2NameTextView.setText(player2Name);

        boardState = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

        currentTurn = new Random().nextInt(2)+1;
//        Log.d("randomNumTest",currentTurn+"");
        if(currentTurn==1){
            playerTurnTextView.setText(player1Name+"'s turn");
            player1LLayout.setBackground(getDrawable(R.drawable.player_card_game_screen));
        } else {
            playerTurnTextView.setText(player2Name+"'s turn");
            player2LLayout.setBackground(getDrawable(R.drawable.player_card_game_screen));
        }

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

    private int coordinateI,coordinateJ;
    private String[]coordinateIJ;

    public void playerClicks(ImageButton imageButton) {
        coordinateIJ = imageButton.getTag().toString().split("[,]");

        coordinateI = Integer.parseInt(coordinateIJ[0]);
        coordinateJ = Integer.parseInt(coordinateIJ[1]);

        if(boardState[coordinateI][coordinateJ]==0){
            boardState[coordinateI][coordinateJ] = currentTurn;
            if(currentTurn == 1) {
                imageButton.setImageResource(R.drawable.round_xml_cross);
                currentTurn = 2;
                playerTurnTextView.setText(player2Name+"'s turn");
                player2LLayout.setBackground(getDrawable(R.drawable.player_card_game_screen));
                player1LLayout.setBackground(null);
            } else {
                imageButton.setImageResource(R.drawable.round_circle_centre_hole);
                currentTurn = 1;
                playerTurnTextView.setText(player1Name+"'s turn");
                player1LLayout.setBackground(getDrawable(R.drawable.player_card_game_screen));
                player2LLayout.setBackground(null);
            }
            if(someoneWins()){
                if(currentTurn==1){
                    playerTurnTextView.setText(player2Name+" wins!");
                } else {
                    playerTurnTextView.setText(player1Name + " wins!");
                }
                disableButtonsAndRestartGame();
            } else if(checkDraw()){
                playerTurnTextView.setText("Match Draw!");
                disableButtonsAndRestartGame();
            }
        }
    }

    public boolean checkDraw(){
        for(int[]b:boardState){
            for(int i:b){
                if(i==0) return false; //i.e there is some empty space left in the board;
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
            if(boardState[i][0]!=0 && boardState[i][0]==boardState[i][1] && boardState[i][1]==boardState[i][2]){
//                Log.d("winner", "checkRowWise: someone wins");
                paintWinBoxes(i,this.ROW_SIDE_VICTORY);
                return true;
            }
        }
        return false;
    }
    public boolean checkColumnWise(){
        for(int i=0;i<3;i++){
            if(boardState[0][i]!=0 && boardState[0][i]==boardState[1][i] && boardState[1][i]==boardState[2][i]){
//                Log.d("winner", "checkColumnWise: someone wins");
                paintWinBoxes(i,this.COLUMN_SIDE_VICTORY);
                return true;
            }
        }
        return false;
    }
    public boolean checkDiagonal0(){
        if(boardState[0][0]!=0 && boardState[0][0]==boardState[1][1] && boardState[1][1]==boardState[2][2]){
//            Log.d("winner", "checkDiagonal0: someone wins");
            paintWinBoxes(0,this.DIAGONAL048_SIDE_VICTORY);
            return true;
        }
        return false;
    }
    public boolean checkDiagonal2(){
        if(boardState[0][2]!=0 && boardState[0][2]==boardState[1][1] && boardState[1][1]==boardState[2][0]){
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

        restartButton = findViewById(R.id.restartGameBtn);
        restartButton.setVisibility(View.VISIBLE);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameScreen.this,TwoPlayerScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private int backButtonCounter = 0;
    @Override
    public void onBackPressed() {
        backButtonCounter++;
        if(backButtonCounter==2){
            super.onBackPressed();
            backButtonCounter%=2;
        } else {
            Toast.makeText(this, "Press Back button again", Toast.LENGTH_SHORT).show();
        }
    }
}

/*
*       {0,1,2}
*       {3,4,5}
*       {6,7,8}
*
*       {0,3,6}
*       {1,4,7}
*       {2,5,8}
*
*       {0,4,8}
*       {2,4,6}
* */