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

		AppUtil.engine.addEntity(ground);
	}

}
