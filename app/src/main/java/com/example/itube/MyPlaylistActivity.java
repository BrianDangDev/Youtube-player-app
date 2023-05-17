package com.example.itube;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itube.DatabaseHelper;

import java.util.List;

public class MyPlaylistActivity extends AppCompatActivity {
    private ListView playlistListView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_playlist);

        databaseHelper = new DatabaseHelper(this);
        playlistListView = findViewById(R.id.playlistListView);

        // Fetch the playlist data from the database
        List<String> playlistUrls = databaseHelper.getPlaylist();

        // Create an ArrayAdapter to populate the data in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlistUrls);
        playlistListView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }
}

