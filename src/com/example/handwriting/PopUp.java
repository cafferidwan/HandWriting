package com.example.handwriting;

import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.ease.EaseBounceOut;

public class PopUp extends Sprite
{
	
	static IEntity en;
	public static int popUpVal = 0, drawingDisabler = 0;
	
	public PopUp(int i, int j, ITextureRegion mShowScreenCaptureRegion,
			VertexBufferObjectManager vertexBufferObjectManager) 
	{
		super(i, j, mShowScreenCaptureRegion, vertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		
	}

		@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
		{
			switch (pSceneTouchEvent.getAction()) 
			{
			case TouchEvent.ACTION_DOWN:

//				if(popUpVal != 1)
//				{
					createPopUp(0);
//				}
			break;
			
			case TouchEvent.ACTION_UP:

			break;
			
			}

			return true;
		}

	public static void createPopUp(int upDown)
	{
		popUpVal = upDown;
		Path createPopUpPath = null;
		
		//Down to up
		if(upDown == 0)
		{
			en = new Entity(100, MainActivity.CAMERA_HEIGHT+100);
			MainActivity.mScene.attachChild(en);
			createPopUpPath = new Path(2)
			.to(100, MainActivity.CAMERA_HEIGHT+500).to(100, 100);
			drawingDisabler = 1;
//			MainActivity.mScene.unregisterTouchArea(MainActivity.pieceChalk);
			//MainActivity.mScene.unregisterTouchArea(MainActivity.showScreen);

		}
		//Up to down
		else if(upDown == 1) 
		{ 
			createPopUpPath = new Path(2)
			.to(100, 100).to(100, MainActivity.CAMERA_HEIGHT+500);
			drawingDisabler = 0;
			
//			MainActivity.mScene.registerTouchArea(MainActivity.pieceChalk);
			//MainActivity.mScene.registerTouchArea(MainActivity.showScreen);
		}
		
		MainActivity.createPopUp = new Sprite(-500, -300, MainActivity.mCreatePopUpRegion,
				MainActivity.vertexBufferObjectManager);
		MainActivity.createPopUp.setScale((float) 0.5);
		en.attachChild(MainActivity.createPopUp);
		 
		MainActivity.correctLetter = new Sprite(300, 0, MainActivity.mCorrectLetterRegion,
				MainActivity.vertexBufferObjectManager);
		en.attachChild(MainActivity.correctLetter);
		
		MainActivity.drawnPicture = new Sprite(200, 150, MainActivity.mDrawnPictureRegion,
				MainActivity.vertexBufferObjectManager);
		en.attachChild(MainActivity.drawnPicture);
		
		MainActivity.cross = new Sprite(500, -100, MainActivity.mCrossRegion, MainActivity.vertexBufferObjectManager)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				switch (pSceneTouchEvent.getAction()) 
				{
				case TouchEvent.ACTION_DOWN:
					
					createPopUp(1);
					
				break;
				
				case TouchEvent.ACTION_UP:

				break;
				
				}

				return true;
			}
		};
		MainActivity.mScene.registerTouchArea(MainActivity.cross);
		MainActivity.cross.setScale((float) 0.5);
		en.attachChild(MainActivity.cross);

		en.registerEntityModifier(new PathModifier((float)1.3, createPopUpPath, null, new IPathModifierListener()
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