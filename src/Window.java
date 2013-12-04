
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.thoughtworks.xstream.XStream;

public class Window {
	private JFrame frame;
	private JTextPane textSaida;
	public final XmlBusca b = new XmlBusca();
	public final Mongo MongoMngr = new Mongo();
	
	
	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 400);
		frame.setTitle("Busca indices");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout() );
		
		
		JPanel boxContainer = new JPanel();
		boxContainer.setLayout(new BoxLayout(boxContainer, BoxLayout.Y_AXIS));
		
		textSaida = new JTextPane() {
			@Override
			public boolean getScrollableTracksViewportWidth() {
				return (getSize().width < getParent().getSize().width);
			}
			@Override
			public void setSize(Dimension d) {
				if (d.width < getParent().getSize().width) {
					d.width = getParent().getSize().width;
				}
				super.setSize(d);
			}
		};
		textSaida.setEditable(false);
//		JScrollPane scrollSaida = new JScrollPane(textSaida);
//		scrollSaida.setAutoscrolls(true);
//		splitPane.setRightComponent(scrollSaida);
		frame.getContentPane().add(textSaida);

		JToolBar toolBar = new JToolBar();
		toolBar.setFont(new Font("Dialog", Font.BOLD, 10));
		toolBar.setRollover(true);
		toolBar.setInheritsPopupMenu(true);
		toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		toolBar.setOrientation(SwingConstants.HORIZONTAL);
		boxContainer.add(toolBar);

		JToolBar toolBar2 = new JToolBar();
		toolBar2.setFont(new Font("Dialog", Font.BOLD, 10));
		toolBar2.setRollover(true);
		toolBar2.setInheritsPopupMenu(true);
		toolBar2.setAlignmentX(Component.LEFT_ALIGNMENT);
		toolBar2.setOrientation(SwingConstants.HORIZONTAL);
		boxContainer.add(toolBar2);
		
		JToolBar toolBar3 = new JToolBar();
		toolBar3.setFont(new Font("Dialog", Font.BOLD, 10));
		toolBar3.setRollover(true);
		toolBar3.setInheritsPopupMenu(true);
		toolBar3.setAlignmentX(Component.LEFT_ALIGNMENT);
		toolBar3.setOrientation(SwingConstants.HORIZONTAL);
		boxContainer.add(toolBar3);
		frame.getContentPane().add(boxContainer,BorderLayout.NORTH);
		
		JButton btnResetar = new JButton("Limpar");
		btnResetar.setMargin(new Insets(2, 2, 2, 2));
		btnResetar.setFont(new Font("Dialog", Font.BOLD, 10));
		btnResetar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				textEntrada.setText("");
				textSaida.setText("");
			}
		});
		toolBar.add(btnResetar);
		
		JButton btnGerar1 = new JButton("Gerar indice busca binaria");
		btnGerar1.setMargin(new Insets(2, 2, 2, 2));
		btnGerar1.setFont(new Font("Dialog", Font.BOLD, 10));
		btnGerar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long startTime = System.currentTimeMillis();
				
				SequencialIndexadoIndice i = new SequencialIndexadoIndice();
				i.makeIndex();
				appendPane(textSaida, "Indice busca binaria gerado");
				
				long endTime = System.currentTimeMillis();
				showTime(endTime - startTime);
			}
		});
		toolBar.add(btnGerar1);
		
		JButton btnGerar2 = new JButton("Gerar indice acesso direto");
		btnGerar2.setMargin(new Insets(2, 2, 2, 2));
		btnGerar2.setFont(new Font("Dialog", Font.BOLD, 10));
		btnGerar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long startTime = System.currentTimeMillis();
				
				AcessoDiretoIndice i = new AcessoDiretoIndice();
				i.makeIndex();
				appendPane(textSaida, "Indice acesso direto gerado");

				long endTime = System.currentTimeMillis();
				showTime(endTime - startTime);
			}
		});
		toolBar.add(btnGerar2);
		
		final JTextField txtBusca = new JTextField();
		toolBar.add(txtBusca);

		JButton btnBusca = new JButton("Busca binaria");
		btnBusca.setMargin(new Insets(2, 2, 2, 2));
		btnBusca.setFont(new Font("Dialog", Font.BOLD, 10));
		btnBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long startTime = System.currentTimeMillis();
				
				SequencialIndexadoBusca b = new SequencialIndexadoBusca();
				String res = b.find(Integer.parseInt(txtBusca.getText()));
				appendPane(textSaida, res);

				long endTime = System.currentTimeMillis();
				showTime(endTime - startTime);
			}
		});
		toolBar.add(btnBusca);

		JButton btnBusca2 = new JButton("Busca acesso direto");
		btnBusca2.setMargin(new Insets(2, 2, 2, 2));
		btnBusca2.setFont(new Font("Dialog", Font.BOLD, 10));
		btnBusca2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long startTime = System.currentTimeMillis();
				
				AcessoDiretoBusca b = new AcessoDiretoBusca();
				String res = b.find(Integer.parseInt(txtBusca.getText()));
				appendPane(textSaida, res);

				long endTime = System.currentTimeMillis();
				showTime(endTime - startTime);
			}
		});
		toolBar.add(btnBusca2);
		

		JButton btnGerar3 = new JButton("Gerar arquivo xml");
		btnGerar3.setMargin(new Insets(2, 2, 2, 2));
		btnGerar3.setFont(new Font("Dialog", Font.BOLD, 10));
		btnGerar3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long startTime = System.currentTimeMillis();
				
				XmlIndice i = new XmlIndice();
				i.makeIndex();
				appendPane(textSaida, "Arquivo xml gerado");
				
				long endTime = System.currentTimeMillis();
				showTime(endTime - startTime);
			}
		});
		toolBar2.add(btnGerar3);
		
		JButton btnGerar4 = new JButton("Gerar indice memoria xml");
		btnGerar4.setMargin(new Insets(2, 2, 2, 2));
		btnGerar4.setFont(new Font("Dialog", Font.BOLD, 10));
		btnGerar4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long startTime = System.currentTimeMillis();
				
				b.makeMemoryIndex();
				appendPane(textSaida, "Indice xml gerado");
				
				long endTime = System.currentTimeMillis();
				showTime(endTime - startTime);
			}
		});
		toolBar2.add(btnGerar4);
		
		final JTextField txtBuscaXml = new JTextField();
		toolBar2.add(txtBuscaXml);
		
		JButton btnBusca3 = new JButton("Busca xml xpath");
		btnBusca3.setMargin(new Insets(2, 2, 2, 2));
		btnBusca3.setFont(new Font("Dialog", Font.BOLD, 10));
		btnBusca3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long startTime = System.currentTimeMillis();
				
				String res = b.findXPath(txtBuscaXml.getText());
				appendPane(textSaida, res);

				long endTime = System.currentTimeMillis();
				showTime(endTime - startTime);
			}
		});
		toolBar2.add(btnBusca3);
		
		JButton btnBusca4 = new JButton("Busca xml indice memoria");
		btnBusca4.setMargin(new Insets(2, 2, 2, 2));
		btnBusca4.setFont(new Font("Dialog", Font.BOLD, 10));
		btnBusca4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long startTime = System.currentTimeMillis();
				
				String res = b.findIndex(txtBuscaXml.getText());
				appendPane(textSaida, res);

				long endTime = System.currentTimeMillis();
				showTime(endTime - startTime);
			}
		});
		toolBar2.add(btnBusca4);
	
		
		JButton btnCriaBD = new JButton("Criar BD");
		btnCriaBD.setMargin(new Insets(2, 2, 2, 2));
		btnCriaBD.setFont(new Font("Dialog", Font.BOLD, 10));
		btnCriaBD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long startTime = System.currentTimeMillis();
				
				MongoMngr.CriaDB();
				//XmlIndice i = new XmlIndice();
				//i.makeIndex();
				//appendPane(textSaida, "Arquivo xml gerado");
				
				long endTime = System.currentTimeMillis();
				showTime(endTime - startTime);
			}
		});
		toolBar3.add(btnCriaBD);
		
		//Criação dos botões para busca no mongo
		JLabel lb = new JLabel();
		lb.setText("Busca Mongo:");
		toolBar3.add(lb);
		final JTextField txtBuscaTitulo = new JTextField();
		toolBar3.add(txtBuscaTitulo);
		
		JButton btnPesqTitulo = new JButton("Pesquisar Titulo");
		btnPesqTitulo.setMargin(new Insets(2, 2, 2, 2));
		btnPesqTitulo.setFont(new Font("Dialog", Font.BOLD, 10));
		btnPesqTitulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long startTime = System.currentTimeMillis();
				
				ArrayList<String> st =  MongoMngr.PesqusiaTitulo(txtBuscaTitulo.getText());
				for(int i=0;i<st.size();i++)
				{
					appendPane(textSaida,st.get(i));
				}
				
				long endTime = System.currentTimeMillis();
				showTime(endTime - startTime);
			}
		});
		toolBar3.add(btnPesqTitulo);
		
		final JTextField txtBuscaAutor = new JTextField();
		toolBar3.add(txtBuscaAutor);
		JButton btnPesqAutor = new JButton("Pesquisar Autor");
		btnPesqAutor.setMargin(new Insets(2, 2, 2, 2));
		btnPesqAutor.setFont(new Font("Dialog", Font.BOLD, 10));
		btnPesqAutor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long startTime = System.currentTimeMillis();
				
				ArrayList<String> st =  MongoMngr.PesquisaAutor(txtBuscaAutor.getText());
				for(int i=0;i<st.size();i++)
				{
					appendPane(textSaida,st.get(i));
				}
				
				long endTime = System.currentTimeMillis();
				showTime(endTime - startTime);
			}
		});
		toolBar3.add(btnPesqAutor);
		
				
		final JTextField txtBuscaCodigo = new JTextField();
		toolBar3.add(txtBuscaCodigo);
		
		
		JButton btnPesqCodigo = new JButton("Pesqusia Codigo");
		btnPesqCodigo.setMargin(new Insets(2, 2, 2, 2));
		btnPesqCodigo.setFont(new Font("Dialog", Font.BOLD, 10));
		btnPesqCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long startTime = System.currentTimeMillis();
				
				ArrayList<String> st =  MongoMngr.PesquisaCodigo(Integer.parseInt(txtBuscaCodigo.getText()));
				for(int i=0;i<st.size();i++)
				{
					appendPane(textSaida,st.get(i));
				}
				
				long endTime = System.currentTimeMillis();
				showTime(endTime - startTime);
			}
		});
		toolBar3.add(btnPesqCodigo);
			
		
		
		
		
	}
	
	public void appendPane(JTextPane textPane, String text){
		textPane.setText(textPane.getText()+text+"\n");
	}
	
	public void showTime(long ms){
		double segundos = ms / 1000.00;
		appendPane(textSaida, "Tempo: "+segundos+" s / "+ms+" ms");
	}
}
