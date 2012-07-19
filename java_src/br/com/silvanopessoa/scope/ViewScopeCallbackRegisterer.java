package br.com.silvanopessoa.scope;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructViewMapEvent;
import javax.faces.event.PreDestroyViewMapEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.ViewMapListener;

public class ViewScopeCallbackRegisterer implements ViewMapListener {

	/* (non-Javadoc)
	 * @see javax.faces.event.SystemEventListener#processEvent(javax.faces.event.SystemEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if (event instanceof PostConstructViewMapEvent) {
			PostConstructViewMapEvent viewMapEvent = (PostConstructViewMapEvent) event;
			UIViewRoot viewRoot = (UIViewRoot) viewMapEvent.getComponent();
			viewRoot.getViewMap().put(ViewScope.VIEW_SCOPE_CALLBACKS, new HashMap<String, Runnable>());
		} else if (event instanceof PreDestroyViewMapEvent) {
			PreDestroyViewMapEvent viewMapEvent = (PreDestroyViewMapEvent) event;
			UIViewRoot viewRoot = (UIViewRoot) viewMapEvent.getComponent();
			Map<String, Runnable> callbacks = (Map<String, Runnable>) viewRoot.getViewMap().get(ViewScope.VIEW_SCOPE_CALLBACKS);
			if (callbacks != null) {
				for (Runnable c : callbacks.values()) {
					c.run();
				}
				callbacks.clear();
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.SystemEventListener#isListenerForSource(java.lang.Object)
	 */
	@Override
	public boolean isListenerForSource(Object source) {
		return source instanceof UIViewRoot;
	}
}
