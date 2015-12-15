package edu.bsu.cs315.core;

import playn.core.Game;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;

public class DroidAsteroid extends Game.Default {
	
	private static final int DELAY_BETWEEN_UPDATES = 33;
	
	private final Clock.Source clock = new Clock.Source(DELAY_BETWEEN_UPDATES);
	public final ScreenStack screenStack = new ScreenStack();
	public static boolean gameOver = false;

	public DroidAsteroid() {
		super(DELAY_BETWEEN_UPDATES);
	}
	
	public void setGameOver(boolean isGameOver){
		gameOver = isGameOver;
	}
	
	public void checkGameOver(){
		if(gameOver == true){
			screenStack.push(new GameOverScreen(screenStack));
		}
	}

	@Override
	public void init() {
		screenStack.push(new LoadingScreen(screenStack));
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		clock.update(delta);
		screenStack.update(delta);
		checkGameOver();
		
	}

	@Override
	public void paint(float alpha) {
		super.paint(alpha);
		clock.paint(alpha);
		screenStack.paint(clock);
	}
}
