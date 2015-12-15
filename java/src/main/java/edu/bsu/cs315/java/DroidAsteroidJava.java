package edu.bsu.cs315.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import edu.bsu.cs315.core.DroidAsteroid;

public class DroidAsteroidJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new DroidAsteroid());
  }
}
