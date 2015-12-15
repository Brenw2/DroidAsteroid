package edu.bsu.cs315.core;

import static playn.core.PlayN.graphics;

import java.util.Random;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import playn.core.ImageLayer;

public class Platform implements FixtureUserData{
	public BodyDef platformDef = new BodyDef();
	public FixtureDef platformFixtureDef = new FixtureDef();
	public ImageLayer layer = graphics().createImageLayer(
			GameImage.PLATFORM.image);
	public static final float WORLD_SCALE = 640f / 64f;
	private Random random = new Random();

	public Platform() {
		createPlatformBodyDefinition();
		createPlatformBodyFixture();
		createPlateformImage();
	}

	private void createPlatformBodyDefinition() {
		
		int x = random.nextInt(200) + 50;
		int y = random.nextInt(200)+ 100;
		platformDef.type = BodyType.STATIC;
		platformDef.position = new Vec2(x, y)//
				.mul(1 / WORLD_SCALE);
	}

	private void createPlatformBodyFixture() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(GameImage.PLATFORM.image.width() / 2 / WORLD_SCALE, //
				GameImage.PLATFORM.image.height() / 2 / WORLD_SCALE);

		platformFixtureDef.density = 1.0f;
		platformFixtureDef.friction = 0.0f;
		platformFixtureDef.restitution = 0.0f;
		platformFixtureDef.shape = shape;
		platformFixtureDef.userData = this;
	}

	private void createPlateformImage() {
		float ManXOrigin = layer.width() / 2;
		float ManYOrigin = layer.height() / 2;
		layer.setOrigin(ManXOrigin, ManYOrigin);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
		
	}

}
