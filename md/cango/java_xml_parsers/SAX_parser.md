SAX is an abbreviation and means "Simple API for XML". A Java SAX XML parser is a stream oriented XML parser. It works by iterating over the XML and call certain methods on a "listener" object when it meets certain structural elements of the XML. For instance, it will call the listener object for the following "events":

- startDocument
- startElement
- characters
- comments
- processing instructions
- endElement
- endDocument 
```java
SAXParserFactory factory = SAXParserFactory.newInstance();
try {

    InputStream    xmlInput  = new FileInputStream("theFile.xml");
    SAXParser      saxParser = factory.newSAXParser();

    DefaultHandler handler   = new SaxHandler();
    saxParser.parse(xmlInput, handler);

} catch (Throwable err) {
    err.printStackTrace ();
```
<hr/>
<hr/>

```java
public class SaxHandler extends DefaultHandler {

    public void startDocument() throws SAXException {
        System.out.println("start document   : ");
    }

    public void endDocument() throws SAXException {
        System.out.println("end document     : ");
    }

    public void startElement(String uri, String localName,
        String qName, Attributes attributes)
    throws SAXException {

        System.out.println("start element    : " + qName);
    }

    public void endElement(String uri, String localName, String qName)
    throws SAXException {
        System.out.println("end element      : " + qName);
    }

    public void characters(char ch[], int start, int length)
    throws SAXException {
        System.out.println("start characters : " +
            new String(ch, start, length));
    }

}
```
### Exceptions
The DefaultHandler class has three methods you can override to handle exceptions encountered during the XML parsing. Here they are:

```java
public void warning(SAXParseException e) throws SAXException {
}

public void error(SAXParseException e) throws SAXException {
}

public void fatalError(SAXParseException e) throws SAXException {
}
```

Let's say that the parser encounters an illegal XML entity (like &notLegal;). The SAXParser will then call the fatalError() method, before breaking the parsing.

If a less dangerous error occurs, the SAXParser may just call the error() or warning() method. That way you can collect all the errors in a list, and return them all at once, instead of one by one, as they are met.







