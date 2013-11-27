import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class XmlBusca {
	private static final int LinkedList = 0;
	private XmlIndice index;
	private ListAutores aut = new ListAutores();

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
	
	public void makeMemoryIndex(){

		try {
	        XStream xstream = new XStream(new DomDriver());
	        xstream.alias("autores", ListAutores.class);
	        xstream.alias("autor", Autor.class);
//	        xstream.alias("enderecos", AutorEnderecos.class);
//	        xstream.aliasField("enderecos", Autor.class, "enderecos");
	        xstream.addImplicitCollection(ListAutores.class, "autores");
//	        xstream.addImplicitCollection(AutorEnderecos.class, "enderecos");
	        InputStream in = new FileInputStream(this.index.getFileXmlLocation());
	        this.aut = (ListAutores)xstream.fromXML(in);
			
//			System.out.println(this.aut.autores.size());

//	        for (Autor ita : this.aut.autores) {
//	        	System.out.println(ita.getNome());
//	        	System.out.println(ita.getEnderecos());
//	        	for (String end : ita.getEnderecos()) {
//					System.out.println(end);
//				}
////	        	authors.add(ita);
//	        }
	        
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String findIndex(String autor){
		String res = "";

//		System.out.println(this.authors.size());
		
		int index = this.aut.autores.indexOf(new Autor(autor));
		Autor a = this.aut.autores.get(index);
		res += "Autor: "+a.getNome()+"\n";
		
		try{
			SequencialIndexadoBusca sequencialIndex = new SequencialIndexadoBusca();
			for (String end : a.getEnderecos()) {
				BookItem b = sequencialIndex.GetBookInsideIndex(Integer.parseInt(end));
				res += "Cod: "+b.getCode()+" Titulo: "+b.getTitle()+"\n";
			}
		}catch(Exception exc){
			
		}
		
		return res;
	}
	
	
}
