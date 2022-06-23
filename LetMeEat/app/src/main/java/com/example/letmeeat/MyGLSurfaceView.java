package com.example.letmeeat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.jar.Attributes;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLSurfaceView extends GLSurfaceView implements SurfaceHolder.Callback {
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float gangle = 30f;
    private MyGLRenderer mRenderer;
    private int score = 0;
    private int counter =0;
    MediaPlayer bGMusic;

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context,attrs);
       init(context);
    }

    private void init(Context context)
    {
        this.setEGLContextClientVersion(2);
        // set the background music
        bGMusic = MediaPlayer.create(context, R.raw.song);
        bGMusic.setLooping(true);
        bGMusic.start();
        mRenderer = new MyGLRenderer(context);

        setRenderer(mRenderer);
    }

    //this the method to record if user touch the screen or not
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        if (e.getAction() == MotionEvent.ACTION_DOWN) {

            float dy = gangle;

            if (x > getWidth() / 2) {
                dy = dy * -1;

            }

            mRenderer.mAngle += dy * TOUCH_SCALE_FACTOR;
        }
        return true;
    }


  public class MyGLRenderer implements GLSurfaceView.Renderer {
        float mAngle = 180f;
        boolean easy = HomeActivity.levelE;
        boolean hard = HomeActivity.levelH;


        Player player;
        Platform platform;
        ArrayList<Enemy> mEnemy;

        private Context context;

        public float speedMove = 0.025f;
        public float enemyS;



        public MyGLRenderer(Context appContext) {

            context = appContext;


        }

        @Override
        public void onSurfaceCreated(GL10 unused, EGLConfig config) {



            GLES20.glEnable(GLES20.GL_DEPTH_TEST);

            GLES20.glEnable(GLES20.GL_CULL_FACE);


            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            MatrixState.setInitStack();
            // load the shader
            Shader.loadCodeFromFile(MyGLSurfaceView.this.getResources());
            //init each objects
            platform = new Platform(MyGLSurfaceView.this);
            player = new Player(MyGLSurfaceView.this,0,0,0,speedMove);
            mEnemy = new ArrayList<>();

            spawnEnemy();


        }

        @Override
        public void onSurfaceChanged(GL10 unused, int width, int height) {
           // set the viewport size
            GLES20.glViewport(0, 0, width, height);

            float ratio = (float) width / height;
           // set projection frustum
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 2, 100);


        }

        @Override
        public void onDrawFrame(GL10 unused) {

            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            MatrixState.setCamera(0, 10f, 0f, 0f, 0f, -1f, 0f, 1.0f, 0.0f);

            platform.draw();
            // check is the the game is finished or nor. if the player is dead, show the game over screen
            if(player.gameOver == false) {
                counter ++;
                enemy();
                enemyAI();
                player();
                // add point to score every 10s
                if(counter % 100 ==0) {
                    score++;
                    update();
                }
            }else if (player.gameOver == true)
            {
                showResult();
            }



        }
        // method to update the angle of the player
        public  void player(){
            player.setAngle(mAngle);
            player.draw();

        }

        // method to spawn the enemy based on the level user selected
        public void spawnEnemy() {

            if (easy == true) {
                // set the speed for enemy in easy level
                enemyS = 0.015f;
                // using the add function from the arraylist to add enemy at different position
                mEnemy.add(new Enemy(MyGLSurfaceView.this, -2.2f, 0, -5, enemyS, 0));
                mEnemy.add(new Enemy(MyGLSurfaceView.this, 1, 0, 2, enemyS, 230));
                mEnemy.add(new Enemy(MyGLSurfaceView.this, -1, 0, 3, enemyS, 180));
                mEnemy.add(new Enemy(MyGLSurfaceView.this, -2, 0, 3, enemyS, 120));
                mEnemy.add(new Enemy(MyGLSurfaceView.this, 0.5f, 0, 2, enemyS, 160));
                mEnemy.add(new Enemy(MyGLSurfaceView.this, 2, 0, -4, enemyS, 40));
            }
            else if (hard == true)
            {
                // set the speed for enemy in hard level
                enemyS = 0.019f;
                // using the add function from the arraylist to add enemy at different position
                mEnemy.add(new Enemy(MyGLSurfaceView.this, -2.2f, 0, -5, enemyS, 0));
                mEnemy.add(new Enemy(MyGLSurfaceView.this, 1, 0, 2, enemyS, 230));
                mEnemy.add(new Enemy(MyGLSurfaceView.this, -1.5f, 0, 3, enemyS, 180));
                mEnemy.add(new Enemy(MyGLSurfaceView.this, -2, 0, 3, enemyS, 120));
                mEnemy.add(new Enemy(MyGLSurfaceView.this, 0.5f, 0, 2, enemyS, 90));
                mEnemy.add(new Enemy(MyGLSurfaceView.this, 2, 0, -4, enemyS, 60));
                mEnemy.add(new Enemy(MyGLSurfaceView.this, -2, 0, -5, enemyS, 140));

            }
        }



        // method to give the enemy some basic AI
      public void enemyAI( )
      {
          for(int i =0; i<mEnemy.size();i++) {
              Enemy ene = mEnemy.get(i);
              // find the distance between enemy and player
              float x = player.getx() - ene.getx();
              float z = player.getz() - ene.getz();
              float y = player.gety() - ene.gety();
              float distance = (float) Math.sqrt(x * x + y*y+ z * z);
              x /= distance;
              z /= distance;
              y /= distance;
              // check is the enemy reaches player or not
              if (isCollide(ene,player)) {
                  player.gameOver = true;

              } else if (isEnemyCollide(ene,ene) & distance < 2){
                  // give some  of the enemy the ability to chase player if player is in  the detect range.
                  if(i==0 || i==1) {
                      float q = (float) Math.toDegrees(Math.atan2(x, z));
                      ene.setAngle(q);
                      float ex = ene.getx();
                      float ez = ene.getz();
                      ex += x * enemyS;
                      ez += z * enemyS;
                      ene.setX(ex);
                      ene.setZ(ez);
                  }else {
                      ene.move();
                  }
              }
              else {
                  ene.move();
              }

          }
      }



      // method to render the enemy to scene
      public void enemy()
      {

          for (int i=0;i<mEnemy.size();i++)
          {
              Enemy e = mEnemy.get(i);
              e.draw();
          }
      }

      // method to check the collision using sphere collision detection by checking the distance with the sum of their radius.
      public boolean isCollide(Enemy e , Player p)
      {
          float dx = p.getx() - e.getx();
          float dz = p.getz() - e.getz();
          float dy = p.gety() - e.gety();
          float distance = (float) Math.sqrt(dx * dx + dy*dy+ dz * dz);
          float radiusS = p.getRadius() + e.getRadius();
          return distance < radiusS * radiusS;
      }

      public boolean isEnemyCollide(Enemy e , Enemy e1)
      {
          float dx = e1.getx() - e.getx();
          float dz = e1.getz() - e.getz();
          float dy = e1.gety() - e.gety();
          float distance = (float) Math.sqrt(dx * dx + dy*dy+ dz * dz);
          float radiusS = e1.getRadius() + e.getRadius();
          return distance < radiusS * radiusS;

      }


      // method to call the updateText method from the class HomeActivity
     public void update()
     {
         Context mcontext = MyGLSurfaceView.this.getContext();
         String  s = Integer.toString(score);
         ((HomeActivity) mcontext).updateText("Score : "+ s);
     }


     // method to show the gameover screen
        public void showResult()
        {
            Intent intent = new Intent(context, Score.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("SCORE", score);
            context.startActivity(intent);
        }

    }
}



