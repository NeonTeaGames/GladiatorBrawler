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
