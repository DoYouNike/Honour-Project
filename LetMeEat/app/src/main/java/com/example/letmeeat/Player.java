package com.example.letmeeat;

import android.util.Log;

// create the player object
public class Player {
    Obj player;
    float px;
    float py;
    float pz;
    float radius;
    int textureId;
    float angle;
    float windowW ;
    float windowH;
    float speedMove;
    public boolean gameOver;

    public Player(MyGLSurfaceView mv,float x, float y, float z, float speed){
        // set the player object
        this.player = LoadObjFile.loadFromFile("player.obj", mv.getResources(),
                mv);

        // set the shader for player object
        player.initShader(Shader.getObjP());
        this.px = x;
        this.py= y;
        this.pz =z;
        this.radius = 0.4f;
        this.speedMove = speed;
        this.gameOver = false;
        windowW = 6f;
        windowH = 6f;

    }
    public void setX(float px)
    {
        this.px = px;
    }

    public void setY(float py)
    {
        this.py = py;
    }
    public void setZ(float pz)
    {
        this.pz = pz;
    }
    public void setAngle(float angle)
    {
        this.angle = angle;
    }






    // method to draw the player
    public void draw(){

        MatrixState.pushMatrix();
        MatrixState.translate(px,py ,pz);
        MatrixState.rotate(angle, 0, 1, 0);
        MatrixState.scale(0.2f, 0.2f, 0.2f);

        //If the loaded object is not empty, draw the object
        if (player != null) {
            player.drawSelf(textureId);

        }
        MatrixState.popMatrix();

        move();
    }
    // method to control the player
    public  void move()
    {

            float dx = (float) Math.sin(Math.toRadians(angle));
            float dz = (float) Math.cos(Math.toRadians(angle));
            px += dx * speedMove;
            pz += dz * speedMove;
            // check is the player reach the edge of the screen or not, if yes stop the player until player change direction
            if(px <= -3 || px >= 3) {

                px -=  dx* speedMove;
                pz -=  dz * speedMove;
             }
            else if(pz >= 3 || pz <= -5.5){
            px -= dx * speedMove;
            pz -=  dz * speedMove;
             }



    }


    public float getx()
    {
        return  px;
    }

    public  float gety()
    {
        return  py;
    }

    public  float getz()
    {
        return  pz;
    }

    public  float getAngle()
    {
        return  angle;
    }
    public  float getRadius()
    {
        return  radius;
    }



}
