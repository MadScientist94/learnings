package com.jo.scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class complexGetter {
	TreeMap<String,TreeMap<String,String>> result= new TreeMap<String,TreeMap<String,String>>();
	String [] ips= {"192.168.200.101","192.168.200.102","192.168.200.103","192.168.200.104","192.168.200.106"};
//	  OID [] oids= {new OID("1.3.6.1.2.1.2.2.1.1"),new OID("1.3.6.1.2.1.2.2.1.2"),new OID("1.3.6.1.2.1.2.2.1.4")};// if index, if descr, if mtu
	 final String [] oids= {"1.3.6.1.2.1.2.2.1.1","1.3.6.1.2.1.2.2.1.2","1.3.6.1.2.1.2.2.1.4"};
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		complexGetter i =new complexGetter();
//		i.initialize();
//	}

	TreeMap<String, TreeMap<String, String>> initialize() {
	for(String ip : ips)
	 createSesson(ip);
	return result;
}

	private TreeMap<String, TreeMap<String, String>> createSesson(final String ip) {
//		 TransportMapping transport;
//			
//				try {
//					transport = new DefaultUdpTransportMapping();
//				
//			
//			    transport.listen();
//			    final Snmp snmp = new Snmp(transport);
////			    snmp.listen();
//			    CommunityTarget target = new CommunityTarget();
//		   target.setCommunity(new OctetString("public"));
//		   target.setAddress(GenericAddress.parse(ip+"/161")); // supply your own udp address and port
//		   target.setRetries(2);
//		   target.setTimeout(1500);
//		   target.setVersion(SnmpConstants.version2c);
//
//			  
//			PDU pdu = new PDU();
//			pdu.setType(PDU.GET);
//			pdu.setRequestID(new Integer32(1));
//			pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.1.0")));
		List sessionDetails=(ArrayList)session(ip,"1.3.6.1.2.1.2.1.0","get");
//		System.out.println(sessionDetails);
		Snmp snmp=(Snmp)sessionDetails.get(0);
		CommunityTarget target=(CommunityTarget)sessionDetails.get(1);
		PDU pdu=(PDU)sessionDetails.get(2);
		try {
			snmp.listen();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//get next listener
//		System.out.println(pdu);			//get listener
			ResponseListener getListen= new ResponseListener() {
//		    	 final String IP=ip;
		    	
		    	public void onResponse(ResponseEvent e) {
		    		if (e.getResponse()!=null) {
		    			String[] currentOids=oids;
					int indexNumber=Integer.parseInt(e.getResponse().get(0).toString().split(" = ")[1]);
					TreeMap<String,String>  values= new TreeMap();
				for(String currentOid: currentOids) {
					String changeOid=currentOid;
					
					for (int i=0; i<indexNumber;i++) {
						List listenSessionDetails=(ArrayList)session(ip,changeOid,"next");
//						System.out.println(sessionDetails);
						Snmp listenSnmp=(Snmp)listenSessionDetails.get(0);
						CommunityTarget listenTarget=(CommunityTarget)listenSessionDetails.get(1);
						PDU listenPdu=(PDU)listenSessionDetails.get(2);
//						System.out.println(listenPdu);
						try {listenSnmp.listen();
						
								ResponseEvent res=listenSnmp.getNext(listenPdu, listenTarget);
								
								if (res!=null) {
									System.out.println();
									VariableBinding responsVB=res.getResponse().get(0);
									
									values.put(responsVB.getOid().toString(), responsVB.getVariable().toString());
									result.put(ip+"/161", values);
	System.out.println(result);
									changeOid=res.getResponse().get(0).getOid().toString();
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
					
					}}
				}}
		    };
		    try {
				snmp.get(pdu, target,null,getListen);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    try {
				TimeUnit.SECONDS.sleep(1);
				
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			this.wait();
			
				return result;
	}
	
	
	
	
	
List session(String ip,String oid,String type) {	
	 TransportMapping transport;
	 List l = new ArrayList();
		try {
			transport = new DefaultUdpTransportMapping();
		
	
	    transport.listen();
	  Snmp snmp = new Snmp(transport);
//	    snmp.listen();
	    CommunityTarget target = new CommunityTarget();
target.setCommunity(new OctetString("public"));
target.setAddress(GenericAddress.parse(ip+"/161")); // supply your own udp address and port
target.setRetries(2);
target.setTimeout(1500);
target.setVersion(SnmpConstants.version2c);

	  
	PDU pdu = new PDU();
	pdu.setRequestID(new Integer32(1));
	pdu.add(new VariableBinding(new OID(oid)));
	if (type=="get") {
	pdu.setType(PDU.GET);
//	System.out.println(snmp.get(pdu, target).getResponse());
	}
	else if (type=="next") { pdu.setType(PDU.GETNEXT);
//	System.out.println(snmp.getNext(pdu, target).getResponse());
	}

	//System.out.println(snmp.getNext(pdu, target).getResponse());
//	System.out.println(pdu);
	l.add(snmp);
	l.add(target);
	l.add(pdu);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
return l;
}

	
	
	
	


}
