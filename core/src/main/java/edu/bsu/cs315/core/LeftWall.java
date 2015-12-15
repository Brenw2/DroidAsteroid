package edu.bsu.cs315.core;

import static playn.core.PlayN.graphics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class LeftWall implements FixtureUserData{
	public BodyDef lWallDef = new BodyDef();
	public FixtureDef lWallFixtureDef = new FixtureDef();
	public static final float WORLD_SCALE = 640f / 64f;

	public LeftWall() {
		createLeftWallBodyDefinition();
		createLeftWallBodyFixture();
	}

	private void createLeftWallBodyDefinition() {
		lWallDef.type = BodyType.STATIC;
		lWallDef.position = new Vec2(0, graphics().height()/2/WORLD_SCALE);
	}

	private void createLeftWallBodyFixture() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0, graphics().height() / WORLD_SCALE);

		lWallFixtureDef.density = 1.0f;
		lWallFixtureDef.friction = 0.0f;
		lWallFixtureDef.restitution = 0.0f;
		lWallFixtureDef.shape = shape;
		lWallFixtureDef.userData = this;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
		
	}
}
