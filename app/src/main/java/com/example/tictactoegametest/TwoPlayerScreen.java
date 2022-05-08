package com.example.tictactoegametest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TwoPlayerScreen extends AppCompatActivity {

    private AppCompatButton button;
    private String player1Name, player2Name;
    private EditText p1Et,p2Et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_screen);

        button = findViewById(R.id.startGameBtn);
        p1Et = findViewById(R.id.playerName1);
        p2Et = findViewById(R.id.playerName2);

        SharedPreferences sp = getSharedPreferences("playerNames",MODE_PRIVATE);
        p1Et.setText(sp.getString("player1NameKey",null));
        p2Et.setText(sp.getString("player2NameKey",null));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1Name = p1Et.getText().toString();
                player2Name = p2Et.getText().toString();
                String whiteSpaceRegex = "\\s*";

                if(player1Name.isEmpty() || player2Name.isEmpty() ||
                        player1Name.matches(whiteSpaceRegex) ||player2Name.matches(whiteSpaceRegex)) {
                    Toast.makeText(TwoPlayerScreen.this, "Enter Names", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences sp = getSharedPreferences("playerNames",MODE_PRIVATE);
                    SharedPreferences.Editor spedit = sp.edit();
                    spedit.putString("player1NameKey",player1Name);
                    spedit.putString("player2NameKey",player2Name);
                    spedit.apply();

                    Intent intent = new Intent(TwoPlayerScreen.this, GameScreen.class);
                    intent.putExtra("playerName1Key", player1Name);
                    intent.putExtra("playerName2Key", player2Name);
//                    Toast.makeText(TwoPlayerScreen.this, player1Name + player2Name, Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish(); // close the current activity
                }
            }
        });
    }
}