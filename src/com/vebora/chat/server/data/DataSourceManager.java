package com.vebora.chat.server.data;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.ObjectifyService;
import com.vebora.chat.shared.model.ChatText;
import com.vebora.chat.shared.model.User;

public class DataSourceManager {

	private DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	static {
		ObjectifyService.register(ChatText.class);
		ObjectifyService.register(User.class);

	}

	private DataSourceManager() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Lazy
	 * 
	 * @author MonsterPc
	 */
	private static class LazyInitializedInstanceHolder {
		private static final DataSourceManager INSTANCE = new DataSourceManager();
	}

	public static DataSourceManager getInstance() {
		return LazyInitializedInstanceHolder.INSTANCE;
	}

	public Key save(Object text) {
		Key result = ofy().save().entity(text).now().getRaw();
		return result;
		// Entity dataToInsert = EntityMapperFactory.getChatTextEntityMapper().toEntity(text);
		// ds.put(dataToInsert);
		// return dataToInsert.getKey();
	}
}
