package com.timbuchalka.model;

import com.timbuchalka.queryReturns.SongDetail;

import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by timbuchalka on 9/12/16.
 */
public class Datasource {

//    public static final String DB_NAME = "music.db";
//public static final String CONNECTION_STRING = "jdbc:sqlite:/home/adam/github/WebDevelopmentStuff/Java/SQLite-JDBC-Music Database/" + DB_NAME;


    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONG_ID = "_id";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM = "album";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;
    private final String CONNECTION_STRING;
    private Connection conn;

    public Datasource(String CONNECTION_STRING) throws SQLException {
        this.CONNECTION_STRING = CONNECTION_STRING;
        open();
        queryTableMetaData(TABLE_SONGS);
        queryTableMetaData(TABLE_ALBUMS);
        queryTableMetaData(TABLE_ARTISTS);
    }

    public void open() throws SQLException {
        conn = DriverManager.getConnection(CONNECTION_STRING);
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public void queryTableMetaData(String tableName) {
        String sql = "SELECT * FROM " + tableName;
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql);
        ) {
            ResultSetMetaData meta = resultSet.getMetaData();
            int numColumns = meta.getColumnCount();
            for (int i = 1; i <= numColumns; i++) {
                System.out.printf("Column %d in the '%s' table is named '%s'\n", i, tableName, meta.getColumnName(i));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Artist> queryArtists() {
        List<Artist> toReturn = new ArrayList<>();

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_ARTISTS)
        ) {
            while (resultSet.next()) {
                String name = resultSet.getString(INDEX_ARTIST_NAME);
                int id = resultSet.getInt(INDEX_ARTIST_ID);
                Artist newArtist = new Artist(name, id);
                if (!toReturn.contains(newArtist)) toReturn.add(newArtist);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return toReturn;
    }

    public List<Album> getAlbums(String artist) {
        return getAlbums(artist, SortOrders.NONE, false);
    }

    public List<Album> getAlbums(String artist, SortOrders sortOrder, boolean isCaseSensitive) {
        List<Album> toReturn = new ArrayList<>();
        String selectClause = getSelectClause(Arrays.asList("*"), TABLE_ALBUMS);
        String joinClause = getJoinClause(JoinTypes.INNER, TABLE_ARTISTS, String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID), String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ARTIST));
        String whereClause = getWhereClause(String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_NAME), artist, true);
        String orderByClause = getOrderByClause(String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_NAME), sortOrder);
        String collateClause = isCaseSensitive ? "" : "COLLATE NOCASE ";

        String query = selectClause +
                joinClause +
                whereClause +
                collateClause +
                orderByClause;

        System.out.println(query);
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int artistId = resultSet.getInt(3);
                toReturn.add(new Album(id, name, artistId));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    //it is not recommended to return a ResultSet as that is implementation specific
    // (if returning a list, then any changes to how data is stored doesn't reflect working outside of this class)
//    public ResultSet executeQuery(String query) {
//        Statement statement = null;
//        try {
//            statement = conn.createStatement();
//            return statement.executeQuery(query);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public List<SongDetail> getSongDetails(String songName) {
        return getSongDetails(songName, -1, true);
    }
    public List<SongDetail> getSongDetails(String songName, boolean isExact) {
        return getSongDetails(songName, -1, isExact);
    }
    public List<SongDetail> getSongDetails(String songName, Integer albumId, boolean isExact) {
        //return artist name, album name, and track number for the song of the album
        if (albumId <= 0 && albumId != -1) throw new IllegalArgumentException("albumId must be larger than 0");
        List<SongDetail> toReturn = new ArrayList<>();
        String selectClause = getSelectClause(Arrays.asList(
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_NAME),
                        String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_NAME),
                        String.format("%s.%s", TABLE_SONGS, COLUMN_SONG_TRACK)
                ),
                TABLE_SONGS
        );
        String joinAlbumsClause = getJoinClause(JoinTypes.INNER, TABLE_ALBUMS, String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ID), String.format("%s.%s", TABLE_SONGS, COLUMN_SONG_ALBUM));
        String joinArtistsClause = getJoinClause(JoinTypes.INNER, TABLE_ARTISTS, String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID), String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ARTIST));
        String whereClause = getWhereClause(String.format("%s.%s", TABLE_SONGS, COLUMN_SONG_TITLE), songName, isExact);
        String whereAlbumIdCondition = getWhereCondition(String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ID), String.format("%s", albumId), WhereClauseOperators.EQUALS);
        String orderByClause = getOrderByClause(String.format("%s.%s, %s.%s", TABLE_ARTISTS, COLUMN_ARTIST_NAME, TABLE_ALBUMS, COLUMN_ALBUM_NAME), SortOrders.ASCENDING);

        String query = selectClause + joinAlbumsClause + joinArtistsClause + whereClause + (albumId > 0 ? " and " + whereAlbumIdCondition : "") + orderByClause;
        System.out.println("query = " + query);

        try (
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query.trim())
        ) {
            while (resultSet.next()) {
                String artistName = resultSet.getString(1);
                String albumName = resultSet.getString(2);
                int trackNumber = resultSet.getInt(3);
                toReturn.add(new SongDetail(songName.substring(0, 1).toUpperCase() + songName.substring(1), artistName, albumName, trackNumber));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return toReturn;
    }

    public int getCount(String tableName) {
        String sql = "SELECT COUNT(*) AS count, MIN(_id) AS minId FROM " + tableName;
        try (
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                ) {

            //getting column values of functions
            int count = resultSet.getInt(1);
            int minId = resultSet.getInt(2);
            System.out.printf("Using column indexes: count = %s and minId = %s", count, minId);

            //can use column alias to get values
            count = resultSet.getInt("count");
            minId = resultSet.getInt("minId");
            System.out.printf("\nUsing labels: count = %s and minId = %s", count, minId);

            return count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
    //region Helpers
    public String getSelectClause(List<String> columns, String tableName) {
        StringBuilder columnString = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            String suffix = "";
            if (columns.size() > 1 && i != columns.size() - 1) suffix = ", ";
            columnString.append(column.trim() + suffix);
        }
        return String.format(" SELECT %s FROM %s ", columnString, tableName);
    }

    public String getOrderByClause(String sortOn, SortOrders sortOrder) {
        if (sortOrder.equals(SortOrders.NONE)) return "";

        String sortOrderString = "DESC";
        if (sortOrder == SortOrders.ASCENDING) sortOrderString = "ASC";

        return String.format(" ORDER BY %s %s ", sortOn, sortOrderString);
    }

    public String getJoinClause(JoinTypes joinType, String tableToJoin, String joinOn1, String joinOn2) {
        return String.format(" %s JOIN %s ON %s = %s ", joinType, tableToJoin, joinOn1, joinOn2);
    }

    public String getWhereClause(String searchIn, String searchFor) {
        /**Exact match**/
        return getWhereClause(searchIn, searchFor, true);
    }

    public String getWhereClause(String searchIn, String searchFor, boolean isExact) {
        /**Exact or LIKE match**/
        WhereClauseOperators searchMethod = WhereClauseOperators.EQUALS;
        if (!isExact) searchMethod = WhereClauseOperators.LIKE;
        return String.format(" WHERE %s ", getWhereCondition(searchIn, String.format("\"%s\"", searchFor), searchMethod));
    }

    public String getWhereClause(String leftOperand, String rightOperand, WhereClauseOperators operator) {
        /**When using with operator**/
        return String.format(" WHERE %s ", getWhereCondition(leftOperand, rightOperand, operator));
    }

    public String getWhereCondition(String leftOperand, String rightOperand, WhereClauseOperators operator) {
        return String.format("%s %s %s", leftOperand, operator.value, rightOperand);
    }

    public enum SortOrders {
        NONE(""),
        ASCENDING("ASC"),
        DESCENDING("DESC");

        public final String value;

        SortOrders(String value) {
            this.value = value;
        }
    }

    public enum JoinTypes {
        INNER("INNER"),
        OUTER("OUTER"),
        CROSS("CROSS");

        public String value;

        JoinTypes(String value) {
            this.value = value;
        }
    }

    public enum WhereClauseOperators {
        EQUALS("="),
        GREATER_THAN(">"),
        LESS_THAN("<"),
        NOT_EQUALS("<>"),
        LIKE("LIKE");

        public String value;

        WhereClauseOperators(String value) {
            this.value = value;
        }
    }

    public String getEscapedString(String strToEscape) {
        List<Integer> charsToEscape = Arrays.asList(39);
        StringBuilder escaped = new StringBuilder("");
        char[] strToEscapeChars = strToEscape.toCharArray();

        for(int i = 0; i < strToEscapeChars.length; i++) {
            int currentCharCode = (int) strToEscapeChars[i];
            if (charsToEscape.contains(currentCharCode)) {
                escaped.append("\\" + (char) currentCharCode);
            }
            else escaped.append((char) currentCharCode);
        }

        return escaped.toString();
    }
    //endregion


}















