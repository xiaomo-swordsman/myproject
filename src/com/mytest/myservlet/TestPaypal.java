package com.mytest.myservlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public class TestPaypal {
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		PayPalUtils pay = new PayPalUtils();
		
		// ��ȡpaypal��token
//		String payPalAccessToken = pay.getPayPalAccessToken();
//		System.out.println("payPalAccessToken == " + payPalAccessToken);
		// A21AAKb8fIGtqvvLiLAu9j05GR8Ml1sjhiGVwetqJrKDuXQLHwb7dmHaF71-3NDr6NCMUs506jEWEQcMsbUQ6wo0PLHxovrww
		
		// ����һ����֧������ 
//		Map<String,Object> resultMap =pay.createPayPalOrder(payPalAccessToken);
//		System.out.println("resultMap == " + resultMap);
		//resultMap == {PayPal-Request-Id=67616c5a-bb11-40b4-b29a-4592bf2111ad, payer-action=https://www.sandbox.paypal.com/checkoutnow?token=5FG603673K4230428, id=5FG603673K4230428}

		
		// �տ�-- һ����֧������
		Map<String,Object> resultMap = new HashMap<String,Object>();
		pay.captureOrder(resultMap, "A21AAKb8fIGtqvvLiLAu9j05GR8Ml1sjhiGVwetqJrKDuXQLHwb7dmHaF71-3NDr6NCMUs506jEWEQcMsbUQ6wo0PLHxovrww");
		
		// ����paypal��Ʒ
		//String productId = pay.createPayPalProduct(payPalAccessToken);
		
		// ����paypal���ڸ���ƻ�
		//pay.createPayPalPlan(productId, payPalAccessToken);
	}
	
}
