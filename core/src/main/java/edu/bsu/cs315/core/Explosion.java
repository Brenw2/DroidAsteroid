package edu.bsu.cs315.core;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import playn.core.GroupLayer;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import tripleplay.anim.Flipbook;
import tripleplay.util.Frames;
import tripleplay.util.SimpleFrames;

public class Explosion {
	public int id;
	public BodyDef explosionDef = new BodyDef();
	public FixtureDef explosionFixtureDef = new FixtureDef();
	public static final float WORLD_SCALE = 640f / 64f;
	GroupLayer.Clipped layer = null;
	private static final IDimension SIZE = new Dimension(192, 192);
	final Frames frames = new SimpleFrames(GameImage.ROBOTEXPLOSION.image,
			SIZE.width());
	Flipbook flipbook = new Flipbook(frames, 60);
	
	
	public Explosion(){
		createExplosionDefinition();
		createExplosionFixture();
	}

		
		

	private void createExplosionDefinition() {
		explosionDef.type = BodyType.DYNAMIC;
		explosionDef.position = new Vec2(50,50);
		explosionDef.fixedRotation = true;
	}
	
	private void createExplosionFixture() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(GameImage.ROBOTEXPLOSION.image.width() / 2 / WORLD_SCALE, //
				GameImage.ROBOTEXPLOSION.image.height() / 2 / WORLD_SCALE);

		explosionFixtureDef.density = 1.0f;
		explosionFixtureDef.friction = 0.0f;
		explosionFixtureDef.restitution = 0.0f;
		explosionFixtureDef.shape = shape;
		explosionFixtureDef.userData = this;
	}

}
