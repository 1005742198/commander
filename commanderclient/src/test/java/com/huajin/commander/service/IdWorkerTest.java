package com.huajin.commander.service;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/30.
 */
public class IdWorkerTest {
    static class IdWorkThread implements Runnable {
        private Set<Long> set;
        private IdWorker idWorker;

        public IdWorkThread(Set<Long> set, IdWorker idWorker) {
            this.set = set;
            this.idWorker = idWorker;
        }

        @Override
        public void run() {
            while (true) {
                long id = idWorker.nextId();
                System.out.println(id);
                if (!set.add(id)) {
                    System.out.println("duplicate:" + id);
                }
            }
        }
    }

    public static void main(String[] args) {
        RandomData randomData = new RandomDataImpl();
        Set<Long> set = new HashSet<Long>();
        final IdWorker idWorker1 = new IdWorker(randomData.nextInt(0,31),randomData.nextInt(0,31));
        final IdWorker idWorker2 = new IdWorker(randomData.nextInt(0,31),randomData.nextInt(0,31));
        Thread t1 = new Thread(new IdWorkThread(set, idWorker1));
        Thread t2 = new Thread(new IdWorkThread(set, idWorker2));
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        //t2.start();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
