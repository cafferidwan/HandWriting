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
import android.content.Intent;
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
	public TimerHandler timer1;
	
	public static Rectangle rectangle1[] = new Rectangle[500];
	public static int Flag1[] = new int[500];

	public float touchPositionX, touchPositionY;
	public static MainActivity getSharedInstances() 
	{
		return MainActivityInstace;
	}
	
	public static int touch, drawLine = 0;

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

		float moOutLineX = CAMERA_WIDTH / 2 - 130;
		float moOutLineY = CAMERA_HEIGHT / 2 - 130;

		moOutLine = new Sprite(moOutLineX, moOutLineY, mMoOutLineTextureRegion,
				getVertexBufferObjectManager());
		mScene.attachChild(moOutLine);
		

		boolean reveal = false; 
		float thick = 3;
		float width = moOutLine.getWidth()/10;
		
		//1st line 
		for(int i=1; i<9; i++)
		{
			rectangle1[i] = Structure(moOutLineX+ 25*i, moOutLineY + 13, width, thick, 0,
				reveal);
		}
		
		touchPositionX = rectangle1[1].getX();
		touchPositionY = rectangle1[1].getY(); 

		//Chalk 
		pieceChalk = new Chalk(200,200,
				mPieceChalkTextureRegion, getVertexBufferObjectManager());
		mScene.attachChild(pieceChalk);
		//mScene.registerUpdateHandler(pieceChalk);
		//pieceChalk.setScale((float) 0.8);
		
		//2nd line
		for(int i=9; i<17; i++)
		{
			rectangle1[i] = Structure(moOutLineX-55 + 11*i , moOutLineY - 84 + 11*i, thick,
					width , -45, reveal); 
		}
		//3rd line
		for(int i=17; i<20; i++) 
		{
			rectangle1[i] = Structure(moOutLineX + 244 - 7*i, moOutLineY - 227 + 20*i, thick,
					width , 18, reveal); 
		}
		//4th line
		for(int i=20; i<22; i++)
		{
			rectangle1[i] = Structure(moOutLineX + 460 - 18*i, moOutLineY + 10 + 8*i, thick,
					width , 70, reveal);
		}
		//5th line
		for(int i=22; i<24; i++)
		{
			rectangle1[i] = Structure(moOutLineX + 460 - 18*i, moOutLineY + 550 - 17*i, thick,
					width , 130, reveal);
		}
		//6th line
		for(int i=24; i<26; i++)
		{
			rectangle1[i] = Structure(moOutLineX + 165 - 5*i, moOutLineY - 260 + 16*i, thick,
					width , 15, reveal); 
		}
		//7th line
		for(int i=25; i<28; i++)
		{
			rectangle1[i] = Structure(moOutLineX -440 + 20*i, moOutLineY+ 165 -2*i, thick,
					width , 85, reveal);
		}
		//8th line
		for(int i=28; i<32; i++)
		{
			rectangle1[i] =  Structure(moOutLineX -375 + 18*i , moOutLineY - 385 + 18*i, thick,
					width +25 , -45, reveal); 
		} 
		//9th line
		for(int i=32; i<40; i++)
		{
			rectangle1[i] =  Structure(moOutLineX + 200, moOutLineY + 1025 - 26*i, thick,
					width, 180, reveal);
		} 

		timer1 = new TimerHandler((float) 1.0f/120,true, new ITimerCallback() 
		{
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				// TODO Auto-generated method stub 
				if(!(pieceChalk.getX()>=0))
				{
					Debug.d("chalk.x nan");
					//Chalk 
					pieceChalk = new Chalk(200,200,
							mPieceChalkTextureRegion, getVertexBufferObjectManager());
					mScene.attachChild(pieceChalk);
					pieceChalk.setScale((float) 0.8);
				}
				
				if(touch == 0)
				{
					//Debug.d("This is UP");
					if(drawLine == 1)
					{
						Chalk.animatedChalk(rectangle1[1].getX(), rectangle1[1].getY(),
								rectangle1[2].getX(), rectangle1[2].getY(), 
								rectangle1[3].getX(), rectangle1[3].getY(), 
								rectangle1[4].getX(), rectangle1[4].getY(), 
								rectangle1[5].getX(), rectangle1[5].getY(),
								rectangle1[6].getX(), rectangle1[6].getY(),
								rectangle1[7].getX(), rectangle1[7].getY(),
								rectangle1[8].getX(), rectangle1[8].getY(),
								rectangle1[8].getX(), rectangle1[8].getY(),
								rectangle1[8].getX(), rectangle1[8].getY());
					}
				    else if(drawLine == 2)
					{
						Chalk.animatedChalk(rectangle1[9].getX(), rectangle1[9].getY(),
								rectangle1[10].getX(), rectangle1[10].getY(), 
								rectangle1[11].getX(), rectangle1[11].getY(), 
								rectangle1[12].getX(), rectangle1[12].getY(), 
								rectangle1[13].getX(), rectangle1[13].getY(),
								rectangle1[14].getX(), rectangle1[14].getY(),
								rectangle1[15].getX(), rectangle1[15].getY(),
								rectangle1[16].getX(), rectangle1[16].getY(),
								rectangle1[17].getX(), rectangle1[17].getY(),
								rectangle1[17].getX(), rectangle1[17].getY());
					}
					else if(drawLine == 3)
					{
						Chalk.animatedChalk(rectangle1[17].getX()+10, rectangle1[17].getY(),
								rectangle1[18].getX()+10, rectangle1[18].getY(), 
								rectangle1[19].getX()+10, rectangle1[19].getY(), 
								rectangle1[20].getX()+10, rectangle1[20].getY(), 
								rectangle1[21].getX()+10, rectangle1[21].getY(),
								rectangle1[21].getX()+10, rectangle1[21].getY(),
								rectangle1[21].getX()+10, rectangle1[21].getY(),
								rectangle1[21].getX()+10, rectangle1[21].getY(),
								rectangle1[21].getX()+10, rectangle1[21].getY(),
								rectangle1[21].getX()+10, rectangle1[21].getY());
					}
					else if(drawLine == 4)
					{
						Chalk.animatedChalk(rectangle1[20].getX()+5, rectangle1[20].getY()+10,
								rectangle1[21].getX(), rectangle1[21].getY()+15, 
								rectangle1[22].getX(), rectangle1[22].getY()+20, 
								rectangle1[23].getX(), rectangle1[23].getY()+20, 
								rectangle1[24].getX(), rectangle1[24].getY()+20,
								rectangle1[24].getX(), rectangle1[24].getY()+20, 
								rectangle1[24].getX(), rectangle1[24].getY()+20, 
								rectangle1[24].getX(), rectangle1[24].getY()+20, 
								rectangle1[24].getX(), rectangle1[24].getY()+20, 
								rectangle1[24].getX(), rectangle1[24].getY()+20);
					}
					else if(drawLine == 5)
					{
						Chalk.animatedChalk(rectangle1[24].getX(), rectangle1[24].getY()+20,
								rectangle1[25].getX(), rectangle1[25].getY()+20, 
								rectangle1[26].getX(), rectangle1[26].getY()+20, 
								rectangle1[27].getX(), rectangle1[27].getY()+20, 
								rectangle1[28].getX(), rectangle1[28].getY()+20,
								rectangle1[28].getX(), rectangle1[28].getY()+20,
								rectangle1[28].getX(), rectangle1[28].getY()+20,
								rectangle1[28].getX(), rectangle1[28].getY()+20,
								rectangle1[28].getX(), rectangle1[28].getY()+20,
								rectangle1[28].getX(), rectangle1[28].getY()+20);
					}
					else if(drawLine == 6)
					{
						Chalk.animatedChalk(rectangle1[29].getX(), rectangle1[29].getY()+20,
								rectangle1[30].getX(), rectangle1[30].getY()+20, 
								rectangle1[31].getX(), rectangle1[31].getY()+20, 
								rectangle1[32].getX(), rectangle1[32].getY()+20, 
								rectangle1[32].getX(), rectangle1[32].getY()+20,
								rectangle1[32].getX(), rectangle1[32].getY()+20,
								rectangle1[32].getX(), rectangle1[32].getY()+20,
								rectangle1[32].getX(), rectangle1[32].getY()+20,
								rectangle1[32].getX(), rectangle1[32].getY()+20,
								rectangle1[32].getX(), rectangle1[32].getY()+20);
					}
					else if(drawLine == 7)
					{
						Chalk.animatedChalk(rectangle1[32].getX(), rectangle1[32].getY()+20,
								rectangle1[33].getX(), rectangle1[33].getY()+20, 
								rectangle1[34].getX(), rectangle1[34].getY()+20, 
								rectangle1[35].getX(), rectangle1[35].getY()+20, 
								rectangle1[36].getX(), rectangle1[36].getY()+20,
								rectangle1[37].getX(), rectangle1[37].getY()+20,
								rectangle1[38].getX(), rectangle1[38].getY()+20,
								rectangle1[39].getX(), rectangle1[39].getY()+20,
								rectangle1[39].getX(), rectangle1[39].getY()+20,
								rectangle1[39].getX(), rectangle1[39].getY()+20);
					}
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
			//The touch is enabled
			touch = 1;
			//For piece chalk to be dragged
			pieceChalk.setPosition(
					pSceneTouchEvent.getX() - pieceChalk.getWidth() / 2,
					pSceneTouchEvent.getY() - pieceChalk.getHeight() / 2 - 35);
			//For drawing white chalk
			DrawImage(pSceneTouchEvent.getX() - 25, 
					pSceneTouchEvent.getY() - 30);
			//One by one Collision checker
			if (Flag1[1] == 1 && whiteChalk.collidesWith(rectangle1[1])) 
			{
				//next instruction for first line
				drawLine = 1; 
				Flag = 0; 
				Flag1[2] = 1;
				loop(1);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[2] == 1 && whiteChalk.collidesWith(rectangle1[2]))
			{
				//next instruction for first line
				drawLine = 1; 
				loop(2);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[3] == 1 && whiteChalk.collidesWith(rectangle1[3])) 
			{
				//next instruction for first line
				drawLine = 1; 
				loop(3);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[4] == 1 && whiteChalk.collidesWith(rectangle1[4]))
			{
				//next instruction for first line
				drawLine = 1; 
				loop(4);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[5] == 1 && whiteChalk.collidesWith(rectangle1[5])) 
			{
				//next instruction for first line
				drawLine = 1; 
				loop(5);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[6] == 1 && whiteChalk.collidesWith(rectangle1[6])) 
			{
				//next instruction for first line
				drawLine = 1; 
				loop(6);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[7] == 1 && whiteChalk.collidesWith(rectangle1[7]))
			{
				//next instruction for first line
				drawLine = 1;
				loop(7);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[8] == 1 && whiteChalk.collidesWith(rectangle1[8]))
			{
				//next instruction for drawing the second line
				drawLine = 2;
				loop(8);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			// 2nd Line
			else if (Flag1[9] == 1 && whiteChalk.collidesWith(rectangle1[9])) 
			{
				//next instruction for drawing the second line
				drawLine = 2;
				loop(9);  
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[10] == 1 && whiteChalk.collidesWith(rectangle1[10]))
			{
				//next instruction for drawing the second line
				drawLine = 2;
				loop(10);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[11] == 1 && whiteChalk.collidesWith(rectangle1[11]))
			{
				//next instruction for drawing the second line
				drawLine = 2;
				loop(11);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[12] == 1 && whiteChalk.collidesWith(rectangle1[12]))
			{
				//next instruction for drawing the second line
				drawLine = 2;
				loop(12);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[13] == 1 && whiteChalk.collidesWith(rectangle1[13]))
			{
				//next instruction for drawing the second line
				drawLine = 2;
				loop(13);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[14] == 1 && whiteChalk.collidesWith(rectangle1[14]))
			{
				//next instruction for drawing the second line
				drawLine = 2;
				loop(14);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[15] == 1 && whiteChalk.collidesWith(rectangle1[15]))
			{
				//next instruction for drawing the second line
				drawLine = 2;
				loop(15);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[16] == 1 && whiteChalk.collidesWith(rectangle1[16]))
			{
				//next instruction for third line
				drawLine = 3; 
				loop(16);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			//3rd line
			else if (Flag1[17] == 1 && whiteChalk.collidesWith(rectangle1[17])) 
			{
				//next instruction for third line
				drawLine = 3; 
				loop(17);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[18] == 1 && whiteChalk.collidesWith(rectangle1[18]))
			{
				//next instruction for third line
				drawLine = 3; 
				loop(18);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[19] == 1 && whiteChalk.collidesWith(rectangle1[19]))
			{
				//next instruction for forth line
				drawLine = 4; 
				loop(19);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			//4th line
			else if (Flag1[20] == 1 && whiteChalk.collidesWith(rectangle1[20]))
			{
				//next instruction for forth line
				drawLine = 4; 
				loop(20);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[21] == 1 && whiteChalk.collidesWith(rectangle1[21]))
			{
				//next instruction for forth line
				drawLine = 4; 
				loop(21);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			//5th line
			else if (Flag1[22] == 1 && whiteChalk.collidesWith(rectangle1[22]))
			{
				//next instruction for forth line
				drawLine = 4; 
				loop(22);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[23] == 1 && whiteChalk.collidesWith(rectangle1[23]))
			{
				//next instruction for fifth line
				drawLine = 5; 
				loop(23);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			//6th line
			else if (Flag1[24] == 1 && whiteChalk.collidesWith(rectangle1[24]))
			{
				//next instruction for fifth line
				drawLine = 5; 
				loop(24);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[25] == 1 && whiteChalk.collidesWith(rectangle1[25]))
			{
				//next instruction for fifth line
				drawLine = 5; 
				loop(25);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			//7th line
			else if (Flag1[26] == 1 && whiteChalk.collidesWith(rectangle1[26]))
			{
				//next instruction for fifth line
				drawLine = 5; 
				loop(26);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[27] == 1 && whiteChalk.collidesWith(rectangle1[27]))
			{
				//next instruction for sixth line
				drawLine = 6; 
				loop(27);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			//8th line
			else if (Flag1[28] == 1 && whiteChalk.collidesWith(rectangle1[28]))
			{
				//next instruction for sixth line
				drawLine = 6; 
				loop(28);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[29] == 1 && whiteChalk.collidesWith(rectangle1[29]))
			{
				//next instruction for sixth line
				drawLine = 6; 
				loop(29);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[30] == 1 && whiteChalk.collidesWith(rectangle1[30]))
			{
				//next instruction for sixth line
				drawLine = 6; 
				loop(30);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[31] == 1 && whiteChalk.collidesWith(rectangle1[31]))
			{
				//next instruction for sixth line
				drawLine = 6; 
				loop(31);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			//9th line
			else if (Flag1[32] == 1 && whiteChalk.collidesWith(rectangle1[32]))
			{
				//next instruction for seventh line
				drawLine = 7; 
				loop(32);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[33] == 1 && whiteChalk.collidesWith(rectangle1[33]))
			{
				//next instruction for seventh line
				drawLine = 7; 
				loop(33);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[34] == 1 && whiteChalk.collidesWith(rectangle1[34]))
			{
				//next instruction for seventh line
				drawLine = 7; 
				loop(34);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[35] == 1 && whiteChalk.collidesWith(rectangle1[35]))
			{
				//next instruction for seventh line
				drawLine = 7; 
				loop(35);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[36] == 1 && whiteChalk.collidesWith(rectangle1[36]))
			{
				//next instruction for seventh line
				drawLine = 7; 
				loop(36);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[37] == 1 && whiteChalk.collidesWith(rectangle1[37]))
			{
				//next instruction for seventh line
				drawLine = 7; 
				loop(37);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[38] == 1 && whiteChalk.collidesWith(rectangle1[38]))
			{
				//next instruction for seventh line
				drawLine = 7; 
				loop(38);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[39] == 1 && whiteChalk.collidesWith(rectangle1[39]))
			{
				//next instruction for seventh line
				drawLine = 7; 
				loop(39);
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
				
				Flag1[39] = 0;
				Flag1[40] = 0;
				mScene.unregisterUpdateHandler(timer1);
				Intent intent = getIntent();
				finish();
				startActivity(intent); 
			}  
			else 
			{
				mScene.detachChild(whiteChalk);
			}

			return true;
		}
		if (pSceneTouchEvent.isActionUp() || pSceneTouchEvent.isActionMove()) 
		{
			if(pSceneTouchEvent.isActionUp())
			{
				touch = 0;
			}
			if (Flag == 1 && whiteChalk.collidesWith(rectangle1[1]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[2] == 1 && whiteChalk.collidesWith(rectangle1[2])) 
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[3] == 1 && whiteChalk.collidesWith(rectangle1[3])) 
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[4] == 1 && whiteChalk.collidesWith(rectangle1[4]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[5] == 1 && whiteChalk.collidesWith(rectangle1[5])) 
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[6] == 1 && whiteChalk.collidesWith(rectangle1[6]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[7] == 1 && whiteChalk.collidesWith(rectangle1[7])) 
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			else if (Flag1[8] == 1 && whiteChalk.collidesWith(rectangle1[8]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[9] == 1 && whiteChalk.collidesWith(rectangle1[9]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			}
			//2nd Line
			else if (Flag1[10] == 1 && whiteChalk.collidesWith(rectangle1[10]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[11] == 1 && whiteChalk.collidesWith(rectangle1[11]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[12] == 1 && whiteChalk.collidesWith(rectangle1[12]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[13] == 1 && whiteChalk.collidesWith(rectangle1[13]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[14] == 1 && whiteChalk.collidesWith(rectangle1[14]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[15] == 1 && whiteChalk.collidesWith(rectangle1[15]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[16] == 1 && whiteChalk.collidesWith(rectangle1[16]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			//3rd line
			else if (Flag1[17] == 1 && whiteChalk.collidesWith(rectangle1[17]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[18] == 1 && whiteChalk.collidesWith(rectangle1[18]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[19] == 1 && whiteChalk.collidesWith(rectangle1[19]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			//4th line
			else if (Flag1[20] == 1 && whiteChalk.collidesWith(rectangle1[20]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[21] == 1 && whiteChalk.collidesWith(rectangle1[21]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			//5th line
			else if (Flag1[22] == 1 && whiteChalk.collidesWith(rectangle1[22]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[23] == 1 && whiteChalk.collidesWith(rectangle1[23]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			//6th line
			else if (Flag1[24] == 1 && whiteChalk.collidesWith(rectangle1[24]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[25] == 1 && whiteChalk.collidesWith(rectangle1[25]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			//7th line
			else if (Flag1[26] == 1 && whiteChalk.collidesWith(rectangle1[26]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[27] == 1 && whiteChalk.collidesWith(rectangle1[27]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			//8th line
			else if (Flag1[28] == 1 && whiteChalk.collidesWith(rectangle1[28]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[29] == 1 && whiteChalk.collidesWith(rectangle1[29]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[30] == 1 && whiteChalk.collidesWith(rectangle1[30]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[31] == 1 && whiteChalk.collidesWith(rectangle1[31]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[32] == 1 && whiteChalk.collidesWith(rectangle1[32]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[33] == 1 && whiteChalk.collidesWith(rectangle1[33]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			//9th line
			else if (Flag1[34] == 1 && whiteChalk.collidesWith(rectangle1[34]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[35] == 1 && whiteChalk.collidesWith(rectangle1[35]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[36] == 1 && whiteChalk.collidesWith(rectangle1[36]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[37] == 1 && whiteChalk.collidesWith(rectangle1[37]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[38] == 1 && whiteChalk.collidesWith(rectangle1[38]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
			} 
			else if (Flag1[39] == 1 && whiteChalk.collidesWith(rectangle1[39]))
			{
				touchPositionX = pSceneTouchEvent.getX();
				touchPositionY = pSceneTouchEvent.getY();
				
			} 
			else if (Flag1[40] == 1 && whiteChalk.collidesWith(rectangle1[40]))
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
	
	public void loop(int a)
	{
		for(int i=1; i<a; i++)
		{
			Flag1[i] = 0;
			Flag1[a] = 1;
			Flag1[a+1] = 1; 
		}
	}

	private void DrawImage(float x, float y)
	{
		// TODO Auto-generated method stub
		whiteChalk = new Sprite(x, y, MainActivity.mSprite4TextureRegion,
				MainActivity.vertexBufferObjectManager);
		mScene.attachChild(whiteChalk);
		whiteChalk.setScale((float) 0.3);
	}
	
}
