package com.example.tictactoe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import static com.example.tictactoe.R.id.board;

public class MainActivity extends AppCompatActivity {
    public static int difficulty;
    public static boolean mode;

    private BoardView boardView;
    private GameEngine gameEngine;
    private long startTime, runtime, bestTime;
    private SharedPreferences sharedPreferences;
    private TextView tvBestTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startTime= System.currentTimeMillis();
        runtime= 0;

        boardView = findViewById(board);
        tvBestTime= findViewById(R.id.tvBestTime);
        gameEngine = new GameEngine();
        boardView.setGameEngine(gameEngine);
        boardView.setMainActivity(this);

        Intent intent= getIntent();
        mode= intent.getBooleanExtra(PlayerActivity.EXTRA_MODE, true);
        difficulty= intent.getIntExtra(DifficultyActivity.EXTRA_DIFFICULTY, 0);

        sharedPreferences=getPreferences(Context.MODE_PRIVATE);
        bestTime=sharedPreferences.getLong(getString(R.string.time_Key), 0);
        Log.i("bestTime", ""+bestTime);
        tvBestTime.setText(getString(R.string.am_tvBestTime)+bestTime/1000F + getString(R.string.am_Seconds));
    }

    @Override
    protected void onPause() {
        super.onPause();
        runtime+= System.currentTimeMillis()-startTime;
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTime=System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            newGame();
        }

        return super.onOptionsItemSelected(item);
    }

    public void gameEnded(char c) {
        String msg = (c == 'T') ? "Game Ended. Tie" : "GameEnded. " + c + " win";
        runtime=runtime+System.currentTimeMillis()-startTime;
        Log.i("bestTime", ""+runtime);
        Log.i("bestTime", ""+runtime/1000F);
        if(runtime<bestTime && c!='T') {
            bestTime=runtime;
            Log.i("bestTime", ""+bestTime/1000F);
            tvBestTime.setText(getString(R.string.am_tvBestTime)+bestTime/1000F + getString(R.string.am_Seconds));
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putLong(getString(R.string.time_Key), bestTime);
            editor.apply();
        }
        runtime=0;

        new AlertDialog.Builder(this).setTitle("Tic Tac Toe").
                setMessage(msg).
                setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        newGame();
                    }
                }).show();
    }

    private void newGame() {
        gameEngine.newGame();
        boardView.invalidate();
        runtime=0;
        startTime=System.currentTimeMillis();
    }


}