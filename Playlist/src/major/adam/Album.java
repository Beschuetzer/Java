package major.adam;

import java.util.ArrayList;
import java.util.LinkedList;

public class Album {
    private String name;
    private String artist;
    private ArrayList<Song> songs;

    public Album(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.songs = new ArrayList<Song>();
    }

    public boolean addSong(String title, double duration) {
        if (title == "" || duration <= 0) {
            System.err.println("Invalid arg in album.addSong()");
            return false;
        }

        Song songFound = findSong(title);
        if (songFound != null) return false;

        songs.add(new Song(title, duration));
        return true;
    }

    private Song findSong(String title) {
        if (title == "") {
            System.err.println("Invalid arg in album.findSong()");
            return null;
        }

        for(Song song : songs) {
            if (song.getTitle() == title) {
                return song;
            }
        }

        return null;
    }

    public boolean addToPlayList(int trackNumber, LinkedList<Song> playlist) {
        if (trackNumber <= 0 || playlist == null) {
            System.err.println("Invalid arg in album.addToPlaylist()");
            return false;
        }

        Song songToAdd = songs.get(trackNumber - 1);
        if (songToAdd == null ) return  false;

        playlist.add(songToAdd);
        return true;
    }

    public boolean addToPlayList(String title, LinkedList<Song> playlist) {
        if (title == "" || playlist == null) {
            System.err.println("Invalid arg in album.addToPlaylist()");
            return false;
        }

        Song songToAdd = findSong(title);
        if (songToAdd == null ) return  false;

        playlist.add(songToAdd);
        return true;
    }

}
