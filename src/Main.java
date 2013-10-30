import javax.swing.JOptionPane;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {		
			new Window();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Interface error: "+e.getMessage());
		}

	}

}
