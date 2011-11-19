package eventoProgresso;

import javax.swing.event.EventListenerList;

public class RegistradorEventosProgresso {
  protected EventListenerList listenerList = new EventListenerList();

  public void addEventoProgressoListener(EventoProgressoListener listener) {
    listenerList.add(EventoProgressoListener.class, listener);
  }
  public void removeEventoProgressoListener(EventoProgressoListener listener) {
    listenerList.remove(EventoProgressoListener.class, listener);
  }
  public void fireEventoProgresso(EventoProgresso evt) {
    Object[] listeners = listenerList.getListenerList();
    for (int i = 0; i < listeners.length; i = i+2) {
      if (listeners[i] == EventoProgressoListener.class) {
        ((EventoProgressoListener) listeners[i+1]).progrediu(evt);
      }
    }
  }
}