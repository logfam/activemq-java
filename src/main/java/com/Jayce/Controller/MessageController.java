package com.Jayce.Controller;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.Jayce.Service.ConsumerService;
import com.Jayce.Service.ProducerService;

@Controller
public class MessageController {

	private Logger logger = LoggerFactory.getLogger(MessageController.class);
	@Resource(name = "demoQueueDestination")
	private Destination destination;
	// 队列消息生产者
	@Resource(name = "producerService")
	private ProducerService producer;

	// 队列消息消费者
	@Resource(name = "consumerService")
	private ConsumerService consumer;
	
	

	public MessageController() {
		logger.debug("debug=====================================");
		logger.info("info======================================");
		logger.warn("warn=======================================");
		logger.error("error======================================");
	}

	@RequestMapping(value = "/SendMessage", method = RequestMethod.POST)
	@ResponseBody
	public void send(String msg) {
		logger.info(Thread.currentThread().getName() + "------------send to jms Start");
		producer.sendMessage(msg);
		logger.info(Thread.currentThread().getName() + "------------send to jms End");
	}

	@RequestMapping(value = "/ReceiveMessage", method = RequestMethod.GET)
	@ResponseBody
	public Object receive() {
		logger.info(Thread.currentThread().getName() + "------------receive from jms Start");
		TextMessage tm = consumer.receive();
		logger.info(Thread.currentThread().getName() + "------------receive from jms End");
		return tm;
	}
}
