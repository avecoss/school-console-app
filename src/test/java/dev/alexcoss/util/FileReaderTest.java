package dev.alexcoss.util;

import dev.alexcoss.util.exceptions.FileReadException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
    private final FileReader fileReader = new FileReader();

    @Test
    void shouldReadListFromResourceFile() {
        String path = "src/test/resources/test.txt";
        List<String> verification = fileReader.fileRead(path, bufferedReader -> bufferedReader.lines()
            .collect(Collectors.toList()));

        List<String> expected = List.of("test_line_1", "test_line_2", "test_line_3");

        assertEquals(expected, verification);
    }

    @Test
    void shouldCatchFileReadExceptionWhenFileNotFound() {
        String nonExistentFilePath = "non_existent_file.txt";

        Function<BufferedReader, String> readLineFunction = bufferedReader -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException e) {
                throw new FileReadException("Error reading line from file ", e);
            }
        };

        assertThrows(FileReadException.class, () -> fileReader.fileRead(nonExistentFilePath, readLineFunction));
    }

}