package com.mytest.myservlet;

import java.io.IOException;
import java.time.LocalDateTime;
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

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.http.HttpRequest;

public class TestProduct {

	// paypal的client id和secret
//	public static String PAYPAL_CLIENT_ID = "AYh1njn9HcwLFy-HOdJ2DyYPQf5VJeM7MT07hyb7dfW0jBcvqsdf512aE8VlESvBvXccIOqKpmg6DtJ9";
//	public static String PAYPAL_CLIENT_SECRET = "EHZ3vkVpAH6J8cXEPr5MYmiv8ZUrgck2K2yOIRo5nAYTRBoiFZuLYqtoHzN1z5U3wqFLF2KZ48unQzWg";
	
	public static String PAYPAL_CLIENT_ID = "ARkhQNYfWz19YZAnpTdDZXtpffhUYuLG3aKu-gLVt8DlZr3LPKZy_ec1wWIldM522RAgQca-DvNwB1CY";
	public static String PAYPAL_CLIENT_SECRET = "EOZAhe5NrWO-qs57V5e31Fjm9GApyphw0Xfz6a3dO80subVZa2lKA_ITelJYEQ8RVs8IYWYBRsaKR4ga";
	
	/**
	 * 	/v1/oauth2/token		获取token
	 * 	/v2/checkout/orders		创建一次性支付订单url
	 * /v1/catalogs/products 	创建产品的url
	 * /v1/billing/plans 		创建付款计划
	 */
	public static String PAYPAL_BASE_URL = "https://api-m.sandbox.paypal.com";	//获取token
	
	public static ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		String accessToken = getToken();
		String productId = goToPayPal(accessToken);
		createPlan(productId,accessToken);
	}

	/**
	 * 创建产品
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	private static String goToPayPal(String accessToken) throws ClientProtocolException, IOException {
		String result = "result";
		// 创建产品
		Map<String,String> map = new HashMap<String,String>();
        map.put("Content-Type","application/json");
        map.put("Authorization","Bearer " + accessToken);
		map.put("PayPal-Request-Id","PRODUCT-18062020-001");
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("name","test product 1");
		data.put("description","application/json");
		data.put("type","SERVICE");
		data.put("category","SOFTWARE");
		data.put("image_url","https://example.com/streaming.jpg");
		data.put("home_url","https://example.com/home");
		result = cn.hutool.http.HttpRequest.post(PAYPAL_BASE_URL + "/v1/catalogs/products").addHeaders(map).body(mapper.writeValueAsString(data)).execute().body();
		System.out.println("创建产品 result == " + result);
		
		JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.get("id").toString();
	}
	
	private static String getToken() throws ClientProtocolException, IOException {
		Map<String,String> authMap = new HashMap<>();
		authMap.put("Content-Type","application/json");
		String body = HttpRequest.post(PAYPAL_BASE_URL + "/v1/oauth2/token").addHeaders(authMap).basicAuth(PAYPAL_CLIENT_ID,PAYPAL_CLIENT_SECRET).body("grant_type=client_credentials").execute().body();
		System.out.println("body == " + body);
		JSONObject jsonObject = JSONObject.parseObject(body);
        return jsonObject.get("access_token").toString();
	}
	
	/**
	 * 创建付款计划
	 * @return	
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static String createPlan(String productId,String accessToken) throws ClientProtocolException, IOException {
		String result = "result";
		String dateTime = LocalDateTime.now().toString();
		// 创建产品
		Map<String,String> map = new HashMap<String,String>();
        map.put("Content-Type","application/json");
        map.put("Authorization","Bearer " + accessToken);
		map.put("PayPal-Request-Id","PLAN-18062020-001");
		map.put("Accept","application/json");
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("product_id", productId); // 产品id
		data.put("name","Basic Plan");// 计划名称
		data.put("description","Basic plan"); // 计划描述
		data.put("status", "ACTIVE"); //计划状态 CREATED. 计划已创建。您无法为处于这种状态的计划创建订阅。INACTIVE. 该计划处于非活动状态。ACTIVE. 该计划处于活动状态。您只能为处于这种状态的计划创建订阅
		
		List<Map<String,Object>> billingList = new ArrayList<Map<String,Object>>();
//		Map<String,Object> billingMap = new HashMap<String,Object>();
//		Map<String,Object> frequencyMap = new HashMap<String,Object>();
//		frequencyMap.put("interval_unit","MONTH");
//		frequencyMap.put("interval_count",1);
//		billingMap.put("frequency", frequencyMap);
//		billingMap.put("tenure_type","TRIAL");
//		billingMap.put("sequence",1);
//		billingMap.put("total_cycles",1);
//		billingList.add(billingMap);
		
		Map<String,Object> billingMap2 = new HashMap<String,Object>();
		
		// 计费周期
		Map<String,Object> frequencyMap2 = new HashMap<String,Object>();
		frequencyMap2.put("interval_unit","MONTH");// 订阅收费或计费的时间间隔。 DAY(365). 每日计费周期。WEEK(52). 每周计费周期。MONTH(12). 每月计费周期。YEAR(1). 年度计费周期。
		frequencyMap2.put("interval_count",1);// 向订户计费的时间间隔数。例如，如果interval_unit带有DAY，interval_count=2，那么订阅每两天计费一次。下表列出了 for interval_counteach 的最大允许值interval_unit
		billingMap2.put("frequency", frequencyMap2);
		billingMap2.put("tenure_type","REGULAR");// 计费周期的使用期限类型。如果计划有试用周期，则每个计划只允许有 2 个试用周期。 REGULAR. 定期计费周期。TRIAL. 试用计费周期
		billingMap2.put("sequence",1);// 此周期在其他计费周期中的运行顺序。例如，试用计费周期为1,常规计费周期为2，因此试用周期在常规周期之前运行
		billingMap2.put("total_cycles",12);// 此计费周期执行的次数。试用计费周期只能执行有限次数（介于和 之间1的值）。定期计费周期可以执行无限次（for的值）或有限次数（和for之间的值）
		Map<String,Object> pricingSchemeMap = new HashMap<String,Object>();
		
		//此计费周期的有效定价方案。免费试用计费周期不需要定价方案。
		Map<String,Object> fixedPriceMap = new HashMap<String,Object>();
		fixedPriceMap.put("value","10");//值，可能是： 此类货币的整数JPY通常不是小数。像这样的货币的小数部分TND被细分为千分之一。
		fixedPriceMap.put("currency_code","USD"); // 标识货币 https://developer.paypal.com/reference/currency-codes/
		pricingSchemeMap.put("fixed_price", fixedPriceMap); // 订阅费用的固定金额。固定金额的变化适用于现有和未来的订阅。对于现有订阅，价格变动后 10 天内的付款不受影响。
		pricingSchemeMap.put("create_time", dateTime);
		billingMap2.put("pricing_scheme", pricingSchemeMap);
		billingList.add(billingMap2);
		data.put("billing_cycles", billingList);
		
		// 订阅的付款偏好设置
		Map<String,Object> paymentPreferencesMap = new HashMap<String,Object>();
		Map<String,Object> setupFeeMap = new HashMap<String,Object>();
		setupFeeMap.put("value","10");
		setupFeeMap.put("currency_code","USD");
		paymentPreferencesMap.put("setup_fee", setupFeeMap); // 服务的初始设置费
		paymentPreferencesMap.put("auto_bill_outstanding",true);// 是否在下一个计费周期自动计费未结金额
		paymentPreferencesMap.put("setup_fee_failure_action","CONTINUE");// 如果设置的初始付款失败，对订阅采取的操作 CONTINUE. 如果设置的初始付款失败，则继续订阅。 CANCEL. 如果设置的初始付款失败，则取消订阅。
		paymentPreferencesMap.put("payment_failure_threshold",3);// 暂停订阅前支付失败的最大次数。例如，如果payment_failure_threshold是2，如果连续两次支付失败，2订阅会自动更新为状态。SUSPEND
		data.put("payment_preferences", paymentPreferencesMap);
		
		// 税务详情
		Map<String,Object> taxesMap = new HashMap<String,Object>();
		taxesMap.put("percentage","10"); // 账单金额的税收百分比
		taxesMap.put("inclusive",false); // 指示税款是否已包含在账单金额中
		data.put("taxes", taxesMap);
		
		data.put("create_time", dateTime);
		data.put("update_time", dateTime);
		
		System.out.println("创建付款计划 data == " + mapper.writeValueAsString(data));
		
		result = cn.hutool.http.HttpRequest.post(PAYPAL_BASE_URL + "/v1/billing/plans").addHeaders(map).body(mapper.writeValueAsString(data)).execute().body();
		System.out.println("创建付款计划 result == " + result);
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
