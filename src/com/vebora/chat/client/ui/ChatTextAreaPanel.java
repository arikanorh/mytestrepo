package com.vebora.chat.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.vebora.chat.client.StyledComposite;
import com.vebora.chat.shared.SystemInfo;
import com.vebora.chat.shared.model.ChatText;

public class ChatTextAreaPanel extends StyledComposite {

	private FlowPanel main = new FlowPanel();

	private ScrollPanel chatScrollpanel = new ScrollPanel();

	private VerticalPanel chatArea = new VerticalPanel();

	private List<String> colors = new ArrayList<String>();

	private Map<Long, String> activeUsers = new HashMap<>();

	private ChatTextAreaPanel() {
		initWidget(main);
		chatArea.addStyleName("chatListPanel");
		chatScrollpanel.addStyleName("chatScrollPanel");

		chatScrollpanel.add(chatArea);
		main.add(chatScrollpanel);

		colors.add("#ff0000");
		colors.add("#ff8000");
		colors.add("#ffff00");
		colors.add("#bfff00");
		colors.add("#40ff00");
		colors.add("#00ffff");
		colors.add("#00bfff");
		colors.add("#0040ff");
		colors.add("#8000ff");
		colors.add("#ff00ff");
		colors.add("#669933");
		colors.add("#339999");

	}

	public static ChatTextAreaPanel aNew() {
		return new ChatTextAreaPanel();
	}

	public void addText(List<ChatText> result) {
		for (ChatText text : result) {
			Long aid = text.getAid();
			String color = "";

			if (SystemInfo.AID.equals(aid)) {
				color = "#000000";
			} else if (activeUsers.containsKey(aid)) {
				color = activeUsers.get(aid);
			} else {
				color = colors.get(activeUsers.size() % colors.size());
				activeUsers.put(aid, color);
			}

			Label labeL = new Label(text.getSenderName() + " : " + text.getChatText());
			labeL.getElement().setAttribute("style", "color:" + color);
			chatArea.add(labeL);
		}
		chatScrollpanel.setVerticalScrollPosition(Integer.MAX_VALUE);
	}
}
