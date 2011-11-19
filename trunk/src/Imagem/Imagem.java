package Imagem;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import eventoProgresso.EventoProgresso;
import eventoProgresso.EventoProgressoListener;
import eventoProgresso.RegistradorEventosProgresso;

public class Imagem{
//dados
	//---- Pixels
	private BufferedImage bufferOriginal;
	private BufferedImage buffer;
	
	//---- Eventos
	private RegistradorEventosProgresso registradorEventosProgresso = new RegistradorEventosProgresso();
	
//métodos	
	public Imagem(){
		
	}
	public Imagem(BufferedImage _buffer) {
		bufferOriginal = criarCopiaBuffer(_buffer);
	    buffer = criarCopiaBuffer(_buffer);
	}
	
	//---- Leitura
	public void lerDoBuffer(BufferedImage _buffer){
		bufferOriginal = criarCopiaBuffer(_buffer);
	    buffer = criarCopiaBuffer(_buffer);
	}
	public void lerDoCaminho(String _caminhoDoArquivo) throws IOException{
		BufferedImage bufferDaImagem;
		
		try {
		    bufferDaImagem = ImageIO.read(new File(_caminhoDoArquivo));
		    bufferOriginal = criarCopiaBuffer(bufferDaImagem);
		    buffer = criarCopiaBuffer(bufferDaImagem);
		} catch (IOException e) {
			throw new IOException("Erro ao tentar carregar a imagem " + _caminhoDoArquivo + ".");
		}
	}
	private BufferedImage criarCopiaBuffer(BufferedImage _origem){
		int rgb;
		BufferedImage copia = new BufferedImage(_origem.getWidth(), _origem.getHeight(), _origem.getType());
		for(int x=0; x<_origem.getWidth(); x++){
			for(int y=0; y<_origem.getHeight(); y++){
				rgb = _origem.getRGB(x, y);
				copia.setRGB(x, y, rgb);
			}
		}
		return copia;
	}
	
	//---- Entrada
	public ImageIcon criarImagem(int _comprimento, int _largura, boolean _aceitarMedidas) {
		BufferedImage resizedImage;
		if(_aceitarMedidas){
			resizedImage = new BufferedImage(_comprimento, _largura, buffer.getType());
		}
		else{
			resizedImage = new BufferedImage(buffer.getWidth(), buffer.getHeight(), buffer.getType());
		}
		Graphics2D g = resizedImage.createGraphics();

		if(_aceitarMedidas){
			g.drawImage(buffer, 0, 0, _comprimento, _largura, null);
		}
		else{
			g.drawImage(buffer, 0, 0, buffer.getWidth(), buffer.getHeight(), null);
		}
		g.dispose();
    	return new ImageIcon(resizedImage);
    }
	
	//---- Saída
	public boolean salvarEmJPGComCaminho(String _caminho){
		try{
			File outputfile = new File(_caminho);
			ImageIO.write(buffer, "jpg", outputfile);
			return true;
		}catch (Exception e){
			return false;
		}
	}
	public int getComprimento(){
		return buffer.getWidth();
	}
	public int getLargura(){
		return buffer.getHeight();
	}
	
	//---- Características da Imagem.
	public boolean colorida(){
		Pixel corPixel;
		boolean achouPixelColorido = false;
		int x=0;
		int y=0;
		
		while(x<buffer.getWidth() && !achouPixelColorido){
			while(y<buffer.getHeight() && !achouPixelColorido){
				corPixel = new Pixel(buffer.getRGB(x, y));
				if(corPixel.colorido()){
					achouPixelColorido = true;
				}
				y++;
			}
			x++;
		}
		
		return achouPixelColorido;
	}
	
	//---- Panel
	public JPanel getPanelImagem(){
		return (new PanelImagem(this));
	}
	public JScrollPane getScrollPanel(){
		JPanel panelImagem = getPanelImagem();
		JScrollPane panelComScroll = new JScrollPane(panelImagem);
		panelComScroll.setPreferredSize(new Dimension(panelImagem.getWidth(),panelImagem.getHeight()));
		return panelComScroll;
	}
	public JPanel getPanel(){
		JPanel panel = new JPanel();
		panel.add(new PanelImagem(this));
		return panel;
	}
	
	//---- Evento
	private void informarProgresso(int _variacao){
		EventoProgresso eventoProgresso = new EventoProgresso(new Object());
		eventoProgresso.nome = "imagem";
		eventoProgresso.variacao = _variacao;
    	registradorEventosProgresso.fireEventoProgresso(eventoProgresso);
	}
	public void registrarListener(EventoProgressoListener _listener){
		registradorEventosProgresso.addEventoProgressoListener(_listener);
	}
	public void removerListener(EventoProgressoListener _listener){
		registradorEventosProgresso.removeEventoProgressoListener(_listener);
	}
	
	
	//---- Transformações
	public void limparEdicoes(){
	    buffer = criarCopiaBuffer(bufferOriginal);
	}
	
	/*----------------------------------------------------------------
	 * INÍCIO DAS OPERAÇÕES DE DRAG_AND_DROP
	 * 
	 * 
	 * 
	 * 
	 *------------------------------------------------------------------- */
	
	
	
	
}
