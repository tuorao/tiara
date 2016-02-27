package kr.co.makeit.tiara.model;

/**
 * 지불 정보 리스트 ( Jackson용 )
 * @author leekangsan
 *
 */
public class PayLists {
	private String pay_id;
	private String pay_num;
	private String service;
	private String pay_type;
	private String pay_date;
	private Float rate;
	private String pay_money;
	private String left_money;
	public String getPay_id() {
		return pay_id;
	}
	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
	}
	public String getPay_num() {
		return pay_num;
	}
	public void setPay_num(String pay_num) {
		this.pay_num = pay_num;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_date() {
		return pay_date;
	}
	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}
	public Float getRate() {
		return rate;
	}
	public void setRate(Float rate) {
		this.rate = rate;
	}
	public String getPay_money() {
		return pay_money;
	}
	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}
	public String getLeft_money() {
		return left_money;
	}
	public void setLeft_money(String left_money) {
		this.left_money = left_money;
	}
	 

}
