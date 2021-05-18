package try1;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CsvApp {

    public static void main(String[] args) throws Exception {
        File csvFile = new File("src/try1/resource/test.csv").getAbsoluteFile();

        CsvMapper csvMapper = CsvMapper.builder().build();
        MappingIterator<Map> rows = csvMapper
                .readerWithSchemaFor(Map.class)
                .with(CsvSchema.emptySchema().withHeader())
                .readValues(csvFile);
        System.out.println(rows.next());
        System.out.println(rows.next());
System.out.println(rows.next());
        DataConverter converter = new DataConverter();
        List<Map<String, Object>> result = converter.toMetricDataPoints(rows);

        ObjectMapper jsonMapper = JsonMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();

        jsonMapper.writeValue(System.out, result);
    }

}

class DataConverter {

    public List<Map<String, Object>> toMetricDataPoints(MappingIterator<Map> rows) {
        return toStream(rows)
            //group by metric -> [value, date]
            .collect(Collectors.groupingBy(map -> Arrays.asList(toNumber(map.get("value")),map.get("metric")),
                Collectors.mapping(map -> Arrays.asList(toNumber(map.get("value")), toNumber(map.get("date"))),
                    Collectors.toList())))
            .entrySet().stream()
            // convert to Map: metric + datapoints
            .map(entry -> {
                Map<String, Object> res = new LinkedHashMap<>(4);
                res.put("metric", entry.getKey());
                res.put("datapoints", entry.getValue());
System.out.println(entry.getKey());
                return res;
            }).collect(Collectors.toList());
    }

    private Stream<Map> toStream(MappingIterator<Map> rowIterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(rowIterator, Spliterator.ORDERED), false);
    }

    private long toNumber(Object value) {
        return new BigDecimal(Objects.toString(value, "0")).longValue();
    }
}