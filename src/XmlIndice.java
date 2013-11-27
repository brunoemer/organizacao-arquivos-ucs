import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
//			xstream.alias("autores", Autores.getClass());
			xstream.alias("autor", Autor.class);
			xstream.alias("endereco", String.class);
			
			FileInputStream fis = new FileInputStream(this.fileLocation);
			FileChannel fileChannel = fis.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(89);
			int bytesRead = fileChannel.read(buffer);
//			int i = 0;
			while (bytesRead != -1) {
				String Line = new String(buffer.array(), "ASCII");
				BookItem b = new BookItem(Line);
				buffer.flip();
				long pos = fileChannel.position() - bytesRead;
				Autor a = new Autor(b.getAuthor().trim());
				
				int endList = Autores.indexOf(a);
				if(endList >= 0){
					Autores.get(endList).IncluiEndereco(String.format("%012d", pos));
				}else{
					a.IncluiEndereco(String.format("%012d", pos));
					Autores.add(a);
				}
				
				buffer.clear();
				bytesRead = fileChannel.read(buffer);
//				i++;
//				if(i % 100 == 0){
//					System.out.println(i);
//				}
			}
			fis.close();
			
			Writer writer = new FileWriter(new File(this.fileXmlLocation));
			ObjectOutputStream out = xstream.createObjectOutputStream(writer, "autores");
			for (Autor ita : Autores) {
				out.writeObject(ita);
				out.flush();
			}
			out.close();
			
//			try{
//				// teste 1
//				String xml = "";
//				xml = xstream.toXML(Autores);
//				Writer writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fileXmlLocation), "utf-8"));
//				writer2.write(xml);
//				writer2.close();
//			}catch(Exception exc){
//				// teste 2
//				Writer writer = new FileWriter(new File(this.fileXmlLocation+"2"));
//				ObjectOutputStream out = xstream.createObjectOutputStream(writer, "autores");
//				out.writeObject(Autores);
//				out.close();
//			}
			
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
