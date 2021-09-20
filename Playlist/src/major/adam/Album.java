package major.adam;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Album {
    private String name;
    private String artist;
    private SongList songs;

    public Album(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.songs = new SongList();
    }


    public boolean addToPlayList(int trackNumber, LinkedList<Song> playlist) {
        if (trackNumber <= 0 || playlist == null) {
            System.err.println("Invalid arg in album.addToPlaylist()");
            return false;
        }

        Song songToAdd = songs.getSongs().get(trackNumber - 1);
        if (songToAdd == null ) return  false;

        playlist.add(songToAdd);
        return true;
    }

    public boolean addToPlayList(String title, LinkedList<Song> playlist) {
        if (title == "" || playlist == null) {
            System.err.println("Invalid arg in album.addToPlaylist()");
            return false;
        }

        Song songToAdd = songs.findSong(title);
        if (songToAdd == null ) return  false;

        playlist.add(songToAdd);
        return true;
    }

    private class SongList {
        private List<Song> songs;

        public SongList() {
            this.songs = new ArrayList<Song>();
        }

        public List<Song> getSongs() {
            return songs;
        }

        public boolean addSong(Song song) {
            if (song.getTitle() == "" || song.getDuration() <= 0) return false;

            Song foundSong = findSong(song);
            if (foundSong != null) return false;

            this.songs.add(song);
            return true;
        }

        private Song findSong(Song song) {
            if (song.getTitle() == "" || song.getDuration() <= 0) return new Song("", 0);

            for(Song songLocal : songs) {
                if (song.getTitle() == songLocal.getTitle() && song.getDuration() == songLocal.getDuration()) return songLocal;
            }
            return null;
        }

        private Song findSong(String title) {
            if (title == "") return new Song("", 0);

            for(Song songLocal : songs) {
                if (title == songLocal.getTitle()) return songLocal;
            }
            return null;
        }
    }
}
