package com.huajin.commander.client.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronization;

import com.huajin.commander.client.ReliableEventManagerClient;


/**
 * 必须是线程安全的
 * 
 * @author bo.yang
 */
public class EventTransactionSynchronization implements TransactionSynchronization{

	private Logger log = LoggerFactory.getLogger(getClass());
	
	//private PlatformTransactionManager transactionManager;
	private ReliableEventManagerClient eventManagerClient;

	public EventTransactionSynchronization(ReliableEventManagerClient reliableEventManagerClient) {
//		this.transactionManager = transactionManager;
		this.eventManagerClient = reliableEventManagerClient;
	}

	@Override
	public void suspend() {
		log.debug("suspend....");
	}

	@Override
	public void resume() {
		log.debug("resume....");
	}

	@Override
	public void flush() {
		log.debug("flush....");
	}

	@Override
	public void beforeCommit(boolean readOnly) {
		log.debug("beforeCommit....");
	}

	@Override
	public void beforeCompletion() {
		log.debug("beforeCompletion....");
	}

	@Override
	public void afterCommit() {
		log.debug("afterCommit....");
	}

	@Override
	public void afterCompletion(int status) {
		log.debug("Transaction sync-manager call afterCompletion with status ({}) [0=commit;1=rollback]", status);
		switch(status) {
		case TransactionSynchronization.STATUS_COMMITTED:
			eventManagerClient.confirmSend();
			break;
		case TransactionSynchronization.STATUS_ROLLED_BACK:
			eventManagerClient.cancelSend();
			break;
		default:
			log.error("Unkown transaction completion status ({})", status);
			eventManagerClient.cancelSend();
			throw new IllegalStateException("Unkown transaction completion status=" + status + ", so we cancel the reliable-event sending!");
		}
	}
	
//	@SuppressWarnings("unused")
//	private void printTransactionInfo() {
//		Thread thread = Thread.currentThread();
//		log.debug("In Thread " + thread.getName() + ":" + thread.getId());
//		log.debug(this.transactionManager.toString());
//		
//		TransactionDefinition definition = 
//				new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_MANDATORY);
//		
//		TransactionStatus status = this.transactionManager.getTransaction(definition);
//		
//		log.debug("Current Transaction Status: " + 
//				ReflectionToStringBuilder.toString(status, ToStringStyle.MULTI_LINE_STYLE));
//	}

}
