package com.example.handwriting;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
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

import android.view.Display;

public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener
{

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	public Camera mCamera;
	public static Scene mScene;
	
	private BuildableBitmapTextureAtlas mBitmapTextureAtlas, mBitmapTextureAtlas1;
	public static ITextureRegion mBlackBoardTextureRegion, mMoOutLineTextureRegion;
	public static ITextureRegion mSprite1TextureRegion, mSprite2TextureRegion, mSprite3TextureRegion, mSprite4TextureRegion;
	
	public static ITextureRegion mbackGroundTextureRegion, mbackGround2TextureRegion;
	
	public static Sprite backGround, backGround2, blackBoard, moOutLine;
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
	
	//private PhysicsWorld mPhysicsWorld;
	
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
				.createFromAsset(this.mBitmapTextureAtlas1, this, "moOutlineCrop.png");
		
		mBlackBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "moOutlineCrop.png"); 
		
		mMoOutLineTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "moOutlineCrop.png");
		
		mSprite1TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "shorea.png");
		mSprite2TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "mo.png");
		mSprite3TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "mo.png");
		mSprite4TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "ChalkRound.png");

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
		
//		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
//		this.mScene.registerUpdateHandler(this.mPhysicsWorld);
		
		
		vertexBufferObjectManager = getVertexBufferObjectManager();
		
		backGround = new Sprite(0, 0, mbackGroundTextureRegion, getVertexBufferObjectManager());
		backGround.setHeight(CAMERA_HEIGHT);
		backGround.setWidth(CAMERA_WIDTH);
		mScene.attachChild(backGround);
		
		float moOutLineX = CAMERA_WIDTH/2 - 130 ;
		float moOutLineY = CAMERA_HEIGHT/2 - 130 ;
		
		moOutLine = new Sprite(moOutLineX, moOutLineY, mMoOutLineTextureRegion, getVertexBufferObjectManager());
//		moOutLine.setHeight(CAMERA_HEIGHT/2);
//		moOutLine.setWidth(CAMERA_WIDTH/2);
		mScene.attachChild(moOutLine);
		//moOutLine.setScale((float) 0.7);
		
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

//		final Rectangle ground = new Rectangle(0, CAMERA_HEIGHT - 2, CAMERA_WIDTH, 2, vertexBufferObjectManager);
//		final Rectangle roof = new Rectangle(0, 0, CAMERA_WIDTH, 2, vertexBufferObjectManager);
//		final Rectangle left = new Rectangle(0, 0, 2, CAMERA_HEIGHT, vertexBufferObjectManager);
//		final Rectangle right = new Rectangle(CAMERA_WIDTH - 2, 0, 2, CAMERA_HEIGHT, vertexBufferObjectManager);
//
//		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.0f, 0.5f);
//		PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
//		PhysicsFactory.createBoxBody(this.mPhysicsWorld, roof, BodyType.StaticBody, wallFixtureDef);
//		PhysicsFactory.createBoxBody(this.mPhysicsWorld, left, BodyType.StaticBody, wallFixtureDef);
//		PhysicsFactory.createBoxBody(this.mPhysicsWorld, right, BodyType.StaticBody, wallFixtureDef);
//
//		this.mScene.attachChild(ground);
//		this.mScene.attachChild(roof);
//		this.mScene.attachChild(left);
//		this.mScene.attachChild(right);
		
		boolean reveal = false;
		float thick = 3;
		float width = moOutLine.getWidth();
		float height = moOutLine.getWidth();
		Debug.d("mo.x:"+moOutLineX);//270
		Debug.d("mo.y:"+moOutLineY);//86
		Debug.d("mo.width:"+width );//127
		//cam.wid/2 = 216
		
		rectangle1 = Structure( moOutLineX, moOutLineY + 13, width, thick, 0, reveal);
		
		rectangle2 = Structure( moOutLineX + 85, moOutLineY, thick, width/2 + 3, -45, reveal);
		
		rectangle3 = Structure( moOutLineX + 120,  moOutLineY + 110, thick, width/2 - 57, 18, reveal);
		
		rectangle4 = Structure( moOutLineX + 89,  moOutLineY + 167, thick, width/2 - 83, 65, reveal);
		
		rectangle5 = Structure( moOutLineX + 53, moOutLineY + 159 , thick, width/2 - 82, 130, reveal);
		 
		rectangle6 = Structure( moOutLineX + 40, moOutLineY + 135 , thick, width/2 - 97, 185, reveal);
		
		rectangle7 = Structure( moOutLineX + 58, moOutLineY + 110, thick, width/2 - 90, 65, reveal);
		
		rectangle8 = Structure( moOutLineX + 100, moOutLineY + 100, thick, width/2 - 67, 113, reveal);
		
		rectangle9 = Structure( moOutLineX + 160, moOutLineY + 125 , thick, width/2 - 27, 135, reveal);
		
		rectangle10 = Structure( moOutLineX + 200, moOutLineY + 5, thick, height, 180, reveal);
		
		return mScene;
	}
	
	public Rectangle Structure( float x, float d, float w, float h, float rotate, boolean reveal)
	{
		Rectangle rect = new Rectangle(x, d, w, h, vertexBufferObjectManager);
		rect.setColor(Color.BLUE);
		rect.setRotation(rotate);
		rect.setVisible(reveal);
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
			  DrawImage(pSceneTouchEvent.getX()-25, pSceneTouchEvent.getY()-30);
			  
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
		sprite4 = new Sprite(x, y, MainActivity.mSprite4TextureRegion,
				MainActivity.vertexBufferObjectManager);
		mScene.attachChild(sprite4);
		sprite4.setScale((float) 0.3);
		
//		sprite3 = new Sprite(x+sprite4.getWidth()/2, y, MainActivity.mSprite3TextureRegion,
//				MainActivity.vertexBufferObjectManager); 
//		mScene.attachChild(sprite3);
//		sprite3.setScale((float) 0.5);
	}

}
