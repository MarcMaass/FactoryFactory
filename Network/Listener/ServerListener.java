package de.hsrm.mi.swt.Network.Listener;

import java.util.EventListener;

/**
 * Interface for ServerListener
 * @author Mario Wandpflug, Justin Albert
 *
 * @param <T>
 */
public interface ServerListener<T> extends EventListener {
	public void fireServerEvent(ServerEvent<T> se);
}
