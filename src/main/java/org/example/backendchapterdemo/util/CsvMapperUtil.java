package org.example.backendchapterdemo.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvMapperUtil {

    private final CsvMapper mapper = new CsvMapper();

    public <T> void exportListAsCsv(List<T> list, PrintWriter writer) {
        if (list.isEmpty()) return;
        try {
            mapper.writer(mapper.schemaFor(list.get(0).getClass()).withHeader()).writeValues(writer).writeAll(list);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public <T> List<T> importCsvAsList(MultipartFile file, Class<T> clazz) {
        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
        try {
            String json = new String(file.getBytes(), StandardCharsets.UTF_8);
            MappingIterator<T> parsedData = mapper.readerWithSchemaFor(clazz)
                    .with(schema)
                    .readValues(json);
            return parsedData.readAll();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return new ArrayList<>();
    }
}
