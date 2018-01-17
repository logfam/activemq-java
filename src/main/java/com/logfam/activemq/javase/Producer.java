package com.logfam.activemq.javase;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

	//ActiveMq的默认用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	//ActiveMq的默认登录密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	//ActiveMq的链接地址
	private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	AtomicInteger count = new AtomicInteger(0);
	
	ThreadLocal<MessageProducer> threadLocal = new ThreadLocal<>();
	
	public void init() {
		try {
			//创建一个连接工厂
			connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEN_URL);
			//从工厂中创建一个链接
			connection = connectionFactory.createConnection();
			//开启链接
			connection.start();
			//创建一个事物(paramA设置为true时：paramB的值忽略， acknowledgment mode被jms服务器设置为SESSION_TRANSACTED )
			session = connection.createSession(true, Session.SESSION_TRANSACTED);
		} catch (JMSException e) {
			System.err.println(e);
		}
	}
	
	public void sendMessage(String disname) {
		try {
			//创建一个消息队列
			Queue queue = session.createQueue(disname);
			//消息生产者
			MessageProducer messageProducer = null;
			if (threadLocal.get()!=null) {
				messageProducer = threadLocal.get();
			}else {
				messageProducer = session.createProducer(queue);
				threadLocal.set(messageProducer);
			}
			while (true) {
				Thread.sleep(10000);
				int num = count.getAndIncrement();
				//创建一条消息
				TextMessage msg = session.createTextMessage(Thread.currentThread().getName()+"producer:我是jerry，准备发工资！,count:"+num);
				System.out.println(Thread.currentThread().getName()+"producer:我是jerry，准备发工资！,count:"+num);
				//发送消息
				messageProducer.send(msg);
				//提交事物
				session.commit();
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
