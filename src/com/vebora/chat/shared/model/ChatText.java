package com.vebora.chat.shared.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class ChatText implements IsSerializable {

	private String sendername;
	@Id
	private Long chattextid;
	private String text;
	@Index
	private Date timestamp;
	private String userid;

	public ChatText() {
	}

	public ChatText(Long chatTextId, String chatText, String senderName, Date timeStamp, String userid) {
		this.chattextid = chatTextId;
		this.text = chatText;
		this.sendername = senderName;
		this.timestamp = timeStamp;
		this.userid = userid;
	}

	public String getChatText() {
		return text;
	}

	public Long getChatTextId() {
		return chattextid;
	}

	public void setChatText(String chatText) {
		this.text = chatText;
	}

	public void setChatTextId(Long chatTextId) {
		this.chattextid = chatTextId;
	}

	public String getSenderName() {
		return sendername;
	}

	public Date getTimeSTamp() {
		return timestamp;
	}

	public String getAid() {
		return userid;
	}

	public void setAid(String userid) {
		this.userid = userid;
	}
}
