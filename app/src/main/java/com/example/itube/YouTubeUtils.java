package com.example.itube;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import java.util.List;

public class YouTubeUtils {
    private static final String YOUTUBE_PACKAGE_NAME = "com.google.android.youtube";

    public static void playVideo(Context context, String videoId) {
        if (isYouTubeAppInstalled(context)) {
            launchYouTubeApp(context, videoId);
        } else {
            openVideoInBrowser(context, videoId);
        }
    }

    private static boolean isYouTubeAppInstalled(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "video_id"));
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.size() > 0;
    }

    private static void launchYouTubeApp(Context context, String videoId) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
        context.startActivity(intent);
    }

    private static void openVideoInBrowser(Context context, String videoId) {
        String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        context.startActivity(intent);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

