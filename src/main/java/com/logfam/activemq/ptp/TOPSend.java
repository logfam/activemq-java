package com.logfam.activemq.ptp;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class TOPSend {

	private String userName="";
	private String password="";
	private String brokerURL="tcp://localhost:61616";
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Destination destination;
	private MessageProducer messageProducer;
	
	public static void main(String[] args) {
		TOPSend send = new TOPSend();
		send.start();
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
			messageProducer = session.createProducer(destination);
			//设置生产者的模式，有两种可选
            //DeliveryMode.PERSISTENT 当activemq关闭的时候，队列数据将会被保存
            //DeliveryMode.NON_PERSISTENT 当activemq关闭的时候，队列里面的数据将会被清空
			messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
//			 //纯字符串的数据
//            session.createTextMessage();
//            //序列化的对象
//            session.createObjectMessage();
//            //流，可以用来传递文件等
//            session.createStreamMessage();
//            //用来传递字节
//            session.createBytesMessage();
//            //这个方法创建出来的就是一个map，可以把它当作map来用，当你看了它的一些方法，你就懂了
//            session.createMapMessage();
//            //这个方法，拿到的是javax.jms.Message，是所有message的接口
//            session.createMessage();
//			TextMessage textMsg = session.createTextMessage("哈哈哈");
			ObjectMessage objMsg = session.createObjectMessage();
			long s = System.currentTimeMillis();
			for (int i = 0; i < 100; i++) {
				Person person =new Person(i, "张三");
//				textMsg.setText("哈哈"+i);
				objMsg.setObject(person);
				messageProducer.send(objMsg);
			}
			long e = System.currentTimeMillis();
			System.out.println(e - s);
			System.out.println("消息发送成功");
			messageProducer.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
