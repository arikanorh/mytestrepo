package com.vebora.chat.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.vebora.chat.client.GreetingService;
import com.vebora.chat.shared.SystemInfo;
import com.vebora.chat.shared.model.ChatText;

@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	// aid-username

	private Map<String, String> usersWithId = new HashMap<>();

	private static Integer cacheId = 1;

	static {
		MemcacheServiceFactory.getMemcacheService().put(cacheId, new ArrayList<ChatText>());
	}

	@Override
	public String authenticate(String aid) {
		// User logged in log
		if (usersWithId.containsKey(aid)) {
			String userName = usersWithId.get(aid);
			addNewChatText("[SYSTEM]", "[" + userName + "] has joined again", SystemInfo.AID);
			return userName;

		} else {
			return null;
		}
	}

	@Override
	public String setName(String name, String aid) {
		String userAid = aid;
		if (aid == null) {
			// New user
			userAid = String.valueOf(new Random().nextInt());
			usersWithId.put(userAid, name);
			addNewChatText("[SYSTEM]", "[" + name + "] has joined", SystemInfo.AID);

		} else {
			if (usersWithId.containsKey(aid)) {
				String oldUserName = usersWithId.get(aid);
				addNewChatText("[SYSTEM]", "[" + oldUserName + "] has changed name as [" + name + "]", SystemInfo.AID);

			}
			usersWithId.put(userAid, name);
		}
		return userAid;
	}

	@Override
	public void sendChatText(String aid, String text) {
		String userName = usersWithId.get(aid);
		addNewChatText(userName, text, aid);
	}

	@Override
	public List<ChatText> getNewChatTexts(String aid, Integer lastReadChatTextId) {
		// Authenticate maybe
		if (lastReadChatTextId == null) {
			return getTextsFromCache();
		} else {
			List<ChatText> unreadchats = new ArrayList<>();
			List<ChatText> texts = getTextsFromCache();
			if (texts.size() > lastReadChatTextId) {
				for (int a = lastReadChatTextId; a < texts.size(); a++) {
					unreadchats.add(texts.get(a));
				}
			}
			return unreadchats;
		}
	}

	@SuppressWarnings("unchecked")
	private List<ChatText> getTextsFromCache() {
		return (List<ChatText>) MemcacheServiceFactory.getMemcacheService().get(cacheId);
	}

	private synchronized void addNewChatText(String userName, String text, String aid) {

		Date timeStamp = new Date();
		Integer chatTextId = getTextsFromCache().size() + 1;
		String chatText = timeStamp + " : " + text;
		List<ChatText> texts = getTextsFromCache();
		texts.add(new ChatText(chatTextId, chatText, userName, timeStamp, aid));

		MemcacheServiceFactory.getMemcacheService().put(cacheId, texts);

	}
}
