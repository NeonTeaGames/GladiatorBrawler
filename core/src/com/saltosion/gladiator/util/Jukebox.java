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
package com.saltosion.gladiator.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Jukebox {

	private Music currentMusic;
	private float volume = 1;

	/**
	 * Plays the musix like a baws.
	 *
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
		}
		if (currentMusic != null) {
			currentMusic.stop();
		};
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
		if (currentMusic == null) {
			return;
		}
		this.currentMusic.setVolume(volume);
	}

	/**
	 * Simply plays the sound
	 *
	 * @param sound
	 * @return long returns sound's id
	 */
	public long playSound(Sound sound) {
		return sound.play();
	}

	/**
	 * Play sound and set it's volume
	 *
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
	 *
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

	public Sound returnRandomSound(Sound... args) {
		return args[(int) Math.floor(Math.random() * args.length)];
	}
}
