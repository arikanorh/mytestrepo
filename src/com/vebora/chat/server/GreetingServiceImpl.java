package com.vebora.chat.server;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.vebora.chat.client.GreetingService;
import com.vebora.chat.server.data.DataSourceManager;
import com.vebora.chat.shared.model.ChatText;

@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	static {
		ObjectifyService.register(ChatText.class);
		// ObjectifyService.register(User.class);

	}

	@Override
	public void sendChatText(String text) {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		addNewChatText(user.getNickname(), text, user.getUserId());
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

	public void addNewChatText(String userName, String text, String aid) {
		Date now = new Date();
		ChatText chatText = new ChatText(now.getTime(), text, userName, now, aid);
		DataSourceManager.getInstance().save(chatText);

	}

}
