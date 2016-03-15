package com.vebora.chat.shared.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class User {

	private String username;

	@Id
	private Long aid;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String userName, Long aid) {
		this.username = userName;
		this.aid = aid;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public void setAid(Long aid) {
		this.aid = aid;
	}

	public Long getAid() {
		return aid;
	}

}
