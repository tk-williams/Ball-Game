package edu.uco.twilliams84.termprojecttimothyw.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import edu.uco.twilliams84.termprojecttimothyw.Database.DatabaseOperator;
import edu.uco.twilliams84.termprojecttimothyw.Model.Player;
import edu.uco.twilliams84.termprojecttimothyw.R;

public class ScoreActivity extends Activity {

    private TextView scoresTextName;
    private TextView scoresTextCity;
    private TextView scoresTextState;
    private TextView scoresTextScore;
    private Button scoresButtonMap;
    private RadioGroup scoresRadioGroup;
    enum ListedScore {
        ALL,
        PERSONAL
    } private ListedScore listedScore;
    public static ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //Scores listed are set to ALL by default
        listedScore = ListedScore.ALL;

        //Initializing xml elements
        scoresTextName = (TextView) findViewById(R.id.scores_name);
        scoresTextCity = (TextView) findViewById(R.id.scores_city);
        scoresTextState = (TextView) findViewById(R.id.scores_state);
        scoresTextScore = (TextView) findViewById(R.id.scores_score);
        scoresButtonMap = (Button) findViewById(R.id.scores_map);
        scoresRadioGroup = (RadioGroup) findViewById(R.id.radio_scores);

        //Adding to player array
        DatabaseOperator databaseOperator = new DatabaseOperator(getBaseContext());
        players = databaseOperator.gatherScores();
        setScores();

        //Adding button listener
        scoresButtonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, ScoreMapActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //Adding radio button listener
        scoresRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_scores_personal:
                        listedScore = ListedScore.PERSONAL;
                        break;
                    case R.id.radio_scores_all:
                    default:
                        listedScore = ListedScore.ALL;
                        break;
                }
                setScores();
            }
        });
    }

    public void setScores() {
        //Sorting the scores
        Collections.sort(players, Player.compareScores());

        //Clearing the text
        scoresTextName.setText("");
        scoresTextCity.setText("");
        scoresTextState.setText("");
        scoresTextScore.setText("");

        //Setting the scores
        if (listedScore == ListedScore.ALL) {
            for (Player player : players) {
                scoresTextName.append(player.getName() + "\n");
                scoresTextCity.append(player.getCity() + "\n");
                scoresTextState.append(player.getState() + "\n");
                scoresTextScore.append(player.getScores().get(0).toString() + "\n");
            }
        } else if (listedScore == ListedScore.PERSONAL) {
            for (Player player : players) {
                if (Player.player != null && player.getName().equals(Player.player.getName())) {
                    for (Integer score : player.getScores()) {
                        scoresTextName.append(player.getName() + "\n");
                        scoresTextCity.append(player.getCity() + "\n");
                        scoresTextState.append(player.getState() + "\n");
                        scoresTextScore.append(score.toString() + "\n");
                    }
                }
            }
        }
    }
}
