package com.saltosion.gladiator.util;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteLoader {

	private static final HashMap<String, Texture> textures = new HashMap<String, Texture>();
	public static boolean loadedAllSprites = false;

	public static void preload() {
		loadTexture(Name.SPLASH_SALTOSION, "splashscreens/saltosion.png");
		loadTexture(Name.SPLASH_LIBGDX, "splashscreens/libgdx.png");
	}

	public static void loadAll() {
		loadTexture(Name.STATICPLAYER, "sprites/staticplayer.png");
		loadTexture(Name.PLAYERIMG, "sprites/player/player.png");
		loadTexture(Name.GROUNDIMG, "sprites/ground.png");
		loadTexture(Name.WALLIMG, "sprites/wall.png");
		loadTexture(Name.AUDIENCEIMG, "sprites/audience.png");
		loadTexture(Name.HEALTHBARIMG, "sprites/player/healthbar.png");

		loadTexture(Name.BUTTON_HUGE, "sprites/buttons/button_huge.png");
		loadTexture(Name.BUTTON_HUGE_HOVER, "sprites/buttons/button_huge_hover.png");
		loadTexture(Name.BUTTON_BIG, "sprites/buttons/button_big.png");
		loadTexture(Name.BUTTON_BIG_HOVER, "sprites/buttons/button_big_hover.png");
		loadTexture(Name.BUTTON_SMALL, "sprites/buttons/button_small.png");
		loadTexture(Name.BUTTON_SMALL_HOVER, "sprites/buttons/button_small_hover.png");

		loadTexture(Name.TITLE_LOGO, "sprites/title_logo.png");
		loadTexture(Name.MENU_BACKGROUND, "sprites/menu_background.png");
		loadTexture(Name.GPLV3_LOGO, "sprites/gplv3_logo.png");
		loadTexture(Name.OSI_LOGO, "sprites/osi_logo.png");
		loadedAllSprites = true;
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
	 * @param key
	 * @param filePath
	 * @return
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
