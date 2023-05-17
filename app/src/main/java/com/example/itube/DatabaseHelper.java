package com.example.itube;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.itube.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "iTubeDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_PLAYLIST = "playlist";
    private static final String COLUMN_VIDEO_ID = "video_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the users table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_PASSWORD + " TEXT" +
                ")";
        db.execSQL(createUsersTable);

        // Create the playlist table
        String createPlaylistTable = "CREATE TABLE " + TABLE_PLAYLIST + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_VIDEO_ID + " TEXT" +
                ")";
        db.execSQL(createPlaylistTable);

        // Create the videos table
        String createVideosTable = "CREATE TABLE videos (" +
                "video_id TEXT PRIMARY KEY," +
                "url TEXT" +
                ")";
        db.execSQL(createVideosTable);
        // Insert sample data into videos table
        ContentValues videoValues = new ContentValues();
        videoValues.put("video_id", "video1");
        videoValues.put("url", "https://example.com/video1.mp4");
        db.insert("videos", null, videoValues);

        videoValues.clear();

        videoValues.put("video_id", "video2");
        videoValues.put("url", "https://example.com/video2.mp4");
        db.insert("videos", null, videoValues);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);

        // Create tables again
        onCreate(db);
    }

    public void addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);

    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
            int usernameColumnIndex = cursor.getColumnIndex(COLUMN_USERNAME);
            int passwordColumnIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
            do {
                int id = cursor.getInt(idColumnIndex);
                String username = cursor.getString(usernameColumnIndex);
                String password = cursor.getString(passwordColumnIndex);
                User user = new User(id, username, password);
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return users;
    }

    public void addToVideos(String videoId, String videoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("video_id", videoId);
        values.put("url", videoUrl);
        db.insert("videos", null, values);
    }



    public List<String> getPlaylistUrls() {
        List<String> playlistUrls = new ArrayList<>();
        String selectQuery = "SELECT v.url FROM " + TABLE_PLAYLIST + " p INNER JOIN videos v ON p." + COLUMN_VIDEO_ID + " = v." + COLUMN_VIDEO_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            int urlColumnIndex = cursor.getColumnIndex("url");
            do {
                String url = cursor.getString(urlColumnIndex);
                playlistUrls.add(url);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return playlistUrls;
    }

    public List<String> getPlaylist() {
        List<String> playlist = new ArrayList<>();
        String selectQuery = "SELECT url FROM videos"; // Retrieve all URLs from the videos table
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            int urlColumnIndex = cursor.getColumnIndex("url");
            do {
                String url = cursor.getString(urlColumnIndex);
                playlist.add(url);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return playlist;
    }



}