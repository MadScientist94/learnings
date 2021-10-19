package Xpath;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgn.parser.commonutils.CounterInfo;
import com.cgn.parser.library.OptiGoParser;
import com.cgn.parser.outputlistener.ParseResultListener;
import com.cgn.services.parser.FileProcessor;
import com.cgn.services.parser.OptiGoParserMain;
import com.cgn.services.parser.ParseFileInfo;
import com.cgn.services.parser.ParseManifestInfo;
import com.cgn.services.parser.ParserContext;
// generic csv fm parser
public class GenericCSV_FMParser implements OptiGoParser {

	Logger logger = LoggerFactory.getLogger(GenericCSV_FMParser.class);

	public class GenericCSVParserContext {
		long core_id;
		long vendor_id;
		long ne_type_id;
		FileSystem fs;
		Boolean hFlag = true;
		Boolean pInfoFlag = true;
		int maxSize;
		/** lookup*/
		Map<String, String> lookupMap = new HashMap<String, String>();
		Map<String, Object> headerIndexMap = new LinkedHashMap<String, Object>();
		/** raw data Headers */
		List<String> headerList = new LinkedList<String>();
		/** lookup file normalized/table headers */
		List<String> lookupHeaderList = new LinkedList<String>();
		List<Map<String, Object>> parseObjectList = new LinkedList<Map<String, Object>>();
		Set<String> neList = new HashSet<>();
		ParseFileInfo parseFileInfo;
		ParseManifestInfo parserManifest;
		ParseResultListener resultListener;
		Properties prop;
		ParserContext pctx;
		List<String> fileLineList = new LinkedList<>();
		List<String> additonal_columns = new LinkedList<String>();
	}

	@Override
	public void parse(ParserContext pctx) {
		for (ParseFileInfo pfInfo : pctx.getFileInfo()) {
			try {
				parseFiles(pctx, pfInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void parseFiles(ParserContext pctx, ParseFileInfo pfInfo) {
		GenericCSVParserContext cntx = new GenericCSVParserContext();
		cntx.pctx = pctx;
		cntx.fs = pctx.getFileSystem();
		cntx.parseFileInfo = pfInfo;
		cntx.parserManifest = pctx.getManifestInfo();
		cntx.prop = OptiGoParserMain.getInstance().getProp();
		cntx.resultListener = pctx.getManifestInfo().getParseResultListener();
		cntx.maxSize = Integer.parseInt(cntx.prop.getProperty("parser.linesToWrite", "250000"));
		String rawPath = cntx.parseFileInfo.getFileLocation();
		logger.info("csv Generic Parser File Name : " + rawPath);

		List<String> fileLines = FileProcessor.getInstance().readCSV(pctx, "LookUp", null);
		for (String line : fileLines) {
			if (!line.startsWith("#")) {
				String lookupLineSplit[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", 2);
				if (!lookupLineSplit[1].trim().isEmpty()) {
					cntx.lookupMap.put(lookupLineSplit[0].trim(), lookupLineSplit[1].trim());
				}
			}
		}

		cntx.fileLineList = FileProcessor.getInstance().readCSV(pctx, "RawFile", cntx.parseFileInfo);
		String result = parseFile(cntx.lookupMap, cntx);
		pctx.setFileStatus(result);
		pctx.setCompleted(true);
		cntx.resultListener.parseComplete(pctx);
	}

	private String parseFile(Map<String, String> lookupMap, GenericCSVParserContext cntx) {
		System.out.println("Lookup File Params:::" + cntx.headerIndexMap);
		if (cntx.fileLineList != null && !cntx.fileLineList.isEmpty()) {
			for (String row : cntx.fileLineList) {
				String[] fileLineSplit = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				if (cntx.hFlag == false) {
					getCounterData(lookupMap, fileLineSplit, cntx);
				} else if (cntx.hFlag == true) {
					getHeader(lookupMap, fileLineSplit, cntx);
				}

			}
			generateCounterResult(true, cntx);
			return "Success";
		} else {
			return "Failed";
		}
	}

	private void generateCounterResult(Boolean partiallySend, GenericCSVParserContext cntx) {

		List<String> counterList = new ArrayList<>();

		
		
		
		 for (Map<String, Object> data : cntx.parseObjectList) {
			 FaultManagementInfo fm_info= new FaultManagementInfo();
			 fm_info.setCore_id(data.get("core_id")==null?0:(Integer)data.get("core_id"));
			 fm_info.setVendor_id(data.get("vendor_id")==null?0:(Integer)data.get("vendor_id"));
			 fm_info.setNe_type_id(data.get("ne_type_id")==null?0:(Integer)data.get("ne_type_id"));
			 fm_info.setNe_id(data.get("ne_id")==null?"":data.get("ne_id").toString());
			 fm_info.setObject_id(data.get("object_id")==null?"":data.get("object_id").toString());
			 fm_info.setObject_name(data.get("object_name")==null?"":data.get("object_name").toString());
			 fm_info.setAlarm_name(data.get("alarm_name")==null?"":data.get("alarm_name").toString());
			 fm_info.setAlarm_object_type(data.get("alarm_object_type")==null?"":data.get("alarm_object_type").toString());
			 fm_info.setAlarm_type(data.get("alarm_type")==null?"":data.get("alarm_type").toString());
			 fm_info.setAlarm_id(data.get("alarm_id")==null?"":data.get("alarm_id").toString());
			 fm_info.setSeverity(data.get("severity")==null?"":data.get("severity").toString());
			 fm_info.setAcknowledgement_state(data.get("acknowledgement_state")==null?"":data.get("acknowledgement_state").toString());
			 fm_info.setAcknowledgement_systemid(data.get("acknowledgement_systemid")==null?"":data.get("acknowledgement_systemid").toString());
			 fm_info.setAcknowledgement_userid(data.get("acknowledgement_userid")==null?"":data.get("acknowledgement_userid").toString());
			 fm_info.setClear_systemid(data.get("clear_systemid")==null?"":data.get("clear_systemid").toString());
			 fm_info.setClear_userid(data.get("clear_userid")==null?"":data.get("clear_userid").toString());
			 fm_info.setStatus(data.get("status")==null?"":data.get("status").toString());
			 fm_info.setOccurance_date(data.get("occurance_date")==null?"":data.get("occurance_date").toString());
			 fm_info.setOccurance_time(data.get("occurance_time")==null?"":data.get("occurance_time").toString());
			 fm_info.setReporting_time(data.get("reporting_time")==null?"":data.get("reporting_time").toString());
			 fm_info.setAcknowledgement_time(data.get("acknowledgement_time")==null?"":data.get("acknowledgement_time").toString());
			 fm_info.setChanged_time(data.get("changed_time")==null?"":data.get("changed_time").toString());
			 fm_info.setClearance_time(data.get("clearance_time")==null?"":data.get("clearance_time").toString());
			 fm_info.setCreated_on(data.get("created_on")==null?"":data.get("created_on").toString());
			 fm_info.setAlarm_escalations(data.get("alarm_escalations")==null?"":data.get("alarm_escalations").toString());
			 fm_info.setAlarm_details(data.get("alarm_details")==null?"":data.get("alarm_details").toString());
			 fm_info.setExternal_alarmid(data.get("external_alarmid")==null?"":data.get("external_alarmid").toString());
			 fm_info.setHref(data.get("href")==null?"":data.get("href").toString());
			 fm_info.setPlanned_outage_indicator(data.get("planned_outage_indicator")==null?"":data.get("planned_outage_indicator").toString());
			 fm_info.setProbable_cause(data.get("probable_cause")==null?"":data.get("probable_cause").toString());
			 fm_info.setProposed_repair_action(data.get("proposed_repair_action")==null?"":data.get("proposed_repair_action").toString());
			 fm_info.setSource_systemid(data.get("source_systemid")==null?"":data.get("source_systemid").toString());
			 fm_info.setReporting_systemid(data.get("reporting_systemid")==null?"":data.get("reporting_systemid").toString());
			 fm_info.setSpecific_problem(data.get("specific_problem")==null?"":data.get("specific_problem").toString());
			 fm_info.setTag(data.get("tag")==null?"":data.get("tag").toString());
			 fm_info.setTower_id(data.get("tower_id")==null?"":data.get("tower_id").toString());
			 fm_info.setSite_id(data.get("site_id")==null?"":data.get("site_id").toString());
			 fm_info.setRegion(data.get("region")==null?"":data.get("region").toString());
			 fm_info.setIs_rootcause(data.get("is_rootcause")==null?false:(Boolean)data.get("is_rootcause"));
			 fm_info.setService_affecting(data.get("service_affecting")==null?false:(Boolean)data.get("service_affecting"));		 
		 
		 }

		if (partiallySend) {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("counterCSV", counterList);
			cntx.pctx.setResult(counterList);
			cntx.pctx.setCompleted(false);
			logger.info("Partial Parsing Completed ! List size is : " + cntx.parseObjectList.size());
			cntx.resultListener.parseComplete(cntx.pctx);
			cntx.parseObjectList.clear();
		} else {
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("counterCSV", counterList);
			cntx.pctx.setResult(counterList);
		}
	}

	private void getHeader(Map<String, String> lookupMap, String[] fileLineSplit, GenericCSVParserContext cntx) {
		for (String header : fileLineSplit) {
			cntx.headerList.add(header.trim());
		}
		cntx.additonal_columns.addAll(cntx.headerList);
		if (cntx.lookupMap.containsKey("unwanted_columns")) {
			cntx.additonal_columns.removeAll(Arrays.stream(cntx.lookupMap.get("unwanted_columns").split(",", -1))
					.map(col -> col.trim()).collect(Collectors.toList()));
			cntx.lookupMap.remove("unwanted_columns");
		}
		for (String key : lookupMap.keySet()) {

			key = key.trim();
			String[] temp = lookupMap.get(key).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			cntx.additonal_columns.remove(temp[0].trim());
			if (!key.equalsIgnoreCase("header")) {
				cntx.headerIndexMap.put(key, cntx.headerList.indexOf(lookupMap.get(temp[0])));
			}

		}
		cntx.hFlag = false;
		cntx.lookupHeaderList.addAll(Arrays.stream(lookupMap.get("header").split(",", -1)).map(head -> head.trim())
				.collect(Collectors.toList()));
	}

	private void getCounterData(Map<String, String> lookupMap, String[] fileLineSplit, GenericCSVParserContext cntx) {
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		for (String col : cntx.lookupHeaderList) {

			if (cntx.headerIndexMap.containsKey(col)) {// if block-1 starts
				String value = fileLineSplit[(Integer) cntx.headerIndexMap.get(col)];
				if (!value.trim().equals("") && value != null) {// if block-2 starts
					String[] temp = cntx.lookupMap.get(col).split(",");
					boolean time_stamp_flag = temp.length > 1 && temp[1].equalsIgnoreCase("time_stamp");
					if (!time_stamp_flag) {
						// if any conditions other than time stamps;

					} else if (time_stamp_flag) {
						String fileDate = fileLineSplit[(Integer) cntx.headerIndexMap.get(col)];// + ":00";
						String giveDformat = temp[2];
						String tableDformat = temp[3];
						value = date_formatter(fileDate, giveDformat, tableDformat);
					}

					dataMap.put(col, value);
				} // if block-2 ends
			} // if block-1 ends
		}
		StringBuilder tag_string = new StringBuilder();
		for (String params : cntx.additonal_columns) {
			String key = params;
			String value = fileLineSplit[(Integer) cntx.headerList.indexOf(key)];
			if (!value.isEmpty() && value != null) {
				tag_string.append(key).append("=").append(value).append("$#");
			}
		}
		String[] staticColumns = lookupMap.get("static_columns").split("~!");
		// Static Columns
		for (String sCol : staticColumns) {
			String[] CnameValue = sCol.split("=");
			dataMap.put(CnameValue[0], CnameValue[1]);
		}
		if (cntx.pInfoFlag == true) {
			cntx.core_id = cntx.parserManifest.getCoreId();
			cntx.vendor_id = cntx.parserManifest.getVendorId();
			cntx.ne_type_id = cntx.parserManifest.getNeTypeId();
			cntx.pInfoFlag = false;
		}
		dataMap.put("core_id", cntx.core_id);
		dataMap.put("vendor_id", cntx.vendor_id);
		dataMap.put("ne_type_id", cntx.ne_type_id);
		cntx.parseObjectList.add(dataMap);
		if (cntx.parseObjectList.size() >= cntx.maxSize) {
			generateCounterResult(true, cntx);
		}
	}

	private String date_formatter(String fileDate, String giveDformat, String tableDformat) {
		try {
			if (giveDformat.equalsIgnoreCase("unixTime")) {
				long fileDateTime = Long.valueOf(fileDate);
				if (fileDate.length() == 10) {
					fileDateTime = fileDateTime * 1000;
				}
				Date unixTimeDate = new java.util.Date(fileDateTime);
				SimpleDateFormat dateTimeFormat = new SimpleDateFormat(tableDformat);
				Calendar c = Calendar.getInstance();
				try {
					c.setTime(dateTimeFormat.parse(dateTimeFormat.format(unixTimeDate)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return dateTimeFormat.format(unixTimeDate);
			} else {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(giveDformat);
				LocalDateTime localDate = LocalDateTime.parse(fileDate, formatter);
				return DateTimeFormatter.ofPattern(tableDformat).format(localDate);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void deinit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(ParseManifestInfo arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(ParseManifestInfo arg0) {
		// TODO Auto-generated method stub
		
	}

	// @Override
	// public synchronized void init(ParseManifestInfo parseManifestInfo) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public synchronized void refresh(ParseManifestInfo parseManifestInfo) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public synchronized void deinit() {
	// // TODO Auto-generated method stub
	//
	// }

}
