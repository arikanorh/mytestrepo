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
import com.vebora.chat.shared.SystemInfo;
import com.vebora.chat.shared.model.ChatText;

@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	private DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

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

	public synchronized void addNewChatText(String userName, String text, String aid) {

		Entity chatTextEntity = new Entity("ChatText");
		chatTextEntity.setProperty("userid", aid);
		chatTextEntity.setProperty("username", userName);
		chatTextEntity.setProperty("text", text);
		chatTextEntity.setProperty("timestamp", new Date());
		ds.put(chatTextEntity);

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
			String username = (String) result.getProperty("username");
			String text = (String) result.getProperty("text");
			String userid = (String) result.getProperty("userid");
			Date timestamp = (Date) result.getProperty("timestamp");
			unreadchats.add(new ChatText(timestamp.getTime(), text, username, timestamp, userid));

		}
		return unreadchats;
	}
}
