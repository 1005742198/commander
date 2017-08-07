package com.huajin.commander.demo.controller;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huajin.commander.demo.service.BusinessService;

@RestController
public class OrderController {
	
//	private Logger log = LoggerFactory.getLogger(OrderController.class);
	
//	@Autowired
//	private ReliableEventManagerClient eventManager;

	@Autowired
	private BusinessService businessService;
	
	@GetMapping("/order")
	public String place(
			@RequestParam Long itemId, 
			@RequestParam Integer amount
	) throws UnknownHostException {
		
		businessService.createOrder(itemId, amount);
		return "ok";
	}
	
}
