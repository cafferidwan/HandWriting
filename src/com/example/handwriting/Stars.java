package com.example.handwriting;

import org.andengine.entity.sprite.Sprite;

public class Stars 
{
	public static void createStars()
	{
		for(int i=1; i<4; i++)
		{
			MainActivity.star[i] = new Sprite(MainActivity.moOutLineX+ 57*i, MainActivity.moOutLineY - 12, 
					MainActivity.mStarTextureRegion, MainActivity.vertexBufferObjectManager);
			MainActivity.star[i].setScale((float) 0.7);
			MainActivity.mScene.attachChild(MainActivity.star[i]);
		}
	}
}
