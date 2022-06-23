package com.example.letmeeat;

/*
Class to create the enemy
 */
public class Enemy {
    Obj enemy;
    float ex;
    float ey;
    float ez;
    float radius;
    int textureId;
    float eangle;
    float speedMove;

    public Enemy(MyGLSurfaceView mv,float x, float y, float z, float speed,float angle){
        // load the enemy object
        this.enemy = LoadObjFile.loadFromFile("Enemy.obj", mv.getResources(),
                mv);
        // set the shader program for enemy
        enemy.initShader(Shader.getObjP());

        this.ex = x;
        this.ey= y;
        this.ez =z;
        this.radius = 0.35f;
        this.speedMove = speed;
        this.eangle = angle;
    }

    // method to draw the enemy with all the transformation
    public void draw()
    {

        MatrixState.pushMatrix();

        MatrixState.translate(ex, ey, ez);
        MatrixState.rotate(eangle, 0, 1, 0);
        MatrixState.scale(0.7f, 0.7f, 0.7f);
        //If the loaded object is not empty, draw the object
        if (enemy != null) {
           enemy.drawSelf(textureId);
        }

        MatrixState.popMatrix();


    }

    // method to make to enemy move
    public  void move()
    {

        float dx = (float) Math.sin(Math.toRadians(eangle));
        float dz = (float) Math.cos(Math.toRadians(eangle));
        ex += dx * speedMove;
        ez += dz * speedMove;
        // check if the enemy reach the edge, if yes bounce back
        if(ex >= 3 || ex <= -3) {

            setAngle(eangle+120f);
        }
        else if(ez >= 3 || ez <= -5.5){

            setAngle(eangle+120f);
        }
    }
    public void setX(float ex)
    {
        this.ex = ex;
    }

    public void setY(float ey)
    {
        this.ey = ey;
    }
    public void setZ(float ez)
    {
        this.ez = ez;
    }
    public void setAngle(float eangle)
    {
        this.eangle = eangle;
    }


    public float getx()
    {
        return  ex;
    }

    public  float gety()
    {
        return  ey;
    }

    public  float getz()
    {
        return  ez;
    }
    public  float getSpeed()
    {
        return  speedMove;
    }

    public  float getAngle()
    {
        return  eangle;
    }
    public  float getRadius()
    {
        return  radius;
    }


}
