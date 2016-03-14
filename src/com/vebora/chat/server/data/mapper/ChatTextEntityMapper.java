package com.vebora.chat.server.data.mapper;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.vebora.chat.shared.model.ChatText;

public class ChatTextEntityMapper implements EntityMapper<ChatText> {

	@Override
	public Entity toEntity(ChatText text) {
		Entity chatTextEntity = new Entity("ChatText");
		chatTextEntity.setProperty("userid", text.getAid());
		chatTextEntity.setProperty("username", text.getSenderName());
		chatTextEntity.setProperty("text", text.getChatText());
		chatTextEntity.setProperty("timestamp", text.getTimeSTamp());
		return chatTextEntity;
	}

	@Override
	public ChatText fromEntity(Entity e) {
		String username = (String) e.getProperty("username");
		String text = (String) e.getProperty("text");
		String userid = (String) e.getProperty("userid");
		Date timestamp = (Date) e.getProperty("timestamp");
		return new ChatText(timestamp.getTime(), text, username, timestamp, userid);
	}

}
