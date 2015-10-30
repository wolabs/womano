package com.culabs.nfvo.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * SSH工具类
 * 
 * @author swj 2015-4-20
 */
public class SSHOperator
{

	/**
	 * 远程 执行命令并返回结果调用过程 是同步的（执行完才会返回）
	 * 
	 * @param host
	 *            主机名
	 * @param user
	 *            用户名
	 * @param psw
	 *            密码
	 * @param port
	 *            端口
	 * @param command
	 *            命令
	 * @return
	 */
	public static String exec(String host, String user, String psw, int port, String command)
	{
		String result = "";
		Session session = null;
		ChannelExec openChannel = null;
		try
		{
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(psw);
			session.connect();
			openChannel = (ChannelExec) session.openChannel("exec");
			openChannel.setCommand(command);
			// int exitStatus = openChannel.getExitStatus();
			openChannel.connect();
			InputStream in = openChannel.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String buf = null;
			while ((buf = reader.readLine()) != null)
			{
				result += new String(buf.getBytes("gbk"), "UTF-8") + "    <br>\r\n";
			}
		} catch (JSchException | IOException e)
		{
			result += e.getMessage();
		} finally
		{
			if (openChannel != null && !openChannel.isClosed())
			{
				openChannel.disconnect();
			}
			if (session != null && session.isConnected())
			{
				session.disconnect();
			}
		}
		return result;
	}

	public static void main(String args[])
	{
		// String exec = exec("172.72.72.55", "root", "cdn%nf", 22,
		// "df -h;ls;");
		// System.out.println(exec);
	}
}