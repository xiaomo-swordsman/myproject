package com.mytest.myservlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	// paypal的client id和secret
	public static String PAYPAL_CLIENT_ID = "AYh1njn9HcwLFy-HOdJ2DyYPQf5VJeM7MT07hyb7dfW0jBcvqsdf512aE8VlESvBvXccIOqKpmg6DtJ9";
	public static String PAYPAL_CLIENT_SECRET = "EHZ3vkVpAH6J8cXEPr5MYmiv8ZUrgck2K2yOIRo5nAYTRBoiFZuLYqtoHzN1z5U3wqFLF2KZ48unQzWg";
	
	public static String PAYPAL_TOKEN_URL = "https://api-m.sandbox.paypal.com/v1/oauth2/token";	//获取token
	public static String PAYPAL_CREATE_ORDER_URL = "https://api-m.sandbox.paypal.com/v2/checkout/orders"; // 创建一次性支付订单url
//	public static String PAYPAL_CREATE_ORDER_REQUEST_ID = "7b92603e-77ed-4896-8e78-5dea2050476a";
//	public static String PAYPAL_CREATE_ORDER_REFERENCE_ID = "d9f80740-38f0-11e8-b467-0ed5f89f718b";
	
	public static String PAYPAL_CREATE_ORDER_REQUEST_ID = "7b92603e-77ed-4896-8e78-5dea2050476c";
	public static String PAYPAL_CREATE_ORDER_REFERENCE_ID = "d9f80740-38f0-11e8-b467-0ed5f89f718d";
	
	public static ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		//goToPayPal();
		String s = null;
		System.out.println(s);
		
		System.out.println(s.trim());
		System.out.println(s);
		s = s.trim();
		System.out.println(s);
		
	}

	/**
	 * 获取
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	private static String goToPayPal() throws ClientProtocolException, IOException {
		// 获取 accessToken 
		String authorization = "Basic " + java.util.Base64.getUrlEncoder().encodeToString((PAYPAL_CLIENT_ID + ":" + PAYPAL_CLIENT_SECRET).getBytes());
		String accessToken = doPost(authorization,PAYPAL_TOKEN_URL,"application/x-www-form-urlencoded",null,"grant_type=client_credentials");
		System.out.println("accessToken == " + accessToken);
		
		// 创建paypal支付订单
		Map<String,Object> payLoad = new HashMap<String,Object>();
		payLoad.put("intent", "CAPTURE");
		
		List<Map<String,Object>> purchaseUnitsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> purchaseUnitsMap = new HashMap<String,Object>();
		purchaseUnitsMap.put("reference_id",UUID.randomUUID().toString());
		Map<String,Object> amountMap = new HashMap<String,Object>();
		amountMap.put("currency_code", "USD");
		amountMap.put("value", "100.00");
		purchaseUnitsMap.put("amount",amountMap);
		purchaseUnitsList.add(purchaseUnitsMap);
		payLoad.put("purchase_units", purchaseUnitsList);
		
		Map<String,Object> paymentSourceMap = new HashMap<String,Object>();
		Map<String,Object> experienceContextMap = new HashMap<String,Object>();
		experienceContextMap.put("payment_method_preference","IMMEDIATE_PAYMENT_REQUIRED");
		experienceContextMap.put("payment_method_selected","PAYPAL");
		experienceContextMap.put("brand_name","EXAMPLE INC");
		experienceContextMap.put("locale","en-US");
		experienceContextMap.put("landing_page","LOGIN");
		experienceContextMap.put("shipping_preference","SET_PROVIDED_ADDRESS");
		experienceContextMap.put("user_action","PAY_NOW");
		experienceContextMap.put("return_url","https://test-ecl.echineselearning.biz/subscribe.do?pdId=2c910aa28620aee6018620b0bb4b0002");
		experienceContextMap.put("cancel_url","https://test-ecl.echineselearning.biz");
		// experienceContextMap.put("approve_url","https://test-ecl.echineselearning.biz/paypalNotify.do?orderId=10047906&pdId=2c910aa28620aee6018620b0bb4b0002");
		Map<String,Object> paypalMap = new HashMap<String,Object>();
		paypalMap.put("experience_context", experienceContextMap);
		paymentSourceMap.put("paypal", paypalMap);
		payLoad.put("payment_source", paymentSourceMap);
		
		String payLoadJson = mapper.writeValueAsString(payLoad);
		System.out.println("payLoadJson == " + payLoadJson);
		
		Object jsonMap = com.alibaba.fastjson.JSON.parse(accessToken);
		Map<String,Object> model= (Map<String,Object>)jsonMap;
		String result =  doPost("Bearer " + model.get("access_token") ,PAYPAL_CREATE_ORDER_URL,"application/json",UUID.randomUUID().toString(),payLoadJson);
		System.out.println("result1 == " + result);
		
		
		
		// 查询支付结果
//		String handleUrl = "https://api-m.sandbox.paypal.com/v2/checkout/orders/4S768330YW024472M/capture"; // 创建一次性支付订单url
//		result =  doPost("Bearer " + model.get("access_token") ,handleUrl,"application/json",PAYPAL_CREATE_ORDER_REQUEST_ID,null);
//		System.out.println("result2 == " + result);
		return result;
	}
	
	/**
	 * @param authorization	传入的token
	 * @param requestUrl	请求的url
	 * @param contentType
	 * @param requestId
	 * @param data
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPost(String authorization,String requestUrl,String contentType,String requestId,String data) throws ClientProtocolException, IOException {
			String body = "";
			// 创建httpclient对象
			CloseableHttpClient client = HttpClients.createDefault();
			// 创建post方式请求对象
			HttpPost httpPost = new HttpPost(requestUrl);
			
			if(null != data) {
				// 装填参数
				StringEntity s = new StringEntity(data, "utf-8");
				// 设置参数到请求对象中
				httpPost.setEntity(s);
			}
			// 设置header信息
			// 指定报文头【Content-type】、【User-Agent】
			httpPost.setHeader("Content-Type", contentType);
			httpPost.setHeader("Authorization", authorization);
			httpPost.setHeader("PayPal-Request-Id",requestId);
			// 执行请求操作，并拿到结果（同步阻塞）
			CloseableHttpResponse response = client.execute(httpPost);
			// 获取结果实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// 按指定编码转换结果实体为String类型
				body = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			// 释放链接
			response.close();
			return body;
    }
	
}
