package CS304.Quiz2;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Quiz2Eventlistener  implements GLEventListener{
	int screenHeight = 200;
	int screenWidth = 200;
	double x = 0;
	double y = 0;
	double dx = Math.random()*10;
	double    dy = Math.random()*10;
	private float angle = 0;
	private final float RADIUS = 30;


	double g=0;
	double f=0;
	double p=0;
	double p1=0;


	@Override
	public void init(GLAutoDrawable glAutoDrawable) {
		GL gl = glAutoDrawable.getGL();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // changing the color of the background(GLCanvas)
		gl.glViewport(0, 0, screenWidth, screenHeight);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(-screenHeight, screenHeight, -screenWidth, screenWidth, -1.0, 1.0);
	}

	@Override
	public void display(GLAutoDrawable glAutoDrawable) {
		GL gl = glAutoDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		p =Math.cos(Math.random())*2 ;
		p1 =Math.sin(Math.random())*2;

		x += dx;
		y += dy;
		double h = Math.random()*70+10;
//        System.out.println(h);
		h = Math.toRadians(h);

		if (x + RADIUS > 300 || x - RADIUS < -300 ){
			dx = Math.cos(h)*2;
//            dy = Math.sin(h)*2;
			if(x + RADIUS > 300 ) dx = -dx;
			System.out.println(dx);
		}


		;
		if (y + RADIUS > 150 || y - RADIUS < -150) {
//            dx = Math.cos(h)*2;
			dy = Math.sin(h)*2 ;
			if(y + RADIUS > 150) dy = -dy;
			System.out.println(dy);
		};


		// Update rotation
		angle += 4;
//        if (angle >= 360) angle -= 360;

		gl.glPushMatrix();
		gl.glTranslated(x, y, 0);
		gl.glRotatef(angle, 0, 0, 1);

		colorIT(255,0,0,gl);
		gl.glPushMatrix();
		gl.glScaled(0.3,.3,1);
		drawEXAM(gl);
		gl.glPopMatrix();
		gl.glPopMatrix();


	}

	@Override
	public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

	}

	@Override
	public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

	}

	public void colorIT(int r, int g, int b, GL gl) {
		gl.glColor3f((float) r / 255, (float) g / 255, (float) b / 255); //(R,G,B)
	}

	public void drawEXAM(GL gl) {
		gl.glPushMatrix();
		gl.glTranslated(-100,0,0);
		drawE(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(-60,0,0);
		drawX(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(30,100,0);
		drawA(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(140,100,0);
		drawM(gl);
		gl.glPopMatrix();
	}
	public void drawE(GL gl) {
		drawRectangle(0,0,7,100,gl);
		drawRectangle(0,0,30,10,gl);
		drawRectangle(0,50,30,10,gl);
		drawRectangle(0,100,30,10,gl);
	}
	public void drawX(GL gl) {
		gl.glPushMatrix();
		gl.glRotated(-30,0,0,1);
		drawRectangle(0,0,7,100,gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glRotated(30, 0, 0, 1);
		gl.glTranslated(50, -25, 0);
		drawRectangle(0, 0, 7, 90, gl);
		gl.glPopMatrix();
	}
	public void drawA(GL gl) {
		gl.glPushMatrix();
		gl.glScaled(1,-1,1);
			gl.glPushMatrix();
			gl.glRotated(-20,0,0,1);
			drawRectangle(0,0,7,100,gl);
			gl.glPopMatrix();
			gl.glPushMatrix();
			gl.glRotated(20,0,0,1);
			drawRectangle(0,0,7,100,gl);
			gl.glPopMatrix();
			gl.glPushMatrix();
			gl.glRotated(-90,0,0,1);
			gl.glTranslated(-60,-20,0);
			drawRectangle(0,0,8,45,gl);
			gl.glPopMatrix();
		gl.glPopMatrix();
	}

	public void drawM(GL gl) {
		gl.glPushMatrix();
		gl.glScaled(1,-1,1);
			gl.glPushMatrix();
			gl.glRotated(-20,0,0,1);
			drawRectangle(0,0,7,100,gl);
			gl.glPopMatrix();
			gl.glPushMatrix();
			gl.glRotated(20,0,0,1);
			drawRectangle(0,0,7,40,gl);
			gl.glPopMatrix();
			gl.glPushMatrix();
			gl.glScaled(-1,1,1);
			gl.glTranslated(20,0,0);
				gl.glPushMatrix();
				gl.glRotated(-20,0,0,1);
				drawRectangle(0,0,7,100,gl);
				gl.glPopMatrix();
				gl.glPushMatrix();
				gl.glRotated(20,0,0,1);
				drawRectangle(0,0,7,40,gl);
				gl.glPopMatrix();
			gl.glPopMatrix();
		gl.glPopMatrix();
	}
	public void drawLine(int startX, int startY, int endX, int endY, GL gl) {
		gl.glBegin(GL.GL_POINTS);
		gl.glPointSize(20);
		gl.glVertex2d(startX, startY);
		gl.glVertex2d(endX,endY);
		gl.glEnd();
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

		gl.glBegin(GL.GL_POLYGON);
		for (double a = 0; a < THREE_SIXTY; a += ONE_DEGREE) {
			int x = (int) (rad * (Math.cos(a))) + scaleX;
			int y = (int) (rad * (Math.sin(a))) + scaleY;
			gl.glVertex2d(x, y);
		}
		gl.glEnd();
	}
}
