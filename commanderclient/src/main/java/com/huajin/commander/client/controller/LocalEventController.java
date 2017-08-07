package com.huajin.commander.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huajin.commander.client.domain.LocalEventEntity;
import com.huajin.commander.client.repository.LocalEventDao;

/**
 * 查询本地“可靠事件”信息的 controller
 * 
 * @author bo.yang
 */
@RestController
@RequestMapping("/commander")
public class LocalEventController {

	@Autowired
	private LocalEventDao localEventRepository;
	
	@GetMapping(path="/event/{id}")
	public LocalEventEntity findById(@PathVariable("id") Long eventId) {
		LocalEventEntity localEvent = localEventRepository.findByEventId(eventId);
		return localEvent;
	}
}
