package com.example.handwriting;

import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Duster extends Sprite
{

	public Duster(float pX, float pY, 
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pSpriteVertexBufferObject) 
	{
		super(pX, pY, pTextureRegion, pSpriteVertexBufferObject);
		// TODO Auto-generated constructor stub
	}
	
	public static void createDusterPopUp(int upDown)
	{
		MainActivity.popUpVal = upDown;
		Path createPopUpPath = null;
		
		//Down to up
		if(upDown == 0)
		{
			
			createPopUpPath = new Path(2)
			.to(100, MainActivity.CAMERA_HEIGHT+500).to(100, 100);
			MainActivity.drawingDisabler = 1;

		}
		//Up to down
		else if(upDown == 1) 
		{ 
			createPopUpPath = new Path(2)
			.to(100, 100).to(100, MainActivity.CAMERA_HEIGHT+500);
			MainActivity.drawingDisabler = 0;
		}
		
		
		
		//If screen shot taken, then show screen shot image
		if(MainActivity.changeTexture == 1)
		{
			
		}
		//If no screen shot taken, then show normal image
		else if(MainActivity.changeTexture == 0)
		{
		
		}
			
	}

}
