package com.huajin.commander.client.service;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;


@Service
public class RandomIdServiceImpl implements IdService {

	@Override
	public Long nextId() {
		
		return RandomUtils.nextLong();
	}

}
