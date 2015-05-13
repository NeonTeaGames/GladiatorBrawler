package com.saltosion.gladiator.gui;

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
