package com.example.handwriting;

import org.andengine.entity.sprite.Sprite;
import org.andengine.util.debug.Debug;

public class Stars 
{
	static int num = 0, aCounter = 0; 
	
	public static void createStars()
	{
		for(int i=1; i<5; i++)
		{ 
			MainActivity.star[i] = new Sprite(MainActivity.moOutLineX+ 53*i , MainActivity.moOutLineY - 12, 
					MainActivity.mStarTextureRegion, MainActivity.vertexBufferObjectManager);
			MainActivity.star[i].setScale((float) 0.7);
			MainActivity.mScene.attachChild(MainActivity.star[i]);
		}
		
		for(int i=5; i<7; i++)
		{
			MainActivity.star[i] = new Sprite(MainActivity.moOutLineX - 130
					+ 38 * i, MainActivity.moOutLineY - 200 + 48 * i, 
					MainActivity.mStarTextureRegion, MainActivity.vertexBufferObjectManager);
			MainActivity.star[i].setScale((float) 0.7);
			MainActivity.mScene.attachChild(MainActivity.star[i]);
		}
		
		for(int i=7; i<9; i++)  
		{ 
			MainActivity.star[i] = new Sprite(MainActivity.moOutLineX
					+ 530 - 62 * i, MainActivity.moOutLineY -150 + 40 * i, 
					MainActivity.mStarTextureRegion, MainActivity.vertexBufferObjectManager);
			MainActivity.star[i].setScale((float) 0.7);
			MainActivity.mScene.attachChild(MainActivity.star[i]);  
		}
		
		for(int i=9; i<11; i++) 
		{  
			MainActivity.star[i] = new Sprite(MainActivity.moOutLineX
					-390 +45 * i, MainActivity.moOutLineY +495 -40 * i,
					MainActivity.mStarTextureRegion, MainActivity.vertexBufferObjectManager);
			MainActivity.star[i].setScale((float) 0.7);
			MainActivity.mScene.attachChild(MainActivity.star[i]);  
		}
		
		for(int i=11; i<13; i++)
		{
			MainActivity.star[i] = new Sprite(MainActivity.moOutLineX - 315
					+ 38 * i, MainActivity.moOutLineY - 420 + 48 * i, 
					MainActivity.mStarTextureRegion, MainActivity.vertexBufferObjectManager);
			MainActivity.star[i].setScale((float) 0.7);
			MainActivity.mScene.attachChild(MainActivity.star[i]);
		}
		
		for(int i=13; i<18; i++)
		{
			MainActivity.star[i] = new Sprite(MainActivity.moOutLineX + 170, MainActivity.moOutLineY
					+ 995 - 60 * i, 
					MainActivity.mStarTextureRegion, MainActivity.vertexBufferObjectManager);
			MainActivity.star[i].setScale((float) 0.7);
			MainActivity.mScene.attachChild(MainActivity.star[i]);
		}
		
		MainActivity.star[1].setVisible(true); 
		
		for(int j=2; j<18; j++)
		{
			MainActivity.star[j].setVisible(false);
		} 
	}
	
	public static void starCol(int n)
	{  
		//Count stars till the last
		if(n<17)
		{
			aCounter++;
			if(aCounter==1)
			{
				num = n;   
				num++;
			}
			
			
			MainActivity.star[num].setVisible(true);
		
			Debug.d("star:"+num); 
		
			if(MainActivity.star[num].getX()<MainActivity.CAMERA_WIDTH && 
			MainActivity.whiteChalk.collidesWith(MainActivity.star[num]) && CollisionChecker.val !=0 )
			{
				//play the sound
				MainActivity.audioPlay = true;
				MainActivity.playAudio(R.raw.star);
			
				MainActivity.mScene.detachChild(MainActivity.star[num]);
				MainActivity.star[num].setPosition(MainActivity.CAMERA_WIDTH, MainActivity.CAMERA_HEIGHT);
				Debug.d("n:"+num);
				aCounter=0;
			}
		}
		else
		{
			//num = 0;
			aCounter=0;
		}
		
	}
}
