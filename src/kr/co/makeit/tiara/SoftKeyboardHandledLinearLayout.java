package kr.co.makeit.tiara;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 키보드가 나올 때 불필요한 레이아웃을 제거해주는 라이브러리
 * https://gist.github.com/mrleolink/8823150
 * @author leekangsan
 *
 */
public class SoftKeyboardHandledLinearLayout extends LinearLayout {
	private boolean isKeyboardShown;
	private SoftKeyboardVisibilityChangeListener listener;

	public SoftKeyboardHandledLinearLayout(Context paramContext) {
		super(paramContext);
	}

	public SoftKeyboardHandledLinearLayout(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	@SuppressLint({ "NewApi" })
	public SoftKeyboardHandledLinearLayout(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public boolean dispatchKeyEventPreIme(KeyEvent paramKeyEvent) {
		if ((paramKeyEvent.getKeyCode() == 4) && (this.isKeyboardShown)) {
			this.isKeyboardShown = false;
			this.listener.onSoftKeyboardHide();
		}
		return super.dispatchKeyEventPreIme(paramKeyEvent);
	}

	protected void onMeasure(int paramInt1, int paramInt2) {
		int i = View.MeasureSpec.getSize(paramInt2);
		if ((getHeight() > i) && (!this.isKeyboardShown)) {
			this.isKeyboardShown = true;
			this.listener.onSoftKeyboardShow();
		}
		super.onMeasure(paramInt1, paramInt2);
	}

	public void setHide() {
		this.isKeyboardShown = false;
		this.listener.onSoftKeyboardHide();
	}

	public void setOnSoftKeyboardVisibilityChangeListener(
			SoftKeyboardVisibilityChangeListener paramSoftKeyboardVisibilityChangeListener) {
		this.listener = paramSoftKeyboardVisibilityChangeListener;
	}

	public static abstract interface SoftKeyboardVisibilityChangeListener {
		public abstract void onSoftKeyboardHide();

		public abstract void onSoftKeyboardShow();
	}
}