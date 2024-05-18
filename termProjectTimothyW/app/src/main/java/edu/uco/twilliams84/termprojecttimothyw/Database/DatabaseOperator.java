package edu.uco.twilliams84.termprojecttimothyw.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import edu.uco.twilliams84.termprojecttimothyw.Model.Player;

public class DatabaseOperator extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PLAYER_DATABASE";

    public DatabaseOperator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PlayerTable.SQL_CREATE_ENTRIES);
        db.execSQL(PlayerTable.SQL_CREATE_SCORE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PlayerTable.SQL_DELETE_SCORE_ENTRIES);
        db.execSQL(PlayerTable.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean addPlayer(edu.uco.twilliams84.termprojecttimothyw.Model.Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PlayerTable.PlayerEntry.COLUMN_EMAIL, player.getEmail());
        values.put(PlayerTable.PlayerEntry.COLUMN_NAME, player.getName());
        values.put(PlayerTable.PlayerEntry.COLUMN_CITY, player.getCity());
        values.put(PlayerTable.PlayerEntry.COLUMN_STATE, player.getState());
        values.put(PlayerTable.PlayerEntry.COLUMN_PASS, player.getPassword());

        long id = db.insert(PlayerTable.PlayerEntry.TABLE_NAME, null, values);
        db.close();

        return true;
    }

    public boolean findPlayer(edu.uco.twilliams84.termprojecttimothyw.Model.Player player) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] projection = {
                PlayerTable.PlayerEntry.COLUMN_NAME,
                PlayerTable.PlayerEntry.COLUMN_EMAIL,
                PlayerTable.PlayerEntry.COLUMN_PASS,
                PlayerTable.PlayerEntry.COLUMN_CITY,
                PlayerTable.PlayerEntry.COLUMN_STATE
        };

        //Filter results where "COLUMN_EMAIL = email
        String selection = PlayerTable.PlayerEntry.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {player.getEmail()};

        Cursor cursor = database.query(
                PlayerTable.PlayerEntry.TABLE_NAME, //Table being queried
                projection,                         //The columns being returned
                selection,                          //The columns for the WHERE clause
                selectionArgs,                      //The values for the WHERE clause
                null,                               //No row grouping
                null,                               //No row group filtering
                null                                //Order of results
        );

        //Navigate the results
        boolean found = false;
        if (cursor.moveToFirst()) {
            String retrievedPassword = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.PlayerEntry.COLUMN_PASS));
            if (retrievedPassword.equals(player.getPassword())) {
                String playerName = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.PlayerEntry.COLUMN_NAME));
                String playerCity = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.PlayerEntry.COLUMN_CITY));
                String playerState = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.PlayerEntry.COLUMN_STATE));
                String playerEmail = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.PlayerEntry.COLUMN_EMAIL));
                String playerPassword = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.PlayerEntry.COLUMN_PASS));

                Player.player = new Player(
                        playerName,
                        playerCity,
                        playerState,
                        playerEmail,
                        playerPassword
                );
                found = true;
            }
        } else {
            found = false;
        }
        return found;
    }

    public ArrayList<Player> gatherScores() {
        ArrayList<Player> players = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(PlayerTable.SQL_GET_ALL_SCORES, null);

        while(cursor.moveToNext()) {
            String playerName = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.PlayerEntry.COLUMN_NAME));
            String playerCity = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.PlayerEntry.COLUMN_CITY));
            String playerState = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.PlayerEntry.COLUMN_STATE));
            String playerEmail = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.PlayerEntry.COLUMN_EMAIL));
            int playerScore = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerTable.ScoreEntry.COLUMN_SCORE));

            boolean foundPlayer = false;
            for (Player player : players) {
                if (player.getEmail().equals(playerEmail)) {
                    player.addScore(playerScore);
                    foundPlayer = true;
                }
            }
            if (!foundPlayer) {
                Player player = new Player(playerName, playerCity, playerState, playerEmail, null);
                player.addScore(playerScore);
                players.add(player);
            }
        }
        database.close();
        return players;
    }

    public boolean addScore(Player player, int score) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PlayerTable.ScoreEntry.COLUMN_EMAIL, player.getEmail());
        values.put(PlayerTable.ScoreEntry.COLUMN_SCORE, score);
        long id = database.insert(PlayerTable.ScoreEntry.TABLE_NAME, null, values);
        return true;
    }
}
