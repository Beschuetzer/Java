package com.timbuchalka.model.testing;

import com.timbuchalka.model.Album;
import com.timbuchalka.model.Datasource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatasourceTesT {
    private static final Datasource datasource = new Datasource();
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
    void getAlbums(String artistName, List<Album> expected) {
        List<Album> retrievedAlbums = datasource.getAlbums(artistName);
        for (Album expectedAlbum : expected) {
            boolean isValid = false;

            for (Album retrievedAlbum : retrievedAlbums) {
                boolean isSameName = retrievedAlbum.getName().equalsIgnoreCase(expectedAlbum.getName());
                boolean isSameId = retrievedAlbum.getId() == expectedAlbum.getId();
                boolean isSameArtistId = retrievedAlbum.getArtistId() == expectedAlbum.getArtistId();

                if (isSameArtistId && isSameId && isSameName) isValid = true;
            }


            if (!isValid) {
                String tabs = "\t\t";
                String albumsString = retrievedAlbums.stream().map(album -> "\n" + tabs + "\t" + album.toString()).reduce("", (last, current) -> {
                    return last + tabs + current;
                });
                fail("\n" + tabs + "Missing album: " + "\n" + tabs + "\t" + expectedAlbum.toString() +
                        "\n" + tabs + "Got: " + albumsString);
            }
        }
    }

    public Stream<Arguments> getAlbums() {
        System.out.println("Get albums...");
        return Stream.of(
                Arguments.of("ZZ top", Arrays.asList(
                                new Album(114, "Antenna", 23),
                                new Album(392, "Degüello", 23),
                                new Album(301, "Mescalero", 23),
                                new Album(263, "Recycler", 23),
                                new Album(22, "Rio Grande Mud", 23),
                                new Album(309, "Tres Hombres", 23)
                        )
                ));
    }

    public Stream<Arguments> getSelectClause() {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                                "*"
                        ),
                        Datasource.TABLE_ALBUMS,
                        "SELECT * FROM " + Datasource.TABLE_ALBUMS
                ),
                Arguments.of(
                        Arrays.asList(
                                String.format("%s.%s", Datasource.TABLE_ALBUMS, Datasource.COLUMN_ALBUM_NAME),
                                String.format("%s.%s", Datasource.TABLE_ALBUMS, Datasource.COLUMN_ALBUM_ARTIST)
                        ),
                        Datasource.TABLE_ALBUMS,
                        String.format("SELECT %s.%s, %s.%s FROM %s",  Datasource.TABLE_ALBUMS, Datasource.COLUMN_ALBUM_NAME,Datasource.TABLE_ALBUMS, Datasource.COLUMN_ALBUM_ARTIST, Datasource.TABLE_ALBUMS)
                ),
                Arguments.of(
                        Arrays.asList(
                                String.format("%s.%s", Datasource.TABLE_ALBUMS, Datasource.COLUMN_ALBUM_NAME),
                                String.format("%s.%s", Datasource.TABLE_ALBUMS, Datasource.COLUMN_ALBUM_ID),
                                String.format("%s.%s", Datasource.TABLE_ALBUMS, Datasource.COLUMN_ALBUM_ARTIST)
                        ),
                        Datasource.TABLE_ALBUMS,
                        String.format("SELECT %s.%s, %s.%s, %s.%s FROM %s",  Datasource.TABLE_ALBUMS, Datasource.COLUMN_ALBUM_NAME, Datasource.TABLE_ALBUMS, Datasource.COLUMN_ALBUM_ID,Datasource.TABLE_ALBUMS, Datasource.COLUMN_ALBUM_ARTIST, Datasource.TABLE_ALBUMS)
                )
        );
    }
}