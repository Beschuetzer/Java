package com.timbuchalka.model.testing;

import com.timbuchalka.Main;
import com.timbuchalka.model.Album;
import com.timbuchalka.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.timbuchalka.model.Datasource.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatasourceTesT {
    private static final Datasource datasource = new Datasource(Main.sqliteConnectionString);
    private static boolean isInitialized = false;

    @AfterAll
    static void tearDownDataSource() {
        datasource.close();
    }

    @BeforeEach
    void setupDataSource() {
        System.out.println("Opening...");
        if (isInitialized) return;
        datasource.open();
        isInitialized = true;
    }

    @org.junit.jupiter.api.Test
    void queryArtists() {
    }

    @ParameterizedTest
    @MethodSource
    void getSelectClause(List<String> columns, String tableName, String expected) {
        String retrieved = datasource.getSelectClause(columns, tableName);
        assertEquals(expected.trim(), retrieved.trim());
    }

    @ParameterizedTest
    @MethodSource
    void getOrderByClause(String sortOn, SortOrders sortOrder, String expected) {
        System.out.println("sortOn = " + sortOn);
        System.out.println("sortOrder = " + sortOrder);
        String received = datasource.getOrderByClause(sortOn, sortOrder);
        assertEquals(expected.trim(), received.trim());
    }

    @ParameterizedTest
    @MethodSource
    void getAlbums(String artistName, SortOrders sortOrder, List<Album> expected) {
        List<Album> retrievedAlbums = datasource.getAlbums(artistName, sortOrder);
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
                fail("\n" + tabs + "Expected album: " + expectedAlbum.toString() + "\n" + tabs + "Got: " + retrievedAlbum.toString());
            }
        }
    }

    //region Test Argument Factories
    public Stream<Arguments> getAlbums() {
        System.out.println("Get albums...");
        return Stream.of(
                Arguments.of(
                        "ZZ top",
                        SortOrders.ASCENDING,
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
                        Arrays.asList(
                                new Album(309, "Tres Hombres", 23),
                                new Album(22, "Rio Grande Mud", 23),
                                new Album(263, "Recycler", 23),
                                new Album(301, "Mescalero", 23),
                                new Album(392, "Degüello", 23),
                                new Album(114, "Antenna", 23)
                        )
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
                        String.format("SELECT %s.%s, %s.%s FROM %s",  TABLE_ALBUMS, COLUMN_ALBUM_NAME,TABLE_ALBUMS, COLUMN_ALBUM_ARTIST, TABLE_ALBUMS)
                ),
                Arguments.of(
                        Arrays.asList(
                                String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_NAME),
                                String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ID),
                                String.format("%s.%s", TABLE_ALBUMS, COLUMN_ALBUM_ARTIST)
                        ),
                        TABLE_ALBUMS,
                        String.format("SELECT %s.%s, %s.%s, %s.%s FROM %s",  TABLE_ALBUMS, COLUMN_ALBUM_NAME, TABLE_ALBUMS, COLUMN_ALBUM_ID,TABLE_ALBUMS, COLUMN_ALBUM_ARTIST, TABLE_ALBUMS)
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
    //endregion
}