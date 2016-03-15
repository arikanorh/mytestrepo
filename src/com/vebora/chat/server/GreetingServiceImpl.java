package com.vebora.chat.server;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.vebora.chat.client.GreetingService;
import com.vebora.chat.server.data.DataSourceManager;
import com.vebora.chat.shared.SystemInfo;
import com.vebora.chat.shared.model.ChatText;
import com.vebora.chat.shared.model.User;

@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	private static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	static {
		ObjectifyService.register(ChatText.class);
		ObjectifyService.register(User.class);

	}

	@Override
	public String authenticate(Long aid) {
		// User logged in log

		try {
			Entity user = ds.get(KeyFactory.createKey("User", aid));
			String userName = (String) user.getProperty("username");
			// addNewChatText("[SYSTEM]", "[" + userName + "] has joined again", SystemInfo.AID);
			return userName;
		} catch (EntityNotFoundException e) {
			return null;

		}
	}

	/**
	 * Returns aid.
	 */
	@Override
	public Long setName(String name, Long userid) {
		User user = new User(name, userid);
		if (userid == null) {
			// New user
			addNewChatText("[SYSTEM]", "[" + name + "] has joined", SystemInfo.AID);

		}
		return DataSourceManager.getInstance().save(user).getId();
	}

	@Override
	public void sendChatText(Long aid, String text) {
		try {
			Entity user = ds.get(KeyFactory.createKey("User", aid));
			String userName = (String) user.getProperty("username");
			addNewChatText(userName, text, aid);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ChatText> getNewChatTexts(Long lastReadChatTextId) {

		Query<ChatText> query = ofy().load().type(ChatText.class);
		query = query.order("timestamp");
		if (lastReadChatTextId != null) {
			FilterPredicate timestampFilter = new FilterPredicate("timestamp", FilterOperator.GREATER_THAN, new Date(lastReadChatTextId));
			query = query.filter(timestampFilter);
		}
		List<ChatText> unreadchats = query.list();
		return new ArrayList<>(unreadchats);

	}

	public void addNewChatText(String userName, String text, Long aid) {
		Date now = new Date();
		ChatText chatText = new ChatText(now.getTime(), text, userName, now, aid);
		DataSourceManager.getInstance().save(chatText);

	}

}
