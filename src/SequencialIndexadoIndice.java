import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SequencialIndexadoIndice {
	private String fileLocation;
	private String indexLocation;
	private ArrayList<BookItem> lb = new ArrayList<BookItem>();
	
	public SequencialIndexadoIndice() {
		this.fileLocation = Config.fileLocation;
		this.indexLocation = Config.indexSequencialIndexadoLocation;
	}
	
	public void makeIndex(){
		try {
            Writer writer = null;
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.indexLocation), "utf-8"));
            
            FileInputStream fis = new FileInputStream(this.fileLocation);
            FileChannel fileChannel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(89);
            int bytesRead = fileChannel.read(buffer);
            while (bytesRead != -1) {
                String Line = new String(buffer.array(), "ASCII");
                buffer.flip();
                BookItem b = new BookItem();
                
                int cod = Integer.parseInt(Line.substring(0, 7));
                long pos = fileChannel.position() - bytesRead;
                b.setCode(cod);
                b.setPosition(pos);
                //writer.write(String.format("%07d", cod) + String.format("%012d", pos) + "\r\n");
                lb.add(b);
                buffer.clear();
                bytesRead = fileChannel.read(buffer);
            }
            fis.close();
            Collections.sort(lb, new Comparator(){
	            public int compare(Object o1, Object o2)
	            {
		            BookItem b1 = (BookItem)o1;
		            BookItem b2 = (BookItem)o2;
		            return b1.getCode() < b2.getCode()? -1 : (b1.getCode() > b2.getCode()? +1 : 0);
	            }
            });
            for(int i = 0; i<lb.size();i++)
            {
            	writer.write(String.format("%07d", lb.get(i).getCode()) + String.format("%012d", lb.get(i).getPosition()) + "\r\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public int getIndexSize() {
        try
        {
            FileInputStream fis = new FileInputStream(this.indexLocation);
            FileChannel fc = fis.getChannel();
            return (int)(fc.size()/21);
        }
        catch (IOException x) {
            System.out.println("I/O Exception: " + x);
            return -1;
        }
    }
	
	public String getIndexLocation() {
		return indexLocation;
	}

	public void setIndexLocation(String indexLocation) {
		this.indexLocation = indexLocation;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
}