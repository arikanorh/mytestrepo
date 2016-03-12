package com.vebora.chat.shared.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ChatText implements IsSerializable {

	private String senderName;
	private Integer chatTextId;
	private String chatText;
	private Date timeSTamp;
	private String aid;

	public ChatText() {
	}

	public ChatText(Integer chatTextId, String chatText, String senderName, Date timeStamp, String aid) {
		this.chatTextId = chatTextId;
		this.chatText = chatText;
		this.senderName = senderName;
		this.timeSTamp = timeStamp;
		this.aid = aid;
	}

	public String getChatText() {
		return chatText;
	}

	public Integer getChatTextId() {
		return chatTextId;
	}

	public void setChatText(String chatText) {
		this.chatText = chatText;
	}

	public void setChatTextId(Integer chatTextId) {
		this.chatTextId = chatTextId;
	}

	public String getSenderName() {
		return senderName;
	}

	public Date getTimeSTamp() {
		return timeSTamp;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}
}
