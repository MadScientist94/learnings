package com.jsch.learning.try20;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import bsh.EvalError;
import bsh.Interpreter;

//import static org.apache.spark.sql.functions.col;
//import org.apache.spark.sql.Column;
//import org.apache.spark.sql.Dataset;
//import org.apache.spark.sql.Row;
//import org.apache.spark.sql.types.DataTypes;


public class f {
	

//	public Column[] getDFColumns(Map<String,String> additionalParams,String outputType) {
//		String columnType=additionalParams.get("DataTypeOfData");
//		if(columnType!=null && !"".equalsIgnoreCase(columnType)) {
//			String[] columnsWithType=columnType.split("\\~!");
//			Column[] columns=new Column[columnsWithType.length];
//			for(int i=0;i<columnsWithType.length;i++) {
//				String[] column=columnsWithType[i].split("@@");
//				if("DF".equalsIgnoreCase(outputType)) {
//					columns[i]=org.apache.spark.sql.functions.col(column[0]).cast(column[1]).as(column[0]);
//				} else {
//					columns[i]=col("allcolumns").getItem(i).cast(column[1]).as(column[0]);
//				}
//				if(column.length>=3 && column[2].contains("substr")) {
//					String[] substrArr=column[2].replace("substr(", "").replace(")", "").split("\\,");
//					int startIndex=Integer.parseInt(substrArr[0].trim());
//					int endIndex=Integer.parseInt(substrArr[1].trim());
//					columns[i]=columns[i].substr(startIndex,endIndex).as(column[0]);
//				}
//			}
//			return columns;
//		}
//			return null;
//		}
		
	public static void main(String[] args) throws EvalError, FileNotFoundException, IOException {
	

		Interpreter intrp = new Interpreter();

//		intrp.source("F:\\cng\\generated jars\\FM_jar\\contentFormatter.bsh");
		
//System.out.println(intrp.);
		intrp.set("value", "NE=20554,EMU=0_0_40");
		intrp.set("col", "object_id");
//		String val=  (String) intrp.eval("extractId(value,col);");
//		System.out.println("Object Id ===>"+val);

		intrp.set("value", "NE=20554");
		intrp.set("col", "ne_id");
	
//		FileReader reader = new FileReader("F:\\cng\\generated jars\\FM_jar\\contentFormatter.bsh");
//		 val=  (String) intrp.eval("extractId(value,col);");
//		System.out.println("Ne Id ===>"+val);
		System.out.println(intrp.eval("import java.util.*;String result=null;switch(col){case \"ne_id\":{if (value.startsWith(\"NE=\")){result = value.replace(\"NE=\",\"\");}	break;}	case \"object_id\": {	if(value.contains(\"~@\")){String[] values=value.split(\"~!\");	String ne_id=values[0].trim();String port_id=values[1].trim();if (ne_id.startsWith(\"NE=\")){	ne_id=ne_id.replace(\"NE=\",\"\");}	if(port_id.contains(\"=\")){port_id=port_id.split(\"=\")[1].replaceAll(\"_\",\"-\");				}				result =ne_id+\"-\"+port_id;			}			break;		}	}	return result;"));
//		System.out.println(intrp.get("extractId(value,col);"));
		
		String val=  (String) intrp.eval("extractId(value,col);");
			System.out.println("Ne Id ===>"+val);
		
//		
//		try (BufferedReader brParser = new BufferedReader(new FileReader(new File("F:\\cng\\FMData\\20210726003006-alarm-log-auto-1.csv")))) {
//
//			String fileLine = brParser.readLine();
//			while (fileLine != null && !fileLine.isEmpty()) {
////				System.out.println(fileLine.replaceAll("\"","" ));
//			
//				fileLine = brParser.readLine();
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
		
	}

}
