package Interface;

import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import eventoProgresso.EventoProgresso;
import eventoProgresso.EventoProgressoListener;
import eventoProgresso.RegistradorEventosProgresso;

import Imagem.Imagem;

public class PainelImagem extends JPanel
	implements EventoProgressoListener{
	private static final long serialVersionUID = 1L;
//dados
	//---- Panels
	private JScrollPane panelImagem;
	
	//---- Imagem
	private Imagem img = null;
	
	//---- Eventos
	private RegistradorEventosProgresso registradorEventosProgresso;
	private EventoProgressoListener listenerProgressoBarra;

//métodos
	public PainelImagem(){
		registradorEventosProgresso = new RegistradorEventosProgresso();
		setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	//---- Panel de Imagem
	public void adicionarImagem(Imagem _img){
		if(!contemImagem()){
			if(_img.colorida()){
				img = _img;
				img.registrarListener(this);
				
				panelImagem = _img.getScrollPanel(); 
				
				add( panelImagem );
				
				atualizar();
				informarProgresso(1);
			}
			else{
				img = _img;
				img.registrarListener(this);
				panelImagem = _img.getScrollPanel(); 
				add( panelImagem );
				validate();
				informarProgresso(50);
			}
		}
		else{
			substituirImagem(_img);
		}
	}

	public void substituirImagem(Imagem _img){	
		if(_img.colorida()){
			img.removerListener(this);
			img = _img;
			img.registrarListener(this);
			
			remove(panelImagem);
			
			panelImagem = _img.getScrollPanel(); 
			
			add( panelImagem );
			atualizar();
			informarProgresso(1);
		}
		else{
			img.removerListener(this);
			img = _img;
			img.registrarListener(this);
			remove(panelImagem);
			panelImagem = _img.getScrollPanel(); 
			informarProgresso(25);
			add( panelImagem );
			atualizar();
			informarProgresso(25);
		}
	}
	
	private void atualizar(){
		invalidate();
		validate();
	}
	
	private boolean contemImagem(){
		if(img!=null){
			return true;
		}
		else{
			return false;
		}
	}
	
	//---- Saída 
	public boolean salvarEmJPGComCaminho(String _caminho){
		return img.salvarEmJPGComCaminho(_caminho);
	}
	
	//---- Operações sobre imagem
	public void acaoBtnLimparEdicoes(){
		img.limparEdicoes();
		informarProgresso(50);
		substituirImagem(img);
	}
	
	private void exibirFrame(JFrame frame){
		frame.pack();
		frame.validate();
		frame.setVisible(true);
		atualizar();
	}
	
	//---- Evento
	private void informarProgresso(int _variacao){
		EventoProgresso eventoProgresso = new EventoProgresso(new Object());
		eventoProgresso.nome = "janela";
		eventoProgresso.variacao = _variacao;
    	registradorEventosProgresso.fireEventoProgresso(eventoProgresso);
	}
	public void registrarListener(EventoProgressoListener _listener){
		registradorEventosProgresso.addEventoProgressoListener(_listener);
	}
	public void removerListener(EventoProgressoListener _listener){
		registradorEventosProgresso.removeEventoProgressoListener(_listener);
	}
	public void progrediu(EventoProgresso _evento) {
		informarProgresso(_evento.variacao);
	}

}
