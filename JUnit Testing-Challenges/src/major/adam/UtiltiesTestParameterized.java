package major.adam;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UtiltiesTestParameterized {
    private Utilties instance;

    @BeforeEach
    void setUp() {
        instance = new Utilties();
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @MethodSource
    void everyNthChar_valid(char[] sourceArray, int n, char[] expected) {
        assertArrayEquals(instance.everyNthChar(sourceArray, n), expected);
    }

    @ParameterizedTest
    @MethodSource
    void removePairs_valid(String source) {
        fail("Implement me");
    }

    @ParameterizedTest
    @MethodSource
    void converter_valid(int a, int b) {
        fail("Implement me");
    }

    @ParameterizedTest
    @MethodSource
    void nullIfOddLength_valid(String source) {
        fail("Implement me");
    }

    public static Stream<Arguments> everyNthChar_valid() {
        return Stream.of(
                Arguments.of(new char[] {'a', 'b', 'c','d','e','f','g'}, 1, new char[]{'a', 'b', 'c','d','e','f','g'} ),
                Arguments.of(new char[] {'a', 'b', 'c','d','e','f','g'}, 2, new char[]{'b','d','f'} ),
                Arguments.of(new char[] {'a', 'b', 'c','d','e','f','g'}, 3, new char[]{'c','f'} )
        );
    }

    public static Stream<Arguments> removePairs_valid() {
        return Stream.of(
                Arguments.of(),
                Arguments.of(),
                Arguments.of()
        );
    }
    public static Stream<Arguments> converter_valid() {
        return Stream.of(
                Arguments.of(),
                Arguments.of(),
                Arguments.of()
        );
    }
    public static Stream<Arguments> nullIfOddLength_valid() {
        return Stream.of(
                Arguments.of(),
                Arguments.of(),
                Arguments.of()
        );
    }
}