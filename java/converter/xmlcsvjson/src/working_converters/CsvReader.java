package working_converters;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CsvReader {
	 MappingIterator<Map>  read(String location) {
	  File csvFile = new File(location).getAbsoluteFile();

	  CsvMapper csvMapper = CsvMapper.builder().build();
	  MappingIterator<Map> rows = null;
	try {
		rows = csvMapper
		          .readerWithSchemaFor(Map.class)
		          .with(CsvSchema.emptySchema().withHeader())
		          .readValues(csvFile);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
return rows;
	 }
}
