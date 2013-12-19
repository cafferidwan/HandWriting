package com.example.handwriting;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.ease.EaseBounceOut;

import android.content.Intent;

public class Duster extends Sprite
{

	public Duster(float pX, float pY, 
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pSpriteVertexBufferObject) 
	{
		super(pX, pY, pTextureRegion, pSpriteVertexBufferObject);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
	{
		switch (pSceneTouchEvent.getAction()) 
		{
		case TouchEvent.ACTION_DOWN:

//			if(MainActivity.drawingDisabler == 0 && MainActivity.animStart == 0)
//			{
				createDusterPopUp(0);
//			}
		break;
		
		case TouchEvent.ACTION_UP:

		break;
		
		}

		return true;
	}
	
	public static void createDusterPopUp(int upDown)
	{
		MainActivity.popUpDuster = upDown;
		Path createPopUpPath = null;
		
		//Down to up
		if(upDown == 0)
		{
//			createPopUpPath = new Path(2)
//			.to(MainActivity.CAMERA_WIDTH/2 + 50, 200).to(100, -400);
			
			MainActivity.dusterDisabler = 1;
			MainActivity.duster.setY(-400);
			
			FinishActivity.finishDuster();
		}
		//Up to down
		else if(upDown == 1) 
		{ 
			createPopUpPath = new Path(2)
			.to(MainActivity.CAMERA_WIDTH/2+70, -400).to(MainActivity.CAMERA_WIDTH/2 + 50, 200);
			MainActivity.dusterDisabler = 0;
		
		
		
		MainActivity.duster.registerEntityModifier(new PathModifier((float)4.0, createPopUpPath, null, new IPathModifierListener()
		{
			@Override
			public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) 
			{

			}
			
			@Override
			public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex)
			{

			}

			@Override
			public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) 
			{

			}

			@Override
			public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) 
			{ 
				//drawingDisabler = 0;
			}
		} , EaseBounceOut.getInstance()));
		}
	}

}
