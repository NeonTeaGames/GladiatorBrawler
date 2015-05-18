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
package com.saltosion.gladiator.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.saltosion.gladiator.util.Log;
import com.saltosion.gladiator.util.SpriteSequence;

public class CRenderedObject extends Component {

	private final HashMap<String, SpriteSequence> spritesequences = new HashMap<String, SpriteSequence>();
	private final ArrayList<String> channels = new ArrayList<String>();
	private final HashMap<String, String> currentSequences = new HashMap<String, String>();
	private final HashMap<String, Float> currentFrames = new HashMap<String, Float>();

	public CRenderedObject() {
		addChannel("default");
	}

	/**
	 * Can be used if the Rendered Object is a single static image always.
	 *
	 * @param sprite
	 */
	public CRenderedObject(Sprite sprite) {
		spritesequences.put("Idle", new SpriteSequence(sprite));
		addChannel("default");
	}

	public void addSequence(String key, SpriteSequence sequence) {
		spritesequences.put(key, sequence);
	}

	/**
	 * Sets sequence for channelName
	 *
	 * @param channelName
	 * @param sequence
	 */
	public void setCurrentSequence(String channelName, String sequence) {
		this.currentSequences.put(channelName, sequence);
	}

	/**
	 * Sets sequence for "default" channel.
	 *
	 * @param sequence
	 */
	public void setCurrentSequence(String sequence) {
		setCurrentSequence("default", sequence);
	}

	/**
	 * Sets frame for channelName
	 *
	 * @param channelName
	 * @param frame
	 */
	public void setCurrentFrame(String channelName, float frame) {
		this.currentFrames.put(channelName, frame);
	}

	/**
	 * Sets frame for "default" channel.
	 *
	 * @param frame
	 */
	public void setCurrentFrame(float frame) {
		setCurrentFrame("default", frame);
	}

	public SpriteSequence getSequence(String key) {
		return spritesequences.get(key);
	}

	/**
	 * Plays animation on the "default" channel, starting in frame 0
	 *
	 * @param key
	 */
	public void playAnimation(String key) {
		playAnimation(key, 0);
	}

	/**
	 * Plays animation on the "default" channel.
	 *
	 * @param key animation name
	 * @param startingframe
	 */
	public void playAnimation(String key, int startingframe) {
		playAnimation("default", key, startingframe);
	}

	/**
	 * Plays animation on "channelName" starting on frame 0
	 *
	 * @param channelName channel name
	 * @param key animation name
	 */
	public void playAnimation(String channelName, String key) {
		playAnimation(channelName, key, 0);
	}

	/**
	 * Plays animation on channelName
	 *
	 * @param channelName
	 * @param key animation name
	 * @param startingframe
	 */
	public void playAnimation(String channelName, String key, int startingframe) {
		if (getCurrentSequence(channelName).equals(key)) {
			return;
		}

		if (spritesequences.containsKey(key) && channels.contains(channelName)) {
			setCurrentSequence(channelName, key);
			setCurrentFrame(channelName, startingframe);
			return;
		}

		Log.info("Channel: " + channelName);
		Log.info("Key: " + key);

		String[] s = (String[]) spritesequences.keySet().toArray();
		setCurrentSequence(channels.get(0), s[0]);
		setCurrentFrame(channels.get(0), 0f);
	}

	public final void addChannel(String channelName) {
		Set<String> sequences = spritesequences.keySet();
		String sequence = "";
		if (sequences.size() > 0) {
			sequence = (String) sequences.toArray()[0];
		}
		this.channels.add(channelName);
		this.currentSequences.put(channelName, sequence);
		this.currentFrames.put(channelName, 0f);
	}

	/**
	 * Sets channel name
	 *
	 * @param oldName Old name of the channel
	 * @param newName New name of the channel to be set
	 * @return <b>boolean</b>, false if channel with oldName doesn't exist or a
	 * channel with newName already exists.
	 */
	public boolean setChannelName(String oldName, String newName) {
		if (!this.channels.contains(oldName) || this.channels.contains(newName)) {
			return false;
		}
		this.channels.remove(oldName);
		this.channels.add(newName);
		this.currentFrames.put(newName, this.currentFrames.remove(oldName));
		this.currentSequences.put(newName, this.currentSequences.remove(oldName));
		return true;
	}

	public ArrayList<String> getChannels() {
		return channels;
	}

	/**
	 * Returns the current frame on "default" channel.
	 *
	 * @return
	 */
	public float getCurrentFrame() {
		return currentFrames.get("default");
	}

	/**
	 * Returns the frame on "channel".
	 *
	 * @param channel
	 * @return
	 */
	public float getCurrentFrame(String channel) {
		return currentFrames.get(channel);
	}

	public String getCurrentSequence() {
		return currentSequences.get("default");
	}

	/**
	 * Returns the current sequence on "channel"
	 *
	 * @param channel
	 * @return
	 */
	public String getCurrentSequence(String channel) {
		return currentSequences.get(channel);
	}

}
