package com.huajin.commander.manager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 运维监控地址
 * @author zhiya.chai
 *
 */
@RestController
@RequestMapping("/f5/monitor")
public class F5MonitorController {

	@GetMapping("/active")
	public Object monitor() {
		return "ok";
	}
}
