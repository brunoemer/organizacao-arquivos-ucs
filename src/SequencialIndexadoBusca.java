import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class SequencialIndexadoBusca {
	private SequencialIndexadoIndice index;

	public SequencialIndexadoBusca() {
		this.index = new SequencialIndexadoIndice();
	}

	public String find(int cod){
		long pos = this.binarySearch(cod);
		if(pos != -1){
			return this.loadRegister(pos).print();
		}else{
			return "Codigo nao encontrado";
		}
	}
	
	public long binarySearch(int cod) {
        int lo = 0;
        int hi = this.index.getIndexSize() - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if (cod < this.GetBookInsideIndex(mid).getCode()) {
                hi = mid - 1;
            } else if (cod > this.GetBookInsideIndex(mid).getCode()) {
                lo = mid + 1;
            } else {
                return this.GetBookInsideIndex(mid).getPosition();
            }
        }
        return -1;
    }
	
	public BookItem GetBookInsideIndex(int index) {
        try {
            FileInputStream fis = new FileInputStream(this.index.getIndexLocation());
            FileChannel fc = fis.getChannel();
            fc.position(index*21); //21 is the size of the index register
            ByteBuffer buffer = ByteBuffer.allocate(19);
            int bytesRead = fc.read(buffer);
            if (bytesRead != -1) {
                String Line = new String(buffer.array(), "ASCII");
                buffer.flip();
                BookItem b = new BookItem();
                b.setCode(Integer.parseInt(Line.substring(0, 7)));
                int x = Integer.parseInt(Line.substring(7, 19));
                b.setPosition((long)x);
                
                buffer.clear();
                return b;
            }
            return null;

        } catch (IOException x) {
            System.out.println("I/O Exception: " + x);
            return null;
        }
    }
        
    public BookItem loadRegister(long pos) {
        try {
            FileInputStream fis = new FileInputStream(this.index.getFileLocation());
            FileChannel fc = fis.getChannel();
            fc.position(pos);

            ByteBuffer buffer = ByteBuffer.allocate(89);
            int bytesRead = fc.read(buffer);
            if (bytesRead != -1) {
                String Line = new String(buffer.array(), "ASCII");
                buffer.flip();
                BookItem b = new BookItem(Line);
                buffer.clear();
                return b;
            }
            return null;

        } catch (IOException x) {
            System.out.println("I/O Exception: " + x);
            return null;
        }
    }
	
}
