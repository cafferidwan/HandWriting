package com.example.handwriting;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;

public class Tutorial 
{

	public static void tutorial(int x, int y)
	{
			Path chalkPath = null;
			
			if(x <= 2)
			{
				chalkPath = new Path(10)
				.to(x - 50, y - 90).to(x - 50, y - 90);
			}
			else if(y >= 2)
			{
				chalkPath = new Path(10)
				.to(x - 150, y - 290).to(x - 110, y - 330);
			}
			MainActivity.pieceChalk.registerEntityModifier(new PathModifier((float)1.5, chalkPath, null, new IPathModifierListener()
			{
				@Override
				public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) 
				{
					//Debug.d("onPathStarted"); 
				}

				@Override
				public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex)
				{
					//Debug.d("onPathWaypointStarted:  " + pWaypointIndex);
				}

				@Override
				public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) 
				{
					//Debug.d("onPathWaypointFinished: " + pWaypointIndex);
				}

				@Override
				public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) 
				{ 
					{
						
					}
				}
			}));
		}
}
