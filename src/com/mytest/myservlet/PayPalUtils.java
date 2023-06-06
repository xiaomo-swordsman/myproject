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
	
	// ��ȡPayPal token
	public String getPayPalAccessToken() throws ClientProtocolException, IOException {
		String paypalAccessToken = "";
		Map<String,String> authMap = new HashMap<>();
		authMap.put("Content-Type","application/json");
		String result = HttpRequest.post(PaypalPayment.SANDBOX_PAYPAL_URL + "/v1/oauth2/token")
				.addHeaders(authMap)
				.basicAuth(PaypalPayment.SANDBOX_PAYPAL_CLIENT_ID,PaypalPayment.SANDBOX_PAYPAL_CLIENT_SECRET)
				.body("grant_type=client_credentials")
				.execute().body();
		// logger.info("���յ���paypal�Ļ�ȡtoken�ӿ����� == " + result);
		System.out.println("���յ���paypal�Ļ�ȡtoken�ӿ����� == " + result);
		if(StringHelp.isNotBlank(result)) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				paypalAccessToken = jsonObject.get("access_token").toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// logger.info("���յ���paypal�Ļ�ȡtoken�ӿ������쳣");
				System.out.println("���յ���paypal�Ļ�ȡtoken�ӿ������쳣");
				e.printStackTrace();
			}
		}
		return paypalAccessToken;
	}
	
	// ����PayPayһ����֧���Ķ���
	public Map<String,Object> createPayPalOrder(String paypalAccessToken) throws ClientProtocolException, IOException {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		// ��װ ͷ��������Ϣ
		Map<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type","application/json");
		headerMap.put("Authorization","Bearer " + paypalAccessToken);
		headerMap.put("PayPal-Request-Id",UUID.randomUUID().toString());
		headerMap.put("Accept","application/json");
		headerMap.put("Prefer","return=representation");
		System.out.println("headerMap == " + mapper.writeValueAsString(headerMap));
		
		resultMap.put("PayPal-Request-Id", headerMap.get("PayPal-Request-Id"));
		
		// ��װ����������Ϣ
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
		
		System.out.println("���ô���PayPayһ����֧���Ĳ�����װ == " + mapper.writeValueAsString(data));
		String result = HttpRequest.post(PaypalPayment.SANDBOX_PAYPAL_URL + "/v2/checkout/orders")
				.addHeaders(headerMap)
				.body(mapper.writeValueAsString(data))
				.execute().body();
		// logger.info("���յ��ô���PayPayһ����֧���Ķ������� == " + result);
		System.out.println("���յ��ô���PayPayһ����֧���Ķ������� == " + result);
		
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
				// logger.info("���յ���paypalһ����֧���Ķ��������쳣");
				System.out.println("���յ���paypalһ����֧���Ķ��������쳣");
				e.printStackTrace();
			}
		}
		
		return resultMap;
	}
		
	// ���񶩵����� /v2/checkout/orders/5O190127TN364715T/capture
	public void captureOrder(Map<String,Object> resultMap,String paypalAccessToken) {
		// ��װ ͷ��������Ϣ
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
	 * ����paypal���ڸ����Ʒ
	 * @return	��Ʒid
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public String createPayPalProduct(String paypalAccessToken) throws ClientProtocolException, IOException {
		String productId = "";
		// ������Ʒ
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
		// logger.info("���յ��ô���paypal��Ʒ���� == " + result);
		System.out.println("���յ��ô���paypal��Ʒ���� == " + result);
		if(StringHelp.isNotBlank(result)) {
			try {
				JSONObject jsonObject = JSONObject.parseObject(result);
				productId = jsonObject.get("id").toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// logger.info("���յ��ô���paypal��Ʒ�����쳣");
				System.out.println("���յ��ô���paypal��Ʒ�����쳣");
				e.printStackTrace();
			}
		}
        return productId;
	}
	
	/**
	 * �������ڸ���ƻ�
	 * @return	
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String createPayPalPlan(String productId,String paypalAccessToken) throws ClientProtocolException, IOException {
		String dateTime = LocalDateTime.now().toString();
		// ������Ʒ
		Map<String,String> authMap = new HashMap<String,String>();
		authMap.put("Content-Type","application/json");
		authMap.put("Authorization","Bearer " + paypalAccessToken);
		authMap.put("PayPal-Request-Id","PLAN-18062020-001");
		authMap.put("Accept","application/json");
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("product_id", productId); // ��Ʒid
		data.put("name","Basic Plan");// �ƻ�����
		data.put("description","Basic plan"); // �ƻ�����
		data.put("status", "ACTIVE"); //�ƻ�״̬ CREATED. �ƻ��Ѵ��������޷�Ϊ��������״̬�ļƻ��������ġ�INACTIVE. �üƻ����ڷǻ״̬��ACTIVE. �üƻ����ڻ״̬����ֻ��Ϊ��������״̬�ļƻ���������
		
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
		
		// �Ʒ�����
		Map<String,Object> frequencyMap2 = new HashMap<String,Object>();
		frequencyMap2.put("interval_unit","MONTH");// �����շѻ�Ʒѵ�ʱ������ DAY(365). ÿ�ռƷ����ڡ�WEEK(52). ÿ�ܼƷ����ڡ�MONTH(12). ÿ�¼Ʒ����ڡ�YEAR(1). ��ȼƷ����ڡ�
		frequencyMap2.put("interval_count",1);// �򶩻��Ʒѵ�ʱ�����������磬���interval_unit����DAY��interval_count=2����ô����ÿ����Ʒ�һ�Ρ��±��г��� for interval_counteach ���������ֵinterval_unit
		billingMap2.put("frequency", frequencyMap2);
		billingMap2.put("tenure_type","REGULAR");// �Ʒ����ڵ�ʹ���������͡�����ƻ����������ڣ���ÿ���ƻ�ֻ������ 2 ���������ڡ� REGULAR. ���ڼƷ����ڡ�TRIAL. ���üƷ�����
		billingMap2.put("sequence",1);// �������������Ʒ������е�����˳�����磬���üƷ�����Ϊ1,����Ʒ�����Ϊ2��������������ڳ�������֮ǰ����
		billingMap2.put("total_cycles",12);// �˼Ʒ�����ִ�еĴ��������üƷ�����ֻ��ִ�����޴��������ں� ֮��1��ֵ�������ڼƷ����ڿ���ִ�����޴Σ�for��ֵ�������޴�������for֮���ֵ��
		Map<String,Object> pricingSchemeMap = new HashMap<String,Object>();
		
		//�˼Ʒ����ڵ���Ч���۷�����������üƷ����ڲ���Ҫ���۷�����
		Map<String,Object> fixedPriceMap = new HashMap<String,Object>();
		fixedPriceMap.put("value","10");//ֵ�������ǣ� ������ҵ�����JPYͨ������С�����������Ļ��ҵ�С������TND��ϸ��Ϊǧ��֮һ��
		fixedPriceMap.put("currency_code","USD"); // ��ʶ���� https://developer.paypal.com/reference/currency-codes/
		pricingSchemeMap.put("fixed_price", fixedPriceMap); // ���ķ��õĹ̶����̶����ı仯���������к�δ���Ķ��ġ��������ж��ģ��۸�䶯�� 10 ���ڵĸ����Ӱ�졣
		pricingSchemeMap.put("create_time", dateTime);
		billingMap2.put("pricing_scheme", pricingSchemeMap);
		billingList.add(billingMap2);
		data.put("billing_cycles", billingList);
		
		// ���ĵĸ���ƫ������
		Map<String,Object> paymentPreferencesMap = new HashMap<String,Object>();
		Map<String,Object> setupFeeMap = new HashMap<String,Object>();
		setupFeeMap.put("value","10");
		setupFeeMap.put("currency_code","USD");
		paymentPreferencesMap.put("setup_fee", setupFeeMap); // ����ĳ�ʼ���÷�
		paymentPreferencesMap.put("auto_bill_outstanding",true);// �Ƿ�����һ���Ʒ������Զ��Ʒ�δ����
		paymentPreferencesMap.put("setup_fee_failure_action","CONTINUE");// ������õĳ�ʼ����ʧ�ܣ��Զ��Ĳ�ȡ�Ĳ��� CONTINUE. ������õĳ�ʼ����ʧ�ܣ���������ġ� CANCEL. ������õĳ�ʼ����ʧ�ܣ���ȡ�����ġ�
		paymentPreferencesMap.put("payment_failure_threshold",3);// ��ͣ����ǰ֧��ʧ�ܵ������������磬���payment_failure_threshold��2�������������֧��ʧ�ܣ�2���Ļ��Զ�����Ϊ״̬��SUSPEND
		data.put("payment_preferences", paymentPreferencesMap);
		
		// ˰������
		Map<String,Object> taxesMap = new HashMap<String,Object>();
		taxesMap.put("percentage","10"); // �˵�����˰�հٷֱ�
		taxesMap.put("inclusive",false); // ָʾ˰���Ƿ��Ѱ������˵������
		data.put("taxes", taxesMap);
		
		data.put("create_time", dateTime);
		data.put("update_time", dateTime);
		
		String result = HttpRequest.post(PaypalPayment.SANDBOX_PAYPAL_URL + "/v1/billing/plans")
				.addHeaders(authMap)
				.body(mapper.writeValueAsString(data))
				.execute().body();
		// logger.info("���յ��ô���PayPal����ƻ����� == " + result);
		System.out.println("���յ��ô���PayPal����ƻ����� == " + result);
//			if(StringHelp.isNotBlank(result)) {
//				try {
//					JSONObject jsonObject = JSONObject.parseObject(result);
//					productId = jsonObject.get("id").toString();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					logger.info("���յ��ô���PayPal����ƻ������쳣");
//					System.out.println("���յ��ô���PayPal����ƻ������쳣");
//					e.printStackTrace();
//				}
//			}
		return result;
	}


}
