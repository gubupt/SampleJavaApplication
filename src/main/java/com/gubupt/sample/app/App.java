package com.gubupt.sample.app;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Sample Java Application
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		start();
	}
	
	private static void start() {
		SampleWorker worker = new SampleWorker();
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate( () -> {
			worker.work();
		}, 1, 19, TimeUnit.SECONDS);
	}
}
