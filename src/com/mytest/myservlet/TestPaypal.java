package com.mytest.myservlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public class TestPaypal {
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		PayPalUtils pay = new PayPalUtils();
		
		// 获取paypal的token
//		String payPalAccessToken = pay.getPayPalAccessToken();
//		System.out.println("payPalAccessToken == " + payPalAccessToken);
		// A21AAKb8fIGtqvvLiLAu9j05GR8Ml1sjhiGVwetqJrKDuXQLHwb7dmHaF71-3NDr6NCMUs506jEWEQcMsbUQ6wo0PLHxovrww
		
		// 创建一次性支付订单 
//		Map<String,Object> resultMap =pay.createPayPalOrder(payPalAccessToken);
//		System.out.println("resultMap == " + resultMap);
		//resultMap == {PayPal-Request-Id=67616c5a-bb11-40b4-b29a-4592bf2111ad, payer-action=https://www.sandbox.paypal.com/checkoutnow?token=5FG603673K4230428, id=5FG603673K4230428}

		
		// 收款-- 一次性支付订单
		Map<String,Object> resultMap = new HashMap<String,Object>();
		pay.captureOrder(resultMap, "A21AAKb8fIGtqvvLiLAu9j05GR8Ml1sjhiGVwetqJrKDuXQLHwb7dmHaF71-3NDr6NCMUs506jEWEQcMsbUQ6wo0PLHxovrww");
		
		// 创建paypal产品
		//String productId = pay.createPayPalProduct(payPalAccessToken);
		
		// 创建paypal分期付款计划
		//pay.createPayPalPlan(productId, payPalAccessToken);
	}
	
}
