package eventoProgresso;

import java.util.EventListener;

public interface EventoProgressoListener extends EventListener {
  public void progrediu(EventoProgresso evt);
}