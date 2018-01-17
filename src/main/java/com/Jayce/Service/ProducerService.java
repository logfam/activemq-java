package com.Jayce.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

	@Resource(name="jmsTemplate")
    private JmsTemplate jmsTemplate;
	
	public void sendMessage(Destination destination,final String msg) {
		jmsTemplate.setDefaultDestinationName("Jaycekon");
		System.out.println(Thread.currentThread().getName()+"向队列"+destination.toString()+"发消息----------->"+msg);
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}
	
	public void sendMessage(final String msg) {
		jmsTemplate.setDefaultDestinationName("Jaycekon");
		String destination = jmsTemplate.getDefaultDestinationName();
		System.out.println(Thread.currentThread().getName()+"向队列"+destination.toString()+"发消息----------->"+msg);
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}
}
