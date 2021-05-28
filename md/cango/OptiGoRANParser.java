package com.cgn.pm.OptiGoRANParser;

import java.math.BigDecimal;
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
import com.cgn.services.parser.ArangoDBService;
import com.cgn.services.parser.DBService;
import com.cgn.services.parser.FileProcessor;
import com.cgn.services.parser.OptiGoParserMain;
import com.cgn.services.parser.ParseFileInfo;
import com.cgn.services.parser.ParseManifestInfo;
import com.cgn.services.parser.ParserContext;

public class OptiGoRANParser implements OptiGoParser{

	Logger logger = LoggerFactory.getLogger(OptiGoRANParser.class);
	static final Map<String,Map<String,Object>> CELL_TOWER_MAP = new HashMap<>();
	public class GenericCSVParserContext {//an inner class for details
		long core_id;
		long vendor_id;
		long ne_type_id;
		FileSystem fs;
		Boolean hFlag = true;
		Boolean pInfoFlag = true;
		int maxSize;
		Map<String, String> lookupMap = new LinkedHashMap<String, String>();
		Map<String, Object> headerIndexMap = new LinkedHashMap<String, Object>();
		StringBuilder sbCounter_nameIndex = new StringBuilder();
		Map<String, String> cRenameMap = new LinkedHashMap<String, String>();
		List<String> headerList = new LinkedList<String>();
		List<String> lookupHeaderList = new LinkedList<String>();
		List<String> counterNameList = new LinkedList<String>();
		List<String> devicePropsList = new ArrayList<>();
		List<String> objectPropsList = new ArrayList<>();
		List<Map<String, Object>> parseObjectList = new LinkedList<Map<String, Object>>();
		Set<String> neList = new HashSet<>();
		Map<String, Set<String>> objectMap = new HashMap<>();
		Map<String, Object> vendorInfo = new HashMap<>();
		List<String> deviceProps = new ArrayList<>();
		List<String> objectProps = new ArrayList<>();
		List<Map<String, Object>> newDeviceList = new ArrayList<>();
		List<Map<String, Object>> newObjectList = new ArrayList<>();
		List<Map<String, Object>> newDevicePropsList = new ArrayList<>();
		List<Map<String, Object>> newObjectPropsList = new ArrayList<>();
		ParseFileInfo parseFileInfo;
		ParseManifestInfo parserManifest;
		ParseResultListener resultListener;
		Properties prop;
		ParserContext pctx;
		List<String> fileLineList = new LinkedList<>();
	}
	
	@Override
	public void parse(ParserContext pctx) {//what is ParserContex
		logger.info("Into RAN Parser...");
		if(CELL_TOWER_MAP.isEmpty()) {//check the CELL_TOWER_MAP isEmpty
			loadCellInfo();// if CELL_TOWER_MAP isEmpty then load it 
		}
		for (ParseFileInfo pfInfo : pctx.getFileInfo()) {//may be csvparsertext data
			try {
				parseFiles(pctx, pfInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	private void loadCellInfo() {
		String query = "for n in Cell filter n.core_name=='RAN' return {cell_name:n.cell_name,tower_id:n.tower_id,"
				+ "ne_type:n.ne_type,ne_id:n.ne_id,ne_name:n.ne_name,vendor:n.vendor,cell_id:n.cell_id}";
		//js or py code
		List<Map<String,Object>> cellDataList =  ArangoDBService.getInstance().executeQuery("nims_poc", query);
		for(Map<String,Object> cellData : cellDataList) {
			String key = cellData.get("tower_id") +"$!"+ cellData.get("cell_name");
			if(!CELL_TOWER_MAP.containsKey(key)) {//check availability data 
				CELL_TOWER_MAP.put(key, cellData);//if not avail enters it in to the map
			}else {
				logger.info("Cell Duplicate NE - " + key);//if data alrady avail logs the presence
			}
		}
		logger.info("CELL_TOWER_MAP SIZE :: " + CELL_TOWER_MAP.size());
	}

	private void parseFiles(ParserContext pctx, ParseFileInfo pfInfo) {
		GenericCSVParserContext cntx = new GenericCSVParserContext();//instance of this class and initializing ParserContext to local variables
		cntx.pctx = pctx;
		cntx.fs = pctx.getFileSystem();
		cntx.parseFileInfo = pfInfo;
		cntx.parserManifest = pctx.getManifestInfo();
		cntx.prop = OptiGoParserMain.getInstance().getProp();
		cntx.resultListener = pctx.getManifestInfo().getParseResultListener();
		cntx.maxSize = Integer.parseInt(cntx.prop.getProperty("parser.linesToWrite", "250000"));
		String rawPath = cntx.parseFileInfo.getFileLocation();
		logger.info("csv Generic Parser File Name : " + rawPath);
		try {//db  connection to initiate two maps [deviceDetailMap,objectDetailMap]
			//assigning local variables [neList,deviceProps]
			/*DBService dbService = DBService.getInstance();
			Map<String, Object> deviceDetailMap = dbService
					.getDeviceDetails((cntx.parserManifest.getNeTypeId() + "").trim());
			Map<String, Object> objectDetailMap = dbService
					.getObjectDetails((cntx.parserManifest.getNeTypeId() + "").trim());
			if (deviceDetailMap != null) {
				cntx.neList = (Set<String>) deviceDetailMap.get("deviceList");
				cntx.deviceProps = (List<String>) deviceDetailMap.get("deviceProps");
				logger.info("Nes Retrieved from DB : " + cntx.neList.size());
			}
			if (objectDetailMap == null) {
				cntx.objectMap = (Map<String, Set<String>>) objectDetailMap.get("objectMap");
				cntx.objectProps = (List<String>) objectDetailMap.get("objectProps");

				logger.info("Objects Retrieved from DB : " + cntx.objectMap.size());
			}*/

			logger.info("Device Props retrieved from DB : " + cntx.deviceProps.size());
			logger.info("Object props retrieved from DB : " + cntx.objectProps.size());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		List<String> fileLines=FileProcessor.getInstance().readCSV(pctx,"LookUp");//reading lookup file line by line and save in array
		for(String line:fileLines) {
			if (!line.startsWith("#")) {//lines not start with # will wnter the loop
				String lookupLineSplit[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", 2);//regex for split by using comma followed by sequence of leters
				cntx.lookupMap.put(lookupLineSplit[0].trim(), lookupLineSplit[1].trim());//creating map by reffering the parser lokeup.txt
			}
		}
		cntx.fileLineList=FileProcessor.getInstance().readCSV(pctx,"RawFile");//raw file read line by line
		if(cntx.lookupMap.containsKey("skipLines")) {
			int skipLines=Integer.parseInt(cntx.lookupMap.get("skipLines"));//from lookup map to local variable
			cntx.fileLineList=cntx.fileLineList.subList(skipLines,cntx.fileLineList.size()-1);//sublist from inclucive  to exclusive
			cntx.lookupMap.remove("skipLines");//remove kv data from lookupmap
		}
		if(cntx.lookupMap.containsKey("vendor_info")) {
			String vendorInfo=cntx.lookupMap.get("vendor_info");//fetch vendor info to local variavle
			cntx.vendorInfo = Arrays.stream(vendorInfo.split("~!"))//array stream ->vendorinfo.split creates array then array is streamed by map
					.map(s -> s.split("=")).collect(Collectors.toMap(s -> s[0].trim(), s -> s[1]));//map  again splits then collected in collect method
			cntx.lookupMap.remove("vendor_info");
			logger.info("vendorInfo - " + cntx.vendorInfo);
		}
		String result=parseFile(cntx.lookupMap, cntx);
		pctx.setFileStatus(result);
		pctx.setCompleted(true);
		cntx.resultListener.parseComplete(pctx);
	}

	private String parseFile(Map<String, String> lookupMap, GenericCSVParserContext cntx) {
		System.out.println("Lookup File Params in cntx:::" + cntx.lookupMap);
		if(cntx.fileLineList!=null && !cntx.fileLineList.isEmpty()) {
			for (String row : cntx.fileLineList) {
				String[] fileLineSplit = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);//it split by comma operator
				if (cntx.hFlag == false) {
					getCounterData(lookupMap, fileLineSplit, cntx,row);
				} else if (cntx.hFlag == true) {
					logger.info("Header row :::"+row);
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
		for (Map<String, Object> map : cntx.parseObjectList) {
			if ((map.get("counter_value") != null) && (map.get("counter_value") != "")
					&& !map.get("counter_value").toString().isEmpty()) {
				try {
					boolean cFlag = true;
					CounterInfo counterRow = new CounterInfo();
					int valLen = map.get("counter_value").toString().length();
					if (valLen != 0) { 
						if ((map.get("counter_value").toString()).matches("[0-9]*\\.?[0-9]*")) {
							BigDecimal counter_Value = new BigDecimal(map.get("counter_value").toString());
							counterRow.setCounter_value(counter_Value);
							cFlag = false;
						}
						if (cFlag == true) {
							counterRow.setCounter_string(
									map.get("counter_value") == null ? "" : map.get("counter_value").toString());
						} else if (cFlag == false) {
							counterRow.setCounter_string(
									map.get("counter_value") == null ? "" : map.get("counter_value").toString());
						}
					}
					counterRow.setVendor_id(Long.valueOf(map.get("vendor_id").toString()));
					counterRow.setCore_id(Long.valueOf(map.get("core_id").toString()));
					counterRow.setNe_type_id(
							Long.valueOf(map.get("ne_type_id") == null ? "0" : map.get("ne_type_id").toString()));
					counterRow.setCounter_id(
							map.get("counter_name") == null ? "" : map.get("counter_name").toString().trim());
					counterRow.setCounter_name(
							map.get("counter_name") == null ? "" : map.get("counter_name").toString().trim());
					counterRow.setDateTime(map.get("ne_datetime") == null ? "" : map.get("ne_datetime").toString());
					counterRow.setGranularity(
							Integer.valueOf(map.get("granularity") == null ? "0" : map.get("granularity").toString()));
					counterRow.setMeasurement_name(
							map.get("measurement_name") == null ? "" : map.get("measurement_name").toString().trim());
					counterRow.setMeasurement_id(Long
							.valueOf(map.get("measurement_id") == null ? "0" : map.get("measurement_id").toString()));
					counterRow.setNe_name(map.get("ne_name") == null ? "" : map.get("ne_name").toString().trim());
					counterRow.setNe_id(map.get("ne_id") == null ? "" : map.get("ne_id").toString());
					counterRow.setObject_name(map.get("object_id") == null ? "" : map.get("object_id") + "");
					counterRow.setObjectId(
							map.get("object_name") == null ? "" : map.get("object_name").toString().trim());
					counterRow.setTag(map.get("tag") == null ? "" : map.get("tag").toString());
					counterRow.setSub_ne_type(map.get("sub_ne_type") == null ? "" : map.get("sub_ne_type").toString());
					counterRow.setInventoryObjectName(
							map.get("object_name") == null ? "" : map.get("object_name").toString().trim());
					provisionNewDevice(counterRow, cntx);
					provisionNewObject(counterRow, cntx);
					counterList.add(counterRow.toString());
				} catch (NumberFormatException e) {
					logger.info("Exception in counter row "+ map,e);
				}
			}
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
			resultMap.put("newDeviceList", cntx.newDeviceList);
			resultMap.put("newObjectList", cntx.newObjectList);
			resultMap.put("newDevicePropsList", cntx.newDevicePropsList);
			resultMap.put("newObjectPropsList", cntx.newObjectPropsList);
			cntx.pctx.setResult(counterList);
		}
	}

	private void getHeader(Map<String, String> lookupMap, String[] fileLineSplit, GenericCSVParserContext cntx) {
		for (String header : fileLineSplit) {
			cntx.headerList.add(header.trim());//iterate the first row from raw data to header list
		}
		logger.info("headerList - " + cntx.headerList);
		for (String key : lookupMap.keySet()) {
			key = key.trim();
			if (key.equalsIgnoreCase("ne_datetime")) {
				String fileDateTime = lookupMap.get(key).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1)[0].trim();
				logger.info("fileDateTime - "  + fileDateTime);
				int index = cntx.headerList.indexOf(fileDateTime.trim());
				if(index==-1) {
					index=0;
				}else {
					logger.info("datetime index Data -" + index);
				}
				cntx.headerIndexMap.put(key, index);
			} else if (key.equalsIgnoreCase("device_props")) {
				String[] deviceProps = lookupMap.get(key).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);//splits all by coma
				for (String str : deviceProps) {
					cntx.devicePropsList.add(str);
					cntx.headerIndexMap.put(str, cntx.headerList.indexOf(str));
				}
			} else if (key.equalsIgnoreCase("object_props")) {
				String[] objectProps = lookupMap.get(key).split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				for (String str : objectProps) {
					cntx.objectPropsList.add(str);
					cntx.headerIndexMap.put(str, cntx.headerList.indexOf(str));
				}
			} else if (key.equalsIgnoreCase("pivot_column")) {
				String[] pivotSplit = lookupMap.get("pivot_column").split("~!");
				for (String pivotIndex : pivotSplit) {
					if (pivotIndex.contains("@#")) {//if delimiter avail in lookup then set renamemap with rename
						String cName = pivotIndex.split("@#")[0];//counter
						String cRename = pivotIndex.split("@#")[1];
						if (cntx.headerList.contains(cName)) {
							cntx.cRenameMap.put(cName.trim(), cRename);
							cntx.counterNameList.add(cName.trim());
							cntx.sbCounter_nameIndex.append(cntx.headerList.indexOf(cName.trim()) + "~");
						}
					} else if (cntx.headerList.contains(pivotIndex)) {//else set renamemap with same name 
						cntx.cRenameMap.put(pivotIndex.trim(), pivotIndex.trim());
						cntx.counterNameList.add(pivotIndex.trim());
						cntx.sbCounter_nameIndex.append(cntx.headerList.indexOf(pivotIndex.trim()) + "~");
					}
				}
				logger.info("sbcountername "+cntx.sbCounter_nameIndex);
				cntx.sbCounter_nameIndex.deleteCharAt(cntx.sbCounter_nameIndex.length() - 1);
				cntx.headerIndexMap.put(key, cntx.sbCounter_nameIndex.toString());
			} else if (!key.equalsIgnoreCase("header")) {
				String lookupVal = lookupMap.get(key);
				if (lookupVal.contains("$!")) {
					String[] paramArr = lookupVal.split("\\$!");
					String value = "";
					for (String singleParam : paramArr) {
						int index = cntx.headerList.indexOf(singleParam);
						if (index == -1) {
							value += singleParam + "@!";
						} else {
							value += index + "@!";
						}
					}
					value = value.substring(0, value.length() - 2);
					cntx.headerIndexMap.put(key, value);
				} else {
					cntx.headerIndexMap.put(key, cntx.headerList.indexOf(lookupMap.get(key)));
				}
				
			}
		}
		cntx.hFlag = false;
		String[] lookupHeader = lookupMap.get("header").split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		for (String header : lookupHeader) {
			cntx.lookupHeaderList.add(header.trim());
		}
		logger.info("headerIndexMap - " +cntx.headerIndexMap);
	}

	private void getCounterData(Map<String, String> lookupMap, String[] fileLineSplit, GenericCSVParserContext cntx,String row) {
		try {
			String[] counterValues = cntx.headerIndexMap.get("pivot_column").toString().split("~");//get stringbuilder value and split
			int i = 0;
			Map<String, Object> dataMap = null;
			for (String counters : cntx.counterNameList) {
				try {
					dataMap = new LinkedHashMap<String, Object>();
					for (String col : cntx.lookupHeaderList) {
						if (cntx.headerIndexMap.keySet().contains(col) && !col.equalsIgnoreCase("pivot_column")) {
							if (!col.equalsIgnoreCase("ne_datetime") && !col.equalsIgnoreCase("ne_date")
									&& !col.equalsIgnoreCase("ne_minute") && !col.equalsIgnoreCase("ne_hour")) {
								String value = "";
								String param = cntx.headerIndexMap.get(col) + "";
								if (param.contains("@!")) { 
									String[] paramList = param.split("\\@!");
									for (String singleParam : paramList) {
										try {
											int index = Integer.parseInt(singleParam);
											if("".equalsIgnoreCase(value)) {
												value = fileLineSplit[index];
											}else {
												value = value + "$!"+ fileLineSplit[index];//current row passed through method arguments
											}
										} catch (NumberFormatException num) {
											/*
											 * if (singleParam.contains("File_")) { singleParam =
											 * singleParam.replaceFirst("File_", ""); value +=
											 * cntx.fileParamsMap.get(singleParam); } else
											 */if (singleParam.contains("Const_")) {
												singleParam = singleParam.replaceFirst("Const_", "");
												value += singleParam;
											}
										}
									}
									if("ne_id".equalsIgnoreCase(col) &&  !"".equalsIgnoreCase(value)){//get cell info
										String towercellKey = value;
										//logger.info("towercellKey - " + towercellKey);
										if(CELL_TOWER_MAP.containsKey(towercellKey)) {
											Map<String,Object> cellInfo = CELL_TOWER_MAP.get(towercellKey);
											//logger.info("cellInfo - "+ cellInfo);
											value = cellInfo.get("ne_id")+"";
											dataMap.put(col, value);//unwanted?
											dataMap.put("object_id",cellInfo.get("cell_id"));
											if(dataMap.get("vendor_id")==null) {
												String vendor = cellInfo.get("vendor")==null?"":cellInfo.get("vendor").toString().toLowerCase();
												dataMap.put("vendor_id", cntx.vendorInfo.get(vendor)==null?"":cntx.vendorInfo.get(vendor));
											}
											//logger.info("dataMap in NEID - " + dataMap);
											dataMap.put(col, value);
										}else {
											logger.info("NEID Missing:::" + towercellKey + "::FileName::"
													+ cntx.parseFileInfo.getFileName());
										}
									}else {
										if(!dataMap.containsKey(col)) {
											dataMap.put(col, value);
										}
									}
								} else {//col that is present in headerIndexMap 
									int index = (Integer) cntx.headerIndexMap.get(col);
									if (index != -1) {
										value = fileLineSplit[index];
									} /*
										 * else { value = fileParamsMap.get(lookupMap.get(col)); }
										 */
									if ("vendor_id".equalsIgnoreCase(col)) {
										int venIndex = (Integer) cntx.headerIndexMap.get("vendor_id");
										String vendorName = fileLineSplit[venIndex];
										if (!"".equalsIgnoreCase(vendorName)) {
											value = cntx.vendorInfo.get(vendorName.split("_")[0].toLowerCase())+"";
											dataMap.put(col, value);
										}else {
											logger.info("VENDOR NOT MATCHED NeName:::" + vendorName + "::FileName::"
													+ cntx.parseFileInfo.getFileName() + "::value::" + value);
										}
									}  
									else {
										if(!dataMap.containsKey(col)) {
											dataMap.put(col, value);
										}
									}
								}
							} else if (col.equalsIgnoreCase("ne_datetime")) {//for setting date time
								try {
									logger.info("fileLineSplit size - " + fileLineSplit.length);
									String fileDate = fileLineSplit[(Integer) cntx.headerIndexMap.get(col)];// + ":00";
									String giveDformat = lookupMap.get("ne_datetime").split(",")[1];
									String tableDformat = lookupMap.get("ne_datetime").split(",")[2];
									if (giveDformat.equalsIgnoreCase("unixTime")) { 
										long fileDateTime = Long.valueOf(fileDate);
										if (fileDate.length() == 10) {
											fileDateTime = fileDateTime * 1000;
										}
										Date unixTimeDate = new java.util.Date(fileDateTime);
										SimpleDateFormat dateTimeFormat = new SimpleDateFormat(tableDformat);
										SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
										Calendar c = Calendar.getInstance();
										try {
											c.setTime(dateTimeFormat.parse(dateTimeFormat.format(unixTimeDate)));
										} catch (ParseException e) {
											e.printStackTrace();
										}
										dataMap.put("ne_datetime", dateTimeFormat.format(unixTimeDate));
										dataMap.put("ne_date", dateFormat.format(unixTimeDate));
										dataMap.put("ne_hour", c.getTime().getHours());
										dataMap.put("ne_minute", c.getTime().getMinutes());
									} else {
										DateTimeFormatter formatter = DateTimeFormatter.ofPattern(giveDformat);
										LocalDateTime localDate = LocalDateTime.parse(fileDate, formatter);
										String ne_datetime = DateTimeFormatter.ofPattern(tableDformat)
												.format(localDate);
										String ne_date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
										String ne_hour = DateTimeFormatter.ofPattern("HH").format(localDate);
										String ne_minute = DateTimeFormatter.ofPattern("mm").format(localDate);
										dataMap.put("ne_datetime", ne_datetime);
										dataMap.put("ne_date", ne_date);
										dataMap.put("ne_hour", ne_hour);
										dataMap.put("ne_minute", ne_minute);
									}
								} catch (Exception ex) {
									logger.info("Exception in getCounter Data - "+ row,ex);
									ex.printStackTrace();
								}
							}
						} else if (!col.equalsIgnoreCase("pivot_column") && !col.equalsIgnoreCase("ne_date")//why its here
								&& !col.equalsIgnoreCase("ne_minute") && !col.equalsIgnoreCase("ne_hour")
								&& !col.equalsIgnoreCase("ne_datetime") && !col.equalsIgnoreCase("ne_datetime")) {
							if(!dataMap.containsKey(col)) {
								dataMap.put(col, null);
							}
						}
					}
					dataMap.put("counter_name", cntx.cRenameMap.get(counters));// counterNames[i]);
					if("-1".equalsIgnoreCase(counterValues[i])) {
						continue;
					}
					dataMap.put("counter_value", fileLineSplit[Integer.parseInt(counterValues[i])]);

					String[] staticColumns = lookupMap.get("static_columns").split("~!");

					// Static Columns
					for (String sCol : staticColumns) {
						String[] CnameValue = sCol.split("=");
						dataMap.put(CnameValue[0], CnameValue[1]);
					}
					if (cntx.parseFileInfo.getFileName().contains("2G")) {
						dataMap.put("sub_ne_type", "2G");
					} else if (cntx.parseFileInfo.getFileName().contains("3G")) {
						dataMap.put("sub_ne_type", "3G");
					} else if (cntx.parseFileInfo.getFileName().contains("4G")) {
						dataMap.put("sub_ne_type", "4G");
					}
					if (cntx.pInfoFlag == true) {
						cntx.core_id = cntx.parserManifest.getCoreId();
						//cntx.vendor_id = cntx.parserManifest.getVendorId();
						cntx.ne_type_id = cntx.parserManifest.getNeTypeId();
						cntx.pInfoFlag = false;
					}
					dataMap.put("core_id", cntx.core_id);
					//dataMap.put("vendor_id", cntx.vendor_id);
					dataMap.put("ne_type_id", cntx.ne_type_id);
					if(dataMap.get("ne_id") != null && !"".equalsIgnoreCase(dataMap.get("ne_id")+"") 
							&& dataMap.get("counter_value") != null && !"".equalsIgnoreCase(dataMap.get("counter_value")+"")) {
						logger.info("RAN Data Added" + dataMap);
						cntx.parseObjectList.add(dataMap);
					}else {
						logger.info("DATA Missing - " + dataMap);
					}
					i++;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			for (String propName : cntx.devicePropsList) {
				int index = (int) cntx.headerIndexMap.get(propName);
				String currentPropValue = fileLineSplit[index];
				String key = dataMap.get("ne_id") + "";
				String value = propName + "~#" + currentPropValue;
				if (!cntx.deviceProps.contains(key + "~#" + value)) {
					Map<String, Object> deviceProp = new HashMap<>();
					deviceProp.put("ne_id", dataMap.get("ne_id"));
					deviceProp.put(propName, currentPropValue);
					cntx.newDevicePropsList.add(deviceProp);
					cntx.deviceProps.add(key + "~#" + value);
				}
			}

			for (String propName : cntx.objectPropsList) {
				int index = (int) cntx.headerIndexMap.get(propName);
				String currentPropValue = fileLineSplit[index];
				String object_id = "";
				object_id = (String) dataMap.get("object_name");
				String key = dataMap.get("ne_id").toString().trim() + "~#" + object_id.trim();
				String value = propName + "~#" + currentPropValue;
				if (!cntx.objectProps.contains(key + "~#" + value)) {
					Map<String, Object> objectProp = new HashMap<>();
					objectProp.put("ne_id", dataMap.get("ne_id"));
					objectProp.put("object_name", dataMap.get("object_name"));
					objectProp.put(propName, currentPropValue);
					cntx.newObjectPropsList.add(objectProp);
					cntx.objectProps.add(key + "~#" + value);
				}
			}

			if (cntx.parseObjectList.size() >= cntx.maxSize) {
				generateCounterResult(true, cntx);
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private void provisionNewDevice(CounterInfo counterRow, GenericCSVParserContext cntx) {
		try {
			if (cntx.neList != null && !cntx.neList.contains(counterRow.getNe_id())) {
				cntx.newDeviceList.add(counterRow.getNewDevicemap());
				cntx.neList.add(counterRow.getNe_id());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void provisionNewObject(CounterInfo counterRow, GenericCSVParserContext cntx) {
		try {
			if (cntx.objectMap != null) {
				Set<String> objectSet = cntx.objectMap.get(counterRow.getNe_id());
				if (objectSet == null) {
					objectSet = new HashSet<>();
				}
				if (!counterRow.getObjectId().equals("") && !objectSet.contains(counterRow.getObjectId())) {
					cntx.newObjectList.add(counterRow.getNewObjectmap());
					objectSet.add(counterRow.getObjectId());
					cntx.objectMap.put(counterRow.getNe_id(), objectSet);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
