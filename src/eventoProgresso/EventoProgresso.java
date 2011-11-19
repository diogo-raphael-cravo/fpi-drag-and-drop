package eventoProgresso;

import java.util.EventObject;

public class EventoProgresso extends EventObject {
	private static final long serialVersionUID = 1L;
	public String nome;
	public int variacao;
	public EventoProgresso(Object source) {
		super(source);
	}
}