package com.vebora.chat.server.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.vebora.chat.server.data.mapper.EntityMapperFactory;
import com.vebora.chat.shared.model.ChatText;

public class DataSourceManager {

	private DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

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

	public Key insert(ChatText text) {
		Entity dataToInsert = EntityMapperFactory.getChatTextEntityMapper().toEntity(text);
		ds.put(dataToInsert);
		return dataToInsert.getKey();
	}
}
