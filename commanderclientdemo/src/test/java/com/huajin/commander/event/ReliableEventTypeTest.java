package com.huajin.commander.event;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.huajin.commander.common.ReliableEventType;

public class ReliableEventTypeTest {

	@Test
	public void testValue() {
		ReliableEventType eventType = ReliableEventType.TimelineEvent;
		assertThat(eventType.value(), equalTo(1));
	}
	
	@Test
	public void testName() {
		ReliableEventType eventType = ReliableEventType.TimelineEvent;
		assertThat(eventType.name(), equalTo("TimelineEvent"));
	}
}
