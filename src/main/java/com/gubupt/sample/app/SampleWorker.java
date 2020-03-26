package com.gubupt.sample.app;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Sample Worker
 * Start 5~200 thread, consume 0~10Mbytes memory and Sleep for 1 ~ 11 seconds
 */
public class SampleWorker {

	public SampleWorker(){
	}
	
	public void work() {
		
		long startTime = System.currentTimeMillis();
		
		Random rand = new Random(); 
        int threadNumber = rand.nextInt(195) + 5;
        ExecutorService taskExecutor = Executors.newFixedThreadPool(threadNumber);
        // Consume Thread Test
        for (int thread = 0; thread < threadNumber; thread++) {
        	final long duration = rand.nextInt(10000) + 1000;
        	// Max Memory Consumption: 200 * 50K = 10Mbytes
        	final int kBytes = rand.nextInt(50);
            taskExecutor.execute(new WorkerThread("Thread" + thread, kBytes, duration));
        }
        taskExecutor.shutdown();
        try {
        	  taskExecutor.awaitTermination(30, TimeUnit.SECONDS);
        	} 
        catch (InterruptedException e) {
        		e.printStackTrace();
        	}
        finally {
        	System.out.println(String.format("Executed %d Threads in %d ms.", threadNumber, System.currentTimeMillis()-startTime));
        }
	}
	
    /**
     * Thread that actually generates sample load
     */
    private static class WorkerThread extends Thread {
        private double kBytes;
        private long duration;

        /**
         * Constructor which creates the thread
         * @param name Name of this thread
         * @param kBytes kbytes of the memory to be consumed
         * @param duration Duration that this thread should sleep in milliseconds
         */
        public WorkerThread(String name, int kBytes, long duration) {
            super(name);
            this.kBytes = kBytes;
            this.duration = duration;
        }

        /**
         * Generates the load when run
         */
        @Override
        public void run() {
            try {            	
                // Consume specified memory
                ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
                for (int i = 0; i < kBytes; i++) {
                	arrayList.add(new byte[1024]);
                 }

                // Sleep for the specified duration
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
