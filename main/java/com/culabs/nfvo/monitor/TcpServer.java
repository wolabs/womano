package com.culabs.nfvo.monitor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.core.IMonitorListener;
import com.culabs.nfvo.core.IServer;

/**
 * 
 * @desc handler tcp message from vm tcp agent.
 * @author tanch
 * @since
 *
 */
public class TcpServer {

	private static Logger LOGGER = LoggerFactory.getLogger(TcpServer.class);
	private static final CopyOnWriteArrayList<IMonitorListener> STATUS_LISTENER = new CopyOnWriteArrayList<IMonitorListener>();
	private static final BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	private static ExecutorService executor = Executors.newFixedThreadPool(6);
	private static int port = 2233;
	private static ServerSocket serverSocket = null;
	
	/**
	 * init tcp serversocket and consumer.
	 */
	public static void init()
	{
		try
		{
			initServer();
			consumer();
			
		} catch (Exception e)
		{
			LOGGER.error("init TcpServer failed. Detail: {}", e);
		}
	}

	/**
	 * ServerSocket listen on port 2233, accept connection from vm agent.
	 * @throws IOException
	 */
	public static void initServer() throws IOException {
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					serverSocket = new ServerSocket(port);
					while (true) {
						Socket socket = serverSocket.accept();
						new SocketReceiveThread(socket).start();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});

	}

	/**
	 *  Take the message from queue, and then send it to monitor listener for showing vm status real-timely.
	 * 
	 */
	private static void consumer() { 
		executor.submit(new Runnable() {

			@Override
			public void run() {
				while (true) {
					String message = null;
					try {
						message = queue.take();
					} catch (InterruptedException e) {
						LOGGER.error("Packet queue be intterrupted: {}", e);
					}
					
					String[] statusinfo = null;
					statusinfo = message.split("\\|");
					if (statusinfo.length < 2) {
						statusinfo[1] = "";
					} else {
						statusinfo[1].substring(1);
					}
					notifyMonitorListener(statusinfo[0], statusinfo[1]);

				}
			}
		});
	}
	
	private static void notifyMonitorListener(String mac, String status){
		try {
			LOGGER.info("MAC: {}, status: {}", mac, status);
			for (IMonitorListener listener : STATUS_LISTENER) {
				listener.monitor(mac, status);
			}
		} catch (Exception e) {
			LOGGER.error("Monitor notify exception: {}", e);
		}
	}
	
	
	public static void shutdown()
	{
		try {
			serverSocket.close();
			executor.shutdownNow();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * @Title: register
	 * @Description: TODO
	 * @param @param server
	 * @return void
	 * @throws
	 */
	public static void register(IMonitorListener server)
	{
		if (!STATUS_LISTENER.contains(server))
		{
			STATUS_LISTENER.add(server);
		}
	}

	public static void unregister(IServer server)
	{
		if (STATUS_LISTENER.contains(server))
		{
			STATUS_LISTENER.remove(server);
		}
	}

	public static void response(Socket socket, String resp)
	{
		try
		{
			Writer writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));  
			writer.write(resp);
			writer.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * Construct a new thread for each vm client, so we can receive the message 
	 * and handler the case of tcp disconnectiong independently.  
	 */
	static class SocketReceiveThread extends Thread {
		private Socket socket;

		public Socket getSocket() {
			return socket;
		}

		public void setSocket(Socket socket) {
			this.socket = socket;
		}

		public SocketReceiveThread(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			Reader reader = null;
			String mac = "";
			try {
				while (true) {
					reader = new InputStreamReader(socket.getInputStream());
					CharBuffer charBuffer = CharBuffer.allocate(8192);
					int readIndex = -1;
					if ((readIndex = reader.read(charBuffer)) != -1) {
						charBuffer.flip();
						String serviceInfo = charBuffer.toString();
						String[] statusinfo = serviceInfo.split("\\|");
						mac = statusinfo[0];
						queue.put(serviceInfo);
					}else {
						LOGGER.warn("================= vm " + mac + " stop !=======================");
						notifyMonitorListener(mac, "-1");
						break;
					} 
				}
			} catch (Exception e) {
				notifyMonitorListener(mac, "-1");
				e.printStackTrace();
			} finally {
				try {
					if (null != reader) {
						reader.close();
					}
					if (socket != null && !socket.isClosed()) {
						socket.close();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
