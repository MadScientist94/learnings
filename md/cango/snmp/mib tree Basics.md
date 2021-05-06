### Understanding the Management Information Base
The most difficult part of the SNMP system to understand is probably the MIB, or management information base. The MIB is a database that follows a standard that the manager and agents adhere to. It is a hierarchical structure that, in many areas, is globally standardized, but also flexible enough to allow vendor-specific additions.

The MIB structure is best understood as a top-down hierarchical tree. Each branch that forks off is labeled with both an identifying number (starting with 1) and an identifying string that are unique for that level of the hierarchy. You can use the strings and numbers interchangeably.

To refer to a specific node of the tree, you must trace the path from the unnamed root of the tree to the node in question. The lineage of its parent IDs (numbers or strings) are strung together, starting with the most general, to form an address. Each junction in the hierarchy is represented by a dot in this notation, so that the address ends up being a series of ID strings or numbers separated by dots. This entire address is known as an object identifier, or OID.

Hardware vendors that embed SNMP agents in their devices sometimes implement custom branches with their own fields and data points. However, there are standard MIB branches that are well defined and can be used by any device.

The standard branches we will be discussing will all be under the same parent branch structure. This branch defines information that adheres to the MIB-2 specification, which is a revised standard for compliant devices.

The base path to this branch is:
1.3.6.1.2.1

This can also be represented in strings like:
iso.org.dod.internet.mgmt.mib-2

The section 1.3.6.1 or iso.org.dod.internet is the OID that defines internet resources. The 2 or mgmt that follows in our base path is for a management subcategory. The 1 or mib-2 under that defines the MIB-2 specification.



reffer these for structurea
=> <http://www.net-snmp.org/docs/mibs/interfaces.html> 

reffer these for structures with interfaces => <https://kb.pulsesecure.net/articles/Pulse_Secure_Article/KB40481/>