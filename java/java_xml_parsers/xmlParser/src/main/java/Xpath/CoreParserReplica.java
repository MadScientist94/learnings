package Xpath;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

import com.cgn.parser.commonutils.CounterInfo;
import com.cgn.parser.library.OptiGoParser;
import com.cgn.parser.outputlistener.ParseResultListener;
import com.cgn.services.parser.FileProcessor;
import com.cgn.services.parser.OptiGoParserMain;
import com.cgn.services.parser.ParseFileInfo;
import com.cgn.services.parser.ParseManifestInfo;
import com.cgn.services.parser.ParserContext;

public class CoreParserReplica implements OptiGoParser {

	Logger logger = LoggerFactory.getLogger(CoreParserReplica.class);

	static Map<String, String> lookup = new HashMap<>();

	public class GenericCSVParserContext {
		long core_id;
		long vendor_id;
		long ne_type_id;
		FileSystem fs;
		Boolean hFlag = true;
		Boolean pInfoFlag = true;
		Boolean skipFlag = false;
		int maxSize;
		Map<String, String> lookupMap = new LinkedHashMap<String, String>();
		/** finding the index-position of the lookup map elements in the header row */
		Map<String, Object> headerIndexMap = new LinkedHashMap<String, Object>();
		StringBuilder sbCounter_nameIndex = new StringBuilder();
		Map<String, String> cRenameMap = new LinkedHashMap<String, String>();
		List<String> headerList = new LinkedList<String>();
		List<String> lookupHeaderList = new LinkedList<String>();
		List<String> counterNameList = new LinkedList<String>();
		List<String> devicePropsList = new ArrayList<>();
		List<String> objectPropsList = new ArrayList<>();
		Map<String, String> custommeasurcounterListMap = new LinkedHashMap<String, String>();
		List<Map<String, Object>> parseObjectList = new LinkedList<Map<String, Object>>();
		Set<String> neList = new HashSet<>();
		Map<String, Set<String>> objectMap = new HashMap<>();;
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
		List<NodeList> fileLineList = new LinkedList<>();
		
		Map<String, Object> vendorInfo = new HashMap<>();
		Map<String, Object> ne_typeInfo = new HashMap<>();
		List<String> counterList = new ArrayList<>();

	}

	@Override
	public void parse(ParserContext pctx) {
		logger.info("Into CORE Parser..." + pctx.getFileInfo().size());
		long pcst = System.currentTimeMillis();
		logger.info("ParserContext Started....");
		
		synchronized (lookup) {
			if (lookup.isEmpty()) {
				List<String> fileLines = FileProcessor.getInstance().readCSV(pctx, "LookUp", null);
				for (String line : fileLines) {
					if (!line.startsWith("#")) {
						String lookupLineSplit[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", 2);
						lookup.put(lookupLineSplit[0].trim(), lookupLineSplit[1].trim());
					}
				}
			}
		}
		logger.info("Into CORE Parser..." + pctx.getFileInfo().size());
		int maxRowLimit = Integer
				.parseInt(OptiGoParserMain.getInstance().getProp().getProperty("parser.linesToWrite", "250000"));
		for (ParseFileInfo pfInfo : pctx.getFileInfo()) {
			try {
				pfInfo.setParsingStartTime(System.currentTimeMillis());
				logger.info(
						"FileParsing Started - " + pfInfo.getParsingStartTime() + "::FN::" + pfInfo.getFileLocation());
				parseFiles(pctx, pfInfo);
			} catch (Exception e) {
				logger.error("Exception in parse " + pfInfo.getFileLocation(), e);
			}
			pfInfo.setParsingEndTime(System.currentTimeMillis());
			logger.info("FileParsing Ended- " + pfInfo.getParsingTimeTaken() + "::FN::" + pfInfo.getFileLocation());
			if (((List<String>) pctx.getResult()).size() >= maxRowLimit) {
				pctx.setCompleted(false);
				long st = System.currentTimeMillis();
				logger.info("Partial Parsed for File Started");
				pctx.getManifestInfo().getParseResultListener().parseComplete(pctx);
				logger.info("Partial Parsed for File Completed:::" + (System.currentTimeMillis() - st) + " Seconds");
				pctx.setResult(new ArrayList<String>());
			}
		}
		pctx.setCompleted(true);
		long st = System.currentTimeMillis();
		logger.info("Complete Parsed for File Started...");
		pctx.getManifestInfo().getParseResultListener().parseComplete(pctx);
		logger.info("Complete Parsed for File Completed:::" + (System.currentTimeMillis() - st) + " Seconds");

		logger.info("ParserContext Completed:::" + (System.currentTimeMillis() - pcst) + " Seconds");
	}

	private void parseFiles(ParserContext pctx, ParseFileInfo pfInfo) {

		GenericCSVParserContext cntx = new GenericCSVParserContext();
		cntx.pctx = pctx;
		cntx.fs = pctx.getFileSystem();
		cntx.parserManifest = pctx.getManifestInfo();
		cntx.prop = OptiGoParserMain.getInstance().getProp();
		cntx.resultListener = pctx.getManifestInfo().getParseResultListener();
		cntx.parseFileInfo = pfInfo;
		String rawPath = cntx.parseFileInfo.getFileLocation();
		cntx.lookupMap.putAll(lookup);
		logger.info("csv Generic Parser File Name : " + rawPath);

		/** Skip Files that mentioned in lookup file will be deleted */
		if (cntx.lookupMap.containsKey("skipFiles")) {
			String[] skipFiles = cntx.lookupMap.get("skipFiles").split("!#");
			for (String prefix : skipFiles) {
				if (cntx.parseFileInfo.getFileName().startsWith(prefix)) {
					cntx.skipFlag = true;
					break;
				}

			}
		}
		if (!cntx.skipFlag) {
//			cntx.fileLineList = FileProcessor.getInstance().readXML(cntx.pctx, "RawFile", cntx.parseFileInfo);//base path add 
			String result = parseFile(cntx.lookupMap, cntx);
			cntx.pctx.setFileStatus(result);
			pfInfo.setStatus(result);

		} else {
			logger.info("file name : " + cntx.parseFileInfo.getFileName() + " was skipped from parsing");
			pfInfo.setStatus("Skipped");
			cntx.pctx.setFileStatus("Success");
			pctx.setResult(new ArrayList<String>());
		}
	}

	private String parseFile(Map<String, String> lookupMap, GenericCSVParserContext cntx) {
		if (cntx.fileLineList != null && !cntx.fileLineList.isEmpty()) {
//			for (String row : cntx.fileLineList) {
//				String[] fileLineSplit = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//				
//					getCounterData(lookupMap, fileLineSplit, cntx);
//				
//			}
			generateCounterResult(cntx);
			return "Success";
		} else {
			return "Failed";
		}
	}

	private void generateCounterResult(GenericCSVParserContext cntx) {
		List<String> result = new ArrayList<String>();
		if (cntx.pctx.getResult() != null) {
			result = (List<String>) cntx.pctx.getResult();
		}
		result.addAll(cntx.counterList);
		cntx.pctx.setResult(result);
		logger.info("Parsing List size is : " + cntx.counterList.size() + "-" + result.size());
		cntx.counterList.clear();

	}


	private void getCounterData(Map<String, String> lookupMap, String[] fileLineSplit, GenericCSVParserContext cntx) {
		try {
			Boolean checkFlag = true;
			String neName=null;
			long vendor_id = 0;
			String neType_id = null;
			String[] counterValues = cntx.headerIndexMap.get("pivot_column").toString().split("~");
			int i = 0;
			Map<String, Object> dataMap = null;
			for (String counters : cntx.counterNameList) {
	
				if (neName == null) {
					int deviceIndex1 = (Integer) cntx.headerIndexMap.get("ne_id");
					logger.info("NE name not  in NODE_LOOKUP : " +fileLineSplit[deviceIndex1].split("_")[0]+" ::: file name : "+ cntx.parseFileInfo.getFileName());
					continue;
				}

				try {
					dataMap = new LinkedHashMap<String, Object>();
					for (String col : cntx.lookupHeaderList) {
//						logger.info(col +":colum & " +cntx.headerIndexMap.keySet().contains(col)+" :col index");
						if (cntx.headerIndexMap.keySet().contains(col) && !col.equalsIgnoreCase("pivot_column")) {
							if (!col.equalsIgnoreCase("ne_datetime") && !col.equalsIgnoreCase("ne_date")
									&& !col.equalsIgnoreCase("ne_minute") && !col.equalsIgnoreCase("ne_hour")) {
								String value = "";
								String param = cntx.headerIndexMap.get(col) + "";
								if (param.contains("@!")) {
									String[] paramList = param.split("\\@!");
									int paramCount = 0;
									for (String singleParam : paramList) {
										try {
											int index = Integer.parseInt(singleParam);
											String delimiter = paramCount < (paramList.length - 1) ? "$!" : "";
											value += fileLineSplit[index] + delimiter;
										} catch (NumberFormatException num) {
											if (singleParam.contains("Const_")) {
												singleParam = singleParam.replaceFirst("Const_", "");
												value += singleParam;
											}
										}
										paramCount++;
									}}

									if ("ne_id".equalsIgnoreCase(col)) {

//										logger.info(col +" : neName :"+neName);
										
									
									
									dataMap.put(col, value);
//									logger.info("dm neid : " +dataMap);
								} else {
									int index = (Integer) cntx.headerIndexMap.get(col);
									if (index != -1) {
										value = fileLineSplit[index];
									}
									dataMap.put(col, value);
								}
							} else if (col.equalsIgnoreCase("ne_datetime")) {// #one date for one file so move to top
								try {
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
										SimpleDateFormat givenDateFormat = new SimpleDateFormat(giveDformat);
										Date date = givenDateFormat.parse(fileDate);
										SimpleDateFormat dateTimeFormat = new SimpleDateFormat(tableDformat);
										SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
										dataMap.put("ne_datetime", dateTimeFormat.format(date));
										dataMap.put("ne_date", dateFormat.format(date));
									}
								} catch (Exception ex) {
									ex.printStackTrace();

								}
							}
						} else if (!col.equalsIgnoreCase("pivot_column") && !col.equalsIgnoreCase("ne_date")
								&& !col.equalsIgnoreCase("ne_minute") && !col.equalsIgnoreCase("ne_hour")
								&& !col.equalsIgnoreCase("ne_datetime") && !col.equalsIgnoreCase("ne_datetime")) {
							dataMap.put(col, null);
						}
					}
					dataMap.put("counter_name", cntx.cRenameMap.get(counters));// counterNames[i]);
					dataMap.put("counter_value", fileLineSplit[Integer.parseInt(counterValues[i])]);
					if(cntx.parseFileInfo.getFileName().startsWith("Sheet1_CS_Core_hw_hss_KPI")) {
						logger.info("hlr File data : " +fileLineSplit[Integer.parseInt(counterValues[i])]);
					}
					String[] staticColumns = lookupMap.get("static_columns").split("~!");
					// Static Columns
					for (String sCol : staticColumns) {
						String[] CnameValue = sCol.split("=");
						dataMap.put(CnameValue[0], CnameValue[1]);
					}
					if (cntx.pInfoFlag == true) {

						cntx.core_id = cntx.parserManifest.getCoreId();
						cntx.vendor_id = cntx.parserManifest.getVendorId();// # from tower lookup or manifest
						cntx.ne_type_id = cntx.parserManifest.getNeTypeId();// # from tower lookup or manifest
						cntx.pInfoFlag = false;
					}

					dataMap.put("core_id", cntx.core_id);
					dataMap.put("vendor_id", cntx.vendor_id>0?cntx.vendor_id:vendor_id);
						dataMap.put("ne_name", neName);
						dataMap.put("ne_type_id",cntx.ne_type_id>0?cntx.ne_type_id:neType_id);
					
					if (cntx.lookupMap.containsKey("customcounterlist")) {
						String counterName = dataMap.get("counter_name") + "";
						if (cntx.custommeasurcounterListMap.containsKey(counterName)) {
							dataMap.put("measurement_name", cntx.custommeasurcounterListMap.get(counterName));
						}
					}
//					 logger.info("generated dataMap : " + dataMap);
					 if(dataMap.get("measurement_name").toString().equalsIgnoreCase("Object")) {
						 //object id will come here 
					 }

					boolean isDeviceValid = 
							dataMap.get("counter_value") != null && !"".equalsIgnoreCase(dataMap.get("counter_value").toString())&& 
							dataMap.get("ne_id") != null && !"".equalsIgnoreCase(dataMap.get("ne_id").toString())&& 
							dataMap.get("ne_type_id") != null && !"".equalsIgnoreCase(dataMap.get("ne_type_id").toString())&& 
							dataMap.get("vendor_id") != null && (long)dataMap.get("vendor_id")!=0;
					boolean isObjectValid= isDeviceValid &&dataMap.get("object_id") != null && !"".equalsIgnoreCase(dataMap.get("object_id")+"");
					String ne_measurement_type=dataMap.get("measurement_name").toString();
					if ((ne_measurement_type.equals("Device")&&isDeviceValid)||
							(isObjectValid)) {
						boolean cFlag = true;
						CounterInfo counterRow = new CounterInfo();
						int valLen = dataMap.get("counter_value").toString().length();
						if (valLen != 0) {
							if ((dataMap.get("counter_value").toString()).matches("[0-9]*\\.?[0-9]*")) {
								BigDecimal counter_Value = new BigDecimal(dataMap.get("counter_value").toString());
								counterRow.setCounter_value(counter_Value);
								cFlag = false;
							}
							if (cFlag == true) {
								counterRow.setCounter_string(dataMap.get("counter_value") == null ? ""
										: dataMap.get("counter_value").toString());
							} else if (cFlag == false) {
								counterRow.setCounter_string(dataMap.get("counter_value") == null ? ""
										: dataMap.get("counter_value").toString());
							}
						}
						counterRow.setVendor_id(Long.valueOf(dataMap.get("vendor_id").toString()));
						counterRow.setCore_id(Long.valueOf(dataMap.get("core_id").toString()));
						counterRow.setNe_type_id(Long.valueOf(
								dataMap.get("ne_type_id") == null ? "0" : dataMap.get("ne_type_id").toString()));
						counterRow.setCounter_id(dataMap.get("counter_name") == null ? ""
								: dataMap.get("counter_name").toString().trim());// why counter
						counterRow.setCounter_name(dataMap.get("counter_name") == null ? ""
								: dataMap.get("counter_name").toString().trim());
						counterRow.setDateTime(
								dataMap.get("ne_datetime") == null ? "" : dataMap.get("ne_datetime").toString());
						counterRow.setGranularity(Integer.valueOf(
								dataMap.get("granularity") == null ? "0" : dataMap.get("granularity").toString()));
						counterRow.setMeasurement_name(dataMap.get("measurement_name") == null ? ""
								: dataMap.get("measurement_name").toString().trim());
						counterRow.setMeasurement_id(Long.valueOf(dataMap.get("measurement_id") == null ? "0"
								: dataMap.get("measurement_id").toString()));
						counterRow.setNe_name(
								dataMap.get("ne_name") == null ? "" : dataMap.get("ne_name").toString().trim());
						counterRow.setNe_id(dataMap.get("ne_id") == null ? "" : dataMap.get("ne_id").toString());
						counterRow
								.setObject_name(dataMap.get("object_id") == null ? "" : dataMap.get("object_id") + "");
						counterRow.setObjectId(
								dataMap.get("object_name") == null ? "" : dataMap.get("object_name").toString().trim());
						counterRow.setTag(dataMap.get("tag") == null ? "" : dataMap.get("tag").toString());
						counterRow.setSub_ne_type(
								dataMap.get("sub_ne_type") == null ? "" : dataMap.get("sub_ne_type").toString());
						counterRow.setInventoryObjectName(
								dataMap.get("object_name") == null ? "" : dataMap.get("object_name").toString().trim());
						if(counterRow.getCounter_value() == null || "".equalsIgnoreCase(counterRow.getCounter_value() + "")) {
							logger.info("Counter Value Missing - " + counterRow.toString());
						}
						else{
						cntx.counterList.add(counterRow.toString());
						}
						logger.info("Counter Row Added to counterList :" + counterRow);
					} else {
						 logger.info("DATA Missing - " + dataMap);
					}
					i++;
				} catch (Exception e) {
					e.printStackTrace();
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
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

	private String neNameTrimmer(String counters, String[] fileLineSplit, GenericCSVParserContext cntx) {
		String neName1=null;
		int deviceIndex1 = (Integer) cntx.headerIndexMap.get("ne_id");
		if ("Object".equals(cntx.custommeasurcounterListMap.get(cntx.cRenameMap.get(counters)))) {
			neName1 = fileLineSplit[deviceIndex1].split("_")[0];
		} else {
			neName1 = fileLineSplit[deviceIndex1];
			if (neName1.contains("_")) {
				for (String ps : cntx.lookupMap.get("ne_id_prefix_sufix").split(",")) {
					if (neName1.contains(ps)) {
//logger.info("prefix: "+ps);
//logger.info("nename1: " +neName1);
						neName1=neName1.replace(ps, "");
//						logger.info("nename1: " +neName1);

					}
				}
			}
		}
//		logger.info("nename1 return: " +neName1);

		return neName1;
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

//	private void add_vendor_id(Map<String, Object> dataMap, GenericCSVParserContext cntx) {
//		if (nodeInfoLookupMap.containsKey(dataMap.get("ne_id"))) {
//			String vendor = nodeInfoLookupMap.get(dataMap.get("ne_id")).split("~!@")[1];
//			if (vendor == null || vendor.equals("")) {
//				if (cntx.parseFileInfo.getFileName().toLowerCase().contains("_hw_"))
//					cntx.vendor_id = Long.valueOf(cntx.vendorInfo.get("huawei").toString());
//				if (cntx.parseFileInfo.getFileName().toLowerCase().contains("_ericsson_"))
//					cntx.vendor_id = Long.valueOf(cntx.vendorInfo.get("ericsson").toString());
//				if (cntx.parseFileInfo.getFileName().toLowerCase().contains("_cisco_"))
//					cntx.vendor_id = Long.valueOf(cntx.vendorInfo.get("cisco").toString());
//
//			} else {
//				try {
//					cntx.vendor_id = Long.valueOf(cntx.vendorInfo.get(vendor).toString());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}


	}


