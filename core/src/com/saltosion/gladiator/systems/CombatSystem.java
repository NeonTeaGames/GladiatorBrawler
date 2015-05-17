package com.saltosion.gladiator.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CDestructive;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.listeners.CombatListener;
import com.saltosion.gladiator.listeners.SwingHitboxListener;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.AudioLoader;
import com.saltosion.gladiator.util.Direction;
import com.saltosion.gladiator.util.Name;

public class CombatSystem extends EntitySystem {

	private final ComponentMapper<CCombat> cm = ComponentMapper.getFor(CCombat.class);
	private final ComponentMapper<CPhysics> pm = ComponentMapper.getFor(CPhysics.class);
	private ImmutableArray<Entity> entities;

	@Override
	public void addedToEngine(Engine engine) {
		updateEntities(engine);
	}

	@Override
	public void update(float deltaTime) {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			CCombat combat = cm.get(e);
			CPhysics obj = pm.get(e);

			if (combat.swingCdCounter > 0) {
				combat.swingCdCounter -= deltaTime;
			}
			if (combat.swingCdCounter > 0) {
				continue;
			}

			// Ready to swing !
			combat.getSwing().setZero();
			if (combat.inputs.get(Direction.LEFT)) {
				combat.getSwing().add(-1, 0);
			}
			if (combat.inputs.get(Direction.RIGHT)) {
				combat.getSwing().add(1, 0);
			}
			if (combat.inputs.get(Direction.UP)) {
				combat.getSwing().add(0, 1);
			}
			if (combat.inputs.get(Direction.DOWN)) {
				combat.getSwing().add(0, -1);
			}

			if (!combat.getSwing().isZero() && combat.swingCdCounter <= 0) {
				
				// Swinging
				Vector2 pos = obj.getPosition().cpy();

				if (combat.getSwingDirection() == Direction.LEFT) {
					pos.add(-combat.getSwingSize().x / 2, 0);
				} else if (combat.getSwingDirection() == Direction.RIGHT) {
					pos.add(combat.getSwingSize().x / 2, 0);
				} else if (combat.getSwingDirection() == Direction.UP) {
					pos.add(0, combat.getSwingSize().y / 3 * 2);
				} else if (combat.getSwingDirection() == Direction.DOWN) {
					pos.add(0, -combat.getSwingSize().y / 3 * 2);
				}
				createSwingHitbox(e, combat.getSwingDirection(), pos);
				
				// SFX

				Sound s = AppUtil.jukebox.returnRandomSound(AudioLoader.getSound(Name.SOUND_SWING01), 
						AudioLoader.getSound(Name.SOUND_SWING02), 
						AudioLoader.getSound(Name.SOUND_SWING03));
				s.play(AppUtil.sfxVolume);
				
				// After-swing
				
				combat.swingCdCounter = combat.getSwingDuration();
			}
		}
	}

	public void createSwingHitbox(Entity source, Direction direction, Vector2 position) {
		Entity e = new Entity();
		CCombat combat = cm.get(source);
		e.add(new CPhysics().setGhost(true).setGravityApplied(false).setMovable(false)
				.setSize(combat.getSwingSize()).setPosition(position)
				.setCollisionListener(new SwingHitboxListener(source, direction)));
		e.add(new CDestructive(combat.getSwingDuration() / 2));
		AppUtil.engine.addEntity(e);
	}

	public void updateEntities(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(CCombat.class));
	}

	/**
	 * Deal <b>damage</b> to <b>target</b>. Source is optional, leave null if
	 * none.
	 *
	 * @param source Source of the <b>damage</b>.
	 * @param target Target to kill.
	 * @param damage Damage taken, that was dealth to the <b>target</b>.
	 */
	public static void dealDamage(Entity source, Entity target, int damage) {
		CCombat combat = target.getComponent(CCombat.class);
		CombatListener listener = combat.getCombatListener();
		if (listener != null) {
			listener.damageTaken(source, target, damage);
		}
		combat.health -= damage;
		if (combat.health <= 0) {
			killEntity(source, target, damage);
		}
	}

	/**
	 * Straight off kill the <b>target</b>.
	 *
	 * @param source Source of the <b/>damage</b>.
	 * @param target Target to kill.
	 * @param damage Damage taken, that killed <b>target</b>.
	 */
	public static void killEntity(Entity source, Entity target, int damage) {
		CCombat combat = target.getComponent(CCombat.class);
		CombatListener listener = combat.getCombatListener();
		if (listener != null) {
			listener.died(source, target, damage);
		}
		AppUtil.engine.removeEntity(target);
	}

}
