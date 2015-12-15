package edu.bsu.cs315.core;

import edu.bsu.cs315.core.SpaceMan;
import edu.bsu.cs315.core.Platform;
import edu.bsu.cs315.core.Bullet;

public interface FixtureUserData {

	public interface Visitor {
		public void visit(SpaceMan man);

		public void visit(Platform platform);
		
		public void visit(Bullet bullet);
		
		public void visit(RightWall rWall);
		
		public void visit(LeftWall lWall);
		
		public void visit(Robot robot);

		public static abstract class Adapter implements Visitor {
			@Override
			public void visit(SpaceMan man) {
			}

			@Override
			public void visit(Bullet bullet) {
			}
			
			@Override
			public void visit(Platform platform)
			{
				
			}
			
			@Override
			public void visit(RightWall rWall)
			{
				
			}
			
			@Override
			public void visit(LeftWall lWall)
			{
				
			}
			
			@Override
			public void visit(Robot robot)
			{
				
			}
		}
	}

	void accept(Visitor v);
}

