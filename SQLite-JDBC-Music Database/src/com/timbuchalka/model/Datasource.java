package com.timbuchalka.model;

import java.sql.*;

/**
 * Created by timbuchalka on 9/12/16.
 */
public class Datasource {

    public static final String DB_NAME = "music.db";

//    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\databases\\" + DB_NAME;
    public static final String CONNECTION_STRING = "jdbc:sqlite:/home/adam/github/WebDevelopmentStuff/Java/SQLite-JDBC-Music Database/" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONG_ID = "_id";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM = "album";

    private Connection conn;

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch(SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch(SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public ResultSet executeQuery(String query) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}















