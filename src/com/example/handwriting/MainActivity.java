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
import org.andengine.entity.util.ScreenCapture;
import org.andengine.entity.util.ScreenCapture.IScreenCaptureCallback;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.FileUtils;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.Display;

public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener
{
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT; 
	public Camera mCamera;   
	public static Scene mScene;
	public ScreenCapture screenCapture;
	public static  BuildableBitmapTextureAtlas mBitmapTextureAtlas,
			mBitmapTextureAtlas1, mBitmapTextureAtlas3;
	public static ITextureRegion mBlackBoardTextureRegion,
			mMoOutLineTextureRegion, mPopUpBlackBoardTextureRegion,
			mShowScreenCaptureRegion, mCreatePopUpRegion,
			mCorrectLetterRegion, mDrawnPictureRegion,
			mCrossRegion, mMoExampleTextureRegion;
	public static ITextureRegion mSprite4TextureRegion, mStarTextureRegion, mTutorialTextureRegion;
	public static ITextureRegion mbackGroundTextureRegion, 
			mbackGround2TextureRegion;
	
	public BitmapTextureAtlas mBitmapTextureAtlas2;
	public static TiledTextureRegion mPieceChalkTextureRegion;

	public static Sprite backGround, blackBoard, moOutLine, moExample, tutorial;
	public static Sprite whiteChalk, createPopUp, correctLetter, drawnPicture, cross, board;
	public static PopUp showScreen;
	public static Chalk pieceChalk; 
	public static Sprite tutorialWhiteChalk[] =new Sprite[3000];

	public static MainActivity MainActivityInstace;
	public static VertexBufferObjectManager vertexBufferObjectManager;

	public static int Flag;
	public static TimerHandler timer1;
	
	public static Rectangle rectangle1[] = new Rectangle[500];
	public static int Flag1[] = new int[500];
	public static Sprite star [] = new Sprite[500];

	public static float touchPositionX;
	public static float touchPositionY;
	public static MainActivity getSharedInstances() 
	{
		return MainActivityInstace;
	}
	
	public static int touch, drawLine = 0;
	public static float moOutLineX, moOutLineY, width, thick;
	public static boolean reveal, screenShot = false;
	static Boolean audioPlay = false;
	static MediaPlayer mediaPlayer = new MediaPlayer();

	//Screen Shot texture
	static TextureRegion textureRegion;
	static BitmapTextureAtlas texture;
	static BitmapTextureAtlasSource source;
	static int changeTexture = 0;
	
	//Stars variables
	static int num = 0, aCounter = 0;
	//Tutorial variables
	static int animStart = 0, counter = 0;
	
	//Popup window variables
	public static int popUpVal = 0, drawingDisabler = 0;
	
	@Override
	public EngineOptions onCreateEngineOptions() 
	{
		// TODO Auto-generated method stub
		
		//Initializing and clearing at every beginning of the game
		Flag1[1] = 1;
		Flag = 1;
		
		for(int i=2; i<40; i++)
		{
			Flag1[i] = 0;
		}
		source = null;
		//texture.clearTextureAtlasSources();
		texture = null;
		changeTexture = 0;
				
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
				this.getTextureManager(), 1650, 950);
		mBitmapTextureAtlas1 = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 2600, 2200);
		mBitmapTextureAtlas3 = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 2200, 1800);
		mBitmapTextureAtlas2 = new BitmapTextureAtlas(this.getTextureManager(), 100, 100, TextureOptions.BILINEAR);
		
		mbackGroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas, this, "JungleBG.png");

		mPieceChalkTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas2, this,
				"pieceChalk.png", 0, 0,  1, 1); 
				
		mPopUpBlackBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas1, this,
						"board.png");
		
		mBlackBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas1, this,
						"blackboard.png");

		mMoOutLineTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas1, this,
						"moOutlineCrop.png");

		mSprite4TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas1, this,
						"chalk2.png");
		
		mStarTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas1, this,
						"star.png");
		
		mShowScreenCaptureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas1, this,
						"bookIcon.png");
		
		mCreatePopUpRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas1, this,
						"handwritingbook.png");
		
		mCorrectLetterRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas1, this,
						"moOutlineCrop.png");
		
		mCrossRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas1, this,
						"cross.png");
		
		mDrawnPictureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas1, this,
						"moOutlineCrop.png");
		
		mMoExampleTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas1, this,
						"moExample.png");
		
		mTutorialTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas, this,
						"star.png");
		
		mBitmapTextureAtlas2.load();
		
		try 
		{
			mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 0));
			mBitmapTextureAtlas.load();
		} 
		catch (TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
		
		try 
		{
			mBitmapTextureAtlas3.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 0));
			mBitmapTextureAtlas3.load();
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

		backGround = new Sprite(0, 0, mbackGroundTextureRegion,
				getVertexBufferObjectManager());
		backGround.setHeight(CAMERA_HEIGHT);
		backGround.setWidth(CAMERA_WIDTH);
		mScene.attachChild(backGround);

		moOutLineX = CAMERA_WIDTH / 2 - 130;
		moOutLineY = CAMERA_HEIGHT / 2 - 130;
		
		blackBoard = new Sprite(moOutLineX-160, moOutLineY-85, mBlackBoardTextureRegion,
				getVertexBufferObjectManager());
		blackBoard.setHeight((float) (blackBoard.getHeight()*1.7));
		blackBoard.setWidth((float) (blackBoard.getWidth()*1.5));
		mScene.attachChild(blackBoard);
		

		moOutLine = new Sprite(moOutLineX, moOutLineY, mMoOutLineTextureRegion,
				getVertexBufferObjectManager());
		mScene.attachChild(moOutLine);
		
		tutorial = new Sprite(moOutLineX + 440, moOutLineY, mTutorialTextureRegion,
				getVertexBufferObjectManager())
		{ 
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				switch (pSceneTouchEvent.getAction() ) 
				{ 
				case TouchEvent.ACTION_DOWN :
					if(animStart == 0 && drawingDisabler == 0)
					{
						AnimationHandler.animatedChalk(MainActivity.rectangle1[1].getX(), MainActivity.rectangle1[1].getY()+20, 
								MainActivity.rectangle1[8].getX()+20, MainActivity.rectangle1[8].getY()+20, 
								MainActivity.rectangle1[9].getX(), MainActivity.rectangle1[9].getY()+20, 
								MainActivity.rectangle1[17].getX()+10, MainActivity.rectangle1[17].getY()+10,
								MainActivity.rectangle1[21].getX()+30, MainActivity.rectangle1[21].getY()+20,
								MainActivity.rectangle1[24].getX()+10, MainActivity.rectangle1[24].getY()+60, 
								MainActivity.rectangle1[28].getX()-60, MainActivity.rectangle1[28].getY()+20,
								MainActivity.rectangle1[30].getX()-10, MainActivity.rectangle1[30].getY()+20, 
								MainActivity.rectangle1[32].getX(), MainActivity.rectangle1[32].getY()+20, 
								MainActivity.rectangle1[39].getX(), MainActivity.rectangle1[39].getY()+20);
					}
				break;
				case TouchEvent.ACTION_UP:
					
				break;
				}

				return true;
			}
	
		};
		mScene.registerTouchArea(tutorial);
		mScene.attachChild(tutorial);
		
		reveal = false;
		thick = 3;
		width = moOutLine.getWidth()/10;
		
		//Draw Outline
		DrawOutline.Draw();
		
		//Create Stars
		Stars.createStars();
		
		//Chalk 
		MainActivity.pieceChalk = new Chalk(MainActivity.moOutLineX -10, MainActivity.moOutLineY -80,
				MainActivity.mPieceChalkTextureRegion, MainActivity.MainActivityInstace.getVertexBufferObjectManager());
		MainActivity.mScene.attachChild(MainActivity.pieceChalk);
		pieceChalk.setScale((float) 0.7);
		
//		mScene.registerUpdateHandler(new TimerHandler((float)0.08,true, new ITimerCallback() {
//			
//			@Override
//			public void onTimePassed(TimerHandler pTimerHandler) {
//				// TODO Auto-generated method stub
//			}
//		}));
		
		timer1 = new TimerHandler((float) 1.0f/120,true, new ITimerCallback() 
		{
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				// TODO Auto-generated method stub 
				
				if(animStart == 1)
				{
					MainActivity.DrawImage2(MainActivity.pieceChalk.getX()+20 , MainActivity.pieceChalk.getY() + 50);
				}
				
//				if(tutorialWhiteChalk!= null && AnimationHandler.j == 0) 
//				{
//					mScene.detachChild(tutorialWhiteChalk);
//					//tutorialWhiteChalk.setVisible(false);
//				}
				
				if(!(pieceChalk.getX()>=0))
				{
					Debug.d("chalk create");
					MainActivity.pieceChalk = new Chalk(MainActivity.moOutLineX -10, MainActivity.moOutLineY -80,
							MainActivity.mPieceChalkTextureRegion, MainActivity.MainActivityInstace.getVertexBufferObjectManager());
					MainActivity.mScene.attachChild(MainActivity.pieceChalk);
					pieceChalk.setScale((float) 0.7);
				}
				
				//When there is no interaction with the device, play the animation
				if(touch == 0)
				{	
					//Tutorial showing
					//AnimationHandler.AnimationStart();
				}
				else if(touch == 1)
				{
					pieceChalk.clearEntityModifiers();
				}
				
				//Continuing the star collision
				if(num != 0)
				{
					Stars.starCol(num);
				}
			}
		});
		mScene.registerUpdateHandler(timer1);
		
		mScene.setOnSceneTouchListener(this);
		
		//Pop up window
		showScreen = new PopUp(40, 310, mShowScreenCaptureRegion, getVertexBufferObjectManager());
		mScene.registerTouchArea(showScreen);
		showScreen.setScale((float) 0.6);
		mScene.attachChild(showScreen);
		
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
		if (pSceneTouchEvent.isActionDown() && animStart == 0)
		{
			//The touch is enabled
			touch = 1;
			//For piece chalk to be dragged within letter mo range
			if((pSceneTouchEvent.getX()> moOutLine.getX() && 
					pSceneTouchEvent.getX()< moOutLine.getX()+moOutLine.getWidth()) &&
					(pSceneTouchEvent.getY()> moOutLine.getY() && 
					pSceneTouchEvent.getY()< moOutLine.getY()+moOutLine.getHeight()))
			{
				pieceChalk.setPosition(
						pSceneTouchEvent.getX() - pieceChalk.getWidth() / 2,
						pSceneTouchEvent.getY() - pieceChalk.getHeight() / 2 - 35);
			}
			
			return true;
		} 
		else if (pSceneTouchEvent.isActionMove() && animStart == 0)
		{
			
			touch = 1;
			
			//For drawing white chalk
			if(drawingDisabler == 0)
			{
			DrawImage(pSceneTouchEvent.getX() - 25, 
					pSceneTouchEvent.getY() - 30); 
			}
			//For piece chalk to be dragged within letter mo range 
			if((pSceneTouchEvent.getX()> moOutLine.getX() && 
					pSceneTouchEvent.getX()< moOutLine.getX()+moOutLine.getWidth()) &&
					(pSceneTouchEvent.getY()> moOutLine.getY() && 
					pSceneTouchEvent.getY()< moOutLine.getY()+moOutLine.getHeight()))
			{
				pieceChalk.setPosition(
						pSceneTouchEvent.getX() - pieceChalk.getWidth() / 2,
						pSceneTouchEvent.getY() - pieceChalk.getHeight() / 2 - 35);
			} 
			
			if(whiteChalk!= null) 
			{
				//One by one Collision checker
				CollisionChecker.collisionCheck(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
				//get the position
				Position.getPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			}
			
			if( whiteChalk!=null && whiteChalk.collidesWith(star[1]) && CollisionChecker.val !=0)
			{
				//play sound
				MainActivity.audioPlay = true;
				MainActivity.playAudio(R.raw.star);
				
				mScene.detachChild(star[1]); 
				MainActivity.star[1].setPosition(MainActivity.CAMERA_WIDTH, MainActivity.CAMERA_HEIGHT);
				num=1; 
			} 
			//Checking for the screenshot
			if(Flag1[38]== 1)
			{
				screenCapture = new ScreenCapture();
				mScene.attachChild(screenCapture);
				
				mScene.registerUpdateHandler(new TimerHandler((float)0.5, new ITimerCallback() 
				{
					@Override
					public void onTimePassed(TimerHandler pTimerHandler)
					{
						// TODO Auto-generated method stub
						screenShot();
					}
				}));
			}
			
			return true;  
		}
		else if (pSceneTouchEvent.isActionUp() && animStart == 0) 
		{
				//The touch is disabled
				touch = 0;
		}
		return true;
	}  
	
	//Audio play Function
	public static void playAudio(int val)
	{
		if(audioPlay)
		{
			mediaPlayer = MediaPlayer.create(MainActivityInstace.getBaseContext(), val);
			try 
			{
				mediaPlayer.start();
				mediaPlayer.setLooping(false);
			} 
			catch (IllegalStateException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			audioPlay = true;
		}
	}
	
	public void DrawImage(float x, float y)
	{ 
		// TODO Auto-generated method stub
		whiteChalk = new Sprite(x, y, MainActivity.mSprite4TextureRegion,
				MainActivity.vertexBufferObjectManager); 
		//whiteChalk.setVisible(false);
		mScene.attachChild(MainActivity.whiteChalk);
		whiteChalk.setScale((float) 0.4);
		
	}
	
	public static void DrawImage2(float x, float y)
	{ 
		// TODO Auto-generated method stub
		counter++;
		Debug.d("counter:"+counter);
		tutorialWhiteChalk[counter] = new Sprite(x, y, MainActivity.mSprite4TextureRegion,
				MainActivity.vertexBufferObjectManager); 
		Debug.d("counter:"+counter);
		//whiteChalk.setVisible(false);
		mScene.attachChild(MainActivity.tutorialWhiteChalk[counter]);
		tutorialWhiteChalk[counter].setScale((float) 0.4);
	}
	 
	public void screenShot()
	{
		final int viewWidth = MainActivity.this.mRenderSurfaceView.getWidth() - 525;
		final int viewHeight = MainActivity.this.mRenderSurfaceView.getHeight() - 165;
		
		//final float time = System.currentTimeMillis();
		screenCapture.capture(264, 80, viewWidth, viewHeight,FileUtils.getAbsolutePathOnInternalStorage
				(getApplicationContext(), "/screen"+".jpg") , new IScreenCaptureCallback() 
		{ 
			@Override
			public void onScreenCaptured(final String pFilePath) 
			{
				MainActivity.this.runOnUiThread(new Runnable()
				{
					@Override
					public void run() 
					{
						//Debug.d("Screenshot: " + pFilePath + " taken!");
						changeTexture = 1;
						new setTexture(FileUtils.getAbsolutePathOnInternalStorage
								(getApplicationContext(), "/screen"+".jpg"));
					}
				});
			}

			@Override
			public void onScreenCaptureFailed(final String pFilePath, final Exception pException)
			{
				MainActivity.this.runOnUiThread(new Runnable()
				{
					@Override
					public void run() 
					{
						changeTexture = 0;
					}
				});
			}
		});

	}
	
	public static class setTexture
	{
		public setTexture(String address)
		{
			//this.mDrawnPictureRegion = textureRegion;
			source = new BitmapTextureAtlasSource(
					BitmapFactory.decodeFile(address));
			texture = new BitmapTextureAtlas(MainActivityInstace.getTextureManager(), 1000, 1000);
			texture.addTextureAtlasSource(source, 0, 0);
			texture.load();
			textureRegion = (TextureRegion) TextureRegionFactory.createFromSource(texture, source, 0, 0);
		}
		
	}
	
}
