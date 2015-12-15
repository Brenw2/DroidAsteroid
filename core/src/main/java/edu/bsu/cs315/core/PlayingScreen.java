package edu.bsu.cs315.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import playn.core.Image;
import playn.core.ImageLayer;
import pythagoras.f.Point;
import react.Value;
import react.ValueView.Listener;
import tripleplay.anim.Flipbook;
import tripleplay.entity.Component;
import tripleplay.entity.Component.Generic;
import tripleplay.entity.Entity;
import tripleplay.entity.System;
import tripleplay.game.UIAnimScreen;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.util.Colors;
import edu.bsu.cs315.core.FixtureUserData.Visitor;

public class PlayingScreen extends UIAnimScreen {

	public static final float WORLD_SCALE = 640f / 64f;
	private static final int VELOCITY_ITERATIONS = 10;
	private static final int POSITION_ITERATIONS = 10;
	public World box2dWorld;
	private KeyboardControls controls = new KeyboardControls();
	Vec2 butlletBodyLocation;
	private Vec2 actualBulletPosition;
	private int counter = 0;
	private int lives = 3;
	private List<Bullet> bulletsToRemove = new ArrayList<Bullet>();
	private List<Robot> robotsToRemove = new ArrayList<Robot>();
	public final Value<Integer> score = Value.create(0);
	public final Value<Integer> livesLeft = Value.create(3);
	public tripleplay.entity.World entityWorld = new tripleplay.entity.World();
	public final Component.Generic<ImageLayer> spriteComponent = new Component.Generic<ImageLayer>(
			entityWorld);
	public final Component.Generic<Body> rigidBody = new Component.Generic<Body>(
			entityWorld);
	public final Component.IScalar jumpable = new Component.IScalar(entityWorld);
	public final Component.Generic<String> robotType = new Component.Generic<String>(
			entityWorld);
	public final Component.Generic<Explosion> robotExplode = new Generic<Explosion>(entityWorld); 
	public static int finalScore;
	
	
	

	// This system is automatically connected to the world through the
	// constructor. It is not "used" otherwise, so this warning is suppressed.
	@SuppressWarnings("unused")
	private final System layerUpdateSystem = new System(entityWorld, 2) {
		@Override
		protected boolean isInterested(Entity entity) {
			return entity.has(spriteComponent) && entity.has(rigidBody);
		}

		@Override
		protected void update(int deltaMS, Entities entities) {
			for (int i = 0, size = entities.size(); i < size; i++) {
				int entityId = entities.get(i);
				updateEntity(entityId);
				controls.updatePosition(deltaMS);
				if (controls.shoot) {
					createBullet();
					GameSound.GUNFIRE.sound.play();
					controls.shoot = false;
				}

				if (counter == 500) {
					createRobot();
					createRobot();
					counter = 0;

				}

				counter++;

			}
		}

		private void updateEntity(int entityId) {
			ImageLayer imageLayer = spriteComponent.get(entityId);
			Body body = rigidBody.get(entityId);
			float x = body.getPosition().x * WORLD_SCALE + controls.xSpeedPPS
					/ WORLD_SCALE;
			float y = body.getPosition().y * WORLD_SCALE + controls.ySpeedPPS;
			if (entityId == 1) {
				controls.processKeys(body);
				if (controls.left) {
					imageLayer.setImage(GameImage.SPACEMANLEFT.image);
				}

				else {
					imageLayer.setImage(GameImage.SPACEMAN.image);
				}

			}
			if (entityWorld.entity(entityId).has(robotType)) {
				body.setGravityScale(15);
				if (body.getPosition().x < butlletBodyLocation.x) {
					body.setLinearVelocity(new Vec2(10, 0));
				} else if (body.getPosition().x > butlletBodyLocation.x) {
					body.setLinearVelocity(new Vec2(-10, 0));
				}

				if (body.getPosition().y > butlletBodyLocation.y) {
					body.setLinearVelocity(new Vec2(0, -10));
				}
			}
			imageLayer.setTranslation(x, y);
			imageLayer.setRotation(0);

		}
	};

	public PlayingScreen() {
		createBackground();
		createWorld();
		createHud();
		createSpaceMan();
		createPlatform();
		createGround();
		createCeiling();
		createRightWall();
		createLeftWall();
		
		for(int i = 0; i<4; i++){
			createRobot();
		}
		
		

		box2dWorld.setContactListener(new ContactListener() {
			private FixtureUserData userDataA;
			private FixtureUserData userDataB;

			@Override
			public void beginContact(Contact contact) {
				userDataA = (FixtureUserData) contact.m_fixtureA.m_userData;
				userDataB = (FixtureUserData) contact.m_fixtureB.m_userData;
				if (userDataA != null && userDataB != null) {
					userDataA.accept(collisionVisitor);
				}

			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub

			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub

			}

			private final Visitor collisionVisitor = new Visitor() {
				@Override
				public void visit(SpaceMan man) {
					userDataB.accept(manVisitor);
				}

				@Override
				public void visit(Bullet bullet) {
					userDataB.accept(bulletVisitor);
				}

				@Override
				public void visit(Platform platform) {
					userDataB.accept(platformVisitor);
				}

				@Override
				public void visit(RightWall rWall) {
					userDataB.accept(rWallVisitor);
				}

				@Override
				public void visit(LeftWall lWall) {
					userDataB.accept(lWallVisitor);
				}

				@Override
				public void visit(Robot robot) {
					userDataB.accept(robotVisitor);
				}

				private final Visitor manVisitor = new Visitor() {

					@Override
					public void visit(SpaceMan man) {
						log().warn("man-man collision?");

					}

					@Override
					public void visit(Platform platform) {
						// TODO Auto-generated method stub

					}

					@Override
					public void visit(Bullet bullet) {
						// TODO Auto-generated method stub

					}

					@Override
					public void visit(RightWall rWall) {
						// TODO Auto-generated method stub

					}

					@Override
					public void visit(LeftWall lWall) {
						// TODO Auto-generated method stub

					}

					@Override
					public void visit(Robot robot) {
						log().debug("Space Man Lives:" + lives);
						GameSound.SPACEMANGRUNT.sound.play();
						lives--;
						livesLeft.update(livesLeft.get() - 1);
						if (lives <= 0) {
							DroidAsteroid.gameOver = true;
						}
					}

				};

				private final Visitor bulletVisitor = new Visitor.Adapter() {

					@Override
					public void visit(Bullet bullet) {
						log().warn("bullet collision?");
						bulletsToRemove.add(bullet);
					}

					@Override
					public void visit(Robot robot) {
						log().warn("bullet error happens here");
						robotsToRemove.add(robot);
						Bullet bullet = (Bullet) userDataA;
						bulletsToRemove.add(bullet);
						final Flipbook flipbook = new Flipbook(robotExplode.get(robot.id).frames, 25);
						float x = spriteComponent.get(robot.id).transform().tx() - 100;
						float y = spriteComponent.get(robot.id).transform().ty() - 100;
						anim.flipbookAt(layer, x,y , flipbook);
						GameSound.EXPLOSION.sound.play();
						score.update(score.get() + 1);
						finalScore++;

					}

				};

				private final Visitor platformVisitor = new Visitor.Adapter() {

					@Override
					public void visit(Bullet bullet) {
						bulletsToRemove.add(bullet);

					}

				};

				private final Visitor rWallVisitor = new Visitor.Adapter() {
					@Override
					public void visit(SpaceMan man) {
						rigidBody.get(1).setTransform(
								butlletBodyLocation.mulLocal(0), 0);
					}

					@Override
					public void visit(Bullet bullet) {
						bulletsToRemove.add(bullet);
						log().debug("right wall hit");
					}

				};

			};

			private final Visitor lWallVisitor = new Visitor.Adapter() {

				@Override
				public void visit(Bullet bullet) {
					bulletsToRemove.add(bullet);
					log().debug("Hit left wall");
				}

			};

			private final Visitor robotVisitor = new Visitor.Adapter() {

				@Override
				public void visit(Bullet bullet) {
					log().warn("robot hit!");
					bulletsToRemove.add(bullet);
					Robot robot = (Robot) userDataA;
					robotsToRemove.add(robot);
					GameSound.EXPLOSION.sound.play();
					score.update(score.get() + 1);
					finalScore++;
					
					final Flipbook flipbook = new Flipbook(robotExplode.get(robot.id).frames, 25);
					float x = spriteComponent.get(robot.id).transform().tx() - 100;
					float y = spriteComponent.get(robot.id).transform().ty() - 100;
					anim.flipbookAt(layer, x,y , flipbook);
				}

				@Override
				public void visit(SpaceMan man) {
					log().debug("Space Man Lives:" + lives);
					GameSound.SPACEMANGRUNT.sound.play();
					lives--;
					livesLeft.update(livesLeft.get() - 1);
				}

			};

		});
	};

	public void createSpaceMan() {
		SpaceMan spaceman = new SpaceMan();
		Body spaceManBody = box2dWorld.createBody(spaceman.bodyDef);
		spaceManBody.createFixture(spaceman.fixtureDef);

		ImageLayer spaceManLayer = spaceman.layer;
		layer.add(spaceManLayer);

		Entity entity = entityWorld.create(true);
		entity.add(spriteComponent, rigidBody, jumpable);
		spriteComponent.set(entity.id, spaceman.layer);
		rigidBody.set(entity.id, spaceManBody);
		jumpable.set(entity.id, 2);
		spaceManBody.getAngularDamping();
		butlletBodyLocation = spaceManBody.getPosition();
	}

	public void createPlatform() {
		Platform platform = new Platform();
		Body platformBody = box2dWorld.createBody(platform.platformDef);
		platformBody.createFixture(platform.platformFixtureDef);
		ImageLayer platformLayer = platform.layer;
		layer.add(platformLayer);
		Entity entity = entityWorld.create(true);
		entity.add(spriteComponent, rigidBody);
		spriteComponent.set(entity.id, platform.layer);
		rigidBody.set(entity.id, platformBody);

	}

	public void createRobot() {
		Robot robot = new Robot();
		Explosion explosion = new Explosion();
		ImageLayer robotLayer = robot.layer;
		Body body;
		body = box2dWorld.createBody(robot.robotDef);
		body.createFixture(robot.robotFixtureDef);
		layer.add(robotLayer);
		Entity entity = entityWorld.create(true);
		entity.add(spriteComponent, rigidBody, robotType, robotExplode);
		spriteComponent.set(entity.id, robot.layer);
		rigidBody.set(entity.id, body);
		robot.id = entity.id;
		
		
		robotExplode.set(entity.id, explosion);
		
		
	}

	public void createBullet() {
		Bullet bullet = new Bullet();
		Body body = box2dWorld.createBody(bullet.bulletDef);
		body.createFixture(bullet.bulletFixtureDef);
		body.setBullet(true);
		body.setGravityScale(0.01f);
		if (controls.left) {
			body.setLinearVelocity(new Vec2(-200, 0));
		} else {
			body.setLinearVelocity(new Vec2(200, 0));
		}
		layer.add(bullet.layer);
		Entity entity = entityWorld.create(true);
		entity.add(spriteComponent, rigidBody);
		spriteComponent.set(entity.id, bullet.layer);
		rigidBody.set(entity.id, body);
		if (controls.left) {
			bullet.layer.setImage(GameImage.BULLETLEFT.image);
			actualBulletPosition = butlletBodyLocation.add(new Vec2(-2, 0));
		} else {
			actualBulletPosition = butlletBodyLocation.add(new Vec2(2, 0));
		}
		body.setTransform(actualBulletPosition, 0);
		bullet.id = entity.id;

	}

	private void createGround() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position = new Vec2(graphics().width() / 2 / WORLD_SCALE,
				graphics().height() / WORLD_SCALE);
		Body body = box2dWorld.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(graphics().width() / 2 / WORLD_SCALE, 0);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.friction = 0.0f;
		fixtureDef.density = 1.0f;
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);
	}

	private void createCeiling() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position = new Vec2(graphics().width() / 2 / WORLD_SCALE, 0);
		Body body = box2dWorld.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(graphics().width() / 2 / WORLD_SCALE, 0);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.friction = 0.0f;
		fixtureDef.density = 1.0f;
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);

	}

	private void createRightWall() {
		RightWall rWall = new RightWall();
		Body rightWallBody = box2dWorld.createBody(rWall.rWallDef);
		rightWallBody.createFixture(rWall.rWallFixtureDef);
	}

	private void createLeftWall() {
		LeftWall lWall = new LeftWall();
		Body leftWallBody = box2dWorld.createBody(lWall.lWallDef);
		leftWallBody.createFixture(lWall.lWallFixtureDef);
	}

	private void createBackground() {
		Image bgImage = GameImage.BACKGROUND.image;
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		graphics().rootLayer().add(bgLayer);
	}

	private void createWorld() {
		box2dWorld = new World(new Vec2(0, 9.8f)); // gravity for the world
		box2dWorld.setWarmStarting(true);
		box2dWorld.setAutoClearForces(true);
	}

	private void createHud() {
		Root root = iface.createRoot(new AbsoluteLayout(),
				SimpleStyles.newSheet(), layer).setSize(graphics().width(),
				graphics().height());
		final Label deathLabel = new Label("Robots Destroyed:   " + score.get());
		final Label lifeLabel = new Label("Lives:   " + livesLeft.get());
		score.connect(new Listener<Integer>() {

			public void onChange(Integer value, Integer oldValue) {
				deathLabel.text.update("Robots Destroyed:   " + value);
			}
		});
		root.add(AbsoluteLayout.at(deathLabel,
				new Point(graphics().width() / 2, 15)).setStyles(
				Style.COLOR.is(Colors.CYAN)));
		livesLeft.connect(new Listener<Integer>()
		{

			@Override
			public void onChange(Integer value, Integer oldValue) {
				lifeLabel.text.update("Lives:   " + value);
				
			}
			
		});
		root.add(AbsoluteLayout.at(lifeLabel, new Point(graphics().width()/2,30)).setStyles(Style.COLOR.is(Colors.CYAN)));
	}

	@Override
	public void update(int deltaMS) {
		super.update(deltaMS);
		box2dWorld.step(deltaMS / 1000f, VELOCITY_ITERATIONS,
				POSITION_ITERATIONS);
		entityWorld.update(deltaMS);

		while (!(bulletsToRemove.isEmpty())) {

			Bullet bullet = bulletsToRemove.get(0);
			box2dWorld.destroyBody(rigidBody.get(bulletsToRemove.get(0).id));
			spriteComponent.get(bulletsToRemove.get(0).id).destroy();
			entityWorld.entity(bulletsToRemove.get(0).id).destroy();
			bulletsToRemove.remove(bullet);
		}

		while (!(robotsToRemove.isEmpty())) {
			Robot robot = robotsToRemove.get(0);
			box2dWorld.destroyBody(rigidBody.get(robot.id));
			spriteComponent.get(robot.id).destroy();
			entityWorld.entity(robot.id).destroy();
			robotsToRemove.remove(robot);
		}

		if (lives == 0) {

		}
	}
}
