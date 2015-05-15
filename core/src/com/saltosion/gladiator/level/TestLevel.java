package com.saltosion.gladiator.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.components.CRenderedObject;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Global;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;

public class TestLevel implements Level {

	@Override
	public void generate() {
		// Generate entities
		AppUtil.entityFactory.createPlayer(new Vector2(0, 5));
		AppUtil.entityFactory.createDummy(new Vector2(-6, 5));

		// Generate level
		Entity ground = new Entity();

		Sprite groundSprite = SpriteLoader.loadSprite(Name.GROUNDIMG);
		CRenderedObject renderedObject = new CRenderedObject(groundSprite);
		ground.add(renderedObject);
		CPhysics physics = new CPhysics().setMovable(false).setGravityApplied(false).setProcessCollisions(false)
				.setSize(groundSprite.getRegionWidth() * Global.SPRITE_SCALE,
						groundSprite.getRegionHeight() * Global.SPRITE_SCALE);
		physics.getPosition().set(new Vector2(0, -4));
		ground.add(physics);

		Sprite wallSprite = SpriteLoader.loadSprite(Name.WALLIMG);

		Entity wall0 = new Entity();
		CRenderedObject wall0RenderedObject = new CRenderedObject(wallSprite);
		CPhysics wall0Physics = new CPhysics().setMovable(false).setGravityApplied(false).setProcessCollisions(false)
				.setSize(wallSprite.getRegionWidth() * Global.SPRITE_SCALE,
						wallSprite.getRegionHeight() * Global.SPRITE_SCALE);
		wall0Physics.getPosition().set(new Vector2(6, 0));
		wall0.add(wall0RenderedObject);
		wall0.add(wall0Physics);

		Entity wall1 = new Entity();
		CRenderedObject wall1RenderedObject = new CRenderedObject(wallSprite);
		CPhysics wall1Physics = new CPhysics().setMovable(false).setGravityApplied(false).setProcessCollisions(false)
				.setSize(wallSprite.getRegionWidth() * Global.SPRITE_SCALE,
						wallSprite.getRegionHeight() * Global.SPRITE_SCALE);
		wall1Physics.getPosition().set(new Vector2(-6, 0));
		wall1.add(wall1RenderedObject);
		wall1.add(wall1Physics);

		AppUtil.engine.addEntity(ground);
		AppUtil.engine.addEntity(wall0);
		AppUtil.engine.addEntity(wall1);
	}

}
