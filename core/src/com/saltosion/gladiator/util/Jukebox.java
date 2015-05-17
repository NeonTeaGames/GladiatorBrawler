
package com.saltosion.gladiator.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Jukebox {
	
	private Music currentMusic;
	private float volume = 1;
	
	/**
	 * Plays the musix like a baws.
	 * @param music
	 * @return boolean if the musix failed to play
	 */
	public boolean playMusic(Music music) {
		if (music == null) {
			currentMusic.stop();
			currentMusic = null;
		}
		if (music.equals(currentMusic)) {
			return false;
		} if (currentMusic != null) { currentMusic.stop(); };
		music.play();
		music.setVolume(volume);
		music.setLooping(true);
		currentMusic = music;
		return true;
	}
	
	public Music getCurrentMusic() {
		return this.currentMusic;
	}
	
	public void setMusicVolume(float volume) {
		this.volume = volume;
		if (currentMusic == null) {return;}
		this.currentMusic.setVolume(volume);
	}
	
	/**
	 * Simply plays the sound
	 * @param sound
	 * @return long returns sound's id
	 */
	public long playSound(Sound sound) {
		return sound.play();
	}
	
	/**
	 * Play sound and set it's volume
	 * @param sound
	 * @param volume 0-1f
	 * @return long returns sound's id
	 */
	public long playSound(Sound sound, float volume) {
		long id = playSound(sound);
		sound.setVolume(id, volume);
		return id;
	}
	
	/**
	 * Play sound and set it's volume & pan
	 * @param sound
	 * @param volume 0-1f
	 * @param pan ?
	 * @return long returns sound's id
	 */
	public long playSound(Sound sound, float volume, float pan) {
		long id = playSound(sound);
		sound.setPan(id, pan, volume);
		return id;
	}
}
