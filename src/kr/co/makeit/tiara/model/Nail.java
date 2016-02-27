package kr.co.makeit.tiara.model;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 네일
 * @author leekangsan
 *
 */
public class Nail {
	private Bitmap Image = null;
	private float X = 0;
	private float Y = 0;
	private float Width = 0;
	private float Height = 0;

	private float offsetX;
	private float offsetY;

	int posX1 = 0, posX2 = 0, posY1 = 0, posY2 = 0;

	float oldDist = 1f;
	float newDist = 1f;

	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;

	int mode = NONE;

	public Nail(Bitmap Image) {
		this.Image = Image;
		setSize(Image.getHeight(), Image.getWidth());
		setXY(0, 0);

	}

	public void Touching(MotionEvent event) {
		int act = event.getAction();
		switch (act & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			if (InObject(event.getX(), event.getY())) {
				posX1 = (int) event.getX();
				posY1 = (int) event.getY();
				offsetX = posX1 - X;
				offsetY = posY1 - Y;

				Log.d("zoom", "mode=DRAG");

				mode = DRAG;
			}
			break;

		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				X = posX2 - offsetX;
				Y = posY2 - offsetY;
				posX2 = (int) event.getX();
				posY2 = (int) event.getY();
				if (Math.abs(posX2 - posX1) > 20
						|| Math.abs(posY2 - posY1) > 20) {
					posX1 = posX2;
					posY1 = posY2;
					Log.d("drag", "mode=DRAG");
				}

			} else if (mode == ZOOM) {
				newDist = spacing(event);

				if (newDist - oldDist > 20) {
					float scale = FloatMath
							.sqrt(((newDist - oldDist) * (newDist - oldDist))
									/ (Height * Height + Width * Width));
					Y = Y - (Height * scale / 2);
					X = X - (Width * scale / 2);

					Height = Height * (1 + scale);
					Width = Width * (1 + scale);

					oldDist = newDist;

				} else if (oldDist - newDist > 20) {
					float scale = FloatMath
							.sqrt(((newDist - oldDist) * (newDist - oldDist))
									/ (Height * Height + Width * Width));
					scale = 0 - scale;
					Y = Y - (Height * scale / 2);
					X = X - (Width * scale / 2);

					Height = Height * (1 + scale);
					Width = Width * (1 + scale);

					oldDist = newDist;
				}
			}
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			// 두번째 손가락 터치
			mode = ZOOM;
			newDist = spacing(event);
			oldDist = spacing(event);
			Log.d("zoom", "newDist=" + newDist);
			Log.d("zoom", "oldDist=" + oldDist);
			Log.d("zoom", "mode=ZOOM");

			break;
		case MotionEvent.ACTION_CANCEL:
		default:
			break;
		}

	}

	public Rect getRect() {
		Rect rect = new Rect();
		rect.set((int) X, (int) Y, (int) (X + Width), (int) (Y + Height));
		return rect;
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);

	}

	public boolean InObject(float eventX, float eventY) {
		if (eventX < (X + Width + 30) && eventX > X - 30
				&& eventY < Y + Height + 30 && eventY > Y - 30) {
			return true;
		}
		return false;
	}

	public void setSize(float Height, float Width) {
		this.Height = Height;
		this.Width = Width;

	}

	public void setXY(float X, float Y) {
		this.X = X;
		this.Y = Y;
	}

	public Bitmap getImage() {
		return Image;
	}
}
