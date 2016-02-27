package kr.co.makeit.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import kr.co.makeit.tiara.app.App;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 인터넷 접속 연결 클래스
 * @author leekangsan
 *
 */
public class ConnectHttp {
	private HttpPost httpPost;
	private HttpGet httpGet;
	private HttpClient httpClient = new DefaultHttpClient();
	private HttpResponse httpResponse;
	private UrlEncodedFormEntity urlEncodedFormEntity = null;
	
	private String html;
	private String line;
	
	/**
	 * 파라미터가 하나인 경우 편의를 위해 사용, 하나 이상인 경우에는 List에 담아서 보낸다.
	 * @param url
	 * @param parameters
	 * @return String
	 */
	public String getHttpPostContents(String url, String parameters) {
		html = "";
		httpPost = new HttpPost(App.getHost()+url + parameters);
		try {
			httpResponse = httpClient.execute(httpPost);

			InputStream inputStream = httpResponse.getEntity().getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			while ((line = bufferedReader.readLine()) != null) {
				html += line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}
	
	public String getHttpGetContents(String url, String parameters) {
		html = "";
		httpGet = new HttpGet(App.getHost()+url + parameters);
		try {
			httpResponse = httpClient.execute(httpGet);

			InputStream inputStream = httpResponse.getEntity().getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			while ((line = bufferedReader.readLine()) != null) {
				html += line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}

	public String getHttpPostContents(String url, List<NameValuePair> paramList) {
		html = "";
		httpPost = new HttpPost(App.getHost()+url);
		try {
			urlEncodedFormEntity = new UrlEncodedFormEntity(paramList, "UTF-8");
			httpPost.setEntity(urlEncodedFormEntity);		
			httpResponse = httpClient.execute(httpPost);

			InputStream inputStream = httpResponse.getEntity().getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			while ((line = bufferedReader.readLine()) != null) {
				html += line;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}
}
