package com.huajin.commander.manager.schedule;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.huajin.commander.common.ReliableEventStatus;
import com.huajin.commander.manager.domain.GlobalEventEntity;
import com.huajin.commander.manager.mq.MessageSender;
import com.huajin.commander.manager.repository.GlobalEventRepository;

/**
 * 事件恢复任务
 * 
 * @author bo.yang
 */
@Component
public class EventRecoveryTask {

    private static final Logger log = LoggerFactory.getLogger(EventRecoveryTask.class);

    @Autowired
    private GlobalEventRepository eventRepository;
    
    @Autowired
    private MessageSender msgService;
    
    /**
     * 恢复“可靠事件”的发送。
     * <p>遍历“已创建” 状态 且有一段时间的事件，然后查询相关服务“发送或取消”该可靠事件。
     */
    @Scheduled(fixedRate=20000)	// 单位毫秒
	public void recoverEvent() {
		log.debug("Start recovering-event task...");
		List<GlobalEventEntity> events = eventRepository.findByStatusAndCreateTimeLessThan(
				ReliableEventStatus.Created.value(),
				DateUtils.addSeconds(new Date(), -30));
		log.debug("Size of candidate-events for recover-by-query: {}", events.size());
		for (GlobalEventEntity eventEntity : events) {
			msgService.sendQueryStatusMessage(eventEntity.getEventId());
		}
		log.debug("End of recovering-event task.");
	}

}
