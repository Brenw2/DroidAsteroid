package edu.bsu.cs315.core;

import static playn.core.PlayN.graphics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class RightWall implements FixtureUserData{

	public BodyDef rWallDef = new BodyDef();
	public FixtureDef rWallFixtureDef = new FixtureDef();
	public static final float WORLD_SCALE = 640f / 64f;

	public RightWall() {
		createRightWallBodyDefinition();
		createRightWallBodyFixture();
	}

	private void createRightWallBodyDefinition() {
		rWallDef.type = BodyType.STATIC;
		rWallDef.position = new Vec2(graphics().width()/WORLD_SCALE, graphics().height()/2/WORLD_SCALE);
	}

	private void createRightWallBodyFixture() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0, graphics().height() / 2 / WORLD_SCALE);

		rWallFixtureDef.density = 1.0f;
		rWallFixtureDef.friction = 0.0f;
		rWallFixtureDef.restitution = 0.0f;
		rWallFixtureDef.shape = shape;
		rWallFixtureDef.userData = this;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
		
	}
}
