package br.edu.popjudge.bean;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class GenericBean {

	public static void setSessionValue(String key, Object value) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(true);

		session.setAttribute(key, value);
	}

	public static Object getSessionValue(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(false);

		return session.getAttribute(key);
	}

	public static void invalidateSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.invalidate();
	}

	public static void setMessage(String clientID, String summary, String key,
			Severity severity) {

		FacesMessage message = new FacesMessage(message(key));
		message.setSeverity(severity);

		FacesContext fc = FacesContext.getCurrentInstance();

		fc.addMessage(clientID, message);
	}

	public static void setMessage(String key, Severity severity) {
		setMessage(null, null, key, severity);
	}
	
	private static String message(String key) {
		
		FacesContext fc = FacesContext.getCurrentInstance();

		String text = null;

		try {

			ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages",
					fc.getViewRoot().getLocale());
			text = bundle.getString(key);

		} catch (Exception e) {
			text = key;
		}

		return text;
	}

}
