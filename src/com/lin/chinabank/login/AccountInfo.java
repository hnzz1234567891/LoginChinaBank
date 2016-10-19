package com.lin.chinabank.login;

/**
 * 中国银行账户详情.
 * @author lin
 *
 */
public class AccountInfo {
	
	private String name;//名称
	private String status;//账户信息
	private String setUpLocation;//开户网点
	private String setUpTime;//开户日期
	private String city;//开户所属地
	private String moneyType;//币种
	private String accountBalance;//账户余额
	private String activeBalance;//可用余额
	
	public AccountInfo() {
	}

	public AccountInfo(String name, String status, String setUpLocation, String setUpTime, String city,
			String moneyType, String accountBalance, String activeBalance) {
		this.name = name;
		this.status = status;
		this.setUpLocation = setUpLocation;
		this.setUpTime = setUpTime;
		this.city = city;
		this.moneyType = moneyType;
		this.accountBalance = accountBalance;
		this.activeBalance = activeBalance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSetUpLocation() {
		return setUpLocation;
	}

	public void setSetUpLocation(String setUpLocation) {
		this.setUpLocation = setUpLocation;
	}

	public String getSetUpTime() {
		return setUpTime;
	}

	public void setSetUpTime(String setUpTime) {
		this.setUpTime = setUpTime;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getActiveBalance() {
		return activeBalance;
	}

	public void setActiveBalance(String activeBalance) {
		this.activeBalance = activeBalance;
	}

	@Override
	public String toString() {
		return "AccountInfo [name=" + name + ", status=" + status + ", setUpLocation=" + setUpLocation + ", setUpTime="
				+ setUpTime + ", city=" + city + ", moneyType=" + moneyType + ", accountBalance=" + accountBalance
				+ ", activeBalance=" + activeBalance + "]";
	}
}
