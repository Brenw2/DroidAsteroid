package edu.bsu.cs315.core;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import playn.core.ImageLayer;
import static playn.core.PlayN.graphics;

public class SpaceMan implements FixtureUserData{
	float vectorHeight = graphics().height() - GameImage.SPACEMAN.image.width()
			/ 2;
	float vectorWidth = graphics().height() - GameImage.SPACEMAN.image.height()
			/ 2;
	public static final float WORLD_SCALE = 640f / 64f;
	PolygonShape shape = new PolygonShape();
	public ImageLayer layer = graphics().createImageLayer(
			GameImage.SPACEMAN.image);
	public ImageLayer layerLeft = graphics().createImageLayer(GameImage.SPACEMANLEFT.image);
	public BodyDef bodyDef = new BodyDef();
	public FixtureDef fixtureDef = new FixtureDef();

	public SpaceMan() {
		createSpaceManBodyDefinition();
		createSpaceManBodyFixture();
		createSpaceManImage();
	}

	private void createSpaceManBodyDefinition() {
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position = new Vec2(graphics().height()
				- GameImage.SPACEMAN.image.width() / 2,
				0 + GameImage.SPACEMAN.image.width() / 2)//
				.mul(1 / WORLD_SCALE);
		bodyDef.fixedRotation = true;

	}

	private void createSpaceManBodyFixture(){
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(GameImage.SPACEMAN.image.width() / 2 / WORLD_SCALE, //
				GameImage.SPACEMAN.image.height() / 2 / WORLD_SCALE);

		fixtureDef.density = 1.0f;
		fixtureDef.friction = 1.0f;
		fixtureDef.restitution = 0.0f;
		fixtureDef.shape = shape;
		fixtureDef.userData = this;

	}

	private void createSpaceManImage() {
		float ManXOrigin = layer.width() / 2;
		float ManYOrigin = layer.height() / 2;
		layer.setOrigin(ManXOrigin, ManYOrigin);
		layer.setRotation(0);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
		
	}

}
