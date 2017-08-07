package com.huajin.commander.client.service;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 使用 Twitter Id 生成算法 snowflake
 * Created by shiqian.zhang on 2017/6/30.
 *
 */
@Service("snowflakeIdService")
public class SnowflakeIdServiceImpl implements IdService{

    protected static final Logger LOG = LoggerFactory.getLogger(SnowflakeIdServiceImpl.class);

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;

    private long twepoch = 1288834974657L;

    private long workerIdBits = 5L;
    private long datacenterIdBits = 5L;
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private long sequenceBits = 12L;

    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;

    @PostConstruct
    private void init (){
        RandomData randomData = new RandomDataImpl();
        this.workerId = randomData.nextLong(0,this.maxWorkerId);
        this.datacenterId = randomData.nextLong(0,maxDatacenterId);
    }

    @Override
    public synchronized Long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            LOG.error(String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
