package com.saltosion.gladiator.input;

public interface InputReceiver {

	/**
	 * @return Returns if the keypress was handled.
	 */
	public boolean pressed();

	/**
	 * @return Returns if the key's release was handled.
	 */
	public boolean released();

}
