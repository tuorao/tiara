package kr.co.makeit.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 내가 원하는 프로그레스바를 쉽게 생성할 수 있다.
 * @author leekangsan
 *
 */
public class CustomProgress {
	private ProgressDialog progressDialog;
	
	public CustomProgress(Context context, int layoutResID){
		progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
		progressDialog.setContentView(layoutResID);
	}
	
	public void dismiss(){
		progressDialog.dismiss();
	}
}
