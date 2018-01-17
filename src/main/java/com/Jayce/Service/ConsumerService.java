package com.Jayce.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

	@Resource(name="jmsTemplate")
    private JmsTemplate jmsTemplate;

	public TextMessage receive(Destination destination) {
		jmsTemplate.setDefaultDestinationName("Jaycekon");
		String defaultDestination = jmsTemplate.getDefaultDestinationName();
		TextMessage textMessage = (TextMessage) jmsTemplate.receive(defaultDestination);
		try {
			System.out.println("从队列" + defaultDestination + "收到了消息：\t" + textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return textMessage;
	}
	
	public TextMessage receive() {
		TextMessage textMessage = (TextMessage) jmsTemplate.receive("Jaycekon");
		try {
			System.out.println("从队列" + "Jaycekon" + "收到了消息：\t" + textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return textMessage;
	}

}
