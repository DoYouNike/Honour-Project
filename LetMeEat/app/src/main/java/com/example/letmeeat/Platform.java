package com.example.letmeeat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Platform {
    int[] textureId=new int[6];
    skybox mskybox;
    public Platform(MyGLSurfaceView mv) {
        this.mskybox = new skybox(mv);
        // set the shader for skybox
        mskybox.initShader(Shader.getSkyboxP());
        // following init the texture for each side of the skybox
        textureId[0]=initTexture(mv,R.mipmap.background);
        textureId[1]=initTexture(mv,R.mipmap.background);
        textureId[2]=initTexture(mv,R.mipmap.background);
        textureId[3]=initTexture(mv,R.mipmap.background);
        textureId[4]=initTexture(mv,R.mipmap.background);
        textureId[5]=initTexture(mv,R.mipmap.background);
    }

    // method to add texture to the skybox
    public int initTexture( MyGLSurfaceView mv, int drawableId)//textureId
    {
        //create the texture id
        int[] textures = new int[1];
        GLES20.glGenTextures
                (
                        1,
                        textures,
                        0
                );
        int textureId=textures[0];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);

        //Load images via input stream
        InputStream is = mv.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp;
        try
        {
            bitmapTmp = BitmapFactory.decodeStream(is);
        }
        finally
        {
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        //load the texture
        GLUtils.texImage2D
                (
                        GLES20.GL_TEXTURE_2D,
                        0,
                        bitmapTmp,
                        0
                );
        bitmapTmp.recycle(); 		  //Release the picture after the texture is loaded successfully
        return textureId;
    }

    //  method to draw the skybox
    public void draw()
    {

        final float x=0.4f;
        MatrixState.pushMatrix();
        MatrixState.translate(0, 0, -skybox.size + x);
        mskybox.drawSelf(textureId[0]);
        MatrixState.popMatrix();

        //front of the skybox
        MatrixState.pushMatrix();
        MatrixState.translate(0, 0, skybox.size - x);
        MatrixState.rotate(180, 0, 1, 0);
        mskybox.drawSelf(textureId[5]);
        MatrixState.popMatrix();

        //left of the skybox
        MatrixState.pushMatrix();
        MatrixState.translate(-skybox.size + x, 0, 0);
        MatrixState.rotate(90, 0, 1, 0);
        mskybox.drawSelf(textureId[1]);
        MatrixState.popMatrix();

        //back of the skybox
        MatrixState.pushMatrix();
        MatrixState.translate(skybox.size - x, 0, 0);
        MatrixState.rotate(-90, 0, 1, 0);
        mskybox.drawSelf(textureId[2]);
        MatrixState.popMatrix();

        //bottom of the skybox
        MatrixState.pushMatrix();
        MatrixState.translate(0, -skybox.size + x, 0);
        MatrixState.rotate(-90, 1, 0, 0);
        mskybox.drawSelf(textureId[3]);
        MatrixState.popMatrix();

        //top of the skybox
        MatrixState.pushMatrix();
        MatrixState.translate(0, skybox.size - x, 0);
        MatrixState.rotate(90, 1, 0, 0);
        mskybox.drawSelf(textureId[4]);
        MatrixState.popMatrix();
    }
}
