package com.vebora.chat.server;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vebora.chat.server.data.DataSourceManager;
import com.vebora.chat.shared.model.ChatText;

public class MailHandler extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req,
			HttpServletResponse resp)
			throws IOException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			MimeMessage message = new MimeMessage(session, req.getInputStream());

			DataSourceManager.getInstance().insert(new ChatText(null, message.getFrom()[0].toString(), message.getSubject(), new Date(), "1"));

		} catch (MessagingException e) {
		}
	}
}
