## DOM Parser
The DOM (Document Object Model) parser parses an XML document into an object graph. The whole document is converted into one big objects. Once created you can traverse the object graph at will. You can walk up and down in the graph as you please. This object graph takes up a lot of memory, so this should only be used in situations where no other options are suitable.

## SAX Parser
The SAX parser is the first API for processing XML entity by entity (element, text, comments etc.) as they are met during traversal of the XML document. When you use a SAX parser you pass a handler object to the SAX parser. This handler object has a method for every "event".

The data is considerably a stream of data read the document line by line the SAX handler perform unique method for their corresponding type of data read by sax parser

## StAX parser
The Java StAX API is a streaming API for reading and writing XML streams. As such it resembles the older SAX API, except that the SAX API can only be used to read XML, not write it.


## XPath Evaluator
Java comes with a built-in XPath evaluator. You set up an XPath expression, and have the evaluator evalute the expression on a DOM object. The evaluator then returns the elements matching the XPath expression. XPaths can be a handy way of finding the nodes you need to process, rather than navigate down to them yourself.






# some use full links
+ XML explainations[https://www.w3resource.com/xml/xml.php]
+  XML resources for practice[http://aiweb.cs.washington.edu/research/projects/xmltk/xmldata/]
+ Java & XML Tool Overview(easy understandable)[http://tutorials.jenkov.com/java-xml/overview.html]
+ DOM4J documentation[https://dom4j.github.io/]
+  stax [https://wso2.com/library/1844/]
+ StAX [https://www.docs4dev.com/docs/en/java/java8/tutorials/jaxp-stax-using.html]
+ DOM parser example [https://mkyong.com/java/how-to-modify-xml-file-in-java-dom-parser/]
+ http://www.cafeconleche.org/books/xmljava/chapters/ch05s02.html
+ Tutorials point link [https://www.tutorialspoint.com/java_xml/java_dom4j_parser.htm]
+ https://docs.microsoft.com/en-us/previous-versions/windows/desktop/ms762271(v=vs.85)

