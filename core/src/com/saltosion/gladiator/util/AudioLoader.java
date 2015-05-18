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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;

public class AudioLoader {

	private static final HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	private static final HashMap<String, Music> musics = new HashMap<String, Music>();

	static {
		// Import all the sounds & musix
		AudioLoader.musics.put(Name.MUSIC_THEME, loadMusic("audio/theme.ogg"));
		AudioLoader.musics.put(Name.MUSIC_BATTLE, loadMusic("audio/battle_music.ogg"));
		AudioLoader.sounds.put(Name.SOUND_SWING01, loadSound("audio/swing01.ogg"));
		AudioLoader.sounds.put(Name.SOUND_SWING02, loadSound("audio/swing02.ogg"));
		AudioLoader.sounds.put(Name.SOUND_SWING03, loadSound("audio/swing03.ogg"));
		AudioLoader.sounds.put(Name.SOUND_HIT01, loadSound("audio/hit01.ogg"));
		AudioLoader.sounds.put(Name.SOUND_HIT02, loadSound("audio/hit02.ogg"));
		AudioLoader.sounds.put(Name.SOUND_HIT03, loadSound("audio/hit03.ogg"));
		AudioLoader.sounds.put(Name.SOUND_HIT04, loadSound("audio/hit04.ogg"));
		AudioLoader.sounds.put(Name.SOUND_HIT05, loadSound("audio/hit05.ogg"));
		AudioLoader.sounds.put(Name.SOUND_CLANG01, loadSound("audio/clang01.ogg"));
		AudioLoader.sounds.put(Name.SOUND_CLANG02, loadSound("audio/clang02.ogg"));
		AudioLoader.sounds.put(Name.SOUND_CLANG03, loadSound("audio/clang03.ogg"));
		AudioLoader.sounds.put(Name.SOUND_CLANG04, loadSound("audio/clang04.ogg"));
		AudioLoader.sounds.put(Name.SOUND_STEP, loadSound("audio/step.ogg"));
		AudioLoader.sounds.put(Name.SOUND_BUTTON_PRESS, loadSound("audio/button_press.ogg"));
		AudioLoader.sounds.put(Name.SOUND_BUTTON_RELEASE, loadSound("audio/button_release.ogg"));
	}

	private static Music loadMusic(String path) {
		return Gdx.audio.newMusic(Gdx.files.internal(path));
	}

	private static Sound loadSound(String path) {
		return Gdx.audio.newSound(Gdx.files.internal(path));
	}

	public static Sound getSound(String id) {
		return sounds.get(id);
	}

	public static Music getMusic(String id) {
		return musics.get(id);
	}

	public static void dispose() {
		Log.info("Disposed sounds and musics!");
		for (String s : sounds.keySet()) {
			sounds.get(s).dispose();
		}
		for (String s : musics.keySet()) {
			musics.get(s).dispose();
		}
	}

}
