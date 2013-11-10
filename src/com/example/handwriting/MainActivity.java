package com.example.handwriting;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import android.view.Display;

public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener,IOnAreaTouchListener
{

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	public Camera mCamera;
	public static Scene mScene;
	
	private BuildableBitmapTextureAtlas mBitmapTextureAtlas, mBitmapTextureAtlas1;
	public static ITextureRegion mMoTextureRegion;
	public static ITextureRegion mSprite1TextureRegion, mSprite2TextureRegion, mSprite3TextureRegion, mSprite4TextureRegion;
	
	public static ITextureRegion mbackGroundTextureRegion, mbackGround2TextureRegion;
	
	public static Sprite backGround, backGround2;
	public static Sprite sprite1, sprite2, sprite3, sprite4;
	public static Sprite mo;
	
	public static MainActivity MainActivityInstace;
	public static VertexBufferObjectManager vertexBufferObjectManager;
	
	
	public static MainActivity getSharedInstances()
	{
		return MainActivityInstace; 
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() 
	{
		// TODO Auto-generated method stub
		MainActivityInstace = this;
		Display display = getWindowManager().getDefaultDisplay();
		CAMERA_HEIGHT = display.getHeight();
		CAMERA_WIDTH = display.getWidth();
		
		
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources()
	{
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("HandWritingGfx/");

		mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 1600, 1200);
		mBitmapTextureAtlas1 = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 1600, 1200);

		mbackGroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this, "JungleBG.png");
		mbackGround2TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "JungleBG.png");
		
		mMoTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "mo.png"); 
		
		mSprite1TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "shorea.png");
		mSprite2TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "mo.png");
		mSprite3TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "mo.png");
		mSprite4TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "akar.png");

		try 
		{
			mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,
					BitmapTextureAtlas>(0, 0, 0));
			mBitmapTextureAtlas.load();
		} 
		catch (TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
		try 
		{
			mBitmapTextureAtlas1.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,
					BitmapTextureAtlas>(0, 0, 0));
			mBitmapTextureAtlas1.load();
		} 
		catch (TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
	
	}

	@Override
	protected Scene onCreateScene()
	{
		// TODO Auto-generated method stub
		mScene = new Scene();
		mScene.setBackground(new Background(Color.WHITE));
		
		vertexBufferObjectManager = getVertexBufferObjectManager();
		
		backGround = new Sprite(0, 0, mbackGroundTextureRegion, getVertexBufferObjectManager());
		backGround.setHeight(CAMERA_HEIGHT);
		backGround.setWidth(CAMERA_WIDTH);
		mScene.attachChild(backGround);
		
		sprite1 = new Sprite(CAMERA_WIDTH , MainActivity.CAMERA_HEIGHT / 18, MainActivity.mSprite1TextureRegion,
				MainActivity.vertexBufferObjectManager); 
		mScene.registerTouchArea(sprite1);
		mScene.attachChild(sprite1);
		
		sprite2 = new Sprite(MainActivity.CAMERA_WIDTH +100,MainActivity.CAMERA_HEIGHT / 18, MainActivity.mSprite2TextureRegion,
				MainActivity.vertexBufferObjectManager); 
		mScene.registerTouchArea(sprite2);
		mScene.attachChild(sprite2);
		
		mScene.setOnSceneTouchListener(this);
		mScene.setOnAreaTouchListener(this);
		
		return mScene;
	}

	public void setCurrentScene(Scene scene)
	{
		mScene = scene;
		getEngine().setScene(mScene);
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,ITouchArea pTouchArea, float pTouchAreaLocalX,float pTouchAreaLocalY) 
	{
		// TODO Auto-generated method stub
		if (pSceneTouchEvent.isActionMove()) 
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent ) 
	{
		// TODO Auto-generated method stub
		  if(pSceneTouchEvent.isActionDown() || pSceneTouchEvent.isActionMove())
		  {
			  DrawImage(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			  return true;
          }
          return false;
	}
	
	
	private void DrawImage(float x, float y) 
	{
		// TODO Auto-generated method stub
		sprite4 = new Sprite(x, y, MainActivity.mSprite4TextureRegion,
				MainActivity.vertexBufferObjectManager);
		mScene.attachChild(sprite4);
		sprite4.setScale((float) 0.5);
		
//		sprite3 = new Sprite(x+sprite4.getWidth()/2, y, MainActivity.mSprite3TextureRegion,
//				MainActivity.vertexBufferObjectManager); 
//		mScene.attachChild(sprite3);
//		sprite3.setScale((float) 0.5);
	}

}
