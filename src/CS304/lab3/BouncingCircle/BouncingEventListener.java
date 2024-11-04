package CS304.lab3.BouncingCircle;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class BouncingEventListener implements GLEventListener {

	int screenHeight = 600;
	int screenWidth = 600;

	int x=0;


	@Override
	public void init(GLAutoDrawable glAutoDrawable) {
		GL gl = glAutoDrawable.getGL();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glLoadIdentity();
		gl.glOrtho(-screenHeight/2.0,  screenHeight/2.0,-screenWidth/2.0,screenWidth/2.0,-1.0, 1.0);
	}
	@Override
	public void display(GLAutoDrawable glAutoDrawable) {
		GL gl = glAutoDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glPointSize(4);

/*
    gl.glPushMatrix(); // saves the Drawing
    gl.glTranslated(0,200,0); // (x,y,x)moves 10 steps up
    gl.glRotated(0,0,0,1); // rotate the axis with 90 anti-clockwise
    gl.glScaled(0.5,0.5,1);//(x,y,z) max = 1 , y = -1 to rotate it anti way
    gl.glPopMatrix();a
*/

		{

			colorIT(247,205,209,gl);
			for (int i = (int)(-screenHeight/2.0); i < screenHeight/2; i+=20) {
				drawRectangle(i,(int)(-screenHeight/2.0),1,screenHeight,gl);
			}
			for (int i = (int)(-screenWidth/2.0); i < screenWidth/2; i+=20) {
				drawRectangle((int)(-screenWidth/2.0),i,screenWidth,2,gl);
			}
			colorIT(255, 255, 255, gl);
			drawRectangle(0,(int)(-screenHeight/2.0),2,screenHeight,gl);
			drawRectangle((int)(-screenWidth/2.0),0,screenWidth,4,gl);
			colorIT(154,53,2,gl);

			gl.glPushMatrix();
			double leftAndRight = Math.cos(x * 0.1) * 260.0;
			double upAndDown = Math.sin(x * 0.1) * 260.0;
//			System.out.println(leftAndRight);
			gl.glTranslated(leftAndRight,upAndDown,0);

			gl.glScaled(0.5,0.5,1);
			gl.glRotated(x++,0,0,1);
			drawCircle(64,0,35,gl);
			colorIT(232,155,11,gl);
			draw3Shape(-50,0,50,0,0,100,gl);
			gl.glPopMatrix();
		}


	}
	public void colorIT(int r, int g, int b, GL gl) {
		gl.glColor3f((float) r / 255, (float) g / 255, (float) b / 255); //(R,G,B)
	}
	public void drawRectangle(int startX, int startY, int width, int height, GL gl) {

		gl.glBegin(GL.GL_POLYGON);

		gl.glVertex2d(startX, startY);
		gl.glVertex2d((width + startX), startY);

		gl.glVertex2d((width + startX), startY);
		gl.glVertex2d((width + startX), (height + startY));

		gl.glVertex2d((width + startX), (height + startY));
		gl.glVertex2d(startX, (height + startY));

		gl.glVertex2d(startX, (height + startY));
		gl.glVertex2d(startX, startY);

		gl.glEnd();
	}
	public void draw3Shape(int x1, int y1, int x2, int y2, int x3, int y3, GL gl) {

		gl.glBegin(GL.GL_POLYGON);

		gl.glVertex2d(x1, y1);
		gl.glVertex2d(x2, y2);

		gl.glVertex2d(x2, y2);
		gl.glVertex2d(x3, y3);

		gl.glVertex2d(x3, y3);
		gl.glVertex2d(x1, y1);

		gl.glEnd();
	}
	public void draw4Shape(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, GL gl) {

		gl.glBegin(GL.GL_POLYGON);

		gl.glVertex2d(x1, y1);
		gl.glVertex2d(x2, y2);

		gl.glVertex2d(x2, y2);
		gl.glVertex2d(x3, y3);

		gl.glVertex2d(x3, y3);
		gl.glVertex2d(x4, y4);

		gl.glVertex2d(x4, y4);
		gl.glVertex2d(x1, y1);

		gl.glEnd();

	}
	public void drawCircle(double rad, int scaleX, int scaleY, GL gl) {

		double ONE_DEGREE = (Math.PI / 180);
		double THREE_SIXTY = 2 * Math.PI;

		gl.glBegin(GL.GL_POINTS);
		for (double a = 0; a < THREE_SIXTY; a += ONE_DEGREE) {
			int x = (int) (rad * (Math.cos(a))) + scaleX;
			int y = (int) (rad * (Math.sin(a))) + scaleY;
			gl.glVertex2d(x, y);
		}
		gl.glEnd();
	}

	@Override
	public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

	}

	@Override
	public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

	}
}
