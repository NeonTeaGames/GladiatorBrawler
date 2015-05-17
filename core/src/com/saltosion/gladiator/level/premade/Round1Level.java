package com.saltosion.gladiator.level.premade;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.saltosion.gladiator.level.Level;
import com.saltosion.gladiator.util.AppUtil;
import com.saltosion.gladiator.util.Direction;
import com.saltosion.gladiator.util.Global;
import java.util.ArrayList;

public class Round1Level implements Level {

	public Entity player;
	public ArrayList<Entity> enemies = new ArrayList<Entity>();

	@Override
	public String getLevelName() {
		return "Round 1";
	}

	@Override
	public boolean levelCleared() {
		for (Entity enemy : enemies) {
			if ((enemy.flags & Global.FLAG_ALIVE) == Global.FLAG_ALIVE) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean levelFailed() {
		return (player.flags & Global.FLAG_ALIVE) == 0;
	}

	@Override
	public void generate() {
		AppUtil.levelFactory.createLevelBase();
		player = AppUtil.entityFactory.createPlayer(new Vector2(-10, 2), Direction.RIGHT);
		enemies.add(AppUtil.entityFactory.createEnemy(new Vector2(10, 2), Direction.LEFT));
	}

}
