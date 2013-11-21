package com.example.handwriting;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Chalk extends Sprite
{
	public Chalk(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager VertexBufferObject)
	{
		super(pX, pY, pTextureRegion, VertexBufferObject);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
	{
		switch (pSceneTouchEvent.getAction()) 
		{
		case TouchEvent.ACTION_DOWN:
			
			break;
		
		case TouchEvent.ACTION_UP:

			break;
		
		case TouchEvent.ACTION_MOVE:

			//this.setPosition(pSceneTouchEvent.getX() - this.getWidth()/2, pSceneTouchEvent.getY() - this.getHeight()/2);
			break;
		}

		return true;
	} 
	
	//Chalk Paths
	public static void chalkPath(float x, float y )
	{
		final Path chalkPath = new Path(2).to(MainActivity.pieceChalk.getX(), MainActivity.pieceChalk.getY())
				.to(x - 50, y - 90);
		
		MainActivity.pieceChalk.registerEntityModifier(new PathModifier((float)0.4, chalkPath, null, new IPathModifierListener()
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
				
			}
		}));
	}
	
	public static void animatedChalk(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4,
			float x5, float y5, float x6, float y6, float x7, float y7, float x8, float y8,float x9, float y9,
			float x10, float y10)
	{
		final Path chalkPath = new Path(10)
				.to(x1 - 50, y1 - 90).to(x2 - 50, y2 - 90).to(x3 - 50, y3 - 90).to(x4 - 50, y4 - 90)
				.to(x5 - 50, y5 - 90).to(x6 - 50, y6 - 90).to(x7 - 50, y7 - 90).to(x8 - 50, y8 - 90)
				.to(x9 - 50, y9 - 90).to(x10 - 50, y10 - 90);
		
		MainActivity.pieceChalk.registerEntityModifier(new LoopEntityModifier(new PathModifier((float)1.3, chalkPath, null, new IPathModifierListener()
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
				MainActivity.drawLine = 9;
			}
		})));
	}
}
