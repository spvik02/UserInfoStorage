package edu.vsp.configuration;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;

public class CsvConfiguration {

    @Getter
    private final Path userCsvFilePath = Path.of("src/main/resources/users.csv");

    @Getter
    private final char separator = ';';

    public <T> StatefulBeanToCsv<T> getBeanToCsvWriter(Writer writer) {
        return new StatefulBeanToCsvBuilder<T>(writer)
                .withSeparator(separator)
                .withApplyQuotesToAll(false)
                .build();
    }

    /**
     * Builds and configures the CsvToBean
     *
     * @param reader
     * @param cls    bean type
     * @param <T>    bean type
     * @return object that help converts CSV data to objects
     */
    public <T> CsvToBean<T> getCsvToBeanReader(Reader reader, Class<T> cls) {
        return new CsvToBeanBuilder<T>(reader)
                .withSeparator(separator)
                .withType(cls)
                .build();
    }

    public Reader getReader() throws FileNotFoundException {
        return new FileReader(userCsvFilePath.toFile());
    }

    public Writer getWriter() throws IOException {
        return new FileWriter(userCsvFilePath.toFile(), true);
    }
}
