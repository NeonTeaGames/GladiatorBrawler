package com.saltosion.gladiator.gui.nodes;

import com.saltosion.gladiator.gui.properties.TextProperty;

public class TextNode extends GUINode implements TextProperty {

	private String text;

	public TextNode(String ID, String text) {
		super(ID);
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

}
