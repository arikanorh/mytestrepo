package com.vebora.chat.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vebora.chat.shared.model.ChatText;

public interface GreetingServiceAsync {
	void authenticate(Long aid, AsyncCallback<String> callback);

	void setName(String name, Long aid, AsyncCallback<Long> callback);

	void sendChatText(Long aid, String text, AsyncCallback<Void> callback);

	void getNewChatTexts(Long lastReadChatTextId, AsyncCallback<List<ChatText>> callback);
}