package com.vebora.chat.server.data;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.datastore.Key;

public class DataSourceManager {

	private DataSourceManager() {
		// TODO Auto-generated constructor stub
	}

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
