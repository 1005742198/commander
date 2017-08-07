package com.huajin.commander.client.mq;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.math.RandomUtils;

/**
 * 消息队列工具类。
 * 
 * @author bo.yang
 */
public class MQUtils {
	public static String randomQueueName(String prefix) {
		StringBuffer buf = new StringBuffer(prefix);
		String host = "localhost";
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
		}
		int randomNum = RandomUtils.nextInt(999);
		buf.append("_").append(host).append(randomNum);
		return buf.toString();
	}
}
