package edu.uco.twilliams84.termprojecttimothyw.Database;

import android.provider.BaseColumns;

public final class PlayerTable {

    public static final String TEXT_TYPE = " TEXT";
    public static final String INT_TYPE = " integer";
    public static final String _COMMA = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PlayerEntry.TABLE_NAME + " (" +
                    PlayerEntry.COLUMN_EMAIL + TEXT_TYPE + " PRIMARY KEY"+ _COMMA +
                    PlayerEntry.COLUMN_NAME + TEXT_TYPE + _COMMA +
                    PlayerEntry.COLUMN_CITY + TEXT_TYPE + _COMMA +
                    PlayerEntry.COLUMN_STATE + TEXT_TYPE + _COMMA +
                    PlayerEntry.COLUMN_PASS + TEXT_TYPE + " )";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PlayerEntry.TABLE_NAME;

    public static final String SQL_CREATE_SCORE_ENTRIES =
            "CREATE TABLE " + ScoreEntry.TABLE_NAME + " (" +
                    ScoreEntry.COLUMN_ID + INT_TYPE + " PRIMARY KEY autoincrement"+ _COMMA +
                    ScoreEntry.COLUMN_EMAIL + TEXT_TYPE +  _COMMA +
                    ScoreEntry.COLUMN_SCORE + INT_TYPE + " )";
    public static final String SQL_DELETE_SCORE_ENTRIES =
            "DROP TABLE IF EXISTS " + ScoreEntry.TABLE_NAME;
    public static final String SQL_GET_ALL_SCORES =
            "SELECT * FROM " + PlayerEntry.TABLE_NAME + " a " +
                    " INNER JOIN " + ScoreEntry.TABLE_NAME + " b " +
                    " ON a." + PlayerEntry.COLUMN_EMAIL + "= b." + ScoreEntry.COLUMN_EMAIL +
                    " ORDER BY " + ScoreEntry.COLUMN_SCORE + " DESC";

    private PlayerTable() {}

    public static class PlayerEntry implements BaseColumns {
        public static final String TABLE_NAME = "PLAYERS";
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_EMAIL = "EMAIL";
        public static final String COLUMN_CITY = "CITY";
        public static final String COLUMN_STATE = "STATE";
        public static final String COLUMN_PASS = "PASS";
    }

    public static class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "SCORES";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_EMAIL = "EMAIL";
        public static final String COLUMN_SCORE = "SCORE";
    }
}
