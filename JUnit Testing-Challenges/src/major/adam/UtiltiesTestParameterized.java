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
    void everyNthChar_valid(char[] sourceArray, int n, Object expected) {
        if (expected instanceof char[]) {
            assertArrayEquals(instance.everyNthChar(sourceArray, n), (char[])expected);
            return;
        } else {
            assertThrows((Class) expected, () -> {
                instance.everyNthChar(sourceArray, n);
            });
        }
    }

    @ParameterizedTest
    @MethodSource
    void removePairs_valid(String source, String expected) {
        assertEquals(expected, instance.removePairs(source));
    }

    @ParameterizedTest
    @MethodSource
    void converter_valid(int a, int b, Object expected) {
        System.out.println(expected);
        if (expected instanceof Integer) {
            System.out.println(1);
            assertEquals(instance.converter(a, b), (int) expected);
            return;
        } {
            assertThrows((Class) expected, () -> {
                instance.converter(a, b);
            });
            System.out.println(2);
        }
    }

    @ParameterizedTest
    @MethodSource
    void nullIfOddLength_valid(String source, String expected) {
        assertEquals(instance.nullIfOddLength(source), expected);
    }

    public static Stream<Arguments> everyNthChar_valid() {
        return Stream.of(
                Arguments.of(new char[] {'a', 'b', 'c','d','e','f','g'}, 1, new char[]{'a', 'b', 'c','d','e','f','g'} ),
                Arguments.of(new char[] {'a', 'b', 'c','d','e','f','g'}, 2, new char[]{'b','d','f'} ),
                Arguments.of(new char[] {'a', 'b', 'c','d','e','f','g'}, 3, new char[]{'c','f'} ),
                Arguments.of(new char[] {'h', 'e', 'l','l','o'}, 2, new char[]{'e','l'} ),
                Arguments.of(new char[] {'a', 'b', 'c','d','e','f','g'}, 0, IllegalArgumentException.class ),
                Arguments.of(null, 2, IllegalArgumentException.class ),
                Arguments.of(null, 4, IllegalArgumentException.class )
        );
    }

    public static Stream<Arguments> removePairs_valid() {
        return Stream.of(
                Arguments.of("AABCDDEFF", "ABCDEF"),
                Arguments.of("ABCCABDEEF", "ABCABDEF"),
                Arguments.of("ABCDEFF", "ABCDEF"),
                Arguments.of("AB88EFFG", "AB8EFG"),
                Arguments.of("112233445566", "123456"),
                Arguments.of("ZYZQQB", "ZYZQB"),
                Arguments.of("", ""),
                Arguments.of("A", "A"),
                Arguments.of(null, "")
        );
    }
    public static Stream<Arguments> converter_valid() {
        return Stream.of(
                Arguments.of(10, 5, 300),
                Arguments.of(10, 0, ArithmeticException.class)
        );
    }
    public static Stream<Arguments> nullIfOddLength_valid() {
        return Stream.of(
                Arguments.of("ABCD", "ABCD"),
                Arguments.of("ABC", null)
        );
    }
}