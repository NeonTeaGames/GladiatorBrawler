package com.saltosion.gladiator.gui;

import java.util.ArrayList;

public class GUIManager {
	private final GUINode rootNode;

	public GUIManager() {
		this.rootNode = new GUINode("root").setPosition(-.5f, -.5f);
	}
	
	public GUINode getRootNode() {
		return this.rootNode;
	}
	
	public ArrayList<GUINode> getAllRecursiveChildren(GUINode guiNode) {
		ArrayList<GUINode> list = new ArrayList<GUINode>();
		for (GUINode child : guiNode.getChildren()) {
			list.add(child);
			list.addAll(getAllRecursiveChildren(child));
		}
		return list;
	}
	
}
