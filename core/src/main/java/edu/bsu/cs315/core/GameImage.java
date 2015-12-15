package edu.bsu.cs315.core;

import static playn.core.PlayN.assets;
import playn.core.Image;

public enum GameImage {

	BACKGROUND("images/bg.png"), //
	SPACEMAN("images/spaceman.png"), //
	PLATFORM("images/platform.png"),
	BULLET("images/bullet.png"),
	SPACEMANLEFT("images/spacemanL.png"),
	BULLETLEFT("images/bulletL.png"),
	ROBOT("images/battle_robot.png"),//
	ROBOTEXPLOSION("images/RobotExplosion.png");

	public final Image image;

	private GameImage(String path) {
		image = assets().getImage(path);
	}
}