package com.Jayce.test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

public class Client {

	@Test
    public void test() {
        HttpClient httpClient = new HttpClient();
        new Thread(new Sender(httpClient)).start();
    }
	
	public static void main(String[] args) {
		HttpClient httpClient = new HttpClient();
        new Thread(new Sender(httpClient)).start();
	}
}


class Sender implements Runnable {
    public static AtomicInteger count = new AtomicInteger(0);
    HttpClient httpClient;

    public Sender(HttpClient client) {
        httpClient = client;
    }

    public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+"---Send message-"+count.getAndIncrement());
                PostMethod post = new PostMethod("http://127.0.0.1:8080/test/SendMessage");
                post.addParameter("msg", "Hello world!");
                httpClient.executeMethod(post);
                System.out.println(Thread.currentThread().getName()+"---Send message Success-"+count.getAndIncrement());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }