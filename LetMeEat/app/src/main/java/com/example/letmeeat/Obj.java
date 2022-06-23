package com.example.letmeeat;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Obj {

    int mProgram;
    int muMVPMatrixHandle;
    int maPositionHandle;
    FloatBuffer   mVertexBuffer;
    int vCount=0;

    public Obj(MyGLSurfaceView mv, float[] vertices)
    {

        initVertexData(vertices);

    }

    //init the vertex data
    public void initVertexData(float[] vertices)
    {

        vCount=vertices.length/3;
        //create byte buffer
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//set byte order
        mVertexBuffer = vbb.asFloatBuffer();//Convert to Float type buffer
        mVertexBuffer.put(vertices);//
        mVertexBuffer.position(0);//设置缓冲区起始位置

    }


    public void initShader(int mProgram)
    {
        this.mProgram = mProgram;
        //Get the vertex position attribute reference in the program
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //Get the final transformation matrix reference in the program
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void drawSelf(int texID)
    {
       // using the shader program
        GLES20.glUseProgram(mProgram);

        //pass the mvpMatrix to the shader
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
        // pass the position to the shader
        GLES20.glVertexAttribPointer
                (
                        maPositionHandle,
                        3,
                        GLES20.GL_FLOAT,
                        false,
                        3*4,
                        mVertexBuffer
                );

        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texID);
        //draw the object
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
    }

}

