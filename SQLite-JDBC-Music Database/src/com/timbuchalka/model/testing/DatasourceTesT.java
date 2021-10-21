package com.timbuchalka.model.testing;

import com.timbuchalka.Main;
import com.timbuchalka.model.Album;
import com.timbuchalka.model.Datasource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.timbuchalka.model.Datasource.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatasourceTesT {
    private static Datasource datasource;
    private static boolean isInitialized = false;

    @AfterAll
    static void tearDownDataSource() {
        System.out.println("Closing Connection");
        datasource.close();
    }

    @BeforeEach
    void setupDataSource() {
        if (isInitialized) return;
        System.out.println("Opening Connection...");
        try {
            datasource = new Datasource(Main.sqliteConnectionString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        isInitialized = true;
    }

    //region Main Method Tests
    @ParameterizedTest
    @MethodSource
    void getAlbums(String artistName, SortOrders sortOrder, boolean isCaseSensitive, List<Album> expected) {
        List<Album> retrievedAlbums = datasource.getAlbums(artistName, sortOrder, isCaseSensitive);
        for (int i = 0; i < expected.size(); i++) {
            boolean isValid = false;
            Album expectedAlbum = expected.get(i);
            Album retrievedAlbum = retrievedAlbums.get(i);

            boolean isSameName = retrievedAlbum.getName().equalsIgnoreCase(expectedAlbum.getName());
            boolean isSameId = retrievedAlbum.getId() == expectedAlbum.getId();
            boolean isSameArtistId = retrievedAlbum.getArtistId() == expectedAlbum.getArtistId();

            if (isSameArtistId && isSameId && isSameName) isValid = true;
            if (!isValid) {
                String tabs = "\t\t";
                String albumsString = retrievedAlbums.stream().map(album -> "\n" + tabs + "\t" + album.toString()).reduce("", (last, current) -> {
                    return last + tabs + current;
                });
                fail("\n" + tabs + "Expected album: " + expectedAlbum + "\n" + tabs + "Got: " + retrievedAlbum);
            }
        }
    }

    @ParameterizedTest
    @MethodSource
    void queryArtists() {
        fail("Implement me");
    }
    //endregion
    //region Query Helper Tests
//    @ParameterizedTest
//    @MethodSource
//    void getPreparedStatement(String table, List<String> columnHeaderNames, List<String> values, Object expected) {
//        if (expected instanceof String) {
//            assertEquals(expected, datasource.getPreparedStatement(, table, columnHeaderNames, values));
//            return;
//        } else {
//            assertThrows((Class) expected, () -> {
//                datasource.getPreparedStatement(, table, columnHeaderNames, values);
//                return;
//            });
//        }
//    }
    @ParameterizedTest
    @MethodSource
    void getJoinClause(JoinTypes joinType, String tableToJoin, String joinOn1, String joinOn2, String expected) {
        String received = datasource.getJoinClause(joinType, tableToJoin, joinOn1, joinOn2);
        assertEquals(expected.trim(), received.trim());
    }
    @ParameterizedTest
    @MethodSource
    void getOrderByClause(String sortOn, SortOrders sortOrder, String expected) {
        String received = datasource.getOrderByClause(sortOn, sortOrder);
        assertEquals(expected.trim(), received.trim());
    }
    @ParameterizedTest
    @MethodSource
    void getSelectClause(List<String> columns, String tableName, String expected) {
        String retrieved = datasource.getSelectClause(columns, tableName);
        assertEquals(expected.trim(), retrieved.trim());
    }
    @ParameterizedTest
    @MethodSource
    void getWhereClauseExact(String searchIn, String searchFor, boolean isExact, String expected) {
        String received = datasource.getWhereClause(searchIn, searchFor, isExact);
        assertEquals(expected.trim(), received.trim());
    }
    @ParameterizedTest
    @MethodSource
    void getWhereClauseOperators(String leftOperand, String rightOperand, WhereClauseOperators operator, String expected) {
        String received = datasource.getWhereClause(leftOperand, rightOperand, operator);
        assertEquals(expected.trim(), received.trim());
    }
    //endregion
    //region Helper Tests
    @ParameterizedTest
    @MethodSource
    void getEscapedString(String toEscape, String expected) {
        assertEquals(expected, datasource.getEscapedString(toEscape));
    }
    //endregion
    //region Test Argument Factories
    public Stream<Arguments> getAlbums() {
        System.out.println("Get albums...");
        return Stream.of(
                Arguments.of(
                        "ZZ top",
                        SortOrders.ASCENDING,
                        false,
                        Arrays.asList(
                                new Album(114, "Antenna", 23),
                                new Album(392, "Degüello", 23),
                                new Album(301, "Mescalero", 23),
                                new Album(263, "Recycler", 23),
                                new Album(22, "Rio Grande Mud", 23),
                                new Album(309, "Tres Hombres", 23)
                        )
                ),
                Arguments.of(
                        "ZZ top",
                        SortOrders.NONE,
                        false,
                        Arrays.asList(
                                new Album(22, "Rio Grande Mud", 23),
                                new Album(114, "Antenna", 23),
                                new Album(263, "Recycler", 23),
                                new Album(301, "Mescalero", 23),
                                new Album(309, "Tres Hombres", 23),
                                new Album(392, "Degüello", 23)
                        )
                ),
                Arguments.of(
                        "ZZ top",
                        SortOrders.DESCENDING,
                        false,
                        Arrays.asList(
                                new Album(309, "Tres Hombres", 23),
                                new Album(22, "Rio Grande Mud", 23),
                                new Album(263, "Recycler", 23),
                                new Album(301, "Mescalero", 23),
                                new Album(392, "Degüello", 23),
                                new Album(114, "Antenna", 23)
                        )
                ),
                Arguments.of(
                        "ZZ top",
                        SortOrders.DESCENDING,
                        true,
                        Arrays.asList()
                )
        );
    }
    public Stream<Arguments> getEscapedString() {
        return Stream.of(
                Arguments.of("ABCDEFGHIJKLMOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz", "ABCDEFGHIJKLMOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz"),
                Arguments.of("She's On Fire", "She\\'s On Fire")
        );
    }
    public Stream<Arguments> getPreparedStatement() {
        return Stream.of(
                Arguments.of(TABLE_ARTISTS, Arrays.asList("1"), Arrays.asList("1", "2"), IllegalArgumentException.class),
                Arguments.of(TABLE_ARTISTS, Arrays.asList("name"), Arrays.asList("Adam"), String.format("INSERT INTO %s(%s) VALUES(%s)", TABLE_ARTISTS, "name", "Adam")),
                Arguments.of(TABLE_ARTISTS, Arrays.asList("name", "track"), Arrays.asList("Adam", "Adam's Song"), String.format("INSERT INTO %s(%s) VALUES(%s)", TABLE_ARTISTS, "name, track", "Adam, Adam's Song")),
                Arguments.of(TABLE_ARTISTS, Arrays.asList("name", "track", "title"), Arrays.asList("Adam", "Adam's Song", "Jim"), String.format("INSERT INTO %s(%s) VALUES(%s)", TABLE_ARTISTS, "name, track, title", "Adam, Adam's Song, Jim"))
        );
    }
    public Stream<Arguments> getJoinClause() {
        return Stream.of(
//          getJoinClause(JoinTypes joinType, String tableToJoin, String joinOn1, String joinOn2) {
                Arguments.of(
                        JoinTypes.INNER,
                        TABLE_ARTISTS,
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ARTIST),
                        String.format("%s JOIN %s ON %s = %s", JoinTypes.INNER.value, TABLE_ARTISTS, String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID), String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ARTIST))
                ),
                Arguments.of(
                        JoinTypes.OUTER,
                        TABLE_ARTISTS,
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ARTIST),
                        String.format("%s JOIN %s ON %s = %s", JoinTypes.OUTER.value, TABLE_ARTISTS, String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID), String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ARTIST))
                ),
                Arguments.of(
                        JoinTypes.CROSS,
                        TABLE_ARTISTS,
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ARTIST),
                        String.format("%s JOIN %s ON %s = %s", JoinTypes.CROSS.value, TABLE_ARTISTS, String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID), String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ARTIST))
                )
        );
    }
    public Stream<Arguments> getOrderByClause() {
        return Stream.of(
                Arguments.of(
                        String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_NAME),
                        SortOrders.NONE,
                        SortOrders.NONE.value
                ),
                Arguments.of(
                        String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_NAME),
                        SortOrders.ASCENDING,
                        String.format("ORDER BY %s %s", String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_NAME), SortOrders.ASCENDING.value)
                ),
                Arguments.of(
                        String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_NAME),
                        SortOrders.DESCENDING,
                        String.format("ORDER BY %s %s", String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_NAME), SortOrders.DESCENDING.value)
                )
        );
    }
    public Stream<Arguments> getSelectClause() {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                                "*"
                        ),
                        TABLE_ALBUMS,
                        "SELECT * FROM " + TABLE_ALBUMS
                ),
                Arguments.of(
                        Arrays.asList(
                                String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_NAME),
                                String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ARTIST)
                        ),
                        TABLE_ALBUMS,
                        String.format("SELECT %s.%s, %s.%s FROM %s", TABLE_ALBUMS, COLUMN_ALBUM_NAME, TABLE_ALBUMS, COLUMN_ALBUM_ARTIST, TABLE_ALBUMS)
                ),
                Arguments.of(
                        Arrays.asList(
                                String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_NAME),
                                String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ID),
                                String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ARTIST)
                        ),
                        TABLE_ALBUMS,
                        String.format("SELECT %s.%s, %s.%s, %s.%s FROM %s", TABLE_ALBUMS, COLUMN_ALBUM_NAME, TABLE_ALBUMS, COLUMN_ALBUM_ID, TABLE_ALBUMS, COLUMN_ALBUM_ARTIST, TABLE_ALBUMS)
                )
        );
    }
    public Stream<Arguments> getWhereClauseExact() {
        return Stream.of(
//          getWhereClause(String searchIn, String searchFor, boolean isExact, String expected) {
                Arguments.of(
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        true,
                        String.format("WHERE %s %s \"%s\"",
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                                WhereClauseOperators.EQUALS.value,
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID)
                        )
                ),
                Arguments.of(
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        false,
                        String.format("WHERE %s %s \"%s\"",
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                                WhereClauseOperators.LIKE.value,
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID)
                        )
                ),
                Arguments.of(
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        "?",
                        false,
                        String.format("WHERE %s %s %s",
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                                WhereClauseOperators.LIKE.value,
                                "?"
                        )
                )
        );
    }
    public Stream<Arguments> getWhereClauseOperators() {
        return Stream.of(
//              getWhereClause(String leftOperand, String rightOperand, WhereClauseOperators operator) {
                Arguments.of(
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        WhereClauseOperators.EQUALS,
                        String.format("WHERE %s %s %s",
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                                WhereClauseOperators.EQUALS.value,
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID)
                        )
                ),
                Arguments.of(
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        WhereClauseOperators.LIKE,
                        String.format("WHERE %s %s %s",
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                                WhereClauseOperators.LIKE.value,
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID)
                        )
                ),
                Arguments.of(
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        WhereClauseOperators.LESS_THAN,
                        String.format("WHERE %s %s %s",
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                                WhereClauseOperators.LESS_THAN.value,
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID)
                        )
                ),
                Arguments.of(
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        WhereClauseOperators.NOT_EQUALS,
                        String.format("WHERE %s %s %s",
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                                WhereClauseOperators.NOT_EQUALS.value,
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID)
                        )
                ),
                Arguments.of(
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                        WhereClauseOperators.GREATER_THAN,
                        String.format("WHERE %s %s %s",
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID),
                                WhereClauseOperators.GREATER_THAN.value,
                                String.format("%s.%s", TABLE_ARTISTS, COLUMN_ARTIST_ID)
                        )
                )
        );
    }
    //endregion
}