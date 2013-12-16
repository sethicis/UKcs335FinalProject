package com.uky.cs535.myopengles;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.opengl.GLU;

//a renderer that can draw and manipulate objects
@SuppressLint("NewApi")
public class MyGL20Renderer implements GLSurfaceView.Renderer {
	
	private Triangle mTriangle;
	private Square mSquare;
	private Cube mCube; 
	private Sphere mSphere; 
	
	public volatile float mAngle; // use volatile because we modify it in other classes
	public volatile float mAngleY; 
	public volatile float Dx, Dy; 
	public volatile float scale; 
	
	private final float[] mVMatrix = new float[16];
	private final float[] mProjMatrix = new float[16];
	private final float[] mNormalMat = new float[16]; 
	private final float[] mMVPMatrix = new float[16];
	private final float[] mRotationMatrix = new float[16];
	private final float[] mTemp = new float [16]; 
	
	private Context mC; 
	
	
	public MyGL20Renderer(Context context) {
		super();
		mC = context; 
	}
	// Set up the view's OpenGL ES environment
	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		// Set the background frame color
		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		
		
		GLES20.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
	    GLES20.glDepthFunc(GL10.GL_LEQUAL); 
		// initialize a triangle
		mTriangle = new Triangle();
		// initialize a square
		mSquare = new Square();
		
		mCube = new Cube(); 
		mCube.loadGLTexture(mC);
		mSphere = new Sphere(1.0f, 20, 40); 
	}

	// Called for each redraw of the view
	@Override
	public void onDrawFrame(GL10 gl) {
		// Redraw background color
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
		
		// Set the camera position (View matrix)
		Matrix.setLookAtM(mVMatrix, 0, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		
		// Calculate the projection and view transformation
		// temp = Proj*View; 
		Matrix.multiplyMM(mTemp, 0, mProjMatrix, 0, mVMatrix, 0);
		
		Matrix.setRotateM(mRotationMatrix,  0,  mAngle,  0,  1.0f,  0.0f);
		
		// MVP = Proj*View*Rot
		Matrix.multiplyMM(mMVPMatrix,  0,  mTemp, 0, mRotationMatrix,  0);
		
		
		// normal mat = transpose(inv(modelview)); 
		Matrix.multiplyMM(mTemp, 0, mVMatrix, 0, mRotationMatrix, 0);
		
		float [] tt = new float[16]; 
		Matrix.invertM(tt, 0, mTemp, 0);
		Matrix.transposeM(mNormalMat, 0, tt, 0);
		
		//scale = 1.0f; 
		//Matrix.scaleM(mTemp, 0, scale, scale, scale);
		//Matrix.multiplyMM(mMVPMatrix, 0, mMVPMatrix, 0, mTemp, 0);
		//mSquare.draw(mMVPMatrix);
		
		mSphere.lightDir[0] += Dx*0.02; 
		mSphere.lightDir[1] -= Dy*0.02;
		
		//mSphere.draw(mMVPMatrix, mNormalMat, mTemp); // mTemp is the model*view matrix;  
		
		
		float [] t2 = new float[16]; 
		
		for (int kk = 0; kk< 16; kk++) t2[kk] = mMVPMatrix[kk]; 
		Matrix.scaleM(t2, 0, 1.0f, 1.0f, 1.0f);
		Matrix.translateM(t2, 0, 1.0f, 0, 2.0f);
		
		mSphere.draw(t2, mNormalMat, mTemp); 
		
		
		mCube.draw(mMVPMatrix, mNormalMat);
		
		
		//mSphere.draw(mProjMatrix, );
		// Create a rotation transformation for the triangle
//		long time = SystemClock.uptimeMillis() % 4000L;
//		float angle = 0.090f * ((int) time);
		// instead of rotate automatically, we use mAngle
		

		// Combine the rotation matrix with the projection and camera view
		//Matrix.multiplyMM(mMVPMatrix,  0,  mRotationMatrix,  0,  mMVPMatrix,  0);
		
		// Draw shape
		//mTriangle.draw(mMVPMatrix);
	}

	// Called if the geometry of the view changes
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// set view port to the windows's width and height
		GLES20.glViewport(0,  0,  width,  height);
		
		//GLU.gluPr
		
		float ratio = (float) width / height;
		
		// this projection matrix is applied to object coordinates
		// in the onDrawFrame() method
		
		// if you have android API level 14+ you can use this
		//Matrix.perspectiveM(mProjMatrix, 0, 60.0f, ratio, 1.0f, 7.0f);
		//Matrix.multiplyMV(arg0, arg1, arg2, arg3, arg4, arg5);
		Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 1, 7);
	}
	
	// compile GLSL code prior to using it in OpenGL ES environment
	public static int loadShader(int type, String shaderCode) {
		
		// create a vertex shader type (GLES20.GL_VERTEX_SHADER)
		// or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
		int shader = GLES20.glCreateShader(type);
		
		// add the source code to the shader and compile it
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		
		return shader;
	}

}