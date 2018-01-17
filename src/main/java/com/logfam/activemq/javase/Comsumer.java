package com.logfam.activemq.javase;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Comsumer {

	// ActiveMq的默认用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	// ActiveMq的默认登录密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	// ActiveMq的链接地址
	private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;

	AtomicInteger count = new AtomicInteger(0);

	ThreadLocal<MessageConsumer> threadLocal = new ThreadLocal<>();
	
	public void init() {
		try {
			//创建一个连接工厂
			connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEN_URL);
			//从工厂中创建一个链接
			connection = connectionFactory.createConnection();
			//开启链接
			connection.start();
			//创建一个事物(这里通过参数可以设置事物的级别)
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			System.err.println(e);
		}
	}
	
	public void getMessage(String disname) {
		try {
			//创建一个消息队列
			Queue queue = session.createQueue(disname);
			//消息生产者
			MessageConsumer messageConsumer = null;
			if (threadLocal.get()!=null) {
				messageConsumer = threadLocal.get();
			}else {
				messageConsumer = session.createConsumer(queue);
				threadLocal.set(messageConsumer);
			}
			while (true) {
				Thread.sleep(10000);
				TextMessage msg = (TextMessage)messageConsumer.receive();
				if (msg!=null) {
					msg.acknowledge();
					System.out.println(Thread.currentThread().getName()+": Consumer:我是tom，我正在领工资"+msg.getText()+"--->"+count.getAndIncrement());
				}else {
					break;
				}
			}
		} catch (JMSException e) {
			// TODO: handle exception
			System.err.println(e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
