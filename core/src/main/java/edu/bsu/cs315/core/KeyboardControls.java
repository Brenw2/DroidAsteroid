package edu.bsu.cs315.core;

import static playn.core.PlayN.keyboard;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import playn.core.GroupLayer.Clipped;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Keyboard.Event;

public class KeyboardControls {

	Clipped layer;

	boolean jump;
	boolean left;
	boolean right;
	boolean shoot;
	float xSpeedPPS = 0;
	float ySpeedPPS = 0;
	float xAccelPPSPS = 0;
	float yAccelPPSPS = 15;
	int bulletCount = 0;
	int jumpCounter = 0;
	boolean canJump = true;

	public void updatePosition(int deltaMS) {

		jump = false;
	}

	public void processKeys(final Body body) {

		keyboard().setListener(new Keyboard.Adapter() {

			@Override
			public void onKeyDown(Event event) {
				if (event.key().equals(Key.UP) && canJump) {

					body.setLinearVelocity(new Vec2(0f, -20.8f));
					jump = true;
					jumpCounter++;
				}

				if (event.key().equals(Key.RIGHT)) {
					if (jump) {
						body.setLinearVelocity(new Vec2(25f, -0.01f));
						right = true;
						left = false;
					} else {
						body.setLinearVelocity(new Vec2(25f, 2.23f));
						right = true;
						left = false;
					}
				}

				if (event.key().equals(Key.LEFT)) {
					if (jump) {
						body.setLinearVelocity(new Vec2(-25f, -0.01f));
						left = true;
						right = false;
					} else {
						body.setLinearVelocity(new Vec2(-25f, 2.23f));
						left = true;
						right = false;
					}
				}
				
				if (event.key().equals(Key.SPACE) && bulletCount < 1) {
					shoot = true;
				}
			}

			public void onKeyUp(Event event) {
				if (event.key().equals(Key.RIGHT)) {
					if (left) {
						body.setLinearVelocity(new Vec2(-25f, 2.23f));
					} else if (jump) {
						body.setLinearVelocity(new Vec2(-25f, -0.1f));
					} else {
						body.setLinearVelocity(new Vec2(0f, 2.23f));
					}
				}
				if (event.key().equals(Key.LEFT)) {
					if (right) {
						body.setLinearVelocity(new Vec2(25f, 2.23f));
					} else if (jump) {
						body.setLinearVelocity(new Vec2(25f, -0.1f));
					} else {
						body.setLinearVelocity(new Vec2(0f, 2.23f));
					}
				}
				if (event.key().equals(Key.SPACE)) {
					shoot = false;
				}
			}
		});
	}
}
