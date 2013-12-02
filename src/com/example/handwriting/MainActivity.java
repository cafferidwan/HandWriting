package com.example.handwriting;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.Display;

public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener
{
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	public Camera mCamera;   
	public static Scene mScene;
	public ScreenCapture screenCapture;
	public static  BuildableBitmapTextureAtlas mBitmapTextureAtlas,
			mBitmapTextureAtlas1;
	public BuildableBitmapTextureAtlas mBitmapTextureAtlas2;
	public static ITextureRegion mBlackBoardTextureRegion,
			mMoOutLineTextureRegion, mPieceChalkTextureRegion, 
			mShowScreenCaptureRegion, mCreatePopUpRegion,
			mCorrectLetterRegion, mDrawnPictureRegion,
			mCrossRegion;
	public static ITextureRegion mSprite4TextureRegion, mStarTextureRegion;
	public static ITextureRegion mbackGroundTextureRegion,
			mbackGround2TextureRegion;

	public static Sprite backGround, blackBoard, moOutLine;
	public static Sprite whiteChalk, createPopUp, correctLetter, drawnPicture, cross;
	public static PopUp showScreen;
	public static Chalk pieceChalk;

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
		mBitmapTextureAtlas2 = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 1600, 1200);
		
		mbackGroundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas, this, "JungleBG.png");

		mPieceChalkTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(MainActivity.mBitmapTextureAtlas, this,
						"pieceChalk.png");

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
				.createFromAsset(this.mBitmapTextureAtlas2, this,
						"bookIcon.png");
		
		mCreatePopUpRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas2, this,
						"handwritingbook.png");
		
		mCorrectLetterRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas2, this,
						"moOutlineCrop.png");
		
//		mDrawnPictureRegion = BitmapTextureAtlasTextureRegionFactory
//				.createFromAsset(this.mBitmapTextureAtlas2, this,
//						"moOutlineCrop.png");
		
		mCrossRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas2, this,
						"cross.png");
		
		mDrawnPictureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas2, this,
						"moOutlineCrop.png");

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
		
		try 
		{
			mBitmapTextureAtlas2.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,
					BitmapTextureAtlas>(0, 0, 0));
			mBitmapTextureAtlas2.load();
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
		
		blackBoard = new Sprite(moOutLineX-160, moOutLineY-58, mBlackBoardTextureRegion,
				getVertexBufferObjectManager());
		blackBoard.setHeight((float) (blackBoard.getHeight()*1.5));
		blackBoard.setWidth((float) (blackBoard.getWidth()*1.5));
		mScene.attachChild(blackBoard);
		

		moOutLine = new Sprite(moOutLineX, moOutLineY, mMoOutLineTextureRegion,
				getVertexBufferObjectManager());
		mScene.attachChild(moOutLine);
		
		reveal = false;
		thick = 3;
		width = moOutLine.getWidth()/10;
		
		//Draw Outline
		DrawOutline.Draw();
		
		Stars.createStars();
		
		//Chalk 
		MainActivity.pieceChalk = new Chalk(MainActivity.moOutLineX -10, MainActivity.moOutLineY -80,
				MainActivity.mPieceChalkTextureRegion, MainActivity.MainActivityInstace.getVertexBufferObjectManager());
		MainActivity.mScene.attachChild(MainActivity.pieceChalk);
		pieceChalk.setScale((float) 0.7);
		
		
		timer1 = new TimerHandler((float) 1.0f/120,true, new ITimerCallback() 
		{
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				// TODO Auto-generated method stub 
				if(!(pieceChalk.getX()>=0))
				{
					Debug.d("chalk create");
					MainActivity.pieceChalk = new Chalk(MainActivity.moOutLineX -10, MainActivity.moOutLineY -80,
							MainActivity.mPieceChalkTextureRegion, MainActivity.MainActivityInstace.getVertexBufferObjectManager());
					MainActivity.mScene.attachChild(MainActivity.pieceChalk);
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
				if(Stars.num != 0)
				{
					Stars.starCol(Stars.num);
				}
			}
		});
		mScene.registerUpdateHandler(timer1);
		
		mScene.setOnSceneTouchListener(this);
		
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
		if (pSceneTouchEvent.isActionDown() )
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
			
//			if(PopUp.popUpVal == 0)
//			{
//				PopUp.createPopUp(1);
//			}
			
			return true;
		} 
		else if (pSceneTouchEvent.isActionMove())
		{
			
			touch = 1;
			
			//For drawing white chalk
			if(PopUp.drawingDisabler == 0)
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
			}
			//get the position
			Position.getPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			
			if(whiteChalk.collidesWith(star[1]) && CollisionChecker.val !=0)
			{
				//play sound
				MainActivity.audioPlay = true;
				MainActivity.playAudio(R.raw.star);
				
				mScene.detachChild(star[1]); 
				MainActivity.star[1].setPosition(MainActivity.CAMERA_WIDTH, MainActivity.CAMERA_HEIGHT);
				Stars.num=1; 
			} 
			//Checking for the screenshot
			if(Flag1[36]== 1)
			{
				screenCapture = new ScreenCapture();
				mScene.attachChild(screenCapture);
			}
			if(Flag1[38]== 1)
			{
				screenShot();
			} 
			
			return true;
		}
		else if (pSceneTouchEvent.isActionUp()) 
		{
				//The touch is disabled
				touch = 0;
		}
		return true;
	}  
	
	
	public static void createImageFromBitmap(Bitmap bmp)
    {
    	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
		File file = new File( Environment.getExternalStorageDirectory() + "/capturedscreen67.jpg");
		try 
		{
			file.createNewFile();
			FileOutputStream ostream = new FileOutputStream(file);
			ostream.write(bytes.toByteArray());        
			ostream.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}    
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
	 
	public void screenShot()
	{
		final int viewWidth = MainActivity.this.mRenderSurfaceView.getWidth() - 250;
		final int viewHeight = MainActivity.this.mRenderSurfaceView.getHeight() - 100;
		
		screenCapture.capture(150, 50, viewWidth, viewHeight, Environment.getExternalStorageDirectory() + 
				"/screen"+System.currentTimeMillis()+".jpg", new IScreenCaptureCallback() 
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
						//setTextureRegion(screenCapture);
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
						
					}
				});
			}
		});
		
	}
	public void setTextureRegion(ScreenCapture screenCapture2)
	{
	    //this.mDrawnPictureRegion = textureRegion;
		mDrawnPictureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas2, this,
						 Environment.getExternalStorageDirectory() + 
							"/screen"+System.currentTimeMillis()+".jpg");
//		Bitmap bmImg = BitmapFactory.decodeFile( Environment.getExternalStorageDirectory() + 
//				"/screen"+System.currentTimeMillis()+".jpg");

//		BitmapTextureAtlas texture = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024);
//		mBeanRegion = BitmapTextureAtlasTextureRegionFactory.createFromSource(texture, FileBitmapTextureAtlasSource.createFromInternalStorage(this, "bean.png", 0, 0), 0, 0);
//		texture.load();
	}
}
