package edu.bsu.cs315.core;

import static playn.core.PlayN.graphics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import playn.core.ImageLayer;

public class Bullet implements FixtureUserData{
	public int id;
	public BodyDef bulletDef = new BodyDef();
	public FixtureDef bulletFixtureDef = new FixtureDef();
	public ImageLayer layer = graphics().createImageLayer(
			GameImage.BULLET.image);
	public static final float WORLD_SCALE = 640f / 64f;

	public Bullet() {
		createBulletBodyDefinition();
		createBulletBodyFixture();
		createBulletImage();
	}

	private void createBulletBodyDefinition() {
		bulletDef.type = BodyType.DYNAMIC;
	}

	private void createBulletBodyFixture() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(GameImage.BULLET.image.width() / 2 / WORLD_SCALE, //
				GameImage.BULLET.image.height() / 2 / WORLD_SCALE);

		bulletFixtureDef.density = 1.0f;
		bulletFixtureDef.friction = 0.0f;
		bulletFixtureDef.restitution = 0.0f;
		bulletFixtureDef.shape = shape;
		bulletFixtureDef.userData = this;
	}

	private void createBulletImage() {
		float bulletXOrigin = layer.width() / 2;
		float bulletYOrigin = layer.height() / 2;
		layer.setOrigin(bulletXOrigin, bulletYOrigin);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
		
	}

	


	
}
