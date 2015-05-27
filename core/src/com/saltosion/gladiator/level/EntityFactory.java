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
		addSpriteSequence(renderedObject, IDLE_ANIMATION_SPEED, "Torso-Idle-Right", playerSprites, 0, new int[]{0, 1}, 0);
		addSpriteSequence(renderedObject, IDLE_ANIMATION_SPEED, "Legs-Idle-Right", playerSprites, 1, new int[]{0, 1}, 0);
		addSpriteSequence(renderedObject, IDLE_ANIMATION_SPEED, "Torso-Idle-Left", playerSprites, 0, new int[]{0, 1}, 1);
		addSpriteSequence(renderedObject, IDLE_ANIMATION_SPEED, "Legs-Idle-Left", playerSprites, 1, new int[]{0, 1}, 1);

		// Running animations
		addSpriteSequence(renderedObject, RUN_ANIMATION_SPEED, "Torso-Run-Right", playerSprites, 0, new int[]{2, 3, 4, 5, 6, 5, 4, 3}, 0);
		addSpriteSequence(renderedObject, RUN_ANIMATION_SPEED, "Legs-Run-Right", playerSprites, 1, new int[]{2, 3, 4, 5, 6, 7, 8, 9}, 0);
		addSpriteSequence(renderedObject, RUN_ANIMATION_SPEED, "Torso-Run-Left", playerSprites, 0, new int[]{2, 3, 4, 5, 6, 5, 4, 3}, 1);
		addSpriteSequence(renderedObject, RUN_ANIMATION_SPEED, "Legs-Run-Left", playerSprites, 1, new int[]{2, 3, 4, 5, 6, 7, 8, 9}, 1);

		// Jumping animation
		addSpriteSequence(renderedObject, IDLE_ANIMATION_SPEED, "Legs-Jump-Right", playerSprites, 1, 8, 0);
		addSpriteSequence(renderedObject, IDLE_ANIMATION_SPEED, "Legs-Jump-Left", playerSprites, 1, 8, 1);

		// Combat animations
		addSpriteSequence(renderedObject, SWING_ANIMATION_SPEED, "Torso-Combat-Right", playerSprites, 0, new int[]{7, 8, 9, 10}, 0);
		addSpriteSequence(renderedObject, SWING_ANIMATION_SPEED, "Torso-Combat-Left", playerSprites, 0, new int[]{7, 8, 9, 10}, 1);
		addSpriteSequence(renderedObject, SWING_ANIMATION_SPEED, "Torso-Combat-Right-Down", playerSprites, 0, new int[]{11, 12, 13, 14}, 0);
		addSpriteSequence(renderedObject, SWING_ANIMATION_SPEED, "Torso-Combat-Right-Up", playerSprites, 0, new int[]{15, 16, 17, 18}, 0);
		addSpriteSequence(renderedObject, SWING_ANIMATION_SPEED, "Torso-Combat-Left-Down", playerSprites, 0, new int[]{11, 12, 13, 14}, 1);
		addSpriteSequence(renderedObject, SWING_ANIMATION_SPEED, "Torso-Combat-Left-Up", playerSprites, 0, new int[]{15, 16, 17, 18}, 1);

		renderedObject.playAnimation("torso", "Torso-Idle-Right");
		renderedObject.playAnimation("legs", "Legs-Idle-Right");

		return renderedObject;
	}

	// Don't mind these
	private void addSpriteSequence(CRenderedObject target, int speed, String name, Sprite[][][] sheet, int xVal, int yVal, int flipVal) {
		addSpriteSequence(target, speed, name, sheet, new int[]{xVal}, new int[]{yVal}, new int[]{flipVal});
	}

	private void addSpriteSequence(CRenderedObject target, int speed, String name, Sprite[][][] sheet, int xVal, int[] yVals, int flipVal) {
		addSpriteSequence(target, speed, name, sheet, new int[]{xVal}, yVals, new int[]{flipVal});
	}

	private void addSpriteSequence(CRenderedObject target, int speed, String name, Sprite[][][] sheet, int[] xVals, int yVal, int flipVal) {
		addSpriteSequence(target, speed, name, sheet, xVals, new int[]{yVal}, new int[]{flipVal});
	}

	private void addSpriteSequence(CRenderedObject target, int speed, String name, Sprite[][][] sheet, int[] xVals, int[] yVals, int flipVal) {
		addSpriteSequence(target, speed, name, sheet, xVals, yVals, new int[]{flipVal});
	}

	private void addSpriteSequence(CRenderedObject target, int speed, String name, Sprite[][][] sheet, int[] xVals, int[] yVals, int[] flipVals) {
		SpriteSequence sequence = new SpriteSequence(speed);
		for (int x : xVals) {
			for (int y : yVals) {
				for (int f : flipVals) {
					sequence.addSprite(sheet[x][y][f]);
				}
			}
		}
		target.addSequence(name, sequence);
	}

}
