package com.adammajor.common;

public class SongDetail {
    //return artist name, album name, and track number for the song of the album

    private String songName;
    private String artistName;
    private String albumName;
    private int trackNumber;

    public SongDetail(String songName, String artistName, String albumName, int trackNumber) {
        this.songName = songName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.trackNumber = trackNumber;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    @Override
    public String toString() {
        return "SongDetail{" +
                "songName='" + songName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", albumName='" + albumName + '\'' +
                ", trackNumber=" + trackNumber +
                '}';
    }
}
