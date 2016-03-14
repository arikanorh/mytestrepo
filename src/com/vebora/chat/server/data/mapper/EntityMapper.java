package com.vebora.chat.server.data.mapper;

import com.google.appengine.api.datastore.Entity;

public interface EntityMapper<T> {

	Entity toEntity(T t);

	T fromEntity(Entity e);
}
