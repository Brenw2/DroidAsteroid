package edu.bsu.cs315.core;

import static playn.core.PlayN.graphics;
import playn.core.AssetWatcher;
import pythagoras.f.Point;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.util.Colors;

public class LoadingScreen extends UIScreen {

	public LoadingScreen(final ScreenStack screenStack) {
		initLoadingLabel();
		goToPlayingScreenAfterAllAssestsAreLoaded(screenStack);
	}

	private void initLoadingLabel() {
		Root root = iface.createRoot(new AbsoluteLayout(),
				SimpleStyles.newSheet(), layer)//
				.setSize(graphics().width(), graphics().height());
		root.add(AbsoluteLayout.at(
				new Label("Loading!").setStyles(Style.COLOR.is(Colors.BLUE)),
				new Point(10, 10)));
	}

	private void goToPlayingScreenAfterAllAssestsAreLoaded(
			final ScreenStack screenStack) {
		AssetWatcher watcher = new AssetWatcher(new AssetWatcher.Listener() {
			@Override
			public void error(Throwable e) {
				throw new RuntimeException(e);
			}

			@Override
			public void done() {
				screenStack.push(new PlayingScreen());
			}
		});
		for(GameSound gameSound : GameSound.values()){
			watcher.add(gameSound.sound);
		}
		for (GameImage gameImage : GameImage.values()) {
			watcher.add(gameImage.image);
		}
		watcher.start();
	}
}