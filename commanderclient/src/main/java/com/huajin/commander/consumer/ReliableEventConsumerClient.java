package com.huajin.commander.consumer;


public interface ReliableEventConsumerClient {
	
	void registerConsumer(ReliableEventConsumer consumer);
}
