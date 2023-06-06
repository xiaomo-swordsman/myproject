package com.mytest.myservlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.http.HttpRequest;

public class PayPalUtils {

	public ObjectMapper mapper = new ObjectMapper();
	
	// 获取PayPal token
	public String getPayPalAccessToken() throws ClientProtocolException, IOException {
		String paypalAccessToken = "";
		Map<String,String> authMap = new HashMap<>();
		authMap.put("Content-Type","application/json");
		String result = HttpRequest.post(PaypalPayment.SANDBOX_PAYPAL_URL + "/v1/oauth2/token")
				.addHeaders(authMap)
				.basicAuth(PaypalPayment.SANDBOX_PAYPAL_CLIENT_ID,PaypalPayment.SANDBOX_PAYPAL_CLIENT_SECRET)
				.body("grant_type=client_credentials")
				.execute().body();
		// logger.info("接收调用paypal的获取token接口数据 == " + result);
		System.out.println("接收调用paypal的获取token接口数据 == " + result);
		if(StringHelp.isNotBlank(result)) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				paypalAccessToken = jsonObject.get("access_token").toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// logger.info("接收调用paypal的获取token接口数据异常");
				System.out.println("接收调用paypal的获取token接口数据异常");
				e.printStackTrace();
			}
		}
		return paypalAccessToken;
	}
	
	// 创建PayPay一次性支付的订单
	public Map<String,Object> createPayPalOrder(String paypalAccessToken) throws ClientProtocolException, IOException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		// 封装 头部数据信息
		Map<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type","application/json");
		headerMap.put("Authorization","Bearer " + paypalAccessToken);
		headerMap.put("PayPal-Request-Id",UUID.randomUUID().toString());
		headerMap.put("Accept","application/json");
		headerMap.put("Prefer","return=representation");
		System.out.println("headerMap == " + mapper.writeValueAsString(headerMap));
		
		resultMap.put("PayPal-Request-Id", headerMap.get("PayPal-Request-Id"));
		
		// 封装订单数据信息
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("intent", "CAPTURE");		
		List<Map<String,Object>> purchaseUnitsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> purchaseUnitsMap = new HashMap<String,Object>();
		purchaseUnitsMap.put("reference_id",UUID.randomUUID().toString());
		Map<String,Object> amountMap = new HashMap<String,Object>();
		amountMap.put("currency_code", "USD");
		amountMap.put("value", "28.00");
		purchaseUnitsMap.put("amount",amountMap);
		purchaseUnitsList.add(purchaseUnitsMap);
		data.put("purchase_units", purchaseUnitsList);
		
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
		data.put("payment_source", paymentSourceMap);
		
		System.out.println("调用创建PayPay一次性支付的参数封装 == " + mapper.writeValueAsString(data));
		String result = HttpRequest.post(PaypalPayment.SANDBOX_PAYPAL_URL + "/v2/checkout/orders")
				.addHeaders(headerMap)
				.body(mapper.writeValueAsString(data))
				.execute().body();
		// logger.info("接收调用创建PayPay一次性支付的订单数据 == " + result);
		System.out.println("接收调用创建PayPay一次性支付的订单数据 == " + result);
		
		if(StringHelp.isNotBlank(result)) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				resultMap.put("id", jsonObject.get("id"));
				List<Map<String,Object>> paypalOrderLinks =(List<Map<String,Object>>)jsonObject.get("links");
				for (Map<String, Object> map : paypalOrderLinks) {
					if("payer-action".equals(map.get("rel").toString())){
						resultMap.put("payer-action", map.get("href").toString());
						
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// logger.info("接收调用paypal一次性支付的订单数据异常");
				System.out.println("接收调用paypal一次性支付的订单数据异常");
				e.printStackTrace();
			}
		}
		
		return resultMap;
	}
		
	// 捕获订单付款 /v2/checkout/orders/5O190127TN364715T/capture
	public void captureOrder(Map<String,Object> resultMap,String paypalAccessToken) {
		// 封装 头部数据信息
		Map<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type","application/json");
		headerMap.put("Authorization","Bearer " + paypalAccessToken);
		// headerMap.put("PayPal-Request-Id",resultMap.get("PayPal-Request-Id").toString());
		headerMap.put("PayPal-Request-Id","67616c5a-bb11-40b4-b29a-4592bf2111ad");
		
//		String result = HttpRequest.post(PaypalPayment.SANDBOX_PAYPAL_URL + "/v2/checkout/orders/" + resultMap.get("id") + "/capture")
//				.addHeaders(headerMap)
//				.execute().body();
		
		String result = HttpRequest.post(PaypalPayment.SANDBOX_PAYPAL_URL + "/v2/checkout/orders/5FG603673K4230428/capture")
				.addHeaders(headerMap)
				.execute().body();
		System.out.println("result == " + result);
	}
	
	/**
	 * 创建paypal分期付款产品
	 * @return	产品id
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public String createPayPalProduct(String paypalAccessToken) throws ClientProtocolException, IOException {
		String productId = "";
		// 创建产品
		Map<String,String> authMap = new HashMap<String,String>();
		authMap.put("Content-Type","application/json");
		authMap.put("Authorization","Bearer " + paypalAccessToken);
		authMap.put("PayPal-Request-Id","PRODUCT-18062020-001");
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("name","test product 1");
		data.put("description","application/json");
		data.put("type","SERVICE");
		data.put("category","SOFTWARE");
		data.put("image_url","https://example.com/streaming.jpg");
		data.put("home_url","https://example.com/home");
		String result = HttpRequest.post(PaypalPayment.SANDBOX_PAYPAL_URL + "/v1/catalogs/products")
				.addHeaders(authMap)
				.body(mapper.writeValueAsString(data))
				.execute().body();
		// logger.info("接收调用创建paypal产品数据 == " + result);
		System.out.println("接收调用创建paypal产品数据 == " + result);
		if(StringHelp.isNotBlank(result)) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				productId = jsonObject.get("id").toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// logger.info("接收调用创建paypal产品数据异常");
				System.out.println("接收调用创建paypal产品数据异常");
				e.printStackTrace();
			}
		}
        return productId;
	}
	
	/**
	 * 创建分期付款计划
	 * @return	
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String createPayPalPlan(String productId,String paypalAccessToken) throws ClientProtocolException, IOException {
		String dateTime = LocalDateTime.now().toString();
		// 创建产品
		Map<String,String> authMap = new HashMap<String,String>();
		authMap.put("Content-Type","application/json");
		authMap.put("Authorization","Bearer " + paypalAccessToken);
		authMap.put("PayPal-Request-Id","PLAN-18062020-001");
		authMap.put("Accept","application/json");
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("product_id", productId); // 产品id
		data.put("name","Basic Plan");// 计划名称
		data.put("description","Basic plan"); // 计划描述
		data.put("status", "ACTIVE"); //计划状态 CREATED. 计划已创建。您无法为处于这种状态的计划创建订阅。INACTIVE. 该计划处于非活动状态。ACTIVE. 该计划处于活动状态。您只能为处于这种状态的计划创建订阅
		
		List<Map<String,Object>> billingList = new ArrayList<Map<String,Object>>();
//			Map<String,Object> billingMap = new HashMap<String,Object>();
//			Map<String,Object> frequencyMap = new HashMap<String,Object>();
//			frequencyMap.put("interval_unit","MONTH");
//			frequencyMap.put("interval_count",1);
//			billingMap.put("frequency", frequencyMap);
//			billingMap.put("tenure_type","TRIAL");
//			billingMap.put("sequence",1);
//			billingMap.put("total_cycles",1);
//			billingList.add(billingMap);
		
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
		
		String result = HttpRequest.post(PaypalPayment.SANDBOX_PAYPAL_URL + "/v1/billing/plans")
				.addHeaders(authMap)
				.body(mapper.writeValueAsString(data))
				.execute().body();
		// logger.info("接收调用创建PayPal付款计划数据 == " + result);
		System.out.println("接收调用创建PayPal付款计划数据 == " + result);
//			if(StringHelp.isNotBlank(result)) {
//				try {
//					JSONObject jsonObject = JSONObject.parseObject(result);
//					productId = jsonObject.get("id").toString();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					logger.info("接收调用创建PayPal付款计划数据异常");
//					System.out.println("接收调用创建PayPal付款计划数据异常");
//					e.printStackTrace();
//				}
//			}
		return result;
	}


}
