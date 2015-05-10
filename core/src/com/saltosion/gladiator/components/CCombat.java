package com.saltosion.gladiator.components;

import com.badlogic.ashley.core.Component;

public class CCombat extends Component {
	
	private int health = 0;
	private int maxHealth = 0;
	private int damage = 0;
	
	public boolean swinging = false;
	private float swingCdCounter = 0;
	private float swingCd = 0;
	
	public int getHealth() {
		return this.health;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}
	
	public int getDamage() {
		float minDmg = damage*0.9f;
		float maxDmg = damage*1.1f;
		int randomdamage = (int) (Math.random()*(maxDmg-minDmg)+minDmg);
		if (Math.random() > 0.7) {
			randomdamage *= 1.5;
		}
		return randomdamage;
	}
	
	public float getSwingCDCounter() {
		return this.swingCdCounter;
	}
	
	public float getSwingCD() {
		return this.swingCd;
	}
	
	public CCombat setBaseDamage(int basedmg) {
		this.damage = basedmg;
		return this;
	}
	
	public CCombat setCurrHealth(int health) {
		this.health = health;
		return this;
	}
	
	public CCombat setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		return this;
	}
	
	public CCombat setSwingCD(float cd) {
		this.swingCd = cd;
		return this;
	}
}
