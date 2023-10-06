package eventSystem;

import java.io.IOException;
import java.util.EventListener;
import java.util.EventObject;

public interface Listener<E extends EventObject> extends EventListener {
    void onCRUDevent(E event) throws ClassNotFoundException, IOException;

}
