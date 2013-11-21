package com.example.handwriting;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
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

	private BuildableBitmapTextureAtlas mBitmapTextureAtlas,
			mBitmapTextureAtlas1;
	public static ITextureRegion mBlackBoardTextureRegion,
			mMoOutLineTextureRegion, mPieceChalkTextureRegion;
	public static ITextureRegion mSprite4TextureRegion;
	public static ITextureRegion mbackGroundTextureRegion,
			mbackGround2TextureRegion;

	public static Sprite backGround, blackBoard, moOutLine;
	public static Sprite whiteChalk;
	public static Chalk pieceChalk;

	public static MainActivity MainActivityInstace;
	public static VertexBufferObjectManager vertexBufferObjectManager;

	public static int Flag;
	public static TimerHandler timer1;
	
	public static Rectangle rectangle1[] = new Rectangle[500];
	public static int Flag1[] = new int[500];

	public static float touchPositionX;
	public static float touchPositionY;
	public static MainActivity getSharedInstances() 
	{
		return MainActivityInstace;
	}
	
	public static int touch, drawLine = 0;
	public static float moOutLineX, moOutLineY, width, thick;
	public static boolean reveal;

	@Override
	public EngineOptions onCreateEngineOptions() 
	{
		// TODO Auto-generated method stub
		Flag1[1] = 1;
		Flag = 1;
		MainActivityInstace = this;
		Display display = getWindowManager().getDefaultDisplay();
		CAMERA_HEIGHT = display.getHeight();
		CAMERA_WIDTH = display.getWidth();

		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources() 
	{
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("HandWritingGfx/");

		mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 1600, 1200);
		mBitmapTextureAtlas1 = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 1600, 1200);

		mbackGroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this, "JungleBG.png");

		mPieceChalkTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this,
						"pieceChalk.png");

		mBlackBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this,
						"moOutlineCrop.png");

		mMoOutLineTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this,
						"moOutlineCrop.png");

		mSprite4TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this,
						"chalk2.png");

		try 
		{
			mBitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 0));
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
		mScene.setOnSceneTouchListener(this);
		vertexBufferObjectManager = getVertexBufferObjectManager();

		backGround = new Sprite(0, 0, mbackGroundTextureRegion,
				getVertexBufferObjectManager());
		backGround.setHeight(CAMERA_HEIGHT);
		backGround.setWidth(CAMERA_WIDTH);
		mScene.attachChild(backGround);

		moOutLineX = CAMERA_WIDTH / 2 - 130;
		moOutLineY = CAMERA_HEIGHT / 2 - 130;

		moOutLine = new Sprite(moOutLineX, moOutLineY, mMoOutLineTextureRegion,
				getVertexBufferObjectManager());
		mScene.attachChild(moOutLine);
		

		reveal = false; 
		thick = 3;
		width = moOutLine.getWidth()/10;
		
		//Draw Outline
		DrawOutline.Draw();
		
		timer1 = new TimerHandler((float) 1.0f/120,true, new ITimerCallback() 
		{
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				// TODO Auto-generated method stub 
//				if(!(pieceChalk.getX()>=0))
//				{
//					Debug.d("chalk.x nan");
//					//Chalk 
//					pieceChalk = new Chalk(200,200,
//							mPieceChalkTextureRegion, getVertexBufferObjectManager());
//					mScene.attachChild(pieceChalk);
//					pieceChalk.setScale((float) 0.8);
//				}
				//When there is no interaction with the device, play the animation
				if(touch == 0)
				{
					AnimationHandler.AnimationStart();
				}
				else if(touch == 1)
				{
					pieceChalk.clearEntityModifiers();
				}
			}
		});
		mScene.registerUpdateHandler(timer1);
		return mScene;
	}

	public void setCurrentScene(Scene scene)
	{
		mScene = scene;
		getEngine().setScene(mScene);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
	{
		// TODO Auto-generated method stub
		if (pSceneTouchEvent.isActionDown() || pSceneTouchEvent.isActionMove())
		{
			//The touch is enabled
			touch = 1;
			//For piece chalk to be dragged within letter mo range
			if(pSceneTouchEvent.getX()> moOutLine.getX() && 
					pSceneTouchEvent.getX()< moOutLine.getX()+moOutLine.getWidth() &&
					pSceneTouchEvent.getY()> moOutLine.getY() && 
					pSceneTouchEvent.getY()< moOutLine.getY()+moOutLine.getHeight())
			{
				pieceChalk.setPosition(
						pSceneTouchEvent.getX() - pieceChalk.getWidth() / 2,
						pSceneTouchEvent.getY() - pieceChalk.getHeight() / 2 - 35);
			}
			else
			{
				//Do nothing
			}
			
			//For drawing white chalk
			AnimationHandler.DrawImage(pSceneTouchEvent.getX() - 25, 
					pSceneTouchEvent.getY() - 30);
			//One by one Collision checker
			CollisionChecker.collisionCheck(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());

			return true;
		}
		if (pSceneTouchEvent.isActionUp() || pSceneTouchEvent.isActionMove()) 
		{
			if(pSceneTouchEvent.isActionUp())
			{
				//The touch is disabled
				touch = 0;
			}
			//get the position
			Position.getPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
		}
		return true;
	}
}
