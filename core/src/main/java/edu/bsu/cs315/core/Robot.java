package edu.bsu.cs315.core;

import static playn.core.PlayN.graphics;

import java.util.Random;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import playn.core.ImageLayer;



public class Robot implements FixtureUserData {
	public int id;
	public BodyDef robotDef = new BodyDef();
	public FixtureDef robotFixtureDef = new FixtureDef();
	public ImageLayer layer = graphics().createImageLayer(
			GameImage.ROBOT.image);
	public static final float WORLD_SCALE = 640f / 64f;
	private Random random = new Random();
	
	
	
	
	
	public Robot() {
		createRobotBodyDefinition();
		createRobotBodyFixture();
		
	}

	private void createRobotBodyDefinition() {
		int x = random.nextInt(50) + 10;
		int y = random.nextInt(50)+ 10;
		robotDef.type = BodyType.DYNAMIC;
		robotDef.position = new Vec2(x,y);
		robotDef.fixedRotation = true;
	}

	private void createRobotBodyFixture() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(GameImage.ROBOT.image.width() / 2 / WORLD_SCALE, //
				GameImage.ROBOT.image.height() / 2 / WORLD_SCALE + 2);

		robotFixtureDef.density = 1.0f;
		robotFixtureDef.friction = 0.0f;
		robotFixtureDef.restitution = 0.0f;
		robotFixtureDef.shape = shape;
		robotFixtureDef.userData = this;
	}
	
	
	@Override
	public void accept(Visitor v) {
		v.visit(this);
		
	}
}
