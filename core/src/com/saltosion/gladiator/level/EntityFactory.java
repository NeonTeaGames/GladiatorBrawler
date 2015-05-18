/**
 * GladiatorBrawler is a 2D swordfighting game.
 * Copyright (C) 2015 Jeasonfire/Allexit
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.saltosion.gladiator.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.components.CAI;
import com.saltosion.gladiator.components.CCombat;
import com.saltosion.gladiator.components.CPhysics;
import com.saltosion.gladiator.components.CRenderedObject;
import com.saltosion.gladiator.listeners.BasicDeathListener;
import com.saltosion.gladiator.listeners.ai.DummyAI;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Direction;
import com.saltosion.gladiator.util.Global;
import com.saltosion.gladiator.util.Name;
import com.saltosion.gladiator.util.SpriteLoader;
import com.saltosion.gladiator.util.SpriteSequence;

public class EntityFactory {

	private final static int IDLE_ANIMATION_SPEED = 1, RUN_ANIMATION_SPEED = 15, SWING_ANIMATION_SPEED = 10;

	public Entity createPlayer(Vector2 pos) {
		return createPlayer(pos, Direction.RIGHT);
	}

	public Entity createPlayer(Vector2 pos, Direction initialDirection) {
		Entity player = new Entity();
		player.flags |= Global.FLAG_ALIVE;

		// Graphics
		player.add(createPlayerRenderedObject());

		// Physics
		player.add(new CPhysics().setSize(1.5f, 3.299f).setPosition(pos.x, pos.y)
				.setDirection(initialDirection).setMoveSpeed(15f));

		// Combat
		player.add(new CCombat().setBaseDamage(100).setHealth(1000)
				.setCombatListener(new BasicDeathListener()));

		AppUtil.engine.addEntity(player);
		AppUtil.player = player;

		return player;
	}

	public Entity createEnemy(Vector2 pos) {
		return createEnemy(pos, Direction.RIGHT);
	}

	public Entity createEnemy(Vector2 pos, Direction initialDirection) {
		return createEnemy(pos, initialDirection, new CAI().setReactDistance(5).setAIListener(new DummyAI()));
	}

	public Entity createEnemy(Vector2 pos, Direction initialDirection, CAI cai) {
		Entity enemy = new Entity();
		enemy.flags |= Global.FLAG_ALIVE;

		// Graphics
		enemy.add(createPlayerRenderedObject());

		// Physics
		enemy.add(new CPhysics().setSize(1.5f, 3.299f).setPosition(pos.x, pos.y)
				.setDirection(initialDirection).setMoveSpeed(14f));

		// Combat
		enemy.add(new CCombat().setBaseDamage(100).setHealth(1000)
				.setCombatListener(new BasicDeathListener()));
		enemy.add(cai);

		AppUtil.engine.addEntity(enemy);

		return enemy;
	}

	private CRenderedObject createPlayerRenderedObject() {
		CRenderedObject renderedObject = new CRenderedObject();

		// Sprite[x][y][flip]
		Sprite[][][] playerSprites = new Sprite[2][19][2];
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 19; y++) {
				Sprite noFlip = SpriteLoader.loadSprite(Name.PLAYERIMG, x, y, 128, 112);
				Sprite flip = new Sprite(noFlip);
				flip.flip(true, false);
				playerSprites[x][y][0] = noFlip;
				playerSprites[x][y][1] = flip;
			}
		}

		renderedObject.setChannelName("default", "legs");
		renderedObject.addChannel("torso");

		// Idle animations
		SpriteSequence torsoIdleRightSequence = new SpriteSequence(IDLE_ANIMATION_SPEED).addSprite(playerSprites[0][0][0]).addSprite(playerSprites[0][1][0]);
		renderedObject.addSequence("Torso-Idle-Right", torsoIdleRightSequence);
		SpriteSequence legsIdleRightSquence = new SpriteSequence(IDLE_ANIMATION_SPEED).addSprite(playerSprites[1][0][0]).addSprite(playerSprites[1][1][0]);
		renderedObject.addSequence("Legs-Idle-Right", legsIdleRightSquence);
		SpriteSequence torsoIdleLeftSequence = new SpriteSequence(IDLE_ANIMATION_SPEED).addSprite(playerSprites[0][0][1]).addSprite(playerSprites[0][1][1]);
		renderedObject.addSequence("Torso-Idle-Left", torsoIdleLeftSequence);
		SpriteSequence legsIdleLeftSquence = new SpriteSequence(IDLE_ANIMATION_SPEED).addSprite(playerSprites[1][0][1]).addSprite(playerSprites[1][1][1]);
		renderedObject.addSequence("Legs-Idle-Left", legsIdleLeftSquence);

		// Running animations
		SpriteSequence torsoRunRightSequence = new SpriteSequence(RUN_ANIMATION_SPEED).addSprite(playerSprites[0][2][0])
				.addSprite(playerSprites[0][3][0]).addSprite(playerSprites[0][4][0]).addSprite(playerSprites[0][5][0])
				.addSprite(playerSprites[0][6][0]).addSprite(playerSprites[0][5][0]).addSprite(playerSprites[0][4][0])
				.addSprite(playerSprites[0][3][0]);
		renderedObject.addSequence("Torso-Run-Right", torsoRunRightSequence);
		SpriteSequence legsRunRightSequence = new SpriteSequence(RUN_ANIMATION_SPEED).addSprite(playerSprites[1][2][0])
				.addSprite(playerSprites[1][3][0]).addSprite(playerSprites[1][4][0]).addSprite(playerSprites[1][5][0])
				.addSprite(playerSprites[1][6][0]).addSprite(playerSprites[1][7][0]).addSprite(playerSprites[1][8][0])
				.addSprite(playerSprites[1][9][0]);
		renderedObject.addSequence("Legs-Run-Right", legsRunRightSequence);
		SpriteSequence torsoRunLeftSequence = new SpriteSequence(RUN_ANIMATION_SPEED).addSprite(playerSprites[0][2][1])
				.addSprite(playerSprites[0][3][1]).addSprite(playerSprites[0][4][1]).addSprite(playerSprites[0][5][1])
				.addSprite(playerSprites[0][6][1]).addSprite(playerSprites[0][5][1]).addSprite(playerSprites[0][4][1])
				.addSprite(playerSprites[0][3][1]);
		renderedObject.addSequence("Torso-Run-Left", torsoRunLeftSequence);
		SpriteSequence legsRunLeftSequence = new SpriteSequence(RUN_ANIMATION_SPEED).addSprite(playerSprites[1][2][1])
				.addSprite(playerSprites[1][3][1]).addSprite(playerSprites[1][4][1]).addSprite(playerSprites[1][5][1])
				.addSprite(playerSprites[1][6][1]).addSprite(playerSprites[1][7][1]).addSprite(playerSprites[1][8][1])
				.addSprite(playerSprites[1][9][1]);
		renderedObject.addSequence("Legs-Run-Left", legsRunLeftSequence);

		// Combat animations
		SpriteSequence torsoCombatRightSequence = new SpriteSequence(SWING_ANIMATION_SPEED).addSprite(playerSprites[0][7][0])
				.addSprite(playerSprites[0][8][0]).addSprite(playerSprites[0][9][0]).addSprite(playerSprites[0][10][0]);
		renderedObject.addSequence("Torso-Combat-Right", torsoCombatRightSequence);
		SpriteSequence torsoCombatLeftSequence = new SpriteSequence(SWING_ANIMATION_SPEED).addSprite(playerSprites[0][7][1])
				.addSprite(playerSprites[0][8][1]).addSprite(playerSprites[0][9][1]).addSprite(playerSprites[0][10][1]);
		renderedObject.addSequence("Torso-Combat-Left", torsoCombatLeftSequence);
		SpriteSequence torsoCombatRightDownSequence = new SpriteSequence(SWING_ANIMATION_SPEED).addSprite(playerSprites[0][11][0])
				.addSprite(playerSprites[0][12][0]).addSprite(playerSprites[0][13][0]).addSprite(playerSprites[0][14][0]);
		renderedObject.addSequence("Torso-Combat-Right-Down", torsoCombatRightDownSequence);
		SpriteSequence torsoCombatRightUpSequence = new SpriteSequence(SWING_ANIMATION_SPEED).addSprite(playerSprites[0][15][0])
				.addSprite(playerSprites[0][16][0]).addSprite(playerSprites[0][17][0]).addSprite(playerSprites[0][18][0]);
		renderedObject.addSequence("Torso-Combat-Right-Up", torsoCombatRightUpSequence);
		SpriteSequence torsoCombatLeftDownSequence = new SpriteSequence(SWING_ANIMATION_SPEED).addSprite(playerSprites[0][11][1])
				.addSprite(playerSprites[0][12][1]).addSprite(playerSprites[0][13][1]).addSprite(playerSprites[0][14][1]);
		renderedObject.addSequence("Torso-Combat-Left-Down", torsoCombatLeftDownSequence);
		SpriteSequence torsoCombatLeftUpSequence = new SpriteSequence(SWING_ANIMATION_SPEED).addSprite(playerSprites[0][15][1])
				.addSprite(playerSprites[0][16][1]).addSprite(playerSprites[0][17][1]).addSprite(playerSprites[0][18][1]);
		renderedObject.addSequence("Torso-Combat-Left-Up", torsoCombatLeftUpSequence);

		renderedObject.playAnimation("torso", "Torso-Idle-Right");
		renderedObject.playAnimation("legs", "Legs-Idle-Right");

		return renderedObject;
	}

}
