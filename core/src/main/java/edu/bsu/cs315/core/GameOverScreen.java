package edu.bsu.cs315.core;

import static playn.core.PlayN.graphics;
import pythagoras.f.Point;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.Background;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.util.Colors;

public class GameOverScreen extends UIScreen {
	
	public GameOverScreen(final ScreenStack screenStack)
	{
		initGameOverLabel();
		goToLoadingScreenAfterSpaceManDeath(screenStack);
	}
	
	private void goToLoadingScreenAfterSpaceManDeath( final ScreenStack screenStack) {
		
	}

	private void initGameOverLabel() {
		Root root = iface.createRoot(new AbsoluteLayout(),
				SimpleStyles.newSheet(), layer)//
				.setSize(graphics().width(), graphics().height());
		root.add(AbsoluteLayout.at(
				new Label("Game Over!  Score:   " + PlayingScreen.finalScore).setStyles(Style.COLOR.is(Colors.RED)).addStyles(Style.BACKGROUND.is(Background.solid(Colors.BLACK))),
				new Point(graphics().width()/2 -100, graphics().height()/2 - 50)));
		root.add(AbsoluteLayout.at(new Label("To play again, please restart/refresh game.").setStyles(Style.COLOR.is(Colors.CYAN)), new Point(graphics().width()/2-100,graphics().height()/2)));
	}
}
