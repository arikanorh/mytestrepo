package com.vebora.chat.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vebora.chat.shared.model.ChatText;

public interface GreetingServiceAsync {
	void authenticate(String aid, AsyncCallback<String> callback);

	void setName(String name, String aid, AsyncCallback<String> callback);

	void sendChatText(String aid, String text, AsyncCallback<Void> callback);

	void getNewChatTexts(String aid, Integer lastReadChatTextId, AsyncCallback<List<ChatText>> callback);
}