import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlIndice {
	private String fileLocation;
	private String fileXmlLocation;
	private LinkedList<Autor> Autores = new LinkedList<Autor>();


	public XmlIndice() {
		this.fileLocation = Config.fileLocation;
		this.fileXmlLocation = Config.fileXmlLocation;
	}

	public void makeIndex(){
		try {
			XStream xstream = new XStream(new DomDriver());
			xstream.alias("Autores", Autores.getClass());

			FileInputStream fis = new FileInputStream(this.fileLocation);
			FileChannel fileChannel = fis.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(89);
			int bytesRead = fileChannel.read(buffer);
			while (bytesRead != -1) {
				String Line = new String(buffer.array(), "ASCII");
				BookItem b = new BookItem(Line);
				buffer.flip();
				long pos = fileChannel.position() - bytesRead;
				Autor a = new Autor(b.getAuthor().trim());
				
				int endList = Autores.indexOf(a);
				if(endList >= 0){
//					System.out.println("Exist: "+a.getNome());
					
					Autores.get(endList).IncluiEndereco(String.format("%012d", pos));
				}else{
//					System.out.println("New: "+a.getNome());
					
					a.IncluiEndereco(String.format("%012d", pos));
					Autores.add(a);
				}
				
				buffer.clear();
				bytesRead = fileChannel.read(buffer);
			}
			fis.close();
		
			String xml = "";
			xml = xstream.toXML(Autores);
			Writer writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fileXmlLocation), "utf-8"));
			writer2.write(xml);
			writer2.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int indexOf(List<Autor> list, Autor searchItem) {
	    int index = 0;
	    for (Autor item : list) {
	        if (item.getNome() == searchItem.getNome())
	            return index;
	        index += 1;
	    }
	    return -1;
	}

	public String getFileXmlLocation() {
		return fileXmlLocation;
	}

	public void setFileXmlLocation(String fileXmlLocation) {
		this.fileXmlLocation = fileXmlLocation;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

}
