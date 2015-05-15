package com.saltosion.gladiator.util;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteLoader {

	public static HashMap<String, Texture> textures = new HashMap<String, Texture>();

	static {
		loadTexture(Name.STATICPLAYER, "sprites/staticplayer.png");
		loadTexture(Name.PLAYERIMG, "sprites/player/player.png");
		loadTexture(Name.GROUNDIMG, "sprites/ground.png");
		loadTexture(Name.WALLIMG, "sprites/wall.png");
		loadTexture(Name.SWINGHITBOXIMG, "sprites/swinghitbox.png");
	}

	/**
	 * Returns a sprite, which is a piece from texture.
	 *
	 * @param texKey
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public static Sprite loadSprite(String texKey, int x, int y, int width, int height) {
		TextureRegion tr = new TextureRegion(textures.get(texKey), x * width, y * height, width, height);
		Sprite s = new Sprite(tr);
		s.setScale(Global.SPRITE_SCALE);
		return s;
	}

	/**
	 * Returns a sprite, which is originally a texture.
	 *
	 * @param texKey
	 * @return
	 */
	public static Sprite loadSprite(String texKey) {
		return loadSprite(texKey, 0, 0, textures.get(texKey).getWidth(), textures.get(texKey).getHeight());
	}

	/**
	 * Load texture from path.
	 *
	 * @param filePath
	 */
	public static Texture loadTexture(String key, String filePath) {
		Texture t = new Texture(Gdx.files.internal(filePath));
		textures.put(key, t);
		return t;
	}

	/**
	 * Disposes all the textures loaded so far.
	 */
	public static void dispose() {
		Log.info("Disposed textures!");
		for (Texture tex : textures.values()) {
			tex.dispose();
		}
	}

}
