package com.saltosion.gladiator.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CParticle;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.components.CRenderedObject;
import com.saltosion.gladiator.gui.nodes.GUINode;
import com.saltosion.gladiator.gui.properties.ImageProperty;
import com.saltosion.gladiator.gui.nodes.TextNode;
import com.saltosion.gladiator.gui.properties.TextProperty;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Global;
import com.saltosion.gladiator.util.Log;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;
import com.saltosion.gladiator.util.SpriteSequence;
import java.util.ArrayList;
import java.util.List;

public class RenderingSystem extends EntitySystem {

	private final ComponentMapper<CRenderedObject> rom = ComponentMapper.getFor(CRenderedObject.class);
	private final ComponentMapper<CPhysics> pm = ComponentMapper.getFor(CPhysics.class);
	private final ComponentMapper<CCombat> cm = ComponentMapper.getFor(CCombat.class);
	private final ComponentMapper<CParticle> pam = ComponentMapper.getFor(CParticle.class);
	private ImmutableArray<Entity> entities;

	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer debugRenderer, particleRenderer;
	private OrthographicCamera camera, fontCamera;

	public float aspectratio;
	public int screenHeight = 0;
	public int screenWidth = 0;

	private boolean debug = false;
	private final Color debugColor = new Color(0, 1, 0, 1);

	private float deltaDelay = 0;
	private double deltaAvgSum;
	private long deltaAvgTimes;
	private String deltaString = "0";

	private List<TextObject> drawableText;

	private Sprite[] healthbar;
	private boolean healthbarLoaded = false;
	private float xMin = -15, xMax = 15;

	@Override
	public void addedToEngine(Engine engine) {
		updateEntities(engine);

		batch = new SpriteBatch();

		font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
		font.setUseIntegerPositions(false);

		debugRenderer = new ShapeRenderer();
		particleRenderer = new ShapeRenderer();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1, 1);

		fontCamera = new OrthographicCamera();
		fontCamera.setToOrtho(false, Global.FONT_SCALE, Global.FONT_SCALE);

		drawableText = new ArrayList<TextObject>();
	}

	public void setViewport(int width, int height) {
		camera.setToOrtho(false, width, height);
		fontCamera.setToOrtho(false, width * Global.FONT_SCALE, height * Global.FONT_SCALE);
	}

	@Override
	public void update(float deltaTime) {
		camera.update();
		fontCamera.update();

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		updateEntityAnimations();
		renderEntities(deltaTime);
		renderParticles();
		renderDebug();
		renderGUI(new Vector2(0, 0));

		if (debug) {
			drawString("FPS: " + Gdx.graphics.getFramesPerSecond(), new Vector2(camera.position.x - 12, camera.position.y + 8));
			drawString("Delta (ms): " + getDeltaWithDelay(deltaTime, 0.1f), new Vector2(camera.position.x - 12, camera.position.y + 7));
		}
		renderFont(fontCamera);
	}

	/**
	 * A debugging function for easier performance logging.
	 *
	 * @param deltaTime The delta of the current frame
	 * @param delay The delay between deltaString updates
	 * @return A string that has the delta formatted
	 */
	private String getDeltaWithDelay(float deltaTime, float delay) {
		this.deltaDelay += deltaTime;
		this.deltaAvgSum += Gdx.graphics.getDeltaTime() * 1000;
		this.deltaAvgTimes++;
		if (this.deltaDelay >= delay) {
			this.deltaDelay = 0;
			this.deltaString = String.format("%.2f", this.deltaAvgSum / this.deltaAvgTimes);
			this.deltaAvgSum = 0;
			this.deltaAvgTimes = 0;
		}
		return deltaString;
	}

	private void updateEntityAnimations() {
		for (int i = 0; i < entities.size(); i++) {
			updateEntityAnimation(entities.get(i));
		}
	}

	private void updateEntityAnimation(Entity entity) {
		CRenderedObject ro = rom.get(entity);
		CPhysics po = pm.get(entity);
		CCombat co = cm.get(entity);
		if (ro == null || po == null || co == null) {
			return;
		}

		boolean moving = false, combat = false;
		String dirMove = po.movedLeftLast ? "Left" : "Right";
		String dirSwing = "";

		if (po.movingLeft || po.movingRight) {
			moving = true;
		}
		if (co.swingCdCounter > 0) {
			combat = true;
			switch (co.getSwingDirection()) {
				default:
				case RIGHT:
					dirSwing += "Right";
					break;
				case LEFT:
					dirSwing += "Left";
					break;
				case UP:
					if (dirMove.equals("Left")) {
						dirSwing = "Left-";
					} else {
						dirSwing = "Right-";
					}
					dirSwing += "Up";
					break;
				case DOWN:
					if (dirMove.equals("Left")) {
						dirSwing = "Left-";
					} else {
						dirSwing = "Right-";
					}
					dirSwing += "Down";
					break;
			}
		}

		// Play animations
		if (moving && combat) {
			ro.playAnimation("torso", "Torso-Combat-" + dirSwing);
			ro.playAnimation("legs", "Legs-Run-" + dirMove);
		} else if (combat) {
			ro.playAnimation("torso", "Torso-Combat-" + dirSwing);
			ro.playAnimation("legs", "Legs-Idle-" + dirMove);
		} else if (moving) {
			ro.playAnimation("torso", "Torso-Run-" + dirMove);
			ro.playAnimation("legs", "Legs-Run-" + dirMove);
		} else {
			ro.playAnimation("torso", "Torso-Idle-" + dirMove);
			ro.playAnimation("legs", "Legs-Idle-" + dirMove);
		}
	}

	private void renderEntities(float deltaTime) {
		if (AppUtil.player == null) {
			return;
		}
		CPhysics playerPhys = pm.get(AppUtil.player);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int i = 0; i < entities.size(); i++) {
			CPhysics physics = pm.get(entities.get(i));
			CRenderedObject renderedObject = rom.get(entities.get(i));
			if (renderedObject == null) {
				continue;
			}
			// Draw entity
			for (String channel : renderedObject.getChannels()) {
				SpriteSequence currSequence = renderedObject.getSequence(renderedObject.getCurrentSequence(channel));
				int currFrame = (int) Math.floor(renderedObject.getCurrentFrame(channel));
				Sprite currSprite = currSequence.getSprite(currFrame);

				int spriteHeight = currSprite.getRegionHeight();
				int spriteWidth = currSprite.getRegionWidth();

				currSprite.setPosition(((physics.getPosition().x - spriteWidth / 2) + getCameraOffset(playerPhys, physics).x),
						(physics.getPosition().y - spriteHeight / 2) + getCameraOffset(playerPhys, physics).y);
				currSprite.draw(batch);

				float nextFrame = renderedObject.getCurrentFrame(channel) + deltaTime * currSequence.getPlayspeed();
				renderedObject.setCurrentFrame(channel, nextFrame % currSequence.frameCount());
			}

			// Draw healthbars
			CCombat combat = cm.get(entities.get(i));
			if (combat != null) {
				if (!healthbarLoaded) {
					loadHealthbarSprites();
				}
				float spriteWidth = healthbar[0].getWidth();
				float spriteHeight = healthbar[0].getHeight();
				float hp = (float) combat.getHealth() / (float) combat.getMaxHealth();

				healthbar[0].setPosition(((physics.getPosition().x - spriteWidth / 2) + getCameraOffset(playerPhys, physics).x),
						(physics.getPosition().y - spriteHeight / 2 + 2.5f) + getCameraOffset(playerPhys, physics).y);
				healthbar[0].draw(batch);
				healthbar[1].setPosition(((physics.getPosition().x - spriteWidth / 2) + getCameraOffset(playerPhys, physics).x),
						(physics.getPosition().y - spriteHeight / 2 + 2.5f) + getCameraOffset(playerPhys, physics).y);
				healthbar[1].setSize(spriteWidth * hp, spriteHeight);
				healthbar[1].draw(batch);
				healthbar[2].setPosition(((physics.getPosition().x - spriteWidth / 2) + getCameraOffset(playerPhys, physics).x),
						(physics.getPosition().y - spriteHeight / 2 + 2.5f) + getCameraOffset(playerPhys, physics).y);
				healthbar[2].draw(batch);
			}
		}
		batch.end();
	}

	private void renderGUI(Vector2 rootPosition) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		renderGUINode(AppUtil.guiManager.getRootNode(), rootPosition);
		batch.end();
	}

	private void renderGUINode(GUINode node, Vector2 position) {
		if (!node.isVisible()) {
			return;
		}
		position.add(node.getPosition());
		if (node instanceof ImageProperty) {
			Sprite s = ((ImageProperty) node).getImage();
			s.setPosition(position.x * AppUtil.VPHEIGHT_CONST * aspectratio - s.getWidth() / 2 + camera.position.x,
					position.y * AppUtil.VPHEIGHT_CONST - s.getHeight() / 2 + camera.position.y);
			s.draw(batch);
		}
		if (node instanceof TextNode) {
			drawString(((TextProperty) node).getText(), new Vector2(position.x * AppUtil.VPHEIGHT_CONST * aspectratio + camera.position.x,
					position.y * AppUtil.VPHEIGHT_CONST + camera.position.y));
		}
		for (GUINode child : node.getChildren()) {
			renderGUINode(child, new Vector2(position));
		}
	}

	private void renderParticles() {
		if (AppUtil.player == null) {
			return;
		}
		CPhysics playerPhys = pm.get(AppUtil.player);
		particleRenderer.setProjectionMatrix(camera.combined);
		particleRenderer.begin(ShapeType.Filled);
		for (int i = 0; i < entities.size(); i++) {
			CParticle particle = pam.get(entities.get(i));
			if (particle == null) {
				continue;
			}

			particleRenderer.setColor(particle.getColor());
			particleRenderer.rect(particle.getPosition().x - particle.getSize().x / 2 + getCameraOffset(playerPhys).x,
					particle.getPosition().y - particle.getSize().y / 2 + getCameraOffset(playerPhys).y,
					particle.getSize().x, particle.getSize().y);
		}
		particleRenderer.end();
	}

	private void renderDebug() {
		if (debug) {
			if (AppUtil.player == null) {
				return;
			}
			CPhysics playerPhys = pm.get(AppUtil.player);
			debugRenderer.setProjectionMatrix(camera.combined);
			debugRenderer.begin(ShapeType.Line);
			for (int i = 0; i < entities.size(); i++) {
				CPhysics physics = pm.get(entities.get(i));
				if (physics == null) {
					continue;
				}

				float x0 = physics.getPosition().x - physics.getSize().x / 2 + getCameraOffset(playerPhys, physics).x;
				float x1 = physics.getPosition().x + physics.getSize().x / 2 + getCameraOffset(playerPhys, physics).x;
				float y0 = physics.getPosition().y - physics.getSize().y / 2 + getCameraOffset(playerPhys, physics).y;
				float y1 = physics.getPosition().y + physics.getSize().y / 2 + getCameraOffset(playerPhys, physics).y;

				debugRenderer.setColor(debugColor);
				debugRenderer.line(x0, y0, x1, y0);
				debugRenderer.line(x1, y0, x1, y1);
				debugRenderer.line(x1, y1, x0, y1);
				debugRenderer.line(x0, y1, x0, y0);
			}
			debugRenderer.end();
		}
	}

	/**
	 * This is the main method that actually _renders_ the text. Use
	 * "drawString(str, pos)" method to add a string to a list that will be
	 * rendered here.
	 *
	 * @param camera
	 */
	private void renderFont(Camera fontCamera) {
		batch.setProjectionMatrix(fontCamera.combined);
		batch.begin();
		for (TextObject obj : drawableText) {
			font.draw(batch, obj.text, obj.position.x * Global.FONT_SCALE, obj.position.y * Global.FONT_SCALE);
		}
		drawableText.clear();
		batch.end();
	}

	public void drawString(String text, Vector2 position) {
		drawableText.add(new TextObject(text, position));
	}

	public void updateEntities(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(ComponentType.getBitsFor(),
				ComponentType.getBitsFor(CPhysics.class, CParticle.class), ComponentType.getBitsFor()));
	}

	public void loadHealthbarSprites() {
		healthbarLoaded = true;
		healthbar = new Sprite[3];
		healthbar[0] = SpriteLoader.loadSprite(Name.HEALTHBARIMG, 0, 0, 32, 8);
		healthbar[1] = SpriteLoader.loadSprite(Name.HEALTHBARIMG, 0, 1, 32, 8);
		healthbar[2] = SpriteLoader.loadSprite(Name.HEALTHBARIMG, 0, 2, 32, 8);
	}

	public boolean getDebug() {
		return this.debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public Vector2 getCameraLocation() {
		return new Vector2(this.camera.position.x, this.camera.position.y);
	}

	private Vector2 getCameraOffset(CPhysics playerPhys) {
		Vector2 offset = new Vector2(Math.max(xMin + camera.viewportWidth / 2, Math.min(xMax - camera.viewportWidth / 2,
				-playerPhys.getPosition().x)) + camera.viewportWidth / 2,
				-playerPhys.getPosition().y + camera.viewportHeight / 3);
		return offset;
	}

	private Vector2 getCameraOffset(CPhysics playerPhys, CPhysics currPhys) {
		Vector2 offset = new Vector2(Math.max(xMin + camera.viewportWidth / 2, Math.min(xMax - camera.viewportWidth / 2,
				-playerPhys.getPosition().x)) / currPhys.getZParallax() + camera.viewportWidth / 2,
				-playerPhys.getPosition().y / currPhys.getZParallax() + camera.viewportHeight / 3);
		return offset;
	}

	public float getXMin() {
		return xMin;
	}

	public RenderingSystem setXMin(float xMin) {
		this.xMin = xMin;
		return this;
	}

	public float getXMax() {
		return xMax;
	}

	public RenderingSystem setXMax(float xMax) {
		this.xMax = xMax;
		return this;
	}

	public void dispose() {
		batch.dispose();
		debugRenderer.dispose();
		particleRenderer.dispose();
		font.dispose();
		SpriteLoader.dispose();
	}

	private class TextObject {

		public String text;
		public Vector2 position;

		public TextObject(String text, Vector2 position) {
			this.text = text;
			this.position = position;
		}
	}

}
