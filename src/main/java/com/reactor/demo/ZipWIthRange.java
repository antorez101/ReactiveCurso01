package com.reactor.demo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;


public class ZipWIthRange {
	
	private static final Logger log = LoggerFactory.getLogger(ZipWIthRange.class);
	
	public static void main(String[] args) throws InterruptedException {
		
		//zipWithRange();
		//callMethods();
		//ejampleInterval();
		//exampleIntervalThread();
		exampleIntervalLatCountDown();
	}
	
	public static void exampleIntervalLatCountDown() throws InterruptedException {
		
		CountDownLatch latch = new CountDownLatch(1);
		
		Flux.interval(Duration.ofSeconds(1))
			.doOnTerminate(latch::countDown)
			.flatMap(i -> {
				if (i >= 5) {
					return Flux.error(new InterruptedException());
				}
				return Flux.just(i);
			})
			.map(i -> "Hola "+i)
			.retry(2)
			.subscribe(i -> log.info(i.toString()), error -> log.error(error.getMessage()));
		
		latch.await();
	}
	
	public static void exampleIntervalThread() throws InterruptedException {
		
		Flux.range(1, 12)
			.delayElements(Duration.ofSeconds(1))
			.doOnNext(interval -> log.info(interval.toString()))
			.subscribe();
		
		Thread.sleep(130000);
	}
	
	public static void ejampleInterval() {
		
		Flux<Integer> range = Flux.range(1, 12);
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
		
		range.zipWith(interval, (ra, interv) -> ra)
		.doOnNext(r -> log.info(r.toString()))
		.blockLast();
	}
	
	public static void zipWithRange() {
		
		Flux<Integer> range = Flux.range(0, 4);
		Flux<Integer> numbers = Flux.just(1,2,3,4);
		
		numbers
				.map(nu -> nu * 2)
				.zipWith(range, (ra, num) -> String.format("Range flux %d, Numbers flux %d", ra, num))
				.subscribe(flux -> log.info(flux));
		
	}
	
	public static void callMethods() {
		
		List<String> valores = new ArrayList<String>();
		
		arrayTest(valores);
		arrayTestTwo(valores);
		arrayTestThree(valores);
		System.out.println(valores);
		
		arrayTest(new ArrayList<String>());
		arrayTestTwo(new ArrayList<String>());
		arrayTestThree(new ArrayList<String>());
	}
	
	
	public static List<String> arrayTest(List<String> valores) {
		valores.add("valor one");
		return valores;
		
	}
	
	public static List<String> arrayTestTwo(List<String> valores) {
		valores.add("valor two");
		return valores;
		
	}
	
	public static List<String> arrayTestThree(List<String> valores) {
		valores.add("valor Three");
		return valores;
		
	}

}
