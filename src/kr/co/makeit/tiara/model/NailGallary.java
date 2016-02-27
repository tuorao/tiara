package kr.co.makeit.tiara.model;

/**
 * 네일 갤러리
 * @author leekangsan
 *
 */
public class NailGallary {
	private int imgResouce; // 임시... 리소스 URL로 변경하기!
	private String title;
	private boolean favorite;

	public NailGallary(String title, int imgResouce, boolean favorite) {
		setImgResouce(imgResouce);
		setTitle(title);
		setFavorite(favorite);
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public int getImgResouce() {
		return imgResouce;
	}

	public void setImgResouce(int imgResouce) {
		this.imgResouce = imgResouce;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
