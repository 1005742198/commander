package com.huajin.commander.consumer;

import com.huajin.commander.common.ReliableEvent;

public interface ReliableEventRepeatChecker {
	
	boolean isProcessed(ReliableEvent reliableEvent);
}
