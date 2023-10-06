package eventSystem;

import java.io.IOException;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

public class Handler<E extends EventObject> { //responsible for subscription and dispatching events to listeners
    private List<Listener<E>> listenerList = new LinkedList<>();

    public void add(Listener<E> listener){ //adds listener to eventhandler
         this.listenerList.add(listener);
    }

    public void remove(Listener<E> listener){ //removes listener from eventhandler
        this.listenerList.remove(listener);
    }
    public void handle(E event)  {
        for (Listener<E> listener : listenerList) {
            try {
                listener.onCRUDevent(event); //notifies all listeners about event (triggers corresponding event)
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
