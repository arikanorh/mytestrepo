package com.vebora.chat.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.vebora.chat.shared.model.ChatText;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {

	void sendChatText(String text);

	List<ChatText> getNewChatTexts(Long lastReadChatTextId);
}
