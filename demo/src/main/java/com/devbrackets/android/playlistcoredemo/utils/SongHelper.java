package com.devbrackets.android.playlistcoredemo.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.devbrackets.android.playlistcoredemo.data.Samples;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Hein Htet on 4/29/18.
 */
public class SongHelper {
    private String TAG = "SongHelper ";
    String audioImage = "https://ia902708.us.archive.org/3/items/count_monte_cristo_0711_librivox/Count_Monte_Cristo_1110.jpg?cnt=0";
    private Context mContext;
    ArrayList<Samples.Sample> list = new ArrayList<>();

    public SongHelper(Context context) {
        mContext = context;
        ;
    }

    public ArrayList<Samples.Sample> getSongList() {
        ContentResolver musicResolver = mContext.getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
            do {
                Long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                Uri path = ContentUris.withAppendedId(
                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        thisId);


                Log.i(TAG, "uri " + path);

                String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "Sc.mp3";
                list.add(new Samples.Sample(thisTitle, file, audioImage));
            } while (musicCursor.moveToNext());
        }
        return list;
    }

    public ArrayList<Samples.Sample> listOfSongs(Context context) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);

        //  Cursor c = context.getContentResolver().query(uri, null, MediaStore.Audio.Media.IS_MUSIC + " != 0", null, null);
        ArrayList<Samples.Sample> listOfSongs = new ArrayList<Samples.Sample>();
        c.moveToFirst();
        while (c.moveToNext()) {
            String title = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String album = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            long duration = c.getLong(c.getColumnIndex(MediaStore.Audio.Media.DURATION));
            String data = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
            long albumId = c.getLong(c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            String composer = c.getString(c.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
//
//            songData.setTitle(title);
//            songData.setAlbum(album);
//            songData.setArtist(artist);
//            songData.setDuration(duration);
//            songData.setPath(data);
//            songData.setAlbumId(albumId);
//            songData.setComposer(composer);
            listOfSongs.add(new Samples.Sample(title, data, artist));
            Log.i(TAG, "alblum Id " + albumId + " alblum name " + album + " data " + data + " artist " + artist);

        }
        c.close();
        Log.i(TAG, "SIZE: " + listOfSongs.size());
        return listOfSongs;
    }


    private void getfolder() {
        // The folder needs to end with the % wildcard or stuff doesn't work properly.
        String folder = "/mnt/sdcard/Music/Download/";
        folder = folder + "%";
        String where = MediaStore.Audio.Artists._ID + " IN (SELECT " + MediaStore.Audio.Media.ARTIST_ID + " FROM audio WHERE " +
                MediaStore.Audio.Media.DATA + " LIKE ?)";
        String[] whereArgs = new String[]{folder};
        Cursor audioCursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, where, whereArgs, MediaStore.Audio.AudioColumns.ARTIST + " COLLATE LOCALIZED ASC");

    }


    public ArrayList<String> getAlbum(long albumId) {
        Uri mediaUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, albumId);
        ArrayList<String> artistName = new ArrayList<>();
//        Cursor cursor = mContext.getContentResolver().query(
//                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
//                new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ID},
//                MediaStore.Audio.Albums._ID + "=?",
//                new String[]{albumId},
//                null);
        Cursor cursor = mContext.getContentResolver().query(mediaUri, null, null, null, null);

        cursor.moveToFirst();
        Log.i(TAG, "cursor counr " + cursor.getCount());

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            Log.i(TAG, "title " + title);
            artistName.add(new String(title));
        }
        cursor.close();
        return artistName;
    }


}
