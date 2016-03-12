package com.vebora.chat.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * StyledComposite builds on top of {@link Composite} by adding automatic style
 * names based on the package and class name. For example, if
 * com.xxx.hello.client.xxx.SearchView class extends StyledComposite, it would
 * automatically get the style name "hello-SearchView".
 * 
 * @author abutun
 * 
 */
public abstract class StyledComposite extends Composite {

	private boolean autoStyle = true;

	public String getAutoStyleName() {
		String className = this.getClass().getName();

		String postFix;
		int d = className.lastIndexOf(".");
		if (d > 0)
			postFix = className.substring(d + 1);
		else
			postFix = className;

		String prefix = "";
		try {
			int ci = className.indexOf(".client.");
			if (ci > 0) {
				prefix = className.substring(0, ci);
				prefix = prefix.substring(prefix.lastIndexOf(".") + 1);
			} else if (className.startsWith("com.")) {
				prefix = className.substring(4, className.indexOf(".", 4));
			}
			prefix = prefix + "-";
		} catch (Exception e) {
		}

		return prefix + postFix;
	}

	public void setAutoStyle(boolean autoStyle) {
		this.autoStyle = autoStyle;
	}

	public boolean isAutoStyle() {
		return autoStyle;
	}

	@Override
	protected void initWidget(Widget widget) {
		super.initWidget(widget);
		if (this.autoStyle) {
			String autoStyleName = getAutoStyleName();
			if (autoStyleName != null) {
				widget.addStyleName(autoStyleName);
			}
		}
	}

}
