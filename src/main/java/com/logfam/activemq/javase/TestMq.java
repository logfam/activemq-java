package com.logfam.activemq.javase;

public class TestMq {

	public static void main(String[] args) {
		Producer producer = new Producer();
		producer.init();
		TestMq testMq = new TestMq();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Thread 1
		new Thread(testMq.new ProducerMq(producer)).start();
		// Thread 2
		new Thread(testMq.new ProducerMq(producer)).start();
		// Thread 3
		new Thread(testMq.new ProducerMq(producer)).start();
		// Thread 4
		new Thread(testMq.new ProducerMq(producer)).start();
		// Thread 5
		new Thread(testMq.new ProducerMq(producer)).start();
	}

	private class ProducerMq implements Runnable {

		Producer producer;

		public ProducerMq(Producer producer) {
			this.producer = producer;
		}

		@Override
		public void run() {
			while (true) {
				try {
					producer.sendMessage("wo-kao");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println(e);
				}
			}
		}

	}

}
