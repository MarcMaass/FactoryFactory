package de.hsrm.mi.swt.Dispatcher.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.hsrm.mi.swt.Dispatcher.intf.IHandler;

/**
 * Handler for a SAX-Parser, which creates IHandler objects with reflection and
 * connects all handler objects. Priority is given by the order of the xml file.
 * 
 */
public class HandlerHandler extends DefaultHandler {
	private boolean flag;
	private IHandler lastHandler;
	private IHandler firstHandler;

	public IHandler getFirstHandler() {
		return firstHandler;
	}

	public HandlerHandler() {
		flag = false;
		// queue elements which haven't been built
		lastHandler = null;
		firstHandler = null;
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) {
		if (qName.equals("handler")) {
			flag = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		// build Handler if tag has been a handler-tag
		if (flag) {
			StringBuffer b = new StringBuffer();
			for (int i = start; i < (start + length); i++) {
				b.append(ch[i]);
			}
			// add package name to avoid ClassNotFoundException
			String packageName = "de.hsrm.mi.swt.Dispatcher.Handler.";
			String handler = packageName + b.toString();

			try {
				Class<?> c = Class.forName(handler);
				Constructor<?> constructor = c.getConstructor();
				// create new handler object
				IHandler h = (IHandler) constructor.newInstance();

				// connect handler chain
				if (lastHandler != null) {
					lastHandler.setNext(h);
				}
				lastHandler = h;

				// save reference to the first handler
				if (firstHandler == null) {
					firstHandler = h;
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			flag = false;
		}
	}
}
