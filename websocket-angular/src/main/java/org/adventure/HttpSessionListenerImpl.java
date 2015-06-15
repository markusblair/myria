package org.adventure;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.adventure.items.armor.Armor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
	private static Logger log = LoggerFactory.getLogger(HttpSessionListenerImpl.class);
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		log.debug(se.getSession().getId() + " opened");

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		log.debug(se.getSession().getId() + " closed");
	}

}
