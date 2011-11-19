package Imagem;

import java.awt.Color;

public class Pixel {
//dados
	private int r;
	private int g;
	private int b;
	
	private int rgb;

//métodos
	public Pixel(int _rgb){
		setRGB(_rgb);
	}
	
	//---- Inicialização
	public void setRGB(int _rgb){
		Color cor = new Color(_rgb);
		
		rgb = cor.getRGB();
		r = cor.getRed();
		g = cor.getGreen();
		b = cor.getBlue();
	}
	public void setRGBIsolados(int _r, int _g, int _b){
		Color cor = new Color(_r, _g, _b);
		
		rgb = cor.getRGB();
		r = cor.getRed();
		g = cor.getGreen();
		b = cor.getBlue();
	}
	public void setCinza(int _intensidade){
		Color cor = new Color(_intensidade, _intensidade, _intensidade);
		
		rgb = cor.getRGB();
		r = cor.getRed();
		g = cor.getGreen();
		b = cor.getBlue();
	}
	
	//---- Saída
	public int getRGB(){
		return rgb;
	}
	public int getRed(){
		return r;
	}
	public int getGreen(){
		return g;
	}
	public int getBlue(){
		return b;
	}
	
	
	//---- Quantização
	public void quantizacao(int _tamanhoIntervalos){
		if(escalaCinza()){
			r = corQuantizacao(r,_tamanhoIntervalos);
			Color cor = new Color(r, r, r);
			setCinza(cor.getRed());
		}
		else{
			r = corQuantizacao(r, _tamanhoIntervalos);
			g = corQuantizacao(g, _tamanhoIntervalos);
			b = corQuantizacao(b, _tamanhoIntervalos);
			Color cor = new Color(r, g, b);
			setRGB(cor.getRGB());
		}
	}
	private int corQuantizacao(int _corCanal, int _tamanhoIntervalos){
		//r = coeficienteIntervalo*_tamanhoIntervalos
		if(_tamanhoIntervalos != 0){
			int coeficienteIntervalo;
			coeficienteIntervalo = (int) Math.floor(_corCanal/_tamanhoIntervalos);
			return coeficienteIntervalo*_tamanhoIntervalos;
		}
		else{
			return _corCanal;
		}
	}
	
	//---- Luminância
	public void calcularLuminancia(){
		if(colorido()){
			int luminancia = (int) (0.299*r + 0.587*g + 0.114*b);
			setCinza(luminancia);
		}
	}
	
	//---- Características
	public boolean escalaCinza(){
		if(r == g && g == b){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean colorido(){
		if(!escalaCinza()){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	
	
	
}
