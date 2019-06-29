package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PlayerActivity extends AppCompatActivity {
    public static final String EXTRA_MODE="com.example.tictactoe.mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Button btnSingle = findViewById(R.id.btnSingle);
        Button btnMulti = findViewById(R.id.btnMulti);

        btnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerActivity.this, DifficultyActivity.class);
                startActivity(intent);
            }
        });

        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerActivity.this, MainActivity.class);
                intent.putExtra(EXTRA_MODE, false);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ap_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        if (item.getItemId() == R.id.apMiShowLeaderboard) {
            Intent intent= new Intent(this, LeaderBoardActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
