package com.example.letmeeat;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

// this class is used to set the shader program ready to use on other class
public class Shader {


        final static int shaderCount=2;
        final static String[][] shaderName=
                {
                        {"vertex.sh","frag.sh"},
                        {"vertex_color.sh","frag_color.sh"}
                };
        static String[]mVertexShader=new String[shaderCount];
        static String[]mFragmentShader=new String[shaderCount];
        static int[] program=new int[shaderCount];

        // method to load the shader program file
        public static void loadCodeFromFile(Resources r )
        {
            for(int i=0;i<shaderCount;i++)
            {
                //Load the script content of the vertex shader
                mVertexShader[i]=ShaderUtil.loadFromAssetsFile(shaderName[i][0],r);
                //Load the script content of the fragment shader
                mFragmentShader[i]=ShaderUtil.loadFromAssetsFile(shaderName[i][1], r);
                //create the program and store in program[i]
                program[i]=ShaderUtil.createProgram(mVertexShader[i], mFragmentShader[i]);
            }
        }

        public static int getSkyboxP()
     {
        return program[0];
            }

     public static int getObjP()
        {
        return program[1];
    }
}
