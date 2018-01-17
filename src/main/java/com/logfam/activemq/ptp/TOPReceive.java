package com.logfam.activemq.ptp;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class TOPReceive {

	private String userName="";
	private String password="";
	private String brokerURL="tcp://localhost:61616";
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Destination destination;
	private MessageConsumer messageConsumer;
	
	public static void main(String[] args) {
		TOPReceive receive = new TOPReceive();
		receive.start();
	}
	
	public void start() {
		try {
			connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerURL);
//			connectionFactory.setTrustAllPackages(true);
			connection = connectionFactory.createConnection();
			connection.start();
			/**
			 * 第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
            	第二个参数为false时，paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
            	Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
            	Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
            	DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
			 */
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			destination = session.createTopic("TOP-text");
			messageConsumer = session.createConsumer(destination);
			messageConsumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					try {
						ObjectMessage msg = (ObjectMessage)message;
						Person person = (Person)msg.getObject();
//						msg.acknowledge();
						System.out.println(person);
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
//			messageConsumer.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
