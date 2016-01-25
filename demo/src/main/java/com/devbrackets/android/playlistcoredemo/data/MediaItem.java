package com.devbrackets.android.playlistcoredemo.data;

import com.devbrackets.android.playlistcore.manager.BasePlaylistManager;
import com.devbrackets.android.playlistcore.manager.IPlaylistItem;
import com.devbrackets.android.playlistcoredemo.helper.AudioItems;
import com.devbrackets.android.playlistcoredemo.helper.VideoItems;

/**
 * A custom {@link IPlaylistItem}
 * to hold the information pertaining to the audio and video items
 */
public class MediaItem implements IPlaylistItem {
    private String artworkUrl;
    private String mediaUrl;
    private String title;
    boolean isAudio;

    public MediaItem(AudioItems.AudioItem audioItem) {
        artworkUrl = audioItem.getArtworkUrl();
        mediaUrl = audioItem.getMediaUrl();
        title = audioItem.getTitle();
        isAudio = true;
    }

    public MediaItem(VideoItems.VideoItem videoItem) {
        artworkUrl = null;
        mediaUrl = videoItem.getMediaUrl();
        title = videoItem.getTitle();
        isAudio = false;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public long getPlaylistId() {
        return 0;
    }

    @Override
    public int getMediaType() {
        return isAudio ? BasePlaylistManager.AUDIO : BasePlaylistManager.VIDEO;
    }

    @Override
    public String getMediaUrl() {
        return mediaUrl;
    }

    @Override
    public String getDownloadedMediaUri() {
        return null;
    }

    @Override
    public String getThumbnailUrl() {
        return artworkUrl;
    }

    @Override
    public String getArtworkUrl() {
        return artworkUrl;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAlbum() {
        return "ExoMedia Demo";
    }

    @Override
    public String getArtist() {
        return "Unknown Artist";
    }
}