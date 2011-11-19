package Interface;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import eventoProgresso.EventoProgresso;
import eventoProgresso.EventoProgressoListener;

import Imagem.Imagem;

public class JanelaInicial extends JFrame
	implements ActionListener, EventoProgressoListener{
//dados
	//---- Constantes
	private static final long serialVersionUID = 1L;
	private static final int COMPRIMENTO_JANELA = 1000;
	private static final int LARGURA_JANELA = 800;
	private static final int COMPRIMENTO_PANEL_BOTOES = (COMPRIMENTO_JANELA-30)/6;
	private static final int LARGURA_PANEL_BOTOES = (LARGURA_JANELA-30)/6;
	private final JFileChooser painelEscolhaArquivo;
	
	//---- Interface
	private PainelImagem painelImagens;
	
	private JMenuItem carregar;
	private JMenuItem salvar;
	private JMenuItem sair;
	
	private JMenuItem limparEdicoes;
	
	private JMenuItem topicos;
	private JMenuItem sobre;
	
	//---- Barra de Progresso
	private JFrame frameBarraProgresso;
	private JPanel panelBarraProgresso;
	private JProgressBar barraProgresso;
	
//métodos	
	public JanelaInicial(int _posicaoX, int _posicaoY){
		//janelas
		painelEscolhaArquivo = new JFileChooser();
		
		painelImagens = new PainelImagem();
		painelImagens.setSize(COMPRIMENTO_PANEL_BOTOES, LARGURA_PANEL_BOTOES);
		
		getContentPane().add( painelImagens );
		//getContentPane().setLayout(new GridLayout(1,2));
		
    	//menu
		JMenuBar barraMenu = new JMenuBar();
    	JMenu menu;
 
        //primeiro menu
        menu = new JMenu("Arquivo");
        menu.setMnemonic(KeyEvent.VK_A);
        barraMenu.add(menu);
 
        carregar = new JMenuItem("Carregar imagem", KeyEvent.VK_C);
        carregar.addActionListener((ActionListener) this);
        carregar.setEnabled(true);
        menu.add(carregar);
        
        salvar = new JMenuItem("Salvar imagem", KeyEvent.VK_V);
        salvar.addActionListener((ActionListener) this);
        salvar.setEnabled(false);
        menu.add(salvar);
        
        sair = new JMenuItem("Sair", KeyEvent.VK_S);
        sair.addActionListener((ActionListener) this);
        sair.setEnabled(true);
        menu.add(sair);
 
        //segundo menu
        menu = new JMenu("Editar");
        menu.setMnemonic(KeyEvent.VK_E);
        
        limparEdicoes = new JMenuItem("Limpar Edicoes", KeyEvent.VK_L);
        limparEdicoes.addActionListener((ActionListener) this);
        limparEdicoes.setEnabled(false);
        menu.add(limparEdicoes);
        
        barraMenu.add(menu);
        
        //Terceiro menu
        menu = new JMenu("Exibir");
        menu.setMnemonic(KeyEvent.VK_X);

        barraMenu.add(menu);
        
        //Quarto menu
        menu = new JMenu("Opções");
        menu.setMnemonic(KeyEvent.VK_O);

        barraMenu.add(menu);
        
        //Quinto menu
        menu = new JMenu("Ajuda");
        menu.setMnemonic(KeyEvent.VK_O);

        topicos = new JMenuItem("Topicos", KeyEvent.VK_H);
        topicos.addActionListener((ActionListener) this);
        topicos.setEnabled(true);
        menu.add(topicos);
        
        sobre = new JMenuItem("Sobre", KeyEvent.VK_H);
        sobre.addActionListener((ActionListener) this);
        sobre.setEnabled(true);
        menu.add(sobre);
        
        barraMenu.add(menu);
    	
        //janela
        setJMenuBar(barraMenu);
		setTitle("Drag and Drop Pasting - Fundamento de Processamento de Imagens");
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    	pack();
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    setBounds(0,0,screenSize.width, screenSize.height);
    	setResizable(true);
	}
	
	//---- Action Listener
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem)(e.getSource());
		if(source.getText() == "Carregar imagem"){
			acaoBtnCarregar();
		}
		else if(source.getText() == "Salvar imagem"){
			acaoBtnSalvar();
		}
		else if(source.getText() == "Sair"){
			dispose();
		}
		
	}

	//---- Interface
	public void exibir(){
		setVisible(true);
	}
	public void adicionarImagem(Imagem _imgEntrada){
		criarBarraProgresso();
		
		atualizarBarraProgresso(0);
		painelImagens.adicionarImagem(_imgEntrada);
		fecharBarraProgresso();
		
		getContentPane().add( painelImagens );
	}
	
	//---- Botões
	private void acaoBtnCarregar(){
		int opcaoUsuario = painelEscolhaArquivo.showOpenDialog(JanelaInicial.this);
		Imagem imgEntrada = new Imagem();
		
        if (opcaoUsuario == JFileChooser.APPROVE_OPTION) {
            File file = painelEscolhaArquivo.getSelectedFile();
            
            try{
            	imgEntrada.lerDoCaminho(file.getPath());
				adicionarImagem(imgEntrada);
			} catch(IOException _excecaoEntrada){
				JOptionPane.showMessageDialog(null, _excecaoEntrada.getMessage());
			}
        }
	}
	private void acaoBtnSalvar(){
		int opcaoUsuario = painelEscolhaArquivo.showOpenDialog(JanelaInicial.this);
		
        if (opcaoUsuario == JFileChooser.APPROVE_OPTION) {
            File file = painelEscolhaArquivo.getSelectedFile();
            
            if(painelImagens.salvarEmJPGComCaminho(file.getPath())){
            	JOptionPane.showMessageDialog(null, "Imagem Salva com sucesso!");
			}
			else{
				JOptionPane.showMessageDialog(null, "Erro ao tentar salvar a imagem.");
			}
        }
	}
	private void desabilitarBotoes(){
		carregar.setEnabled(false);
		salvar.setEnabled(false);
		sair.setEnabled(false);
		limparEdicoes.setEnabled(false);
		topicos.setEnabled(false);
		sobre.setEnabled(false);
	}
	private void habilitarBotoes(){
		carregar.setEnabled(true);
		salvar.setEnabled(true);
		sair.setEnabled(true);
		limparEdicoes.setEnabled(true);
		topicos.setEnabled(true);
		sobre.setEnabled(true);
	}
	
	//---- Barra de Progresso
	private void atualizarBarraProgresso(int _porcentagem){
		barraProgresso.setValue(_porcentagem);
		//quatro linhas abaixo encontradas em "http://www.coderanch.com/t/339910/GUI/java/Help-updating-JProgressBar"
        Rectangle progressRect = barraProgresso.getBounds();  
        progressRect.x = 0;  
        progressRect.y = 0;  
        barraProgresso.paintImmediately( progressRect ); 
	}
	private void criarBarraProgresso(){
		frameBarraProgresso = new JFrame();
		panelBarraProgresso = new JPanel();
		barraProgresso = new JProgressBar(0, 100);
		barraProgresso.setStringPainted(true);
		panelBarraProgresso.add(barraProgresso);
		panelBarraProgresso.setLayout(new GridLayout(1, 1));
		frameBarraProgresso.add(panelBarraProgresso);
		frameBarraProgresso.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameBarraProgresso.setTitle("Carregar...");
		frameBarraProgresso.setBounds(300, 300, 200, 200);
		frameBarraProgresso.setResizable(false);
		frameBarraProgresso.pack();
		frameBarraProgresso.validate();
		frameBarraProgresso.setVisible(true);
		
		desabilitarBotoes();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       
	    painelImagens.registrarListener(this);
	    atualizarBarraProgresso(0);
	}
	private void fecharBarraProgresso(){
		frameBarraProgresso.dispose();
		habilitarBotoes();
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	public void progrediu(EventoProgresso _evento) {
		if(_evento.nome == "janela"){
			int progresso = barraProgresso.getValue();
			atualizarBarraProgresso(progresso + _evento.variacao);
		}
	}
}
