import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AcessoDiretoBusca {
	private AcessoDiretoIndice index;

	public AcessoDiretoBusca() {
		this.index = new AcessoDiretoIndice();
	}
	
	public String find(int cod){
		try {
			RandomAccessFile f = new RandomAccessFile(this.index.getFileLocation(), "r");
			RandomAccessFile i = new RandomAccessFile(this.index.getIndexLocation(), "r");
			int blockEnd = this.index.hashFunction(cod);
			
			int indexSize = this.index.getIndexSize();
//			System.out.println(blockEnd+" - "+indexSize);
			int startLine = blockEnd * this.index.getBlockSize();
            int endLine = startLine + this.index.getBlockSize();
            for(int j = startLine; j < endLine; j++){
                long endI = (j * indexSize);
                i.seek(endI);
                String li = i.readLine();
//                System.out.println(j+" - "+endI+" - "+li);
                if(cod == Integer.parseInt(li.substring(0, 7))){
                	f.seek((Integer.parseInt(li.substring(7, 14))) * 89);
                	li = f.readLine();
                	BookItem b = new BookItem(li);
                	return b.print();
                }
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
		
		return "Codigo nao encontrado";
	}
	
}
