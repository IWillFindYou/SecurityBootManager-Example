package model.database;

import java.util.Date;

public class LogEntity {
	private int id;
	private Date regdate;
	private String ip;
	private String apSSID;
	private String apMAC;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getApSSID() {
		return apSSID;
	}
	public void setApSSID(String apSSID) {
		this.apSSID = apSSID;
	}
	public String getApMAC() {
		return apMAC;
	}
	public void setApMAC(String apMAC) {
		this.apMAC = apMAC;
	}	
}
