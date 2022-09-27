package poo.polinomi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;





@SuppressWarnings("serial")
class FinestraGUI extends JFrame {
	String regexPolinomio="([\\-\\+]?[\\d]*[x]?([\\^][\\d]+)?)+";// polinomio
	String regexMonomio=   "[\\-\\+]?[\\d]*[x]?([\\^][\\d]+)?"; // monomio
	Pattern ptrn=Pattern.compile(regexMonomio);
	
	private File fileDiSalvataggio=null;
	private JMenuItem nuova,apri,salva,esci,
					  aggiungiPolinomio,rimuoviPolinomio,
					  somma,prodotto,derivata,valuta,elenco,svuota,about;
	private String titolo="Polinomio GUI"; 
	private String polinomio;// quello preso dalla textbox
	private boolean stringaOK;
	private ArrayList<JCheckBox>listaDiPolinomi=new ArrayList<>();

	private FrameAggiungiPolinomio FAP;
	private FrameRimuoviPolinomio FRP;
	private FrameElencoPolinomi FEP;
	private FrameSommaPolinomi FSP;
	private FrameProdottoPoli FPP;
	private FrameDerivataPoli FDP;
	private FrameValorePoli FVP;
	
	public FinestraGUI()
	{	setTitle(titolo);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(consensoUscita())
					System.exit(0);
			}
			});
		AscoltatoreEventiAzione listener=new AscoltatoreEventiAzione();
		
		//creazione barra dei menù
		JMenuBar menuBar= new JMenuBar();
		this.setJMenuBar(menuBar);
		//menu -> FILE
		JMenu menuFile=new JMenu("File");
		menuBar.add(menuFile);
		//voci del menu --FILE
		nuova=new JMenuItem("Nuova");
		nuova.addActionListener(listener);
		menuFile.add(nuova);
		menuFile.addSeparator();
		apri=new JMenuItem("Apri");
		apri.addActionListener(listener);
		menuFile.add(apri);
		salva=new JMenuItem("Salva");
		salva.addActionListener(listener);
		menuFile.add(salva);
		menuFile.addSeparator();
        esci=new JMenuItem("Esci");
        esci.addActionListener(listener);
        menuFile.add(esci);
        
        //menu->Comandi
        JMenu menuComandi=new JMenu("Comandi");
        menuBar.add(menuComandi);
        //voci del meni -- Comandi
        aggiungiPolinomio=new JMenuItem("Aggiungi polinomio");
        aggiungiPolinomio.addActionListener(listener);
        menuComandi.add(aggiungiPolinomio);
        rimuoviPolinomio=new JMenuItem("Rimuovi polinomio");
        rimuoviPolinomio.addActionListener(listener);
        menuComandi.add(rimuoviPolinomio);
        somma=new JMenuItem("Somma pollinomi");
        somma.addActionListener(listener);
        menuComandi.add(somma);
        prodotto=new JMenuItem("Prodotto polinomi");
        prodotto.addActionListener(listener);
        menuComandi.add(prodotto);
        derivata=new JMenuItem("Derivata");
        derivata.addActionListener(listener);
        menuComandi.add(derivata);
        valuta=new JMenuItem("Valuta");
        menuComandi.add(valuta);
        valuta.addActionListener(listener);
        elenco=new JMenuItem("Elenco poli");
        elenco.addActionListener(listener);
        menuComandi.add(elenco);
        svuota=new JMenuItem("Svuota");
        svuota.addActionListener(listener);
        menuComandi.add(svuota);
        
        //menu Help
        JMenu menuHelp=new JMenu("Help");
        menuBar.add(menuHelp);
        about=new JMenuItem("About");
        about.addActionListener(listener);
        menuHelp.add(about);
        
        menuIniziale();
        pack();
        setLocation(200, 200);
        setSize(500,200);		
	}//costruttore
	
	private class FrameAggiungiPolinomio extends JFrame implements ActionListener
	{	private JTextField polinomio;
		private JButton ok;
		public FrameAggiungiPolinomio()
		{	setTitle("Aggiungi Polinomio");
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener( new WindowAdapter() {
				public void windowClosing(WindowEvent e){
					if(consensoUscita())
						FrameAggiungiPolinomio.this.setVisible(false);
					}
				}//windowclosing
			);
			stringaOK=false;
			JPanel p=new JPanel();
			p.setLayout(new FlowLayout());
			p.add(new JLabel("Polinomio", JLabel.CENTER));
			p.add( polinomio=new JTextField("", 15));
			p.add(ok=new JButton("OK"));
			add(p);
			
			polinomio.addActionListener(this);
			ok.addActionListener(this);
			setLocation(230	, 230);
			setSize(370,150);
		}//FrameAggiungiPolinomo
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Polinomio po=new PolinomioLL();
			if(e.getSource()==polinomio)
			{	FinestraGUI.this.polinomio=polinomio.getText().toLowerCase();
				stringaOK=true;
			
			}
			else if(e.getSource()==ok)
			{	
				if(stringaOK && FinestraGUI.this.polinomio.matches(regexPolinomio))
				{		
					FinestraGUI.this.stringaOK=true;
					
					po=creaPolinomio(FinestraGUI.this.polinomio, regexMonomio);

					JCheckBox poli=new JCheckBox(po.toString());
					listaDiPolinomi.add(poli); // aggiungo la check
				
					stringaOK=false;
					polinomio.setText("");  //ripristinano le imp iniziali
				}
				else
					JOptionPane.showMessageDialog(null, "Polinomio non valido");
				
			}
		}//actionPerformed
		
	}//FrameAggiungiPolinomo
	private class FrameRimuoviPolinomio extends JFrame implements ActionListener 
	{	private JButton PulsanteRimuovi=new JButton("Rimuovi");
		private JPanel cbPanel=new JPanel();
		
		public FrameRimuoviPolinomio()
		{	setTitle("Rimuovi");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			if(listaDiPolinomi.size()!=0)
			{	for(JCheckBox j:listaDiPolinomi)
					cbPanel.add(j);
				cbPanel.add(PulsanteRimuovi);
				add(cbPanel);
			}
			else
				add(new JTextArea("Nessun polinomio"));
			PulsanteRimuovi.addActionListener(this);
			setLocation(250	, 230);
			setSize(330,150);
			
			
		}//FrameRimuoviPolinomio

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==PulsanteRimuovi)
			{	ArrayList<JCheckBox>daElimanre=new ArrayList<>();
				for(JCheckBox j:listaDiPolinomi)
					if(j.isSelected())
					{	cbPanel.remove(j);	
						daElimanre.add(j);
					}
				for(JCheckBox jcb:daElimanre)
				{	
					listaDiPolinomi.remove(jcb);
				}
				FrameRimuoviPolinomio.this.setVisible(false);
			}
		}//actionPerformed
		
	}//class FrameRimuoviPolinomio
	
	private class FrameSommaPolinomi extends JFrame implements ActionListener
	{	private JButton bottone=new JButton("Somma");
		private JPanel cbPanel=new JPanel();
		private JPanel risPanel=new JPanel();
		private JTextField risultatoText=new JTextField("", 20);

		public FrameSommaPolinomi()
		{	setTitle("Somma poli");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			risultatoText.setEditable(false);
			
			if(listaDiPolinomi.size()>=2)
			{	for(JCheckBox j:listaDiPolinomi)
					cbPanel.add(j);
				risPanel.add(bottone);
				risPanel.add(new JLabel("Risultato:",JLabel.LEFT));
				risPanel.add(risultatoText);
				
				add(risPanel,BorderLayout.NORTH);
				add(cbPanel,BorderLayout.SOUTH);
			}
			else
				add(new JTextArea("Non ci sono abbastanza polinomi per fare l'operazione"));
			bottone.addActionListener(this);
			setLocation(250	, 230);
			setSize(430,150);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==bottone)
			{	
				ArrayList<JCheckBox>daSommare=new ArrayList<>();
				int selezionati=0;
				for(JCheckBox j:listaDiPolinomi)
					if(j.isSelected())
					{	selezionati++;
						daSommare.add(j);
					}
				if(selezionati!=2)
					JOptionPane.showMessageDialog(null, "Selezionare 2 polinomi");
				else {
					Polinomio daSomm1=creaPolinomio(daSommare.get(0).getText(), regexMonomio);
					Polinomio daSomm2=creaPolinomio(daSommare.get(1).getText(), regexMonomio);
					Polinomio ret=daSomm1.add(daSomm2);
					String ret1=ret.toString();
					risultatoText.setText(ret1);
					listaDiPolinomi.add(new JCheckBox(ret1));
					
					for(JCheckBox j:listaDiPolinomi)
						j.setSelected(false);
				}
				
			}
			
		}
		
	}
	
	private class FrameProdottoPoli extends JFrame implements ActionListener
	{	private JButton bottone=new JButton("Prodotto");
		private JPanel cbPanel=new JPanel();
		private JPanel risPanel=new JPanel();
		private JTextField risultatoText=new JTextField("", 20);

		public FrameProdottoPoli()
		{	setTitle("Prodotto poli");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			risultatoText.setEditable(false);
			
			if(listaDiPolinomi.size()>=2)
			{	for(JCheckBox j:listaDiPolinomi)
					cbPanel.add(j);
				risPanel.add(bottone);
				risPanel.add(new JLabel("Risultato:",JLabel.LEFT));
				risPanel.add(risultatoText);
				
				add(risPanel,BorderLayout.NORTH);
				add(cbPanel,BorderLayout.SOUTH);
			}
			else
				add(new JTextArea("Non ci sono abbastanza polinomi per fare l'operazione"));
			bottone.addActionListener(this);
			setLocation(250	, 230);
			setSize(430,150);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==bottone)
			{	
				ArrayList<JCheckBox>daSommare=new ArrayList<>();
				int selezionati=0;
				for(JCheckBox j:listaDiPolinomi)
					if(j.isSelected())
					{	selezionati++;
						daSommare.add(j);
					}
				if(selezionati!=2)
					JOptionPane.showMessageDialog(null, "Selezionare 2 polinomi");
				else {
					Polinomio daSomm1=creaPolinomio(daSommare.get(0).getText(), regexMonomio);
					Polinomio daSomm2=creaPolinomio(daSommare.get(1).getText(), regexMonomio);
					Polinomio ret=daSomm1.mul(daSomm2);
			
					String ret1=ret.toString();
					risultatoText.setText(ret1);
					listaDiPolinomi.add(new JCheckBox(ret1));
					
					for(JCheckBox j:listaDiPolinomi)
						j.setSelected(false);
				}
				
			}
			
		}
		
	}
	
	private class FrameDerivataPoli extends JFrame implements ActionListener
	{	private JButton bottone=new JButton("Derivata");
		private JPanel cbPanel=new JPanel();
		private JPanel risPanel=new JPanel();
		private JTextField risultatoText=new JTextField("", 20);

		public FrameDerivataPoli()
		{	setTitle("Derivata poli");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			risultatoText.setEditable(false);
			
			if(listaDiPolinomi.size()>=1)
			{	for(JCheckBox j:listaDiPolinomi)
					cbPanel.add(j);
				risPanel.add(bottone);
				risPanel.add(new JLabel("Risultato:",JLabel.LEFT));
				risPanel.add(risultatoText);
				
				add(risPanel,BorderLayout.NORTH);
				add(cbPanel,BorderLayout.SOUTH);
			}
			else
				add(new JTextArea("Non ci sono abbastanza polinomi per fare l'operazione"));
			bottone.addActionListener(this);
			setLocation(250	, 230);
			setSize(430,150);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==bottone)
			{	
				JCheckBox daDerivare=null;
				int selezionati=0;
				for(JCheckBox j:listaDiPolinomi)
					if(j.isSelected())
					{	selezionati++;
						daDerivare=j;
					}
				if(selezionati!=1)
					JOptionPane.showMessageDialog(null, "Selezionare 1 polinomio");
				else {
					Polinomio daSomm1=creaPolinomio(daDerivare.getText(),regexMonomio);
					Polinomio ret=daSomm1.derivata();
			
					String ret1=ret.toString();
					risultatoText.setText(ret1);
					listaDiPolinomi.add(new JCheckBox(ret1));
					
					for(JCheckBox j:listaDiPolinomi)
						j.setSelected(false);
				}
				
			}
			
		}
		
	}
		
	private class FrameValorePoli extends JFrame implements ActionListener
	{	private JButton bottone=new JButton("Calcola");
		private JPanel cbPanel=new JPanel();
		private JPanel risPanel=new JPanel();
		private JTextField risultatoText=new JTextField("", 20);

		public FrameValorePoli()
		{	setTitle("Valore polinomio");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			risultatoText.setEditable(false);
			
			if(listaDiPolinomi.size()>=1)
			{	for(JCheckBox j:listaDiPolinomi)
					cbPanel.add(j);
				risPanel.add(bottone);
				risPanel.add(new JLabel("Risultato:",JLabel.LEFT));
				risPanel.add(risultatoText);
				
				add(risPanel,BorderLayout.NORTH);
				add(cbPanel,BorderLayout.SOUTH);
			}
			else
				add(new JTextArea("Non ci sono abbastanza polinomi per fare l'operazione"));
			bottone.addActionListener(this);
			setLocation(250	, 230);
			setSize(430,150);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==bottone)
			{	JCheckBox daVedere=null;
				double valore;
				int selezionati=0;
				for(JCheckBox j:listaDiPolinomi)
					if(j.isSelected())
					{	selezionati++;
						daVedere=j;
					}
				if(selezionati!=1)
					JOptionPane.showMessageDialog(null, "Selezionare 1 polinomio");
				else {
			        for(;;){
			        	try{
			        		String input=JOptionPane.showInputDialog("Fornire il valore intero di x");
			        		valore=Float.parseFloat(input);
			        		break;
			        	}catch( Exception exc ){
			        		JOptionPane.showMessageDialog(null/*parent*/,"Nessun intero. Ripetere..."/*String*/);
			        	}		
					}
					
					Polinomio daValutare=creaPolinomio(daVedere.getText(),regexMonomio);
					double valutato=daValutare.valore(valore);
					risultatoText.setText(valutato+"");
					
					for(JCheckBox j:listaDiPolinomi)
						j.setSelected(false);
				}
				
			}
			
		}
		
	}
		
	
	
	
	private class FrameElencoPolinomi extends JFrame
	{	private JTextArea area;
		public FrameElencoPolinomi()
		{	setTitle("Elenco Polinomi");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JPanel p=new JPanel();
			p.setLayout( new FlowLayout() );
			area= new JTextArea(10,25);
			area.setEditable(false);
			JScrollPane sp=new JScrollPane(area);
			p.add(sp);
			add(p);
			
			if(listaDiPolinomi.size() != 0)
				for(JCheckBox j :listaDiPolinomi)
				{	area.append(j.getText());
					area.append("\n");
				}
			else
				area.append("Nessun polinomio presente");
						
			setLocation(250, 340);
			setSize(400,150);
		}
	}//ElencoPolinomi
	
	private void menuIniziale()
	{	apri.setEnabled(true);
		salva.setEnabled(false);
		aggiungiPolinomio.setEnabled(false);
		rimuoviPolinomio.setEnabled(false);
		somma.setEnabled(false);
		prodotto.setEnabled(false);
		derivata.setEnabled(false);
		valuta.setEnabled(false);
		elenco.setEnabled(false);
		svuota.setEnabled(false);
	}
	
	private void menuAvviato()
	{	apri.setEnabled(true);
		salva.setEnabled(true);
		aggiungiPolinomio.setEnabled(true);
		rimuoviPolinomio.setEnabled(true);
		somma.setEnabled(true);
		prodotto.setEnabled(true);
		derivata.setEnabled(true);
		valuta.setEnabled(true);
		elenco.setEnabled(true);
		svuota.setEnabled(true);
	}
	
	
    private boolean consensoUscita(){
	   int option=JOptionPane.showConfirmDialog(
			   null, "Continuare ?", "Uscendo si perderanno tutti i dati!",
			   JOptionPane.YES_NO_OPTION);
	   return option==JOptionPane.YES_OPTION;
	 }//consensoUscita
	
    private class AscoltatoreEventiAzione implements ActionListener
    {	@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==esci)
			{	if(consensoUscita())
					System.exit(0);
			}
			else if(e.getSource()==nuova)
			{	
				listaDiPolinomi=new ArrayList<>();
				menuAvviato();
			}
			
			else if(e.getSource()==apri)
			{	JFileChooser jfc=new JFileChooser();
				try {
					if(jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
					{	if(!jfc.getSelectedFile().exists())
							JOptionPane.showMessageDialog(null, "File inesistente");
						else {
							fileDiSalvataggio=jfc.getSelectedFile();
							menuAvviato();
							try {
								ripristina(fileDiSalvataggio.getAbsolutePath());
							}catch(Exception io) {
								JOptionPane.showMessageDialog(null, "Impossibile aprire");
							}
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Operazione annullata");
						}
					}catch(Exception exc) {
						exc.printStackTrace();
				}
			}
			
			else if(e.getSource()==salva)
			{	JFileChooser jfc=new JFileChooser();
				try
				{	if(fileDiSalvataggio!=null)
					{	int risposta=JOptionPane.showConfirmDialog(null,"Sovrascrivere "+fileDiSalvataggio.getAbsolutePath()+" ?");
						if(risposta == 0)//si
							salva(fileDiSalvataggio.getAbsolutePath());
						else
							JOptionPane.showMessageDialog(null, "File mai salvato");
					}
					if(jfc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
					{	fileDiSalvataggio=jfc.getSelectedFile();
						salva(fileDiSalvataggio.getAbsolutePath());
					}
					
				}catch(Exception exc){
					exc.printStackTrace();
				}
			}
			
			
			
			else if(e.getSource()==aggiungiPolinomio)
			{	if(FAP==null)
					FAP=new FrameAggiungiPolinomio();
				FAP.setVisible(true);
			}
			else if(e.getSource()==rimuoviPolinomio)
			{	FRP=new FrameRimuoviPolinomio();
				FRP.setVisible(true);
			
			}
			else if(e.getSource()==somma)
			{	FSP=new FrameSommaPolinomi();
				FSP.setVisible(true);
			}
			
			else if(e.getSource()==prodotto)
			{	FPP=new FrameProdottoPoli();
				FPP.setVisible(true);
			}
			
			else if(e.getSource()==derivata)
			{	FDP=new FrameDerivataPoli();
				FDP.setVisible(true);
			}
			
			else if(e.getSource()==valuta)
			{	FVP=new FrameValorePoli();
				FVP.setVisible(true);
			}
			else if(e.getSource()==elenco)
			{	FEP=new FrameElencoPolinomi();
				FEP.setVisible(true);
			}
			else if(e.getSource()==svuota)
			{	int option=JOptionPane.showConfirmDialog(
						null, "Continuare ?", "si perderanno tutti i dati!",
						JOptionPane.YES_NO_OPTION);
			   if (option==JOptionPane.YES_OPTION)
			       listaDiPolinomi.clear();
			   
			}
			else if(e.getSource() == about) {
				JOptionPane.showMessageDialog( null,"Progetto POO - PolinomioGUI - Francesco Zumpano,209693","About", 
						JOptionPane.PLAIN_MESSAGE );
			}
			
			
		}
    }
    
    private void salva(String nome)throws IOException
    {	PrintWriter pw = new PrintWriter(new FileWriter(nome));
		for(JCheckBox j : listaDiPolinomi)
			pw.println(j.getText());
		pw.close();
    }//salva

    @SuppressWarnings("resource")
	private void ripristina(String nome) throws IOException 
    {	BufferedReader br = new BufferedReader(new FileReader(nome));
    	listaDiPolinomi.clear();
    	for(;;) 
    	{	String linea = br.readLine();
    		if(linea == null)
    			break;
    		if(linea.matches(regexPolinomio))
    		{	listaDiPolinomi.add(new JCheckBox(linea));
    		}
    	}
    	br.close();
    }//ripristina
    
    
    public static Polinomio creaPolinomio(String s,String regexM)
    {	Polinomio ret=new PolinomioLL();
		Pattern p=Pattern.compile(regexM);
		Matcher m=p.matcher(s);
		while(m.find())		//se entro qui sicuramente m non punta a null
			ret.add(new Monomio(m.group()));
		return ret;
    }
	
}//FinestraGUI
public class PolinomioGUI {
	public static void main( String []args ){
		FinestraGUI f=new FinestraGUI();
		f.setVisible(true);
	}//main
}//AgendinaGUI


