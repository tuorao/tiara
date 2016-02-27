package kr.co.makeit.tiara;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 네일 미리보기 카페라 SurfaceView
 * @author leekangsan
 *
 */
public class NailCamera extends SurfaceView implements SurfaceHolder.Callback {
	final static public int CAMARA_STOP = 1;

	SurfaceHolder mHolder;
	Camera mCamera;

	@SuppressWarnings("deprecation")
	public NailCamera(Context context) {
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPreviewSize(w, h);
		mCamera.setParameters(parameters);
		mCamera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open();
		mCamera.setDisplayOrientation(90);
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.setPreviewCallback(null);
			mCamera.release();
			mCamera = null;
		}
	}

	public void stopCamera() {
		mCamera.stopPreview();
	}

	public void startCamera() {
		mCamera.startPreview();
	}

	public void onCameraFocus(final int afterState) {
		Camera.AutoFocusCallback focusCb = new AutoFocusCallback() {
			@Override
			public void onAutoFocus(boolean success, Camera camera) {
				if (success) {
					switch (afterState) {
					case CAMARA_STOP:
						mCamera.stopPreview();
						break;
					}
				}
			}
		};
		mCamera.autoFocus(focusCb);
	}

}
