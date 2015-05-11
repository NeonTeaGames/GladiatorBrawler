package com.saltosion.gladiator.gui;

public class GUIManager {
	private final GUINode rootNode;

	public GUIManager() {
		this.rootNode = new GUINode("root");
	}
	
	public GUINode getRootNode() {
		return this.rootNode;
	}
	
}
