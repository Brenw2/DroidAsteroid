package edu.bsu.cs315.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import edu.bsu.cs315.core.DroidAsteroid;

public class DroidAsteroidActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new DroidAsteroid());
  }
}
