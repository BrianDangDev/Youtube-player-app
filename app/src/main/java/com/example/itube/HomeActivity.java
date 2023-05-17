package com.example.itube;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itube.DatabaseHelper;
import com.example.itube.MyPlaylistActivity;
import com.example.itube.R;
import com.example.itube.VideoActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeActivity extends AppCompatActivity {
    private EditText youtubeUrlEditText;
    private Button playButton, addToPlaylistButton, myPlaylistButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseHelper = new DatabaseHelper(this);
        youtubeUrlEditText = findViewById(R.id.youtubeUrlEditText);
        playButton = findViewById(R.id.playButton);
        addToPlaylistButton = findViewById(R.id.addToPlaylistButton);
        myPlaylistButton = findViewById(R.id.myPlaylistButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String youtubeUrl = youtubeUrlEditText.getText().toString();
                String videoId = extractVideoId(youtubeUrl);

                if (videoId != null) {
                    Intent intent = new Intent(HomeActivity.this, VideoActivity.class);
                    intent.putExtra("videoId", videoId);
                    startActivity(intent);
                } else {
                    // Show an error message indicating that the YouTube URL is invalid
                    Toast.makeText(HomeActivity.this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addToPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String youtubeUrl = youtubeUrlEditText.getText().toString();
                String videoId = extractVideoId(youtubeUrl);

                if (videoId != null) {
                    String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
                    // Add the video to the videos table
                    databaseHelper.addToVideos(videoId, videoUrl);
                    Toast.makeText(HomeActivity.this, "Video added to videos table", Toast.LENGTH_SHORT).show();
                } else {
                    // Show an error message indicating that the YouTube URL is invalid
                    Toast.makeText(HomeActivity.this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        myPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the "My Playlist" page
                Intent intent = new Intent(HomeActivity.this, MyPlaylistActivity.class);
                startActivity(intent);
            }
        });
    }

    private String extractVideoId(String youtubeUrl) {
        String videoId = null;
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youtubeUrl); //url is youtube url for which you want to extract video id.
        if (matcher.find()) {
            videoId = matcher.group();
        }
        return videoId;
    }
}
