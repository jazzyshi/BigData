package com.jz.snake.important;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by jzshi on 2018/5/21.
 */
public class LocalAddressUtil {
	public static final String getIp() {
		try {
			String local = fetchIpv4();
			if (local == null)
				local = fetchIpv6();
			if (local == null)
				local = "127.0.0.1";
			return local;
		} catch (SocketException e) {
			return "127.0.0.1";
		}
	}
	
	/**
	 * 获取本地IPv4地址
	 */
	public static final String getIpv4() {
		String local;
		try {
			local = fetchIpv4();
			if (local != null)
				return local;
		} catch (SocketException e) {
		}
		
		return "127.0.0.1";
	}
	
	/**
	 * 获取本地IPv6地址
	 * @return
	 */
	public static final String getIpv6() {
		String local;
		try {
			local = fetchIpv6();
			if (local != null)
				return local;
		} catch (SocketException e) {
		}
		return "::1";
	}
	
	/**
	 * 获取本机所有有效的外网地址，不包括内网地址，包括IPv4和IPv6地址
	 * @return
	 * @throws SocketException
	 */
	public static final List<String> getAvailableIps() throws SocketException {
		return getAvailableIps(true);
	}
	
	/**
	 * 获取本机所有有效的IP地址，包括IPv4和IPv6地址
	 * @param ignoreInternals 是否过滤掉内网地址
	 * @return
	 * @throws SocketException
	 */
	public static final List<String> getAvailableIps(boolean ignoreInternals) throws SocketException {
		List<String> ips = new ArrayList<String>();
		ips.addAll(getAvailableIpv4s(ignoreInternals));
		ips.addAll(getAvailableIpv6s(ignoreInternals));
		return ips;
	}
	
	/**
	 * 获得本机所有有效的IPv4地址
	 * @param ignoreInternals 是否忽略内网地址
	 * @return
	 * @throws SocketException
	 */
	public static final List<String> getAvailableIpv4s(boolean ignoreInternals) throws SocketException {
		List<String> ips = new ArrayList<String>();
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (ip.isLoopbackAddress() || ip.isLinkLocalAddress() || Inet6Address.class.isAssignableFrom(ip.getClass())) {
					continue;
				}
				
				if (!ip.isSiteLocalAddress() || !ignoreInternals) {
					ips.add(ip.getHostAddress());
				}
				
			}
		}
		
		return ips;
	}

	/**
	 * 获取本机所有有效的IPv6地址
	 * @param ignoreInternals 是否忽略内网地址
	 * @return
	 * @throws SocketException
	 */
	public static final List<String> getAvailableIpv6s(boolean ignoreInternals) throws SocketException {
		List<String> ips = new ArrayList<String>();
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (ip.isLoopbackAddress() || ip.isLinkLocalAddress() || Inet4Address.class.isAssignableFrom(ip.getClass())) {
					continue;
				}
				if (!ip.isSiteLocalAddress() || !ignoreInternals) {
					String hostaddr = ip.getHostAddress();
					int idx;
					if ((idx = hostaddr.indexOf("%")) > -1) {
						hostaddr = hostaddr.substring(0, idx);
					}
					ips.add(hostaddr);
				}
				
			}
		}
		
		return ips;
	}
	
	/**
	 * 获取本机机器名
	 * <b>如果机器名为中文，则返回IP地址</b>
	 * @return
	 */
	public static final String getHostName() {
		String hostname;
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (Exception e) { //处理中文机器名问题，使用IP代替
			hostname = getIp();
		}
		return hostname;
	}
	
	private static String fetchIpv4() throws SocketException {
		List<String> ips = getAvailableIpv4s(true);
		if (ips == null || ips.isEmpty())
			ips = getAvailableIpv4s(false);
		
		if (ips != null && !ips.isEmpty())
			return ips.iterator().next();
		return null;
	}
	
	private static String fetchIpv6() throws SocketException {
		List<String> ips = getAvailableIpv6s(true);
		if (ips == null || ips.isEmpty()) {
			ips = getAvailableIpv6s(false);
		}
		
		if (ips != null && !ips.isEmpty()) {
			return ips.iterator().next();
		}
		return null;
	}

}
