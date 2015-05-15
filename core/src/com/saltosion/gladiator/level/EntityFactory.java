package com.saltosion.gladiator.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.components.CAI;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.components.CRenderedObject;
import com.saltosion.gladiator.listeners.CombatListener;
import com.saltosion.gladiator.listeners.ai.DummyAI;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Direction;
import com.saltosion.gladiator.util.Log;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;
import com.saltosion.gladiator.util.SpriteSequence;

public class EntityFactory {

	public void createPlayer(Vector2 pos) {
		Entity player = new Entity();

		CRenderedObject renderedObject = new CRenderedObject();
		Sprite playertorso1 = SpriteLoader.loadSprite(Name.PLAYERIMG, 0, 0, 128, 112);
		Sprite playertorso2 = SpriteLoader.loadSprite(Name.PLAYERIMG, 0, 1, 128, 112);
		Sprite playerlegs1 = SpriteLoader.loadSprite(Name.PLAYERIMG, 1, 0, 128, 112);
		Sprite playerlegs2 = SpriteLoader.loadSprite(Name.PLAYERIMG, 1, 1, 128, 112);
		SpriteSequence torsosequence = new SpriteSequence(1).addSprite(playertorso1).addSprite(playertorso2);
		SpriteSequence legsequence = new SpriteSequence(1).addSprite(playerlegs1).addSprite(playerlegs2);
		renderedObject.setChannelName("default", "torso");
		renderedObject.addChannel("legs");
		renderedObject.addSequence("Torso-Idle", torsosequence);
		renderedObject.addSequence("Legs-Idle", legsequence);
		renderedObject.playAnimation("torso", "Torso-Idle");
		renderedObject.playAnimation("legs", "Legs-Idle");
		player.add(renderedObject);
		player.add(new CPhysics().setSize(2, 4).setPosition(pos.x, pos.y));
		player.add(new CCombat().setBaseDamage(100).setHealth(1000));
		AppUtil.engine.addEntity(player);

		Log.info("Created player!");
		AppUtil.player = player;
	}

	public void createDummy(Vector2 pos) {
		Entity dummy = new Entity();
		CRenderedObject renderedObject = new CRenderedObject();
		Sprite playertorso1 = SpriteLoader.loadSprite(Name.PLAYERIMG, 0, 0, 128, 112);
		Sprite playertorso2 = SpriteLoader.loadSprite(Name.PLAYERIMG, 0, 1, 128, 112);
		Sprite playerlegs1 = SpriteLoader.loadSprite(Name.PLAYERIMG, 1, 0, 128, 112);
		Sprite playerlegs2 = SpriteLoader.loadSprite(Name.PLAYERIMG, 1, 1, 128, 112);
		SpriteSequence torsosequence = new SpriteSequence(1).addSprite(playertorso1).addSprite(playertorso2);
		SpriteSequence legsequence = new SpriteSequence(1).addSprite(playerlegs1).addSprite(playerlegs2);
		renderedObject.setChannelName("default", "torso");
		renderedObject.addChannel("legs");
		renderedObject.addSequence("Torso-Idle", torsosequence);
		renderedObject.addSequence("Legs-Idle", legsequence);
		renderedObject.playAnimation("torso", "Torso-Idle");
		renderedObject.playAnimation("legs", "Legs-Idle");
		dummy.add(renderedObject);
		dummy.add(new CPhysics().setSize(2, 4).setPosition(pos.x, pos.y));
		dummy.add(new CCombat().setBaseDamage(100).setHealth(1000).setSwingCD(.5f).setCombatListener(
				new CombatListener() {
					@Override
					public void died(Entity source, int damageTaken) {
						System.out.println("Nooooo! I died! I will revenge this!");
					}

					@Override
					public void damageTaken(Entity source, int damageTaken) {
						System.out.println(String.format("I took %d damage! Damnit!", damageTaken));
					}

				}));
		dummy.add(new CAI().setReactDistance(5).setAIListener(new DummyAI()));
		AppUtil.engine.addEntity(dummy);
		dummy.getComponent(CCombat.class).inputs.put(Direction.UP, true);
	}

}
