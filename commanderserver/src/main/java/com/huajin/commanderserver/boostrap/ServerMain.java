package com.huajin.commanderserver.boostrap;

import org.springframework.boot.SpringApplication;

import com.huajin.commander.CommanderServerApplication;

/**
 * 部署平台需要的启动类。
 * 
 * @author bo.yang
 */
public class ServerMain {
	public static void main(String[] args) {
		SpringApplication.run(CommanderServerApplication.class, args);
	}
}
