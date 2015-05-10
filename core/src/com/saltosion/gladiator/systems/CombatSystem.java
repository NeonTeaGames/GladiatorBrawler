package com.saltosion.gladiator.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CDestructive;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.components.CRenderedObject;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Direction;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;

public class CombatSystem extends EntitySystem {

	private ComponentMapper<CCombat> cm = ComponentMapper.getFor(CCombat.class);
	private ComponentMapper<CPhysics> pm = ComponentMapper.getFor(CPhysics.class);
	private ImmutableArray<Entity> entities;
	
	@Override
	public void addedToEngine(Engine engine) {
		updateEntities(engine);
	}
	
	@Override
	public void update(float deltaTime) {
		for (int i=0; i<entities.size(); i++) {
			Entity e = entities.get(i);
			CCombat combat = cm.get(e);
			CPhysics obj = pm.get(e);
			
			if (combat.swingCdCounter > 0) {
				combat.swingCdCounter -= deltaTime;
			} if (combat.swingCdCounter > 0) { return; }
			
			// Ready to swing !
			System.out.println("Ready to swing!");
			
			combat.getSwing().setZero();
			if (combat.inputs.get(Direction.LEFT)) {
				combat.getSwing().add(-1, 0);
			} if (combat.inputs.get(Direction.RIGHT)) {
				combat.getSwing().add(1, 0);
			} if (combat.inputs.get(Direction.UP)) {
				combat.getSwing().add(0, 1);
			} if (combat.inputs.get(Direction.DOWN)) {
				combat.getSwing().add(0, -1);
			}
			
			if (!combat.getSwing().isZero() && combat.swingCdCounter <= 0) {
				Vector2 pos = obj.getPosition().cpy();

				if (combat.getSwingDirection() == Direction.LEFT) {
					pos.add(-2, 0);
				} else if (combat.getSwingDirection() == Direction.RIGHT) {
					pos.add(2, 0);
				} else if (combat.getSwingDirection() == Direction.UP) {
					pos.add(0, 2);
				} else if (combat.getSwingDirection() == Direction.DOWN) {
					pos.add(0, -2);	
				}
				createSwingHitbox(e, pos);
				
				System.out.println("Swing to " + combat.getSwingDirection().toString());
				combat.swingCdCounter = combat.getSwingCD();
			}
		}
	}
	
	public void createSwingHitbox(Entity source, Vector2 position) {
		Entity e = new Entity();
		CCombat combat = cm.get(source);
		Sprite s = SpriteLoader.loadSprite(Name.WALLIMG);
		e.add(new CRenderedObject(s));
		e.add(new CPhysics().setGhost(true).setGravityApplied(false).setMovable(false)
				.setSize(combat.getSwingSize()));
		e.getComponent(CPhysics.class).setPosition(position);
		e.add(new CDestructive(.1f));
		AppUtil.engine.addEntity(e);
		
	}
	
	public void updateEntities(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(CCombat.class));
	}

}
