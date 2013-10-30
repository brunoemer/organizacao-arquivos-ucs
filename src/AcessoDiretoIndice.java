import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AcessoDiretoIndice {
	private String fileLocation;
	private String indexLocation;
//	private int blockSize = 10; // para 30000 registros
//	private int hashDiv = 3000;
	private int blockSize = 100;
	private int hashDiv = 30000;
	
	public AcessoDiretoIndice() {
		this.fileLocation = Config.fileLocation;
		this.indexLocation = Config.indexAcessoDiretoLocation;
	}
	
	public void makeIndex(){
		try {
			RandomAccessFile f = new RandomAccessFile(this.fileLocation, "r");
			RandomAccessFile i = new RandomAccessFile(this.indexLocation, "rw");
			i.seek(0);
			i.setLength(0);
            i.close();
            i = new RandomAccessFile(this.indexLocation, "rw");
			String line;
			int end = 0;
			while((line = f.readLine()) != null){
				int cod = Integer.parseInt(line.substring(0, 7));
				int blockEnd = this.hashFunction(cod);
				String iLine = String.format("%07d%07d\r\n", cod, end);
				int start_line = blockEnd * this.blockSize;
                int end_line = start_line + this.blockSize;
                for(int j = start_line; j < end_line; j++){
                    long endI = (j * iLine.length());
                    i.seek(endI);
                    byte[] c = new byte[1];
                    i.read(c);
                    if(c[0] == (byte)' ' || c[0] == 0){
                        i.seek(endI);
                        i.writeBytes(iLine);
                        break;
                    }
                }
				
				end++;
			}
			f.close();
			i.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getIndexSize(){
		RandomAccessFile i;
		try {
			i = new RandomAccessFile(this.indexLocation, "r");
			return i.readLine().length() + 2;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int hashFunction(int cod){
		return (cod % hashDiv);
	}

	public String getIndexLocation() {
		return indexLocation;
	}

	public void setIndexLocation(String indexLocation) {
		this.indexLocation = indexLocation;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getHashDiv() {
		return hashDiv;
	}

	public void setHashDiv(int hashDiv) {
		this.hashDiv = hashDiv;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
}
