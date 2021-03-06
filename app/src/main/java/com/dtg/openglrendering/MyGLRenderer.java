package com.dtg.openglrendering;


/**
 * Created by Taran on 12/14/2015.
 */
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
/**
 *  OpenGL Custom renderer used with GLSurfaceView
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    Context context;   // Application's context
    private TextureCube cube;
    // For controlling cube's z-position, x and y angles and speeds (NEW)
    float angleX = 0;   // (NEW)
    float angleY = 0;   // (NEW)
    float speedX = 0;   // (NEW)
    float speedY = 0;   // (NEW)
    float z = -6.0f;    // (NEW)

    // Rotational angle and speed (NEW)
    private float angleTriangle = 0.0f; // (NEW)
    private float angleQuad = 0.0f;     // (NEW)
    private float speedTriangle = 0.5f; // (NEW)
    private float speedQuad = -0.4f;    // (NEW)


    private static float anglePyramid = 0; // Rotational angle in degree for pyramid (NEW)
    private static float angleCube = 0;    // Rotational angle in degree for cube (NEW)
    private static float speedPyramid = 2.0f; // Rotational speed for pyramid (NEW)
    private static float speedCube = -1.5f;   // Rotational speed for cube (NEW)


    // Constructor with global application context
    public MyGLRenderer(Context context) {
        this.context = context;

        // Set up the data-array buffers for these shapes ( NEW )
//        triangle = new Triangle();   // ( NEW )
//        quad = new Square();         // ( NEW )

        // Set up the buffers for these shapes
//        pyramid = new Pyramid();   // (NEW)
//        cube = new Cube();         // (NEW)
        cube = new TextureCube();
    }

    // Call back when the surface is first created or re-created
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // Set color's clear-value to black
        gl.glClearDepthf(1.0f);            // Set depth's clear-value to farthest
        gl.glEnable(GL10.GL_DEPTH_TEST);   // Enables depth-buffer for hidden surface removal
        gl.glDepthFunc(GL10.GL_LEQUAL);    // The type of depth testing to do
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);  // nice perspective view
        gl.glShadeModel(GL10.GL_SMOOTH);   // Enable smooth shading of color
        gl.glDisable(GL10.GL_DITHER);      // Disable dithering for better performance

        // Setup Texture, each time the surface is created
        cube.loadTexture(gl, context);    // Load image into Texture
        gl.glEnable(GL10.GL_TEXTURE_2D);  // Enable texture
    }

    // Call back after onSurfaceCreated() or whenever the window's size changes
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0) height = 1;   // To prevent divide by zero
        float aspect = (float)width / height;

        // Set the viewport (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
        gl.glLoadIdentity();                 // Reset projection matrix
        // Use perspective projection
        GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);  // Select model-view matrix
        gl.glLoadIdentity();                 // Reset
    }

    // Call back to draw the current frame.
    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear color and depth buffers using clear-value set earlier
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

//        gl.glLoadIdentity();                 // Reset model-view matrix ( NEW )
//        gl.glTranslatef(0.0f, 0.0f, -8.0f); // Translate left and into the screen ( NEW )
////        gl.glRotatef(anglePyramid, getRotateX(), getRotateY(), getRotateZ()); // Rotate (NEW)
//        pyramid.draw(gl);                              // Draw the pyramid (NEW)

        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, z);   // Translate into the screen (NEW)
        gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f); // Rotate (NEW)
        gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f); // Rotate (NEW)
        cube.draw(gl);

        // Update the rotational angle after each refresh (NEW)
        angleX += speedX;  // (NEW)
        angleY += speedY;  // (NEW)

    }
}