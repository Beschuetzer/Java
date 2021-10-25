package com.adammajor.common;

public class Song {
    private int id;
    private String name;
    private int track;
    private int albumId;

    public Song(int id, String name, int track, int albumId) {
        this.id = id;
        this.name = name;
        this.track = track;
        this.albumId = albumId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTrack() {
        return track;
    }

    public int getAlbumId() {
        return albumId;
    }
}
