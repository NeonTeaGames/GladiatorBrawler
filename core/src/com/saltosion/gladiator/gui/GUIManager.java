/**
 * GladiatorBrawler is a 2D swordfighting game.
 * Copyright (C) 2015 Jeasonfire/Allexit
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.saltosion.gladiator.gui;

import com.saltosion.gladiator.gui.nodes.GUINode;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.systems.RenderingSystem;
import com.saltosion.gladiator.util.AppUtil;

public class GUIManager {

	private final GUINode rootNode;

	public GUIManager() {
		this.rootNode = new GUINode("root").setPosition(-.5f, -.5f);
	}

	public GUINode getRootNode() {
		return this.rootNode;
	}

	public void clearGUI() {
		getRootNode().clearChildren();
	}

	public ArrayList<GUINode> getAllRecursiveChildren(GUINode guiNode) {
		ArrayList<GUINode> list = new ArrayList<GUINode>();
		for (GUINode child : guiNode.getChildren()) {
			list.add(child);
			list.addAll(getAllRecursiveChildren(child));
		}
		return list;
	}

	public GUINode getNode(String id) {
		for (GUINode node : getAllRecursiveChildren(rootNode)) {
			if (node.getID().equals(id)) {
				return node;
			}
		}
		return null;
	}

	public static Vector2 physicsLocationToGUILocation(Vector2 physicslocation) {
		RenderingSystem rs = AppUtil.engine.getSystem(RenderingSystem.class);
		float cameraY = 1 - (rs.getCameraLocation().y / AppUtil.VPHEIGHT_CONST
				+ .5f);
		float cameraX = 1 - (rs.getCameraLocation().x / (AppUtil.VPHEIGHT_CONST * rs.aspectratio)
				+ .5f);
		System.out.println(cameraX + ":" + cameraY);
		float y = physicslocation.y / AppUtil.VPHEIGHT_CONST
				+ cameraY;
		float x = physicslocation.x / (AppUtil.VPHEIGHT_CONST * rs.aspectratio)
				+ cameraX;

		Vector2 v = new Vector2(x, y);

		return v;
	}

}
