package com.huajin.commander.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huajin.commander.client.ReliableEventManagerClientImpl;
import com.huajin.commander.common.ReliableEventType;
import com.huajin.commander.demo.domain.GlobalFile;
import com.huajin.commander.demo.domain.TimelineDetailVo;

@Service
public class BusinessService {
	
	@Autowired
	private ReliableEventManagerClientImpl eventManager;
	
//	@Autowired
//	private OrderRepository orderRepository;
	
	@Transactional
	public void createOrder(Long itemId, Integer amount) {
		// 1. 创建订单
		TimelineDetailVo event = new TimelineDetailVo();
		event.setEventName("创建挂牌");
		
		GlobalFile file = new GlobalFile();
		file.setFileName("文件");
		GlobalFile audit = new GlobalFile();
		audit.setFileName("审核通过");

		//orderRepository.save(order);
		
//		OrderEntity order2 = new OrderEntity();
//		order2.setItemId(itemId+1);
//		order2.setAmount(amount+1);
//		orderRepository.save(order2);
		
		// 2. 保存订单创建事件
		eventManager.createEvents(ReliableEventType.TimelineEvent, event);
//		eventManager.createEvents(ReliableEventType.GlobalFileEvent, file);
//		eventManager.createEvents(ReliableEventType.Audit, audit);
		
		
//		throw new IllegalArgumentException("test rollback");
	}
}
