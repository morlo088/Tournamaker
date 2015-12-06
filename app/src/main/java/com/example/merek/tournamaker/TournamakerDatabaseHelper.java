package com.example.merek.tournamaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandre Lara on 05/12/2015.
 */
public class TournamakerDatabaseHelper extends SQLiteOpenHelper {
    private static TournamakerDatabaseHelper tInstance;
    private static final String TAG = "TournamakerDBHelper";

    // Database Info
    private static final String DATABASE_NAME = "tournamaker.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_TEAMS = "teams";

    // Teams Table Columns
    private static final String KEY_TEAM_ID = "id";
    private static final String KEY_TEAM_NAME = "name";
    private static final String KEY_TEAM_NUM_GOALS = "goals";
    private static final String KEY_TEAM_NUM_WINS = "wins";
    private static final String KEY_TEAM_NUM_LOSES = "loses";
    private static final String KEY_TEAM_LEAGUE_POS = "pos";
    private static final String KEY_TEAM_ICON_NAME = "icon_name"; // Name of the icon
    private static final String KEY_TEAM_ICON_PATH = "icon_path"; // Check if icon is stored on drawables or in a SD card
    private static final String KEY_TEAM_ICON_IS_DRAWABLE = "icon_is_drawable";

    public static synchronized TournamakerDatabaseHelper getInstance(Context context){
        if (tInstance == null){
            tInstance = new TournamakerDatabaseHelper(context.getApplicationContext());
        }
        return tInstance;
    }

    private TournamakerDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when database connection is being configured
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


    // Called when database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TEAMS_TABLE = "CREATE TABLE " + TABLE_TEAMS +
                "(" +
                    KEY_TEAM_ID + " INTEGER PRIMARY KEY," + // Define primary key
                    KEY_TEAM_NAME + " TEXT UNIQUE," +
                    KEY_TEAM_NUM_GOALS + " INTEGER," +
                    KEY_TEAM_NUM_WINS + " INTEGER," +
                    KEY_TEAM_NUM_LOSES + " INTEGER," +
                    KEY_TEAM_LEAGUE_POS + " INTEGER," +
                    KEY_TEAM_ICON_NAME + " TEXT," +
                    KEY_TEAM_ICON_PATH + " TEXT UNIQUE," +
                    KEY_TEAM_ICON_IS_DRAWABLE + " BOOLEAN" +
                ")";

        // Execute SQL
        db.execSQL(CREATE_TEAMS_TABLE);
    }

    // Called when database is already created and will be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop all old tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        onCreate(db);
    }

    public void addTeam(Team team, String teamIconName, String iconPath, Boolean isDrawable) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            //long teamId = addOrUpdateTeam(team, teamIconName, iconPath, isDrawable);

            ContentValues values = new ContentValues();
            //values.put(KEY_TEAM_ID, teamId);
            values.put(KEY_TEAM_NAME, team.getName());
            values.put(KEY_TEAM_NUM_GOALS, team.getNumOfGoals());
            values.put(KEY_TEAM_NUM_WINS, team.getNumGamesWon());
            values.put(KEY_TEAM_NUM_LOSES, team.getNumGamesLost());
            values.put(KEY_TEAM_LEAGUE_POS, team.getLeaguePosition());
            values.put(KEY_TEAM_ICON_NAME, teamIconName);
            values.put(KEY_TEAM_ICON_PATH, iconPath);
            values.put(KEY_TEAM_ICON_IS_DRAWABLE, isDrawable);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_TEAMS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

//    public long addOrUpdateTeam(Team team, String teamIconName, String iconPath, Boolean isDrawable) {
//        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
//        SQLiteDatabase db = getWritableDatabase();
//        long teamId = -1;
//
//        db.beginTransaction();
//        try {
//            ContentValues values = new ContentValues();
//            values.put(KEY_TEAM_NAME, team.getName());
//            values.put(KEY_TEAM_NUM_GOALS, team.getNumOfGoals());
//            values.put(KEY_TEAM_NUM_WINS, team.getNumGamesWon());
//            values.put(KEY_TEAM_NUM_LOSES, team.getNumGamesLost());
//            values.put(KEY_TEAM_LEAGUE_POS, team.getLeaguePosition());
//            values.put(KEY_TEAM_ICON_NAME, teamIconName);
//            values.put(KEY_TEAM_ICON_PATH, iconPath);
//            values.put(KEY_TEAM_ICON_IS_DRAWABLE, isDrawable);
//
//            // First try to update the user in case the user already exists in the database
//            // This assumes userNames are unique
//            int rows = db.update(TABLE_TEAMS, values, KEY_TEAM_NAME + "= ?", new String[]{team.getName()});
//
//            // Check if update succeeded
//            if (rows == 1) {
//                // Get the primary key of the user we just updated
//                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
//                        KEY_TEAM_ID, TABLE_TEAMS, KEY_TEAM_NAME);
//                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(team.getName())});
//                try {
//                    if (cursor.moveToFirst()) {
//                        teamId = cursor.getInt(0);
//                        db.setTransactionSuccessful();
//                    }
//                } finally {
//                    if (cursor != null && !cursor.isClosed()) {
//                        cursor.close();
//                    }
//                }
//            } else {
//                // user with this userName did not already exist, so insert new user
//                teamId = db.insertOrThrow(TABLE_TEAMS, null, values);
//                db.setTransactionSuccessful();
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to add or update user");
//        } finally {
//            db.endTransaction();
//        }
//        return teamId;
//    }

    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<>();

        // SELECT * FROM TEAMS
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_TEAMS);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(KEY_TEAM_NAME));
                    int numOfGoals = cursor.getInt(cursor.getColumnIndex(KEY_TEAM_NUM_GOALS));
                    int numOfWins = cursor.getInt(cursor.getColumnIndex(KEY_TEAM_NUM_WINS));
                    int numOfLoses = cursor.getInt(cursor.getColumnIndex(KEY_TEAM_NUM_LOSES));
                    int numOfLeaguePosition = cursor.getInt(cursor.getColumnIndex(KEY_TEAM_LEAGUE_POS));
                    String path = cursor.getString(cursor.getColumnIndex(KEY_TEAM_ICON_PATH)) + cursor.getString(cursor.getColumnIndex(KEY_TEAM_ICON_NAME));
                    boolean isIconDrawable = cursor.getInt(cursor.getColumnIndex(KEY_TEAM_ICON_IS_DRAWABLE))>0;
                    Team newTeam = new Team(name, numOfGoals, numOfWins, numOfLoses, numOfLeaguePosition, path, isIconDrawable);
                    newTeam.setIconPath(path);
                    teams.add(newTeam);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return teams;
    }

}