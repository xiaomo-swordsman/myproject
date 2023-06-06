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

	// paypal��client id��secret
//	public static String PAYPAL_CLIENT_ID = "AYh1njn9HcwLFy-HOdJ2DyYPQf5VJeM7MT07hyb7dfW0jBcvqsdf512aE8VlESvBvXccIOqKpmg6DtJ9";
//	public static String PAYPAL_CLIENT_SECRET = "EHZ3vkVpAH6J8cXEPr5MYmiv8ZUrgck2K2yOIRo5nAYTRBoiFZuLYqtoHzN1z5U3wqFLF2KZ48unQzWg";
	
	public static String PAYPAL_CLIENT_ID = "ARkhQNYfWz19YZAnpTdDZXtpffhUYuLG3aKu-gLVt8DlZr3LPKZy_ec1wWIldM522RAgQca-DvNwB1CY";
	public static String PAYPAL_CLIENT_SECRET = "EOZAhe5NrWO-qs57V5e31Fjm9GApyphw0Xfz6a3dO80subVZa2lKA_ITelJYEQ8RVs8IYWYBRsaKR4ga";
	
	/**
	 * 	/v1/oauth2/token		��ȡtoken
	 * 	/v2/checkout/orders		����һ����֧������url
	 * /v1/catalogs/products 	������Ʒ��url
	 * /v1/billing/plans 		��������ƻ�
	 */
	public static String PAYPAL_BASE_URL = "https://api-m.sandbox.paypal.com";	//��ȡtoken
	
	public static ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		String accessToken = getToken();
		String productId = goToPayPal(accessToken);
		createPlan(productId,accessToken);
	}

	/**
	 * ������Ʒ
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	private static String goToPayPal(String accessToken) throws ClientProtocolException, IOException {
		String result = "result";
		// ������Ʒ
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
		System.out.println("������Ʒ result == " + result);
		
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
	 * ��������ƻ�
	 * @return	
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static String createPlan(String productId,String accessToken) throws ClientProtocolException, IOException {
		String result = "result";
		String dateTime = LocalDateTime.now().toString();
		// ������Ʒ
		Map<String,String> map = new HashMap<String,String>();
        map.put("Content-Type","application/json");
        map.put("Authorization","Bearer " + accessToken);
		map.put("PayPal-Request-Id","PLAN-18062020-001");
		map.put("Accept","application/json");
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("product_id", productId); // ��Ʒid
		data.put("name","Basic Plan");// �ƻ�����
		data.put("description","Basic plan"); // �ƻ�����
		data.put("status", "ACTIVE"); //�ƻ�״̬ CREATED. �ƻ��Ѵ��������޷�Ϊ��������״̬�ļƻ��������ġ�INACTIVE. �üƻ����ڷǻ״̬��ACTIVE. �üƻ����ڻ״̬����ֻ��Ϊ��������״̬�ļƻ���������
		
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
		
		System.out.println("��������ƻ� data == " + mapper.writeValueAsString(data));
		
		result = cn.hutool.http.HttpRequest.post(PAYPAL_BASE_URL + "/v1/billing/plans").addHeaders(map).body(mapper.writeValueAsString(data)).execute().body();
		System.out.println("��������ƻ� result == " + result);
		return result;
	}
	
	
	/**
	 * @param authorization	�����token
	 * @param requestUrl	�����url
	 * @param contentType
	 * @param requestId
	 * @param data
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPost(String authorization,String requestUrl,String contentType,String requestId,String data) throws ClientProtocolException, IOException {
			String body = "";
			// ����httpclient����
			CloseableHttpClient client = HttpClients.createDefault();
			// ����post��ʽ�������
			HttpPost httpPost = new HttpPost(requestUrl);
			
			if(null != data) {
				// װ�����
				StringEntity s = new StringEntity(data, "utf-8");
				// ���ò��������������
				httpPost.setEntity(s);
			}
			// ����header��Ϣ
			// ָ������ͷ��Content-type������User-Agent��
			httpPost.setHeader("Content-Type", contentType);
			httpPost.setHeader("Authorization", authorization);
			httpPost.setHeader("PayPal-Request-Id",requestId);
			// ִ��������������õ������ͬ��������
			CloseableHttpResponse response = client.execute(httpPost);
			// ��ȡ���ʵ��
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// ��ָ������ת�����ʵ��ΪString����
				body = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			// �ͷ�����
			response.close();
			return body;
    }
	
}
