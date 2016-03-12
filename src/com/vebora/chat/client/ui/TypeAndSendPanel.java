package com.vebora.chat.client.ui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.vebora.chat.client.StyledComposite;

public class TypeAndSendPanel extends StyledComposite {

	private HorizontalPanel main = new HorizontalPanel();

	private TextBox typeArea = new TextBox();

	private Button sendButon = new Button();

	private TypeAndSendPanel() {
		initWidget(main);

		sendButon.addStyleName("sendButton");
		main.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		typeArea.getElement().setAttribute("placeholder", "Type..");
		typeArea.addStyleName("textArea");

		main.add(typeArea);
		main.add(sendButon);
	}

	public static TypeAndSendPanel aNew() {
		return new TypeAndSendPanel();
	}

	public TextBox getTypeArea() {
		return typeArea;
	}

	public Button getSendButon() {
		return sendButon;
	}

}
