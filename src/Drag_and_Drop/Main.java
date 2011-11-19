package Drag_and_Drop;

import Interface.*;
//importante comando para saber onde está sendo lido o arquivo!
//System.out.println(System.getProperty("user.dir")); 

public final class Main {
	public static JanelaInicial janela;
	
	public static void main(String[] args) {
		//JFrame.setDefaultLookAndFeelDecorated(true);
		janela = new JanelaInicial(1, 40);
		janela.exibir();
	}
	
	
	
	
	
	

}
