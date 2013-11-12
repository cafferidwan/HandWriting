package com.example.handwriting;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Ellipse;
import org.andengine.entity.primitive.PolyLine;
import org.andengine.entity.primitive.Polygon;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
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

import android.graphics.Rect;
import android.view.Display;

public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener
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
	
	public static MainActivity MainActivityInstace;
	public static VertexBufferObjectManager vertexBufferObjectManager;
	
	public static boolean moFalg = false, moFalg1 = false;;
	
	Rectangle rectangle1, rectangle2, rectangle3, rectangle4, rectangle5, rectangle6, rectangle7, 
	rectangle8, rectangle9, rectangle10;
	
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
				.createFromAsset(this.mBitmapTextureAtlas, this, "writingLayout.png");
		mbackGround2TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "moOutline.png");
		
		mMoTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "mo.png"); 
		
		mSprite1TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "shorea.png");
		mSprite2TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "mo.png");
		mSprite3TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "mo.png");
		mSprite4TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "chalkimg.png");

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
		
//		backGround2 = new Sprite(100, 0, mbackGround2TextureRegion, getVertexBufferObjectManager());
//		backGround2.setHeight(CAMERA_HEIGHT);
//		backGround2.setWidth(CAMERA_WIDTH);
//		mScene.attachChild(backGround2);
//		backGround2.setScale((float) 0.5);
		
//		sprite1 = new Sprite(CAMERA_WIDTH - 550, MainActivity.CAMERA_HEIGHT / 18 + 15, MainActivity.mSprite1TextureRegion,
//				MainActivity.vertexBufferObjectManager); 
//		mScene.registerTouchArea(sprite1);
//		mScene.attachChild(sprite1);
//		sprite1.setScale((float) 0.4);
//		//sprite1.setVisible(false);
//		
//		sprite2 = new Sprite(MainActivity.CAMERA_WIDTH - 400, MainActivity.CAMERA_HEIGHT / 18 + 15, MainActivity.mSprite2TextureRegion,
//				MainActivity.vertexBufferObjectManager); 
//		mScene.registerTouchArea(sprite2);
//		mScene.attachChild(sprite2);
//		sprite2.setScale((float) 0.4);
//		//sprite2.setVisible(false);
		
		mScene.setOnSceneTouchListener(this);
		

		boolean bool = false;
		
		rectangle1 = Structure( 270, CAMERA_HEIGHT/2 - 125, 250, 10, 0, bool);
		
		rectangle2 = Structure( 349, CAMERA_HEIGHT/2 - 135, 10, 130, -50, bool);
		
		rectangle3 = Structure( 390,  CAMERA_HEIGHT/2 - 25, 10, 70, 18, bool);
		
		rectangle4 = Structure( 356,  CAMERA_HEIGHT/2 + 30, 10, 50, 65, bool);
		
		rectangle5 = Structure( 310, CAMERA_HEIGHT/2 + 30, 10, 45, 115, bool);
		 
		rectangle6 = Structure( 292, CAMERA_HEIGHT/2 , 10, 30, 185, bool);
		
		rectangle7 = Structure( 322, CAMERA_HEIGHT/2 - 30, 10, 50, 65, bool);
		
		rectangle8 = Structure( 370, CAMERA_HEIGHT/2 - 30, 10, 60, 123, bool);
		
		rectangle9 = Structure( 430, CAMERA_HEIGHT/2 , 10, 100, 135, bool);
		
		rectangle10 = Structure( 482, CAMERA_HEIGHT/2 - 125, 10, 255, 180, bool);
		
//		mScene.registerUpdateHandler(new TimerHandler((float) 0.08, true, new ITimerCallback() 
//		{
//			@Override
//			public void onTimePassed(TimerHandler pTimerHandler)
//			{
//				// TODO Auto-generated method stub
//				
//			}
//		}));
		
		return mScene;
	}
	
	public Rectangle Structure( float x, float y, float w, float h, float rotate, boolean bool)
	{
		Rectangle rect = new Rectangle(x, y, w, h, vertexBufferObjectManager);
		rect.setColor(Color.BLUE);
		rect.setRotation(rotate);
		rect.setVisible(bool);
		mScene.attachChild(rect);
		
		return rect;
	}

	public void setCurrentScene(Scene scene)
	{
		mScene = scene;
		getEngine().setScene(mScene);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent ) 
	{
		// TODO Auto-generated method stub
		  if(pSceneTouchEvent.isActionDown() || pSceneTouchEvent.isActionMove())
		  {
			  DrawImage(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			  
			  if(sprite4.collidesWith(rectangle1) || sprite4.collidesWith(rectangle2)|| 
				 sprite4.collidesWith(rectangle3) || sprite4.collidesWith(rectangle4)||
				 sprite4.collidesWith(rectangle5) || sprite4.collidesWith(rectangle6)||
				 sprite4.collidesWith(rectangle7) || sprite4.collidesWith(rectangle8)||
				 sprite4.collidesWith(rectangle9) || sprite4.collidesWith(rectangle10)
				 )
			  {
				  Debug.d("Collision1");
				  moFalg = true;
			  }
			  else
			  { 
				  mScene.detachChild(sprite4);
				  moFalg = false;
			  }
			  
				
			  return true;
          }
          return false;
	}
	
	
	private void DrawImage(float x, float y) 
	{
		// TODO Auto-generated method stub
		sprite4 = new Sprite(x, y-10, MainActivity.mSprite4TextureRegion,
				MainActivity.vertexBufferObjectManager);
		mScene.attachChild(sprite4);
		sprite4.setScale((float) 0.5);
		
//		sprite3 = new Sprite(x+sprite4.getWidth()/2, y, MainActivity.mSprite3TextureRegion,
//				MainActivity.vertexBufferObjectManager); 
//		mScene.attachChild(sprite3);
//		sprite3.setScale((float) 0.5);
	}

}
