package com.devbrackets.android.playlistcoredemo.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.devbrackets.android.playlistcore.data.MediaProgress;
import com.devbrackets.android.playlistcore.data.PlaybackState;
import com.devbrackets.android.playlistcore.listener.PlaylistListener;
import com.devbrackets.android.playlistcore.listener.ProgressListener;
import com.devbrackets.android.playlistcore.manager.BasePlaylistManager;
import com.devbrackets.android.playlistcoredemo.App;
import com.devbrackets.android.playlistcoredemo.R;
import com.devbrackets.android.playlistcoredemo.data.MediaItem;
import com.devbrackets.android.playlistcoredemo.manager.PlaylistManager;
import com.devbrackets.android.playlistcoredemo.ui.adapter.StartupListAdapter;
import com.devbrackets.android.playlistcoredemo.utils.Song;
import com.devbrackets.android.playlistcoredemo.utils.SongList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class StartupActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, PlaylistListener<MediaItem>, ProgressListener {
    private PlaylistManager playlistManager;
    private String TAG = "StartUpActivity";
    private SongList songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_activity);
        testFunction();

        ListView exampleList = findViewById(R.id.startup_activity_list);
        exampleList.setAdapter(new StartupListAdapter(this));
        exampleList.setOnItemClickListener(this);
    }

    private void testFunction() {
        songList = new SongList();
        songList.scanSongs(this, "external");
        Log.i(TAG, " get all play list " + songList.getPlaylistNames());
        Log.i(TAG, " get all play list songs " + songList.getSongsByPlaylist("test play list ").size());
        Log.i(TAG, " get alblum for songs " + songList.getAlbumsByArtist("BREAKING BENJAMIN (MUSIC HOUR MP3)").size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        playlistManager = App.getPlaylistManager();
        playlistManager.registerPlaylistListener(this);
        playlistManager.registerProgressListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playlistManager.unRegisterPlaylistListener(this);
        playlistManager.unRegisterProgressListener(this);
        songList.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playlistManager.unRegisterPlaylistListener(this);
        playlistManager.unRegisterProgressListener(this);
    }

    private void permissionRequest() {
        String[] array = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(array, 1);
                return;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case StartupListAdapter.INDEX_AUDIO_PLAYBACK:
                MediaSelectionActivity.show(this, BasePlaylistManager.AUDIO);
                break;

            case StartupListAdapter.INDEX_VIDEO_PLAYBACK:
                MediaSelectionActivity.show(this, BasePlaylistManager.VIDEO);
                break;

            default:
        }
    }

    @Override
    public boolean onPlaylistItemChanged(@Nullable MediaItem currentItem, boolean hasNext, boolean hasPrevious) {
        return false;
    }

    @Override
    public boolean onPlaybackStateChanged(@NotNull PlaybackState playbackState) {
        return false;
    }

    @Override
    public boolean onProgressUpdated(@NotNull MediaProgress mediaProgress) {
        Log.i(TAG, "media progress " + mediaProgress.getDuration());
        return true;
    }
}
