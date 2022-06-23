package com.example.letmeeat;

import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LoadObjFile {


    // method to read the obj file
    public static Obj loadFromFile(String fname, Resources r,MyGLSurfaceView mv)
    {
        Obj lo=null;

        ArrayList<Float> alv=new ArrayList<Float>();//crete the arraylist to store the original vertex coordinates
        ArrayList<Float> alvResult=new ArrayList<Float>();//store the final vertex.

        try
        {
            InputStream in=r.getAssets().open(fname);
            InputStreamReader isr=new InputStreamReader(in);
            BufferedReader br=new BufferedReader(isr);
            String temps=null;

            while((temps=br.readLine())!=null)
            {
                String[] tempsa=temps.split("[ ]+");
                if(tempsa[0].trim().equals("v"))
                {//get the vertex coordinates
                    alv.add(Float.parseFloat(tempsa[1]));
                    alv.add(Float.parseFloat(tempsa[2]));
                    alv.add(Float.parseFloat(tempsa[3]));
                }
                else if(tempsa[0].trim().equals("f"))
                {//get the triangle
                    int index=Integer.parseInt(tempsa[1].split("/")[0])-1;
                    alvResult.add(alv.get(3*index));
                    alvResult.add(alv.get(3*index+1));
                    alvResult.add(alv.get(3*index+2));

                    index=Integer.parseInt(tempsa[2].split("/")[0])-1;
                    alvResult.add(alv.get(3*index));
                    alvResult.add(alv.get(3*index+1));
                    alvResult.add(alv.get(3*index+2));

                    index=Integer.parseInt(tempsa[3].split("/")[0])-1;
                    alvResult.add(alv.get(3*index));
                    alvResult.add(alv.get(3*index+1));
                    alvResult.add(alv.get(3*index+2));
                }
            }

            //Generate vertex array
            int size=alvResult.size();
            float[] vXYZ=new float[size];
            for(int i=0;i<size;i++)
            {
                vXYZ[i]=alvResult.get(i);
            }
            //create the object
            lo=new Obj(mv,vXYZ);
        }
        catch(Exception e)
        {
            Log.d("load error", "load error");
            e.printStackTrace();
        }
        return lo;
    }

}

