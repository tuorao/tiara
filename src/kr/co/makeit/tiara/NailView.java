package kr.co.makeit.tiara;

import kr.co.makeit.tiara.model.Nail;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * 네일이 표시되는 부분 (View)
 * @author leekangsan
 *
 */
public class NailView extends View {

	private Nail nail = null;
	private Bitmap bitmap = null;

	public NailView(Context context) {
		super(context);

	}

	public void setSelectedImage(int resorceID) {
		bitmap = BitmapFactory.decodeResource(getResources(), resorceID);
		nail = new Nail(bitmap);
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		nail.Touching(event);
		invalidate();
		return (true);
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(nail.getImage(), null, nail.getRect(), null);

		super.draw(canvas);
	}

}