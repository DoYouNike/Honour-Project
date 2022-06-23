package com.example.letmeeat;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class skybox {
    int sProgram;
    int muMVPMatrixHandle;
    int maPositionHandle; 
    int maTexCoorHandle;
    FloatBuffer mVertexBuffer;
    FloatBuffer   mTexCoorBuffer;
    int vCount=0;
    public static float size=10f;
    public skybox(MyGLSurfaceView mv)
    {

        initVertexData();

    }

    //Method for initializing vertex coordinates and texture coloring data
    public void initVertexData()
    {
        // init Vertex coordinate data
        vCount=6;

        float vertices[]=new float[]
                {
                        -size,size,0,
                        -size,-size,0,
                        size,-size,0,

                        size,-size,0,
                        size,size,0,
                        -size,size,0
                };

        //Create vertex coordinate data buffer
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//Set byte order
        mVertexBuffer = vbb.asFloatBuffer();//Convert to Float type buffer
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        //init Vertex texture coordinate data
        float texCoor[]=new float[]
                {
                        1,0, 1,1, 0,1,
                        0,1, 0,0, 1,0
                };
        //Create vertex texture coordinate data buffer
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//Set byte order
        mTexCoorBuffer = cbb.asFloatBuffer();//Convert to Float type buffer
        mTexCoorBuffer.put(texCoor);
        mTexCoorBuffer.position(0);
    }

    //init shader
    public void initShader(int sProgram )
    {
        this.sProgram = sProgram;
        maPositionHandle = GLES20.glGetAttribLocation(sProgram, "aPosition");
        maTexCoorHandle= GLES20.glGetAttribLocation(sProgram, "aTexCoor");

        muMVPMatrixHandle = GLES20.glGetUniformLocation(sProgram, "uMVPMatrix");
    }
    public void drawSelf(int texId)
    {
        //set using shader program
        GLES20.glUseProgram(sProgram);

        //Pass the final transformation matrix into the shader program
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
        //Transfer vertex position data
        GLES20.glVertexAttribPointer
                (
                        maPositionHandle,
                        3,
                        GLES20.GL_FLOAT,
                        false,
                        3*4,
                        mVertexBuffer
                );
        //Transfer vertex texture coordinate data
        GLES20.glVertexAttribPointer
                (
                        maTexCoorHandle,
                        2,
                        GLES20.GL_FLOAT,
                        false,
                        2*4,
                        mTexCoorBuffer
                );
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maTexCoorHandle);
        //Bind texture
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
        //Draw texture skybox
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
    }


}
