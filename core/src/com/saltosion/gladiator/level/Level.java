package com.saltosion.gladiator.level;

public interface Level {

	public String getLevelName();

	public boolean levelCleared();

	public boolean levelFailed();

	public void generate();

}
