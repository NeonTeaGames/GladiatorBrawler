package com.saltosion.gladiator.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.components.CRenderedObject;
import com.saltosion.gladiator.systems.RenderingSystem;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Global;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;
import com.saltosion.gladiator.util.SpriteSequence;

public class TestLevel implements Level {

	@Override
	public void generate() {
		// Audience
		Entity audience = new Entity();
		Sprite audienceSprite0 = SpriteLoader.loadSprite(Name.AUDIENCEIMG, 0, 0, 768, 576);
		Sprite audienceSprite1 = SpriteLoader.loadSprite(Name.AUDIENCEIMG, 1, 0, 768, 576);
		CRenderedObject audienceRO = new CRenderedObject();
		SpriteSequence audienceAnim = new SpriteSequence(1).addSprite(audienceSprite0).addSprite(audienceSprite1);
		audienceRO.addSequence("Default-Animation", audienceAnim);
		audienceRO.playAnimation("Default-Animation");
		audience.add(audienceRO);
		CPhysics audiencePO = new CPhysics().setMovable(false).setGravityApplied(false)
				.setProcessCollisions(false).setGhost(true).setPosition(0, 10).setZParallax(9)
				.setSize(audienceSprite0.getRegionWidth() * Global.SPRITE_SCALE,
						audienceSprite0.getRegionHeight() * Global.SPRITE_SCALE);
		audience.add(audiencePO);
		AppUtil.engine.addEntity(audience);

		// Wall
		Entity wall = new Entity();
		Sprite wallSprite = SpriteLoader.loadSprite(Name.WALLIMG);
		CRenderedObject wallRO = new CRenderedObject(wallSprite);
		wall.add(wallRO);
		CPhysics wallPO = new CPhysics().setMovable(false).setGravityApplied(false)
				.setProcessCollisions(false).setGhost(true).setPosition(0, 2).setZParallax(1.5f)
				.setSize(wallSprite.getRegionWidth() * Global.SPRITE_SCALE,
						wallSprite.getRegionHeight() * Global.SPRITE_SCALE);
		wall.add(wallPO);
		AppUtil.engine.addEntity(wall);

		// Ground
		Entity ground = new Entity();
		Sprite groundSprite = SpriteLoader.loadSprite(Name.GROUNDIMG);
		CRenderedObject groundRO = new CRenderedObject(groundSprite);
		ground.add(groundRO);
		CPhysics groundPO = new CPhysics().setMovable(false).setGravityApplied(false).setProcessCollisions(false)
				.setSize(groundSprite.getRegionWidth() * Global.SPRITE_SCALE,
						groundSprite.getRegionHeight() * Global.SPRITE_SCALE);
		groundPO.getPosition().set(new Vector2(0, -4));
		ground.add(groundPO);
		AppUtil.engine.addEntity(ground);

		// Level borders
		float xClamp = groundPO.getSize().x / 2f;
		AppUtil.engine.getSystem(RenderingSystem.class).setXMin(-xClamp).setXMax(xClamp);

		Entity borderLeft = new Entity();
		CPhysics borderLeftPhysics = new CPhysics().setMovable(false).setGravityApplied(false)
				.setProcessCollisions(false).setSize(0.1f, 20);
		borderLeftPhysics.setPosition(-xClamp - borderLeftPhysics.getSize().x, 0);
		borderLeft.add(borderLeftPhysics);
		AppUtil.engine.addEntity(borderLeft);
		Entity borderRight = new Entity();
		CPhysics borderRightPhysics = new CPhysics().setMovable(false).setGravityApplied(false)
				.setProcessCollisions(false).setSize(0.1f, 20);
		borderRightPhysics.setPosition(xClamp + borderRightPhysics.getSize().x, 0);
		borderRight.add(borderRightPhysics);
		AppUtil.engine.addEntity(borderRight);

		// Generate entities
		AppUtil.entityFactory.createPlayer(new Vector2(0, 5));
		AppUtil.entityFactory.createDummy(new Vector2(-6, 5));
	}

}
