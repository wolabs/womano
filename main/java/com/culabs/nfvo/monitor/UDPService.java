package com.culabs.nfvo.monitor;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.config.SystemConfig;
import com.culabs.nfvo.config.UNFVOConfig;
import com.culabs.nfvo.core.IMonitorListener;
import com.culabs.nfvo.core.IServer;
import com.culabs.nfvo.log.LogService;
import com.culabs.nfvo.model.ConfigBlock;
import com.culabs.nfvo.util.DirUtils;
import com.culabs.nfvo.util.NFVOUtils;

/**
 * @说明 UDP连接服务端，这里一个包就做一个线程处理
 * @author 
 * @version 1.0
 * @Deprecated u can @see com.culabs.nfvo.monitor.TcpServer
 */
@Deprecated
public class UDPService
{
	private static Logger LOGGER = LoggerFactory.getLogger(UDPService.class);
	private static final CopyOnWriteArrayList<IMonitorListener> STATUS_LISTENER = new CopyOnWriteArrayList<IMonitorListener>();
	private static DatagramSocket datagramSocket = null; // 连接对象
	private static final BlockingQueue<DatagramPacket> queue = new LinkedBlockingQueue<DatagramPacket>();
	private static ExecutorService es = Executors.newFixedThreadPool(3);
	private static int port = 2233;

	public static void init()
	{
		try
		{
			init(InetAddress.getLocalHost().getHostAddress().toString(), port);
		} catch (UnknownHostException e)
		{
			LOGGER.error("init UDVService failed. Detail: {}", e);
		}
	}

	/**
	 * 初始化连接
	 * 
	 * @throws SocketException
	 */
	public static void init(String host, int port)
	{
		try
		{
			LOGGER.warn("==============Server status monitor starting...==================");
			datagramSocket = new DatagramSocket(new InetSocketAddress(host, port));
			ConfigBlock block = SystemConfig.getInstance().getBlock("work_environment");
			int timeout = 0;
			if (!NFVOUtils.isEmpty(block))
			{
				timeout = NFVOUtils.toIntger(block.getPairs().getPairVal("monitor_timeout"), timeout);
				LOGGER.info("UDP Monintor timeout: {}", timeout);

			}
			datagramSocket.setSoTimeout(timeout);
			block = SystemConfig.getInstance().getBlock("work_environment");
			if (!NFVOUtils.isEmpty(block))
			{
				port = NFVOUtils.toIntger(block.getPairs().getPairVal("monitor_port"), port);
				LOGGER.info("UDP Monintor host {} port: {}", host, port);
			}
		} catch (Exception e)
		{
			datagramSocket = null;
			LOGGER.error("Receive udp packet failed. Detail: {}", e);
		}
		receive();
		dispatch();
	}

	private static void dispatch()
	{
		es.submit(new Runnable() {

			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				while (true)
				{
					DatagramPacket packet = null;
					try
					{
						packet = queue.take();
					} catch (InterruptedException e)
					{
						LOGGER.error("Packet queue be intterrupted: {}", e);
					}
					String[] statusinfo = null;
					try
					{
						statusinfo = new String(packet.getData(), 0, packet.getLength()).split("\\|");
						if (statusinfo.length < 2)
						{
							statusinfo[1] = "";
						}else{
							statusinfo[1].substring(1);
						}
						LOGGER.info("MAC: {}, status: {}", statusinfo[0], statusinfo[1]);
						for (IMonitorListener listener : STATUS_LISTENER)
						{
							listener.monitor(statusinfo[0], statusinfo[1]);
						}
					} catch (Exception e)
					{
						LOGGER.error("Monitor notify exception: {}", e);
					}
				}
			}
		});
	}

	private static void receive()
	{
		
		es.submit(new Runnable() {
			@Override
			public void run()
			{
				while (true)
				{
					try
					{
						byte[] buffer = new byte[1024 * 64]; // 缓冲区
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
						datagramSocket.receive(packet);
						System.out.println(packet.getAddress() + ":" + packet.getPort() + ":" +datagramSocket.isBound() + ":" + datagramSocket.isClosed());
						queue.put(packet);
					} catch (Exception e)
					{
						LOGGER.error(Thread.currentThread().getName() + "timeout. Receive udp packet failed. Detail: {}", e);
					}
				}
			}
		});
	}

	public static void shutdown()
	{
		datagramSocket.close();
		es.shutdownNow();
		LOGGER.warn("==============Server status monitor stopped===================");
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

	/**
	 * 
	 * @Title: unregister
	 * @Description: TODO
	 * @param @param server
	 * @return void
	 * @throws
	 */
	public static void unregister(IServer server)
	{
		if (STATUS_LISTENER.contains(server))
		{
			STATUS_LISTENER.remove(server);
		}
	}

	/**
	 * 将响应包发送给请求端
	 * 
	 * @param bt
	 * @throws IOException
	 */
	public static void response(DatagramPacket packet)
	{
		try
		{
			datagramSocket.send(packet);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		LogService.prepare();
		UNFVOConfig.getInstance().init(new File(DirUtils.getInstance().getConfigDir() + "/unfvo-config.xml"));
		init();
	}
}