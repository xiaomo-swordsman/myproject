package com.mytest.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class Consumer {
	String text = "";
	public static void main(String[] args){
        Consumer consumer = new Consumer();
        try{
            String message = consumer.consumer();
            System.out.println("��Ϣ���ѳɹ���" + message);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
	
	public String consumer() throws JMSException {
        ConnectionFactory factory = null;
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;
        try {
            factory = new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
            connection = factory.createConnection();
            /**
             * �����߱����������ӣ������޷�����
             */
            connection.start();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Destination destination = session.createQueue(Producer.QUEUE_NAME);
            consumer = session.createConsumer(destination);
            /**
             * ��ȡ������Ϣ
             */
            
            // �˴�д��ֻ��һ�����ֶ����òſ��Ի�ȡ��������Ϣ
//          Message message =consumer.receive();
//          text = ((TextMessage) message).getText();
            
            // �˴����Լ���������������Ϣ���˴�������������Ϣ
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {

                    try {
                        //��Ҫ�ֶ�ȷ����Ϣ
                        message.acknowledge();
                        TextMessage om = (TextMessage) message;
                        String data = om.getText();
                        System.out.println(data);
                        text  = om.getText();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            
            return text;
        } catch(Exception ex){
            throw ex;
        } finally {
            /**
             * 7.�ͷ���Դ
             */
//            if(consumer != null){
//                consumer.close();
//            }
//            
//            if(session != null){
//                session.close();
//            }
//
//            if(connection != null){
//                connection.close();
//            }
        }
    }
}
