snmp Get command with snmp4j:
snmp get is the process of aquiring particular oids value. if it provide in complete oid then it will say no such object  
[snmp4j get](./snmp4j_get.md)

snmp Get Next with snmp4j:
snmp getNext is the command used to aquire the next oids data. if we provide oid as 1.3.6.1.2.1. but it is a incomplete oid then the snmp protocol search for the immediate next available oid eg 1.3.6.1.2.1.1.1.0.
if no oids available in child nodes then it calls the next available brother node or brother nodes first valid oid 
snmp getbulk command with snmp4j:
snmp getBulk is similar to snmp get command but get command only request one oid where as getBulk command request array of oids data in a single request hence the time consumption on requesting is minimized 

snmp walk command with snmp4j
snmp walk is used to get the entire child nodes with valid oids and responses all in one single request



