package com.logfam.activemq.javase;


public class TestComsumer {
	public static void main(String[] args) {
		Comsumer comsumer = new Comsumer();
		comsumer.init();
		TestComsumer testComsumer = new TestComsumer();
		// Thread 1
		new Thread(testComsumer.new ComsumerMq(comsumer)).start();
		// Thread 2
		new Thread(testComsumer.new ComsumerMq(comsumer)).start();
		// Thread 3
		new Thread(testComsumer.new ComsumerMq(comsumer)).start();
		// Thread 4
		new Thread(testComsumer.new ComsumerMq(comsumer)).start();
	}

	private class ComsumerMq implements Runnable {

		Comsumer comsumer;

		public ComsumerMq(Comsumer comsumer) {
			this.comsumer = comsumer;
		}

		@Override
		public void run() {
			while (true) {
				try {
					comsumer.getMessage("wo-kao");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println(e);
				}
			}
		}

	}
}
