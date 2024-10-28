package CS304.lab3.Owl;


import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GL;


public class OwlEventListener implements GLEventListener {
	int screenHeight = 600;
	int screenWidth = 600;
	int x = 0;
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
		gl.glPointSize(10);

		{
			colorIT(210, 149, 80, gl);
			drawRectangle(-screenWidth / 2, -screenHeight / 2, screenWidth, screenHeight, gl);
			colorIT(197, 109, 34, gl);
			draw3Shape(-100, 100, 100, 100, 0, -125, gl);
			colorIT(249, 249, 216, gl);
			drawCircle(125, -90, 100, gl);
			drawCircle(125, 90, 100, gl);
			{
				colorIT(12,15,22,gl);
				gl.glPushMatrix();

				/*double ONE_DEGREE = (Math.PI / 180);
				double THREE_SIXTY = Math.PI;
				double rad =50;
				int scaleX = 0;
				int scaleY = 0 ;
				for (double a = 0; a < THREE_SIXTY; a += ONE_DEGREE) {
					int x = (int) (rad * (Math.cos(a))) + scaleX;
					int y = (int) (rad * (Math.sin(a))) + scaleY;
				}*/
				double leftAndRight = Math.cos(x * 0.005) * -40.0;
				double upAndDown = Math.sin(x * 0.004) * -17.0;
				if(upAndDown > 0) upAndDown = -upAndDown;
				System.out.println(upAndDown);
				gl.glTranslated(leftAndRight + 30, upAndDown, 0);

				drawCircle(65,-120,80,gl);
				drawCircle(65,60,80,gl);
				gl.glPopMatrix();
			}//^eyes motion
		}//^owl
		{
			colorIT(46, 36, 23, gl);
			drawRectangle(-screenWidth / 2, -screenHeight / 2, screenWidth, 50, gl);
			colorIT(178, 0, 0, gl);
			gl.glPushMatrix();
			gl.glTranslated(x += 5, 0, 0);
			if (x == screenWidth + 75) {
				x = -75; // go right and back from left
				System.out.println("done");
			}
			drawCircle(30, -screenWidth / 2, -((screenHeight / 2) - 79), gl);
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
/*	public void draw4Shape(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, GL gl) {

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

	}*/
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

	@Override
	public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

	}

	@Override
	public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

	}
}
