package com.saltosion.gladiator.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.saltosion.gladiator.components.CParticle;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.AudioLoader;
import com.saltosion.gladiator.util.Global;
import com.saltosion.gladiator.util.Name;

public class BasicDeathListener implements CombatListener {

	private static final float FX_FORCE = 16f, FX_GRAV = -40f;

	@Override
	public void died(Entity source, Entity target, int damageTaken) {
		target.flags &= ~Global.FLAG_ALIVE;

		CPhysics cp = target.getComponent(CPhysics.class);

		Sound s = AppUtil.jukebox.returnRandomSound(AudioLoader.getSound(Name.SOUND_HIT01),
				AudioLoader.getSound(Name.SOUND_HIT02),
				AudioLoader.getSound(Name.SOUND_HIT03),
				AudioLoader.getSound(Name.SOUND_HIT04),
				AudioLoader.getSound(Name.SOUND_HIT05));
		s.play(AppUtil.sfxVolume);

		for (int i = 0; i < getGoreAmount(damageTaken) * 2; i++) {
			Entity fx = new Entity();
			fx.add(new CParticle().setColor(1, 0, 0, 1).setDecayTime(2).setGravity(0, FX_GRAV)
					.setVelocity((float) Math.cos(Math.toRadians(Math.random() * 360)) * FX_FORCE,
							(float) Math.sin(Math.toRadians(Math.random() * 360)) * FX_FORCE)
					.setPosition(cp.getPosition().x + (float) Math.random() - 0.5f,
							cp.getPosition().y + (float) Math.random() - 0.5f)
					.setSize(0.2f, 0.2f));
			AppUtil.engine.addEntity(fx);
		}
	}

	@Override
	public void damageTaken(Entity source, Entity target, int damageTaken) {
		CPhysics cp = target.getComponent(CPhysics.class);

		Sound s = AppUtil.jukebox.returnRandomSound(AudioLoader.getSound(Name.SOUND_HIT01),
				AudioLoader.getSound(Name.SOUND_HIT02),
				AudioLoader.getSound(Name.SOUND_HIT03),
				AudioLoader.getSound(Name.SOUND_HIT04),
				AudioLoader.getSound(Name.SOUND_HIT05));
		s.play(AppUtil.sfxVolume);

		for (int i = 0; i < getGoreAmount(damageTaken); i++) {
			Entity fx = new Entity();
			fx.add(new CParticle().setColor(1, 0, 0, 1).setDecayTime(2).setGravity(0, FX_GRAV)
					.setVelocity((float) Math.cos(Math.toRadians(Math.random() * 360)) * FX_FORCE,
							(float) Math.sin(Math.toRadians(Math.random() * 360)) * FX_FORCE)
					.setPosition(cp.getPosition().x + (float) Math.random() - 0.5f,
							cp.getPosition().y + (float) Math.random() - 0.5f)
					.setSize(0.2f, 0.2f));
			AppUtil.engine.addEntity(fx);
		}
	}

	public int getGoreAmount(int damage) {
		return (damage - 89) / 8 * Global.GORE_LEVEL;
	}

}
