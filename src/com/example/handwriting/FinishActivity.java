package com.example.handwriting;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.Sprite;

import android.content.Intent;

public class FinishActivity 
{

	public static void finishDuster() 
	{

		MainActivity.slidingScreen = new Sprite(0, -500, MainActivity.mSlidingScreenTextureRegion, MainActivity.vertexBufferObjectManager);
		MainActivity.mScene.attachChild(MainActivity.slidingScreen);
		
		Path finishingPath = new Path(2).to(0, 0).to(MainActivity.CAMERA_WIDTH  + 50, 0);

		MainActivity.slidingScreen.registerEntityModifier(new PathModifier((float) 2.0, finishingPath, null, new IPathModifierListener()
				{
					@Override
					public void onPathStarted(final PathModifier pPathModifier,final IEntity pEntity) 
					{

					}

					@Override
					public void onPathWaypointStarted(final PathModifier pPathModifier,final IEntity pEntity, final int pWaypointIndex) 
					{

					}

					@Override
					public void onPathWaypointFinished(final PathModifier pPathModifier,final IEntity pEntity, final int pWaypointIndex) 
					{

					}

					@Override
					public void onPathFinished(final PathModifier pPathModifier,final IEntity pEntity)
					{
						MainActivity.MainActivityInstace.finish();
						MainActivity.MainActivityInstace.startActivity(new Intent(MainActivity.MainActivityInstace.getBaseContext(),
								MainActivity.class));
					}
				}));
	}
}
