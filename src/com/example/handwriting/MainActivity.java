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

public class MainActivity extends SimpleBaseGameActivity implements
		IOnSceneTouchListener {
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	public Camera mCamera;
	public static Scene mScene;

	private BuildableBitmapTextureAtlas mBitmapTextureAtlas,
			mBitmapTextureAtlas1;
	public static ITextureRegion mBlackBoardTextureRegion,
			mMoOutLineTextureRegion, mPieceChalkTextureRegion;
	public static ITextureRegion mSprite1TextureRegion, mSprite2TextureRegion,
			mSprite3TextureRegion, mSprite4TextureRegion;

	public static ITextureRegion mbackGroundTextureRegion,
			mbackGround2TextureRegion;

	public static Sprite backGround, blackBoard, moOutLine;
	public static Sprite sprite1, sprite2, sprite3, sprite4;
	public static Chalk pieceChalk;

	public static MainActivity MainActivityInstace;
	public static VertexBufferObjectManager vertexBufferObjectManager;

	public static int Flag1, Flag2, Flag3, Flag4, Flag5, Flag6, Flag7, Flag8,
			Flag9, Flag10, Flag11;

	public static Rectangle rectangle1, rectangle2, rectangle3, rectangle4,
			rectangle5, rectangle6, rectangle7, rectangle8, rectangle9,
			rectangle10;

	public static Rectangle rectangle[] = new Rectangle[500];
	public static int Flag[] = new int[500];

	public static float touchPositionX, touchPositionY;

	public static MainActivity getSharedInstances() 
	{
		return MainActivityInstace;
	}

	@Override
	public EngineOptions onCreateEngineOptions() 
	{
		// TODO Auto-generated method stub
		Flag[0] = 1;
		Flag1 = 1;
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

		mSprite1TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "shorea.png");
		mSprite2TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "mo.png");
		mSprite3TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this, "mo.png");
		mSprite4TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas1, this,
						"ChalkRound.png");

		try {
			mBitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 0));
			mBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			mBitmapTextureAtlas1
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 0));
			mBitmapTextureAtlas1.load();
		} catch (TextureAtlasBuilderException e) {
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

		float moOutLineX = CAMERA_WIDTH / 2 - 130;
		float moOutLineY = CAMERA_HEIGHT / 2 - 130;

		moOutLine = new Sprite(moOutLineX, moOutLineY, mMoOutLineTextureRegion,
				getVertexBufferObjectManager());
		mScene.attachChild(moOutLine);

		mScene.setOnSceneTouchListener(this);

		boolean reveal = false;
		float thick = 3;
		float width = moOutLine.getWidth();
		float height = moOutLine.getWidth();

		rectangle1 = Structure(moOutLineX, moOutLineY + 13, width, thick, 0,
				reveal);

		rectangle2 = Structure(moOutLineX + 85, moOutLineY, thick,
				width / 2 + 3, -45, reveal);

		rectangle3 = Structure(moOutLineX + 120, moOutLineY + 110, thick,
				width / 2 - 57, 18, reveal);

		rectangle4 = Structure(moOutLineX + 89, moOutLineY + 167, thick,
				width / 2 - 83, 65, reveal);

		rectangle5 = Structure(moOutLineX + 53, moOutLineY + 159, thick,
				width / 2 - 82, 130, reveal);

		rectangle6 = Structure(moOutLineX + 40, moOutLineY + 135, thick,
				width / 2 - 97, 185, reveal);

		rectangle7 = Structure(moOutLineX + 58, moOutLineY + 110, thick,
				width / 2 - 90, 65, reveal);

		rectangle8 = Structure(moOutLineX + 100, moOutLineY + 100, thick,
				width / 2 - 67, 113, reveal);

		rectangle9 = Structure(moOutLineX + 160, moOutLineY + 125, thick,
				width / 2 - 27, 135, reveal);

		rectangle10 = Structure(moOutLineX + 200, moOutLineY + 5, thick,
				height, 180, reveal);

		touchPositionX = rectangle1.getX();
		touchPositionY = rectangle1.getY();

		pieceChalk = new Chalk(touchPositionX, touchPositionY,
				mPieceChalkTextureRegion, getVertexBufferObjectManager());
		mScene.attachChild(pieceChalk);
		pieceChalk.setScale((float) 0.8);

		return mScene;
	}

	public Rectangle Structure(float x, float d, float w, float h,
			float rotate, boolean reveal)
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
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
	{
		// TODO Auto-generated method stub
		if (pSceneTouchEvent.isActionDown() || pSceneTouchEvent.isActionMove())
		{
			pieceChalk.setPosition(
					pSceneTouchEvent.getX() - pieceChalk.getWidth() / 2,
					pSceneTouchEvent.getY() - pieceChalk.getHeight() / 2 - 35);

			DrawImage(pSceneTouchEvent.getX() - 25,
					pSceneTouchEvent.getY() - 30);

			if (Flag1 == 1 && sprite4.collidesWith(rectangle1)) 
			{
				Flag2 = 1;
			} 
			else if (Flag2 == 1 && sprite4.collidesWith(rectangle2))
			{
				Flag1 = 0;
				Flag2 = 1;
				Flag3 = 1;
			} 
			else if (Flag3 == 1 && sprite4.collidesWith(rectangle3)) 
			{
				Flag1 = 0;
				Flag2 = 0;
				Flag3 = 1;
				Flag4 = 1;
			} 
			else if (Flag4 == 1 && sprite4.collidesWith(rectangle4))
			{
				Flag1 = 0;
				Flag2 = 0;
				Flag3 = 0;
				Flag4 = 1;
				Flag5 = 1;
			} 
			else if (Flag5 == 1 && sprite4.collidesWith(rectangle5)) 
			{
				Flag1 = 0;
				Flag2 = 0;
				Flag3 = 0;
				Flag4 = 0;
				Flag5 = 1;
				Flag6 = 1;
			} 
			else if (Flag6 == 1 && sprite4.collidesWith(rectangle6)) 
			{
				Flag1 = 0;
				Flag2 = 0;
				Flag3 = 0;
				Flag4 = 0;
				Flag5 = 0;
				Flag6 = 1;
				Flag7 = 1;
			}
			else if (Flag7 == 1 && sprite4.collidesWith(rectangle7))
			{
				Flag1 = 0;
				Flag2 = 0;
				Flag3 = 0;
				Flag4 = 0;
				Flag5 = 0;
				Flag6 = 0;
				Flag7 = 1;
				Flag8 = 1;
			} 
			else if (Flag8 == 1 && sprite4.collidesWith(rectangle8))
			{
				Flag1 = 0;
				Flag2 = 0;
				Flag3 = 0;
				Flag4 = 0;
				Flag5 = 0;
				Flag6 = 0;
				Flag7 = 0;
				Flag8 = 1;
				Flag9 = 1;
			}
			else if (Flag9 == 1 && sprite4.collidesWith(rectangle9)) 
			{
				Flag1 = 0;
				Flag2 = 0;
				Flag3 = 0;
				Flag4 = 0;
				Flag5 = 0;
				Flag6 = 0;
				Flag7 = 0;
				Flag8 = 0;
				Flag9 = 1;
				Flag10 = 1;
			}
			else if (Flag10 == 1 && sprite4.collidesWith(rectangle10))
			{
				Flag1 = 0;
				Flag2 = 0;
				Flag3 = 0;
				Flag4 = 0;
				Flag5 = 0;
				Flag6 = 0;
				Flag7 = 0;
				Flag8 = 0;
				Flag9 = 0;
				Flag10 = 1;
				Flag11 = 1;
			} 
			else if (Flag11 == 11)
			{
//				 Flag1 = 0;
//				 Flag2 = 0;
//				 Flag3 = 0;
//				 Flag4 = 0;
//				 Flag5 = 0;
//				 Flag6 = 0;
//				 Flag7 = 0;
//				 Flag8 = 0;
//				 Flag9 = 0;
//				 Flag10 = 0;
//				 Flag11 = 0;
//				 Flag12 = 0;
			} 
			else 
			{
				mScene.detachChild(sprite4);
			}

			return true;
		}
		if (pSceneTouchEvent.isActionUp()) 
		{
			if (Flag1 == 1 && sprite4.collidesWith(rectangle1))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag2 == 1 && sprite4.collidesWith(rectangle2)) 
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag3 == 1 && sprite4.collidesWith(rectangle3)) 
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag4 == 1 && sprite4.collidesWith(rectangle4))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag5 == 1 && sprite4.collidesWith(rectangle5)) 
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag6 == 1 && sprite4.collidesWith(rectangle6))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag7 == 1 && sprite4.collidesWith(rectangle7)) 
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag8 == 1 && sprite4.collidesWith(rectangle8))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag9 == 1 && sprite4.collidesWith(rectangle9))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag10 == 1 && sprite4.collidesWith(rectangle10))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else 
			{
				Chalk.chalkPath(touchPositionX, touchPositionY);
			}
		}
		return true;
	}

	private void DrawImage(float x, float y)
	{
		// TODO Auto-generated method stub
		sprite4 = new Sprite(x, y, MainActivity.mSprite4TextureRegion,
				MainActivity.vertexBufferObjectManager);
		mScene.attachChild(sprite4);
		sprite4.setScale((float) 0.3);

	}

}
