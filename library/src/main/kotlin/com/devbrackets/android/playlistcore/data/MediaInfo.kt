/*
 * Copyright (C) 2016 - 2017 Brian Wernick
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devbrackets.android.playlistcore.data

import android.graphics.Bitmap
import com.devbrackets.android.playlistcore.api.PlaylistItem

/**
 * An object to hold the information necessary to populate the
 * [PlaylistNotificationProvider] and [MediaSessionProvider]
 * with the information associated with the current playlist
 * item
 */
open class MediaInfo {
    var playlistItem: PlaylistItem? = null
    var largeNotificationIcon: Bitmap? = null
    var artwork: Bitmap? = null

    var appIcon: Int = 0
    var notificationId: Int = 0

    var mediaState: MediaState = MediaState()

    val title: String get() = playlistItem?.title.orEmpty()
    val album: String get() = playlistItem?.album.orEmpty()
    val artist: String get() = playlistItem?.artist.orEmpty()

    fun clear() {
        appIcon = 0
        notificationId = 0
        playlistItem = null

        largeNotificationIcon = null
        artwork = null
    }

    open class MediaState {
        var isPlaying: Boolean = false
        var isLoading: Boolean = false
        var isPreviousEnabled: Boolean = false
        var isNextEnabled: Boolean = false

        open fun reset() {
            isPlaying = false
            isLoading = false
            isPreviousEnabled = false
            isNextEnabled = false
        }
    }
}
