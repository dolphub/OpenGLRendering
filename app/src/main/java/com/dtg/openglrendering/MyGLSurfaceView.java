package com.dtg.openglrendering;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/*
 * Custom GL view by extending GLSurfaceView so as
 * to override event handlers such as onKeyUp(), onTouchEvent()
 */
public class MyGLSurfaceView extends GLSurfaceView {
    MyGLRenderer renderer;    // Custom GL Renderer

    // For touch event
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320.0f;
    private float previousX;
    private float previousY;

    // Constructor - Allocate and set the renderer
    public MyGLSurfaceView(Context context) {
        super(context);
        renderer = new MyGLRenderer(context);
        this.setRenderer(renderer);
        // Request focus, otherwise key/button won't react
        this.requestFocus();
        //this.setFocusableInTouchMode(true);
    }
    // Handler for touch event
    private boolean pinch;
    @Override
    public boolean onTouchEvent(final MotionEvent evt) {
        float currentX = evt.getX();
        float currentY = evt.getY();
        float deltaX, deltaY;
        switch (evt.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (pinch)
                {
                    renderer.z += 0.02f;
                }
                else
                {
                    // Modify rotational angles according to movement
                    deltaX = currentX - previousX;
                    deltaY = currentY - previousY;
                    renderer.angleX += deltaY * TOUCH_SCALE_FACTOR;
                    renderer.angleY += deltaX * TOUCH_SCALE_FACTOR;
//                    renderer.z -= 0.02f;
                }
            case MotionEvent.ACTION_POINTER_DOWN:
                pinch = true;
            case MotionEvent.ACTION_POINTER_UP:
                pinch = false;
        }
        // Save current x, y
        previousX = currentX;
        previousY = currentY;
        return true;  // Event handled
    }
}