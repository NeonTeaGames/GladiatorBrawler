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
package com.saltosion.gladiator.gui.properties;

public interface InteractiveProperty {

	/**
	 * Called when the mouse enters the node's elements.
	 *
	 * @param x position of the mouse.
	 * @param y position of the mouse.
	 */
	public void mouseEnter(float x, float y);

	/**
	 * Called when the mouse leaves the node's elements.
	 *
	 * @param x position of the mouse.
	 * @param y position of the mouse.
	 */
	public void mouseLeave(float x, float y);

	/**
	 * Called when someone presses a mouse button on the node's elements.
	 *
	 * @param x position of the mouse.
	 * @param y position of the mouse.
	 * @param mouseButton button pressed.
	 */
	public void pressed(int x, int y, int mouseButton);

	/**
	 *
	 * Called when someone releases a mouse button on the node's elements.
	 *
	 * @param x position of the mouse.
	 * @param y position of the mouse.
	 * @param mouseButton button pressed.
	 */
	public void released(int x, int y, int mouseButton);
}
