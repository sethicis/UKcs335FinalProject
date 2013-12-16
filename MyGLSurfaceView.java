package com.uky.cs535.myopengles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

// a view that can draw and manipulate objects using OpenGL ES 2.0
public class MyGLSurfaceView extends GLSurfaceView {
	
	private float mPreviousX;
	private float mPreviousY;
	
	private MyGL20Renderer mRenderer;
	
	private final float TOUCH_SCALE_FACTOR = 180.0F / 320;
	
	private Context mC; 
	public MyGLSurfaceView(Context context) {
		super(context);
		mC = context; 
		
		// Create and OpenGL ES 2.0 context
		setEGLContextClientVersion(2);

		// Set the Renderer for drawing on the GLSurfaceView
		mRenderer = new MyGL20Renderer(context);
		setRenderer(mRenderer);
		
		// Render the view only when there is a change in the drawing data
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY); // comment out for auto-rotation
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		// MotionEvent reports input details from the touch screen
		// and other input controls. In this case, you are only
		// interested in events where the touch position changed.
		
		float x = e.getX();
		float y = e.getY();
		
		
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			
			float dx = x - mPreviousX;
			float dy = y - mPreviousY;
			
			mRenderer.Dx = dx;
			mRenderer.Dy = dy; 
			// reverse direction of rotation above the mid-line
			if (y > getHeight() / 2) {
				dx = dx * -1;
			}
			
			// reverse direction of rotation to left of the mid-line
			if (x < getWidth() / 2) {
				dy = dy * -1;
			}
			
			mRenderer.mAngle += (dx + dy) * TOUCH_SCALE_FACTOR;	// = 180.0f / 320
			requestRender();
		}
		
		mPreviousX = x;
		mPreviousY = y;
		
		return true;
	}

}
