import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XmlBusca {
	private XmlIndice index;

	public XmlBusca() {
		this.index = new XmlIndice();
	}

	public String findXPath(String autor){
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
		    builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		    e.printStackTrace(); 
		}
		Document document = null;
		try {
		    document = builder.parse(new FileInputStream(this.index.getFileXmlLocation()));
		} catch (SAXException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		XPath xPath =  XPathFactory.newInstance().newXPath();

//		String expression = "/object-stream/autores/autor/nome[contains(text(), '"+autor+"')]";
//		String expression = "/autores/autor/nome[text()='"+autor+"']"; // igual
		String expression = "/autores/autor/nome[contains(text(), '"+autor+"')]"; // parcial
		String res = "";
		NodeList nodeList;
		try {
			nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
//			    System.out.println(nodeList.item(i).getFirstChild().getNodeValue());
				res += nodeList.item(i).getFirstChild().getNodeValue()+"\n";
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	
	public String findIndex(String autor){
		
		return "";
	}
	
	
}
