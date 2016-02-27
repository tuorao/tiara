package kr.co.makeit.tiara.model;

import android.graphics.Bitmap;

/**
 * 네일 팁
 * @author leekangsan
 *
 */
public class NailTip {
	private int imgResouce; // 임시... 리소스 URL로 변경하기!
	private String title;
	private boolean favorite;
	private String videoUrl;
	private String fileUrl;
	private Bitmap thumbNail;
	private String gBody;
	private Bitmap youtubeThumb;
	private String gubun;

	public NailTip(String title, int imgResouce, boolean favorite) {
		setImgResouce(imgResouce);
		setTitle(title);
		setFavorite(favorite);
	}

	public NailTip(String gubun, String title, int imgResouce, boolean favorite, String videoUrl, Bitmap youtubeThumb) {
		setImgResouce(imgResouce);
		setTitle(title);
		setFavorite(favorite);
		setVideoUrl(videoUrl);
		setYoutubeThumb(youtubeThumb);
		setGubun(gubun);
	}

	public NailTip(String gubun, String title, Bitmap thumbNail, String fileUrl, String gBody, boolean favorite){
		setTitle(title);
		setFavorite(favorite);
		setThumbNail(thumbNail);
		setFileUrl(fileUrl);
		setgBody(gBody);
		setGubun(gubun);
	}
	
	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public Bitmap getYoutubeThumb() {
		return youtubeThumb;
	}

	public void setYoutubeThumb(Bitmap youtubeThumb) {
		this.youtubeThumb = youtubeThumb;
	}

	public String getgBody() {
		return gBody;
	}

	public void setgBody(String gBody) {
		this.gBody = gBody;
	}

	public Bitmap getThumbNail() {
		return thumbNail;
	}

	public void setThumbNail(Bitmap thumbNail) {
		this.thumbNail = thumbNail;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
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
