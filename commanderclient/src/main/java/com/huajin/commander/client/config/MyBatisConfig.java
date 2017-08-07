package com.huajin.commander.client.config;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.huajin.commander.client.repository.LocalEventDao;

/**
 * 配置 mybatis.
 * <p>
 * 主要工作是手工注册 mapper，因为不想引入对baymax中的 @MyBatisDao
 * 
 * @author bo.yang
 * @date 2017年6月30日 上午9:38:43
 */
@Configuration
public class MyBatisConfig {

	@Bean
	public LocalEventDao localEventDao(SqlSessionTemplate sessionTemplate) {
		return sessionTemplate.getMapper(LocalEventDao.class);
	}
}
