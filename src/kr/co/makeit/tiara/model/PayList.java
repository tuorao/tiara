package kr.co.makeit.tiara.model;

/**
 * 지불정보 리스트
 * @author leekangsan
 *
 */
public class PayList {
	private String service;
	private String charge;
	private String discount;
	private String balance;
	private String date;

	public PayList(String service, String charge, String discount, String balance, String date) {
		setDate(date);
		setBalance(balance);
		setCharge(charge);
		setDiscount(discount);
		setService(service);
	}
	
	public String getDate(){
		return date;
	}
	public void setDate(String date){
		this.date = date;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

}
