package com.huajin.commander.client.service;

/**
 * 全局id生成器.
 * 
 * @author bo.yang
 *
 */
public interface IdService {

	/**
	 * 生成新的全局唯一id
	 * @return 全局唯一ID
	 */
	Long nextId();
}
