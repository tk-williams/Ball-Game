package edu.uco.twilliams84.termprojecttimothyw.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import edu.uco.twilliams84.termprojecttimothyw.Model.Player;
import edu.uco.twilliams84.termprojecttimothyw.R;

public class MenuActivity extends Activity {

    private Button menuButtonStart;
    private Button menuButtonScores;
    private Button menuButtonInvite;
    private Button menuButtonLogin;
    private Button menuButtonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Initializing buttons
        menuButtonStart = (Button) findViewById(R.id.menu_start);
        menuButtonScores = (Button) findViewById(R.id.menu_scores);
        menuButtonInvite = (Button) findViewById(R.id.menu_invite);
        menuButtonLogin = (Button) findViewById(R.id.menu_login);
        menuButtonLogout = (Button) findViewById(R.id.menu_logout);
        if (Player.player != null) {
            menuButtonLogin.setVisibility(View.GONE);
            menuButtonLogout.setVisibility(View.VISIBLE);
        } else {
            menuButtonLogin.setVisibility(View.VISIBLE);
            menuButtonLogout.setVisibility(View.GONE);
        }

        //Adding button listeners
        menuButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                startActivity(intent);
                //startActivityForResult(intent, 1);
            }
        });

        menuButtonScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ScoreActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        menuButtonInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, InviteActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        menuButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LogInActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        menuButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player.player = null;
                Toast.makeText(getBaseContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
                menuButtonLogin.setVisibility(View.VISIBLE);
                menuButtonLogout.setVisibility(View.GONE);
            }
        });
    }
}
