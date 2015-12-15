package edu.bsu.cs315.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import edu.bsu.cs315.core.DroidAsteroid;

public class DroidAsteroidHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    // use config to customize the HTML platform, if needed
    HtmlPlatform platform = HtmlPlatform.register(config);
    platform.assets().setPathPrefix("droidAsteroid/");
    PlayN.run(new DroidAsteroid());
  }
}
