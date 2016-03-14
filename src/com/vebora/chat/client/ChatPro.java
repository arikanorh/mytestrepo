package com.vebora.chat.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.vebora.chat.client.ui.ChatMainPanel;
import com.vebora.chat.shared.model.ChatText;

public class ChatPro implements EntryPoint {
	private ChatMainPanel chatMain = ChatMainPanel.anew();
	private Long lastReadChatId = null;
	private String aid = Cookies.getCookie("aid");

	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	private final Integer chatTimer = 2000;

	private Timer unreadChatTimer = new Timer() {

		@Override
		public void run() {
			checkUnreadChats();
		}

	};

	@Override
	public void onModuleLoad() {

		if (aid == null || aid.equals("")) {
			chatMain.enableChat(false);
		} else {
			greetingService.authenticate(aid, new AsyncCallback<String>() {

				@Override
				public void onSuccess(String result) {
					if (result == null) {
						chatMain.enableChat(false);
						Cookies.removeCookie("aid");
						aid = null;
						chatMain.getSetNickPanel().getTypeArea().setFocus(true);

					} else {
						chatMain.setUserName(result);
						chatMain.enableChat(true);
						chatMain.getTypeAndSendPanel().getTypeArea().setFocus(true);

					}
				}

				@Override
				public void onFailure(Throwable caught) {

				}
			});
		}

		chatMain.getSetNickPanel().getSendButon().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final String nickName = chatMain.getSetNickPanel().getTypeArea().getText();
				greetingService.setName(nickName, aid, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						chatMain.enableChat(false);

					}

					@Override
					public void onSuccess(String result) {
						chatMain.enableChat(true);
						if (aid == null) {
							Cookies.setCookie("aid", result);
							aid = result;
						}
						chatMain.getTypeAndSendPanel().getTypeArea().setFocus(true);
					}
				});

			}
		});

		chatMain.getTypeAndSendPanel().getSendButon().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String typedText = chatMain.getTypeAndSendPanel().getTypeArea().getText();

				greetingService.sendChatText(aid, typedText, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						chatMain.getTypeAndSendPanel().getTypeArea().setText("");
						chatMain.getTypeAndSendPanel().getTypeArea().setFocus(true);
					}
				});

			}
		});

		RootPanel.get().add(chatMain);
		startTimer();

	}

	private void checkUnreadChats() {
		greetingService.getNewChatTexts(aid, lastReadChatId, new AsyncCallback<List<ChatText>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<ChatText> result) {
				if (result.size() != 0) {
					lastReadChatId = result.get(result.size() - 1).getChatTextId();
					chatMain.addChatTexts(result);
				}
				unreadChatTimer.schedule(chatTimer);
			}
		});

	}

	private void startTimer() {
		if (!unreadChatTimer.isRunning()) {
			unreadChatTimer.run();
		}
	}
}
