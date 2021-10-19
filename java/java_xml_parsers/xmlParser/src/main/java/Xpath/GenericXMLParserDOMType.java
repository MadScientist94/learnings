package Xpath;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cgn.parser.commonutils.CounterInfo;
import com.cgn.parser.library.OptiGoParser;
import com.cgn.parser.outputlistener.ParseResultListener;
import com.cgn.services.parser.FileProcessor;
import com.cgn.services.parser.OptiGoParserMain;
import com.cgn.services.parser.ParseFileInfo;
import com.cgn.services.parser.ParseManifestInfo;
import com.cgn.services.parser.ParserContext;

public class GenericXMLParserDOMType implements OptiGoParser {
Logger logger = LoggerFactory.getLogger(GenericXMLParserDOMType.class);

		public class GenericXMLParserContext {
			List<Document> XMLdom;
			long core_id;
			long vendor_id;
			long ne_type_id;
			FileSystem fs;
			Boolean pInfoFlag = true;
			int maxSize;
			Map<String, String> lookupMap = new HashMap<String, String>();
			Map<String, String> cRenameMap = new LinkedHashMap<String, String>();
			List<String> counterNameList = new LinkedList<String>();
			List<String> devicePropsList = new ArrayList<>();
			List<String> objectPropsList = new ArrayList<>();
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
			GenericXMLParserContext cntx = new GenericXMLParserContext();
			cntx.pctx = pctx;
			cntx.fs = pctx.getFileSystem();
			cntx.parseFileInfo = pfInfo;
			cntx.parserManifest = pctx.getManifestInfo();
			cntx.prop = OptiGoParserMain.getInstance().getProp();
			cntx.resultListener = pctx.getManifestInfo().getParseResultListener();
			cntx.maxSize = Integer.parseInt(cntx.prop.getProperty("parser.linesToWrite", "250000"));
			String rawPath = cntx.parseFileInfo.getFileLocation();
			logger.info("csv Generic Parser File Name : " + rawPath);
			List<String> fileLines=FileProcessor.getInstance().readCSV(pctx,"LookUp",null);
			for(String line:fileLines) {
				if (!line.startsWith("#")) {
					String lookupLineSplit[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", 2);
					cntx.lookupMap.put(lookupLineSplit[0].trim(), lookupLineSplit[1].trim());
				}
			}
			//xml reader
//			cntx.XMLdom=FileProcessor.getInstance().readXML(pctx,"RawFile",cntx.parseFileInfo);
			String result=parseFile( cntx);
			pctx.setFileStatus(result);
			pctx.setCompleted(true);
			cntx.resultListener.parseComplete(pctx);
		}

		private String parseFile(GenericXMLParserContext cntx) {
			if(cntx.XMLdom!=null & cntx.lookupMap.containsKey("base_path_expression")) {
				String base_node_expression=cntx.lookupMap.get("base_path_expression");
				for(Document doc:cntx.XMLdom) {
				if(!"".equals(base_node_expression)) {
					NodeList base_nodes= (NodeList) get_node_by_xpath(cntx, base_node_expression, XPathConstants.NODESET);
					for( int i =0 ; i<base_nodes.getLength();i++) {
						Node base_node= base_nodes.item(i);
						getCounterData(cntx,base_node);
					}
				}
				}
				
				generateCounterResult(true, cntx);
				return "Success";
			} else {
				return "Failed";
			}

		}
		private Object get_node_by_xpath(Object doc,String expression,QName type) {
			XPath xPath = XPathFactory.newInstance().newXPath();
			try {
				return xPath.compile(expression).evaluate(doc,type);
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
			return null;
		}
		private void generateCounterResult(Boolean partiallySend, GenericXMLParserContext cntx) {

			List<String> counterList = new ArrayList<>();
			for (Map<String, Object> map : cntx.parseObjectList) {
				if ((map.get("counter_value") != null) && (map.get("counter_value") != "")
						&& !map.get("counter_value").toString().isEmpty()) {
//					boolean cFlag = true;
					CounterInfo counterRow = new CounterInfo();
					int valLen = map.get("counter_value").toString().length();
					if (valLen != 0) {
						
						/*
						 * if ((map.get("counter_value").toString()).matches("[0-9]*\\.?[0-9]*")) {
						 * BigDecimal counter_Value = new
						 * BigDecimal(map.get("counter_value").toString());
						 * counterRow.setCounter_value(counter_Value); cFlag = false; } if (cFlag ==
						 * true) { counterRow.setCounter_string( map.get("counter_value") == null ? "" :
						 * map.get("counter_value").toString()); } else if (cFlag == false) {
						 * counterRow.setCounter_string( map.get("counter_value") == null ? "" :
						 * map.get("counter_value").toString()); }
						 */
						 
						try {
							BigDecimal counter_Value = new BigDecimal(map.get("counter_value").toString());
							counterRow.setCounter_value(counter_Value);
							counterRow.setCounter_string(
									map.get("counter_value") == null ? "" : map.get("counter_value").toString());
						} catch (NumberFormatException e) {
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
		private void getCounterData(GenericXMLParserContext cntx, Node base_node) {
			Map<String,String> lookup_map =cntx.lookupMap;
			Map<String, Object> data_map = new LinkedHashMap<String, Object>();
			
			for(String head : lookup_map.get("header").split(",")) {
				if(lookup_map.containsKey(head)) {
					String[] temp= lookup_map.get(head).split(",");
					QName type =null;
					String  expression= temp[0];
					if(expression.startsWith("number")) {
						type = XPathConstants.NUMBER;
					} else if(expression.startsWith("boolean")) {
						type = XPathConstants.BOOLEAN;
					} else {
						type = XPathConstants.STRING;
					}
					expression=expression.substring(0, expression.length()-1).split("\\(",2)[1];
					data_map.put(head,get_node_by_xpath(base_node,expression,type) );
					if(head.equalsIgnoreCase("ne_datetime")) {
						if(temp.length==3) {
							date_formatter(data_map, head, temp);
						}
					}
				}
			}
			
			if (cntx.pInfoFlag == true) {
				cntx.core_id = cntx.parserManifest.getCoreId();
				cntx.vendor_id = cntx.parserManifest.getVendorId();
				cntx.ne_type_id = cntx.parserManifest.getNeTypeId();
				cntx.pInfoFlag = false;
			}
			data_map.put("core_id", cntx.core_id);
			data_map.put("vendor_id", cntx.vendor_id);
			data_map.put("ne_type_id", cntx.ne_type_id);

			
			if(lookup_map.containsKey("static_columns")) {
				String [] static_datas= lookup_map.get("static_columns").split("~!");
				for(String static_data: static_datas) {
					data_map.put(static_data.split("=")[0], static_data.split("=")[1]);
				}
			}

				

						cntx.parseObjectList.add(data_map);
						
				
//
//				for (String propName : cntx.devicePropsList) {
//					int index = (int) cntx.headerIndexMap.get(propName);
//					String currentPropValue = fileLineSplit[index];
//					String key = dataMap.get("ne_id") + "";
//					String value = propName + "~#" + currentPropValue;
//					if (!cntx.deviceProps.contains(key + "~#" + value)) {
//						Map<String, Object> deviceProp = new HashMap<>();
//						deviceProp.put("ne_id", dataMap.get("ne_id"));
//						deviceProp.put(propName, currentPropValue);
//						cntx.newDevicePropsList.add(deviceProp);
//						cntx.deviceProps.add(key + "~#" + value);
//					}
//				}
//
//				for (String propName : cntx.objectPropsList) {
//					int index = (int) cntx.headerIndexMap.get(propName);
//					String currentPropValue = fileLineSplit[index];
//					String object_id = "";
//					object_id = (String) dataMap.get("object_name");
//					String key = dataMap.get("ne_id").toString().trim() + "~#" + object_id.trim();
//					String value = propName + "~#" + currentPropValue;
//					if (!cntx.objectProps.contains(key + "~#" + value)) {
//						Map<String, Object> objectProp = new HashMap<>();
//						objectProp.put("ne_id", dataMap.get("ne_id"));
//						objectProp.put("object_name", dataMap.get("object_name"));
//						objectProp.put(propName, currentPropValue);
//						cntx.newObjectPropsList.add(objectProp);
//						cntx.objectProps.add(key + "~#" + value);
//					}
//				}

				if (cntx.parseObjectList.size() >= cntx.maxSize) {
					generateCounterResult(true, cntx);
				}

			
		}

		private void provisionNewDevice(CounterInfo counterRow, GenericXMLParserContext cntx) {
			try {
				if (cntx.neList != null && !cntx.neList.contains(counterRow.getNe_id())) {
					cntx.newDeviceList.add(counterRow.getNewDevicemap());
					cntx.neList.add(counterRow.getNe_id());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void provisionNewObject(CounterInfo counterRow, GenericXMLParserContext cntx) {
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
		
		private void date_formatter(Map<String, Object> return_map, String head, String[] temp) {
			try {
				String fileDate = return_map.get(head).toString();
				String giveDformat = temp[1];
				String tableDformat = temp[2];
				if (giveDformat.equalsIgnoreCase("unixTime")) {
					long fileDateTime = Long.valueOf(fileDate);
					if (fileDate.length() == 10) {
						fileDateTime = fileDateTime * 1000;
					}
					Date unixTimeDate = new java.util.Date(fileDateTime);
					SimpleDateFormat dateTimeFormat = new SimpleDateFormat(tableDformat);
					//dateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Calendar c = Calendar.getInstance();
					try {
						c.setTime(dateTimeFormat.parse(dateTimeFormat.format(unixTimeDate)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return_map.put("ne_datetime", dateTimeFormat.format(unixTimeDate));
					return_map.put("ne_date", dateFormat.format(unixTimeDate));
					return_map.put("ne_hour", c.getTime().getHours());
					return_map.put("ne_minute", c.getTime().getMinutes());
				} else {
					SimpleDateFormat givenDateFormat = new SimpleDateFormat(giveDformat);
					Date date = givenDateFormat.parse(fileDate);
					SimpleDateFormat dateTimeFormat = new SimpleDateFormat(tableDformat);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					// dateTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
					return_map.put("ne_datetime", dateTimeFormat.format(date));
					return_map.put("ne_date", dateFormat.format(date));
				}
			} catch (Exception ex) {
				ex.printStackTrace();

			}
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

		
//		@Override
//		public synchronized void init(ParseManifestInfo parseManifestInfo) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public synchronized void refresh(ParseManifestInfo parseManifestInfo) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public synchronized void deinit() {
//			// TODO Auto-generated method stub
//			
//		}
		
		

	}

