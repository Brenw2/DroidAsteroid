package edu.bsu.cs315.core;

import playn.core.Sound;
import static playn.core.PlayN.assets;

public enum GameSound {
	EXPLOSION("sounds/RobotExplosion"),//
	GUNFIRE("sounds/Gunfire"),//
	SPACEMANGRUNT("sounds/SpaceManGrunt");
	
	public final Sound sound;
	
	private GameSound(String path){
		sound = assets().getSound(path);
	}
	
	
}
