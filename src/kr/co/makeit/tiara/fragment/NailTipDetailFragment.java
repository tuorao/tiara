package kr.co.makeit.tiara.fragment;

import kr.co.makeit.tiara.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.actionbarsherlock.app.SherlockFragment;

/**
 * 네일 팁 상세보기 프래그먼트
 * @author leekangsan
 *
 */
public class NailTipDetailFragment extends SherlockFragment {
	private String videoUrl;
	private WebView webView;
	private View v;
	
	public static NailTipDetailFragment newInstance(String videoUrl) {
		NailTipDetailFragment fragment = new NailTipDetailFragment();
		Bundle localBundle = new Bundle();
		localBundle.putString("videoUrl", videoUrl);
		fragment.setArguments(localBundle);
		return fragment;
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_nail_tip_detail,
				container, false);
		this.videoUrl = getArguments().getString("videoUrl");
		
		webView = (WebView) v.findViewById(R.id.gall_tip_webView);
		WebSettings webSettings = webView.getSettings();
		webSettings.setSaveFormData(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);

		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			};
		});

		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.setWebChromeClient(new WebChromeClient() {
		});

		webView.loadUrl(videoUrl);
		
		return v;
	}
	
	
	@Override
	public void onPause() {
		webView.onPause();
		super.onPause();
	}
}
