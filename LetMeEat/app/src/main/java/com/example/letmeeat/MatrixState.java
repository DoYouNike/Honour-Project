package com.example.letmeeat;

import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Stack;
// this class was used to perform the function pop to stack and push to stack
public class MatrixState
{
    private static float[] mProjMatrix = new float[16];
    private static float[] mVMatrix = new float[16];
    private static float[] currMatrix;
    public static FloatBuffer cameraFB;


    public static Stack<float[]> mStack=new Stack<float[]>();//create the stack for transformation

    public static void setInitStack()
    {
        currMatrix=new float[16];
        Matrix.setRotateM(currMatrix, 0, 0, 1, 0, 0);
    }

    public static void pushMatrix()
    {
        mStack.push(currMatrix.clone());
    }

    public static void popMatrix()
    {
        currMatrix=mStack.pop();
    }

    public static void translate(float x,float y,float z)//make object translate
    {
        Matrix.translateM(currMatrix, 0, x, y, z);
    }

    public static void rotate(float angle,float x,float y,float z)//make object rotate
    {
        Matrix.rotateM(currMatrix,0,angle,x,y,z);
    }
    public static void scale(float x,float y,float z)//chaneg size of object
    {
        Matrix.scaleM(currMatrix,0,x,y,z);
    }




    //set camera
    public static void setCamera
    (
            float cx,
            float cy,
            float cz,
            float tx,
            float ty,
            float tz,
            float upx,
            float upy,
            float upz
    )
    {
        Matrix.setLookAtM
                (
                        mVMatrix,
                        0,
                        cx,
                        cy,
                        cz,
                        tx,
                        ty,
                        tz,
                        upx,
                        upy,
                        upz
                );

        float[] cameraLocation=new float[3];//camera position
        cameraLocation[0]=cx;
        cameraLocation[1]=cy;
        cameraLocation[2]=cz;

        ByteBuffer bB = ByteBuffer.allocateDirect(3*4);
        bB.order(ByteOrder.nativeOrder());
        cameraFB=bB.asFloatBuffer();
        cameraFB.put(cameraLocation);
        cameraFB.position(0);
    }

    //Set perspective projection parameters
    public static void setProjectFrustum
    (
            float left,
            float right,
            float bottom,
            float top,
            float near,
            float far
    )
    {
        Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    //Set orthogonal projection parameters
    public static void setProjectOrtho
    (
            float left,
            float right,
            float bottom,
            float top,
            float near,
            float far
    )
    {
        Matrix.orthoM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    //Get the final transformation matrix of a specific object
    public static float[] getFinalMatrix()
    {
        float[] mMVPMatrix=new float[16];
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, currMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }

    //Get the transformation matrix of a object
    public static float[] getMMatrix()
    {
        return currMatrix;
    }


}
