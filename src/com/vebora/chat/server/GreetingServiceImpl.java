package com.vebora.chat.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.vebora.chat.client.GreetingService;
import com.vebora.chat.server.data.DataSourceManager;
import com.vebora.chat.server.data.mapper.EntityMapperFactory;
import com.vebora.chat.shared.SystemInfo;
import com.vebora.chat.shared.model.ChatText;

@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	private static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	static {
	}

	@Override
	public String authenticate(String aid) {
		// User logged in log

		try {
			Entity user = ds.get(KeyFactory.createKey("User", Long.valueOf(aid)));
			String userName = (String) user.getProperty("username");
			// addNewChatText("[SYSTEM]", "[" + userName + "] has joined again", SystemInfo.AID);
			return userName;
		} catch (EntityNotFoundException e) {
			return null;

		}
	}

	@Override
	public String setName(String name, String aid) {
		String userAid = aid;
		if (aid == null) {
			// New user
			Entity user = new Entity("User");
			user.setProperty("username", name);
			ds.put(user);
			userAid = String.valueOf(user.getKey().getId());
			addNewChatText("[SYSTEM]", "[" + name + "] has joined", SystemInfo.AID);

		} else {
			Entity user = new Entity("User", userAid);
			user.setProperty("username", name);
			ds.put(user);

		}
		return userAid;
	}

	@Override
	public void sendChatText(String aid, String text) {
		try {
			Entity user = ds.get(KeyFactory.createKey("User", Long.valueOf(aid)));
			String userName = (String) user.getProperty("username");
			addNewChatText(userName, text, aid);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ChatText> getNewChatTexts(String aid, Long lastReadChatTextId) {

		return getUnreadMessages(lastReadChatTextId, aid);

	}

	public void addNewChatText(String userName, String text, String aid) {

		ChatText chatText = new ChatText(null, text, userName, new Date(), aid);
		DataSourceManager.getInstance().insert(chatText);

	}

	public List<ChatText> getUnreadMessages(Long lastReadDate, String aid) {
		List<ChatText> unreadchats = new ArrayList<>();
		Query query = new Query("ChatText");
		if (lastReadDate != null) {
			FilterPredicate timestampFilter = new FilterPredicate("timestamp", FilterOperator.GREATER_THAN, new Date(lastReadDate));
			query.setFilter(timestampFilter);
		}
		query.addSort("timestamp", SortDirection.ASCENDING);
		PreparedQuery pq = ds.prepare(query);

		for (Entity result : pq.asIterable()) {
			ChatText chatText = EntityMapperFactory.getChatTextEntityMapper().fromEntity(result);
			unreadchats.add(chatText);

		}
		return unreadchats;
	}
}
