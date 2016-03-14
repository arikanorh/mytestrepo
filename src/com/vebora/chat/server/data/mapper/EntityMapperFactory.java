package com.vebora.chat.server.data.mapper;

public class EntityMapperFactory {

	private static ChatTextEntityMapper chatTextEntityMapper = new ChatTextEntityMapper();

	private static UserEntityMapper userEntityMapper = new UserEntityMapper();

	public static ChatTextEntityMapper getChatTextEntityMapper() {
		return chatTextEntityMapper;
	}

	public static UserEntityMapper getUserEntityMapper() {
		return userEntityMapper;
	}
}
