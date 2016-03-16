package com.vebora.chat.client.ui;

import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.vebora.chat.client.StyledComposite;
import com.vebora.chat.shared.model.ChatText;

public class ChatMainPanel extends StyledComposite {

	private VerticalPanel main = new VerticalPanel();

	private ChatTextAreaPanel textAreaPanel = ChatTextAreaPanel.aNew();

	private TypeAndSendPanel typeAndSendPanel = TypeAndSendPanel.aNew();

	private ChatMainPanel() {
		initWidget(main);

		main.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				typeAndSendPanel.getTypeArea().setFocus(true);

			}
		});
		typeAndSendPanel.getSendButon().setText("SEND");

		main.add(textAreaPanel);
		main.add(typeAndSendPanel);
	}

	public static ChatMainPanel anew() {
		return new ChatMainPanel();
	}

	public void enableChat(boolean b) {
		typeAndSendPanel.getTypeArea().setEnabled(b);
		typeAndSendPanel.getSendButon().setEnabled(b);
	}

	public void addChatTexts(List<ChatText> result) {
		textAreaPanel.addText(result);
	}

	public TypeAndSendPanel getTypeAndSendPanel() {
		return typeAndSendPanel;
	}

	public ChatTextAreaPanel getTextAreaPanel() {
		return textAreaPanel;
	}
}
