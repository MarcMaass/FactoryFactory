package de.hsrm.mi.swt.Dispatcher;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.hsrm.mi.swt.Dispatcher.Utils.HandlerHandler;
import de.hsrm.mi.swt.Dispatcher.intf.IDispatcher;
import de.hsrm.mi.swt.Dispatcher.intf.IHandler;
import de.hsrm.mi.swt.Network.Communication.Command;
import de.hsrm.mi.swt.Network.Communication.enums.CommandType;
import de.hsrm.mi.swt.Network.Communication.intf.ICommand;
import de.hsrm.mi.swt.Network.Controller.intf.IController;

/**
 * The Dispatcher delivers ICommands from controller to different managers. It
 * also builds the handler-chain.
 * 
 */
public class Dispatcher implements IDispatcher {
	private IHandler handler;

	public Dispatcher() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser;
		try {
			saxParser = factory.newSAXParser();
			DefaultHandler myHandler = new HandlerHandler();
			try {
				// build handler chain from xml structure
				saxParser.parse(new File("./config/handler.xml"), myHandler);
				handler = ((HandlerHandler) myHandler).getFirstHandler();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		// IHandler h1 = new LogHandler();
		// IHandler h2 = new AuthentificationHandler();
		// IHandler h3 = new CommandPatternHandler();
		// IHandler h4 = new FactoryHandler();
		// IHandler h5 = new MachineHandler();
		// IHandler h6 = new LogoutHandler();
		// IHandler h7 = new DefaultHandler();
		//
		// h1.setNext(h2);
		// h2.setNext(h3);
		// h3.setNext(h4);
		// h4.setNext(h5);
		// h5.setNext(h6);
		// h6.setNext(h7);
		//
		// handler = h1;
	}

	@Override
	public void dispatch(ICommand c, IController controller) {
		try {
			handler.handle(c, null, controller);
		} catch (Exception e) {
			controller.respond(new Command(CommandType.EXCEPTION, c.getTarget(), e.getMessage(), true));
		}
	}

	public IHandler getHandler() {
		return handler;
	}

}
