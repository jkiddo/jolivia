/*******************************************************************************
 * Copyright (c) 2013 Jens Kristian Villadsen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Jens Kristian Villadsen - Lead developer, owner and creator
 ******************************************************************************/
package org.dyndns.jkiddo;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import javax.jmdns.JmmDNS;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.dyndns.jkiddo.guice.JoliviaListener;
import org.dyndns.jkiddo.jetty.extension.DmapConnector;
import org.dyndns.jkiddo.logic.desk.DeskMusicStoreReader;
import org.dyndns.jkiddo.logic.interfaces.IImageStoreReader;
import org.dyndns.jkiddo.logic.interfaces.IMusicStoreReader;
import org.dyndns.jkiddo.raop.ISpeakerListener;
import org.dyndns.jkiddo.service.daap.client.IClientSessionListener;
import org.dyndns.jkiddo.service.daap.server.DAAPResource;
import org.dyndns.jkiddo.service.daap.server.MusicItemManager;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.inject.servlet.GuiceFilter;

public class Jolivia
{
	static Logger logger = LoggerFactory.getLogger(Jolivia.class);

	public static void main(String[] args) throws Exception
	{

		new Jolivia(new DeskMusicStoreReader(), new IImageStoreReader() {

			@Override
			public Set<IImageItem> readImages() throws Exception
			{
				// TODO Auto-generated method stub
				return Sets.newHashSet();
			}

			@Override
			public File getImage(IImageItem image) throws Exception
			{
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

	public Jolivia(IClientSessionListener clientSessionListener) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", clientSessionListener, null, null, null);
	}

	public Jolivia(IClientSessionListener clientSessionListener, IMusicStoreReader musicStoreReader, IImageStoreReader imageStoreReader) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", clientSessionListener, null, musicStoreReader, imageStoreReader);
	}

	public Jolivia(IClientSessionListener clientSessionListener, IImageStoreReader imageStoreReader) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", clientSessionListener, null, null, imageStoreReader);
	}

	public Jolivia(IClientSessionListener clientSessionListener, IMusicStoreReader musicStoreReader) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", clientSessionListener, null, musicStoreReader, null);
	}

	public Jolivia(ISpeakerListener speakerListener) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", null, speakerListener, null, null);
	}

	public Jolivia(ISpeakerListener speakerListener, IMusicStoreReader musicStoreReader) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", null, speakerListener, musicStoreReader, null);
	}

	public Jolivia(ISpeakerListener speakerListener, IImageStoreReader imageStoreReader) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", null, speakerListener, null, imageStoreReader);
	}

	public Jolivia(ISpeakerListener speakerListener, IMusicStoreReader musicStoreReader, IImageStoreReader imageStoreReader) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", null, speakerListener, musicStoreReader, imageStoreReader);
	}

	public Jolivia(IClientSessionListener clientSessionListener, ISpeakerListener speakerListener, IMusicStoreReader musicStoreReader, IImageStoreReader imageStoreReader) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", clientSessionListener, speakerListener, musicStoreReader, imageStoreReader);
	}

	public Jolivia(IClientSessionListener clientSessionListener, ISpeakerListener speakerListener, IMusicStoreReader musicStoreReader) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", clientSessionListener, speakerListener, musicStoreReader, null);
	}

	public Jolivia(IClientSessionListener clientSessionListener, ISpeakerListener speakerListener, IImageStoreReader imageStoreReader) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", clientSessionListener, speakerListener, null, imageStoreReader);
	}

	public Jolivia(IClientSessionListener clientSessionListener, ISpeakerListener speakerListener) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", clientSessionListener, speakerListener, null, null);
	}

	public Jolivia(IImageStoreReader imageStoreReader) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", null, null, null, imageStoreReader);
	}

	public Jolivia(IMusicStoreReader musicStoreReader) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", null, null, musicStoreReader, null);
	}
	
	public Jolivia(IMusicStoreReader musicStoreReader, IImageStoreReader imageStoreReader) throws Exception
	{
		this(4000, 5000, 1337, "Jolivia", null, null, musicStoreReader, imageStoreReader);
	}

	public Jolivia(Integer port, Integer airplayPort, Integer pairingCode, String name, IClientSessionListener clientSessionListener, ISpeakerListener speakerListener, IMusicStoreReader musicStoreReader, IImageStoreReader imageStoreReader) throws Exception
	{
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		Preconditions.checkArgument(!(pairingCode > 9999), "Pairingcode must be expressed within 4 ciphers");
		logger.info("Starting " + name + " on port " + port);
		Server server = new Server(port);
		// Server server = new
		// Server(InetSocketAddress.createUnresolved("0.0.0.0", port));
		Connector dmapConnector = new DmapConnector();
		dmapConnector.setPort(port);
		// ServerConnector dmapConnector = new ServerConnector(server, new
		// DmapConnectionFactory());
		// dmapConnector.setPort(port);
		server.setConnectors(new Connector[] { dmapConnector });

		// Guice
		ServletContextHandler sch = new ServletContextHandler(server, "/");
		sch.addEventListener(new JoliviaListener(port, airplayPort, pairingCode, name, clientSessionListener, speakerListener, imageStoreReader, musicStoreReader));
		sch.addFilter(GuiceFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
		sch.addServlet(DefaultServlet.class, "/");

		// No Guice No. 1
		// ServletContextHandler sch = new ServletContextHandler(server, "/",
		// ServletContextHandler.SESSIONS);
		// sch.addServlet(new ServletHolder(new ServletContainer(new
		// DefaultResourceConfig(DAAPServlet.class))), "/*");

		// No Guice No. 2
		// ServletContextHandler sch = new
		// ServletContextHandler(ServletContextHandler.SESSIONS);
		// sch.setContextPath("/");
		// server.setHandler(sch);
		// sch.addServlet(new ServletHolder(new JoliviaServlet()), "/*");

		server.start();
		logger.info(name + " started");
		server.join();
	}

	// http://download.eclipse.org/jetty/stable-8/apidocs/
	// http://download.eclipse.org/jetty/stable-8/xref/

	class JoliviaServlet extends HttpServlet
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3494608154563076505L;
		private DAAPResource daap;

		public JoliviaServlet()
		{
			try
			{
				daap = new DAAPResource(JmmDNS.Factory.getInstance(), 4000, "Jolivia", new MusicItemManager("Jolivia", new DeskMusicStoreReader()));
			}
			catch(IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
		{
			System.out.println(httpServletRequest.getPathInfo());
			Response response = null;
			try
			{
				if(httpServletRequest.getPathInfo().startsWith("/server-info"))
					response = daap.serverInfo(httpServletRequest, httpServletResponse);
				else if(httpServletRequest.getPathInfo().startsWith("/login"))
					response = daap.login(httpServletRequest, httpServletResponse);
				else if(httpServletRequest.getPathInfo().startsWith("/update"))
					response = daap.update(httpServletRequest, httpServletResponse, getParameterAsInteger("session-id", httpServletRequest), getParameterAsInteger("revision-number", httpServletRequest), getParameterAsInteger("delta", httpServletRequest), getParameterAsInteger("daap-no-disconnect", httpServletRequest));
				else if(httpServletRequest.getPathInfo().startsWith("/databases") && httpServletRequest.getPathInfo().endsWith("items") && httpServletRequest.getPathInfo().contains("containers"))
					response = daap.containerItems(httpServletRequest, httpServletResponse, 1, 0, getParameterAsInteger("session-id", httpServletRequest), getParameterAsInteger("revision-number", httpServletRequest), getParameterAsInteger("delta", httpServletRequest), getParameterAsString("meta", httpServletRequest), getParameterAsString("type", httpServletRequest), getParameterAsString("group-type", httpServletRequest), getParameterAsString("sort", httpServletRequest), getParameterAsString("include-sort-headers", httpServletRequest), getParameterAsString("query", httpServletRequest), getParameterAsString("index", httpServletRequest));
				else if(httpServletRequest.getPathInfo().startsWith("/databases") && httpServletRequest.getPathInfo().endsWith("items"))
					response = daap.items(httpServletRequest, httpServletResponse, 0, getParameterAsInteger("session-id", httpServletRequest), getParameterAsInteger("revision-number", httpServletRequest), getParameterAsInteger("delta", httpServletRequest), getParameterAsString("type", httpServletRequest), getParameterAsString("meta", httpServletRequest), getParameterAsString("query", httpServletRequest));
				else if(httpServletRequest.getPathInfo().startsWith("/databases") && httpServletRequest.getPathInfo().endsWith("containers"))
					response = daap.containers(httpServletRequest, httpServletResponse, 0, getParameterAsInteger("session-id", httpServletRequest), getParameterAsInteger("revision-number", httpServletRequest), getParameterAsInteger("delta", httpServletRequest), getParameterAsString("meta", httpServletRequest));
				else if(httpServletRequest.getPathInfo().startsWith("/databases") && httpServletRequest.getPathInfo().endsWith("mp3"))
				{
					// response = daap.item(httpServletRequest,
					// httpServletResponse, databaseId, itemId, format,
					// rangeHeader);
				}
				else if(httpServletRequest.getPathInfo().startsWith("/databases"))
					response = daap.databases(httpServletRequest, httpServletResponse, getParameterAsInteger("session-id", httpServletRequest), getParameterAsInteger("revision-number", httpServletRequest), getParameterAsInteger("delta", httpServletRequest));
				else
					System.out.println("Unmapped ...");

				if(response != null)
				{
					byte[] e = (byte[]) response.getEntity();
					MultivaluedMap<String, Object> meta = response.getMetadata();
					Set<String> keys = meta.keySet();
					for(String key : keys)
					{
						httpServletResponse.setHeader(key, (String) meta.getFirst(key));
					}
					httpServletResponse.setContentLength(e.length);
					httpServletResponse.setStatus(response.getStatus());
					ServletOutputStream outputStream = httpServletResponse.getOutputStream();
					outputStream.write(e);
					outputStream.flush();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		private int getParameterAsInteger(String string, HttpServletRequest httpServletRequest)
		{
			String value = getParameterAsString(string, httpServletRequest);
			if(value != null)
			{
				return Integer.parseInt(value);
			}
			return -1;
		}

		private String getParameterAsString(String string, HttpServletRequest httpServletRequest)
		{
			String[] v = httpServletRequest.getParameterValues(string);
			if(v == null)
			{
				return null;
			}
			String value = v[0];
			return value;
		}
	}
}