package CS304.lab1;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class SimpleGLEventListener implements GLEventListener {
int screenHeight = 600;
int screenWidth = 600;

  @Override
  public void init(GLAutoDrawable glAutoDrawable) { // when running this first thing starts, it just make the structure of our GUI

    GL gl = glAutoDrawable.getGL();

    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // changing the color of the background(GLCanvas)

//    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();

    // ortho of colored triangle
    //gl.glOrtho(0.0, 500.0, 0.0, 500.0, -1.0, 1.0); // the pixels inside the window
    // gl.glOrtho(miniY,maxY,miniX,maxX)l

    gl.glOrtho(0.0, screenHeight, 0.0, screenWidth, -1.0, 1.0);
  }




  @Override
  public void display(GLAutoDrawable glAutoDrawable) {

    GL gl = glAutoDrawable.getGL();

    gl.glClear(GL.GL_COLOR_BUFFER_BIT);

    gl.glPointSize(1);

//    gl.glPushMatrix(); // saves the Drawing
//    gl.glTranslated(0,200,0); // (x,y,x)moves 10 steps up
//    gl.glRotated(0,0,0,1); // rotate the axis with 90 anti clockwise
//    gl.glScaled(0.5,0.5,1);//(x,y,z) max = 1 , y = -1 to rotate it anti way

    {
      gl.glBegin(GL.GL_POLYGON);

      gl.glColor3f(1.0f, 0.0f, 0.0f); //(R,G,B)
      gl.glVertex2d(100, 100);
      gl.glColor3f(0.0f, 1.0f, 0.0f); //(R,G,B)
      gl.glVertex2d(200, 200);

      gl.glColor3f(0.0f, 1.0f, 0.0f); //(R,G,B)
      gl.glVertex2d(200, 200);
      gl.glColor3f(0.0f, 0.0f, 1.0f); //(R,G,B)
      gl.glVertex2d(300, 300);

      gl.glColor3f(0.0f, 0.0f, 1.0f); //(R,G,B)
      gl.glVertex2d(300, 300);
      gl.glColor3f(0.0f, 1.0f, 1.0f); //(R,G,B)
      gl.glVertex2d(400, 400);


      gl.glColor3f(1.0f, 0.0f, 0.0f); //(R,G,B)
      gl.glVertex2d(400, 100);
      gl.glColor3f(0.0f, 1.0f, 0.0f); //(R,G,B)
      gl.glVertex2d(400, 200);

      gl.glColor3f(0.0f, 1.0f, 0.0f); //(R,G,B)
      gl.glVertex2d(400, 200);
      gl.glColor3f(0.0f, 0.0f, 1.0f); //(R,G,B)
      gl.glVertex2d(400, 300);

      gl.glColor3f(0.0f, 0.0f, 1.0f); //(R,G,B)
      gl.glVertex2d(400, 300);
      gl.glColor3f(0.0f, 1.0f, 1.0f); //(R,G,B)
      gl.glVertex2d(400, 400);


      gl.glColor3f(1.0f, 0.0f, 0.0f); //(R,G,B)
      gl.glVertex2d(100, 100);
      gl.glColor3f(0.0f, 1.0f, 0.0f); //(R,G,B)
      gl.glVertex2d(200, 100);

      gl.glColor3f(0.0f, 1.0f, 0.0f); //(R,G,B)
      gl.glVertex2d(200, 100);
      gl.glColor3f(0.0f, 0.0f, 1.0f); //(R,G,B)
      gl.glVertex2d(300, 100);

      gl.glColor3f(0.0f, 0.0f, 1.0f); //(R,G,B)
      gl.glVertex2d(300, 100);
      gl.glColor3f(0.0f, 1.0f, 1.0f); //(R,G,B)
      gl.glVertex2d(400, 100);
      gl.glEnd();
    }
    {
      gl.glBegin(GL.GL_POINTS);
      gl.glVertex2d(500, 500);
      gl.glEnd();
    }
//    gl.glPopMatrix(); // get the saved Drawing
    //^ colored trinagle

    /*gl.glBegin(GL.GL_POLYGON);

    float r = (float) 40 /255;
    float g = (float) 140 /255;
    float b = (float) 84 /255;
    gl.glColor3f(r, g, b); //(R,G,B)
    gl.glVertex2d(0,0);
    gl.glVertex2d(600,0);

    gl.glVertex2d(600,0);
    gl.glVertex2d(600,100);

    gl.glVertex2d(600,100);
    gl.glVertex2d(0,100);

    gl.glVertex2d(0,100);
    gl.glVertex2d(0,0);

    gl.glEnd();

    gl.glBegin(GL.GL_POLYGON);

    gl.glColor3f(1, 1, 1); //(R,G,B)

    gl.glVertex2d(600,200);
    gl.glVertex2d(0,200);

    gl.glVertex2d(600,200);
    gl.glVertex2d(600,100);

    gl.glVertex2d(600,100);
    gl.glVertex2d(0,100);

    gl.glVertex2d(0,100);
    gl.glVertex2d(0,200);

    gl.glEnd();

    gl.glBegin(GL.GL_POLYGON);

    float rf = (float) 228 /255;
    float gf = (float) 49 /255;
    float bf = (float) 43 /255;

    gl.glColor3f(rf, gf, bf); //(R,G,B)

    gl.glVertex2d(0,0);
    gl.glVertex2d(150,150);

    gl.glVertex2d(150,150);
    gl.glVertex2d(0,300);





    gl.glEnd();*/
    //^ palastine flag

    /*
    {
      gl.glBegin(GL.GL_POLYGON);

      float r = (float) 122 / 255;
      float g = (float) 79 / 255;
      float b = (float) 37 / 255;
      gl.glColor3f(r, g, b); //(R,G,B)

      gl.glVertex2d(0, 0);
      gl.glVertex2d(600, 0);

      gl.glVertex2d(600, 0);
      gl.glVertex2d(600, 200);

      gl.glVertex2d(600, 200);
      gl.glVertex2d(0, 200);

      gl.glVertex2d(0, 200);
      gl.glVertex2d(0, 0);

      gl.glEnd();

    }// earth^
    {
      gl.glBegin(GL.GL_POLYGON);

      float r1 = (float) 128 / 255;
      float g1 = (float) 218 / 255;
      float b1 = (float) 235 / 255;
      gl.glColor3f(r1, g1, b1); //(R,G,B)

      gl.glVertex2d(0, 200);
      gl.glVertex2d(0, 600);

      gl.glVertex2d(0, 600);
      gl.glVertex2d(600, 600);

      gl.glVertex2d(600, 600);
      gl.glVertex2d(600, 200);

      gl.glEnd();
    }// ^sky
    {
      gl.glBegin(GL.GL_POLYGON);
      float r3 = (float) 237 / 255;
      float g3 = (float) 201 / 255;
      float b3 = (float) 175 / 255;

      gl.glColor3f(r3, g3, b3); //(R,G,B)

      gl.glVertex2d(0, 0);
      gl.glVertex2d(0, 280);

      gl.glVertex2d(0, 280);
      gl.glVertex2d(200, 450);

      gl.glVertex2d(200, 450);
      gl.glVertex2d(450, 0);

      gl.glVertex2d(450, 0);
      gl.glVertex2d(0, 0);

      gl.glEnd();
    }//middle pyramid
    {
      gl.glBegin(GL.GL_POLYGON);


      float r2 = (float) 245 / 255;
      float g2 = (float) 211 / 255;
      float b2 = (float) 165 / 255;

      gl.glColor3f(r2, g2, b2); //(R,G,B)

      gl.glVertex2d(0, 0);
      gl.glVertex2d(0, 400);

      gl.glVertex2d(0, 400);
      gl.glVertex2d(100, 500);

      gl.glVertex2d(100, 500);
      gl.glVertex2d(350, 0);

      gl.glVertex2d(350, 0);
      gl.glVertex2d(0, 0);

      gl.glEnd();
    }//^bigger pyramid
    {
      double radius = 60;
      double ONE_DEGREE = (Math.PI / 180);
      double THREE_SIXTY = 2 * Math.PI;

      colorIT(255,223,0,glAutoDrawable); //(R,G,B)
      gl.glBegin(GL.GL_POLYGON);
      for (double a = 0; a < THREE_SIXTY; a += ONE_DEGREE) {
        int x = (int) (radius * (Math.cos(a))) + 450;
        int y = (int) (radius * (Math.sin(a))) + 450;
        gl.glVertex2d(x, y);
      }
      gl.glEnd();
    }//^Sun

    */
    //^ Pyramids


  }


  @Override
  public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

  }

  @Override
  public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

  }
}
