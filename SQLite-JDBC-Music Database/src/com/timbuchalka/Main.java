package com.timbuchalka;

import com.timbuchalka.model.Album;
import com.timbuchalka.model.Artist;
import com.timbuchalka.model.Datasource;
import com.timbuchalka.queryReturns.SongDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String sqliteConnectionString = "jdbc:sqlite:/home/adam/github/WebDevelopmentStuff/Java/SQLite-JDBC-Music Database/music.db";

    public static void main(String[] args) {
        Datasource datasource;
        try {
            datasource = new Datasource(Main.sqliteConnectionString);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }
        System.out.println("Datasource successfully opened!");

        //Getting artists
        List<Artist> artists = datasource.queryArtists();
        if (artists == null) {
            System.out.println("No artists");
            return;
        }
        artists.forEach(artist -> System.out.println(artist.toString()));

        //Getting Albums by Artist
        System.out.println("-".repeat(50));
        List<Album> albumsByZZTop = new ArrayList<>();
        albumsByZZTop = datasource.getAlbums("pink floyd", Datasource.SortOrders.ASCENDING, false);
        albumsByZZTop.forEach(album -> System.out.println(album.toString()));

        //Getting Song Details
        System.out.println("-".repeat(50));
        List<SongDetail> wishYouWereHereSongDetails = datasource.getSongDetails("She's on fire", false);
        for(SongDetail song : wishYouWereHereSongDetails) {
            System.out.println("song = " + song);
        }

        //Using a function and retrieving result
        int numberOfSongs = datasource.getCount(Datasource.TABLE_SONGS);





        datasource.close();
    }
}
