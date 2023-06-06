package com.mytest.activemq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

public class Producer {
	public static final  String QUEUE_NAME = "ptp-demo";//������
	
	public static void main(String[] args) throws ParseException {
		String dateStr = "2023-03-08T08:09:42Z";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = sdf.parse(dateStr);	
		System.out.println(date);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf2.format(date));
        System.out.println(sdf2.parse(sdf2.format(date)));
//		Producer producer = new Producer();
//        try{
//            producer.producer("hello, activemq");
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
	}
	
	public void producer(String message) throws JMSException {
		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			/**
			 * 1.�������ӹ��� �������������췽���������������ֱ����û��������롢���ӵ�ַ �޲ι��죺��Ĭ�ϵ����ӵ�ַ��localhost
			 * һ������������֤ģʽ�����û�����֤ ��������������֤�����ӵ�ַ��������ʹ�����������Ĺ��췽��
			 */
			factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
			/**
			 * 2.�������ӣ�������������������ʹ���޲����ģ� �޲��� �в������û��������룻
			 */
			connection = factory.createConnection();
			/**
			 * 3.�������� �����߿��Բ��õ���start()������������Ϊ�ڷ�����Ϣ��ʱ��ؽ��м�� ���δ�������ӣ����Զ�������
			 * ������������ã���Ҫ������ɺ�����������
			 */
			connection.start();
			/**
			 * 4.�����Ӵ����Ự �������������Ƿ���Ҫ������Ϣȷ�ϻ���
			 * ���֧�����񣬶�����������˵�ڶ�����������Ч�ˣ����ʱ��ڶ����������鴫��Session.SESSION_TRANSACTED
			 * �����֧�����񣬵ڶ���������Ч�ұ��봫��
			 *
			 * AUTO_ACKNOWLEDGE���Զ�ȷ�ϣ���Ϣ������Զ�ȷ�ϣ���ҵ�������Ƽ���
			 * CLIENT_ACKNOWLEDGE���ͻ����ֶ�ȷ�ϣ������ߴ��������ֶ�ȷ��
			 * DUPS_OK_ACKNOWLEDGE���и����Ŀͻ����ֶ�ȷ�ϣ���Ϣ���Զ�δ��������飩
			 */
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			/**
			 * 5.�ûỰ����Ŀ�ĵأ����У��������ߡ���Ϣ �������Ƕ��е�Ψһ��� ���������ߵ�ʱ�����ָ��Ŀ�ĵأ�Ҳ�����ڷ�����Ϣ��ʱ����ָ��
			 */
			Destination destination = session.createQueue(QUEUE_NAME);
			producer = session.createProducer(destination);
			TextMessage textMessage = session.createTextMessage(message);
			
			// �����Ҫ����Ƶ����������Ϣ,��Ҫ�޸������ļ�activemq.xml,��broker��ǩ��������� schedulerSupport="true",����:
			//<broker xmlns="http://activemq.apache.org/schema/core" schedulerSupport="true" brokerName="localhost" dataDirectory="${activemq.data}">
			textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,5 * 1000L);// ��ʱ
			textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 5 * 1000L);// Ͷ�ݼ��
			textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 5);// �ظ�����
//			textMessage.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, "0 * * * *");
			
			/**
			 * 6.�����߷�����Ϣ��Ŀ�ĵ�
			 */
			producer.send(textMessage);
			System.out.println("��Ϣ���ͳɹ�...");
		} catch (Exception ex) {
			throw ex;
		} finally {
			/**
			 * 7.�ͷ���Դ
			 */
//			if (producer != null) {
//				producer.close();
//			}
//
//			if (session != null) {
//				session.close();
//			}
//
//			if (connection != null) {
//				connection.close();
//			}
		}
	}
}
