package CS304.lab2.boat;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class BoatEventListener implements GLEventListener {
	int screenHeight = 600;
	int screenWidth = 600;

	@Override
	public void init(GLAutoDrawable glAutoDrawable) { // when running this first thing starts, it just make the structure of our GUI
		GL gl = glAutoDrawable.getGL();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // changing the color of the background(GLCanvas)
		gl.glLoadIdentity();
		gl.glOrtho(0.0, screenHeight, 0.0, screenWidth, -1.0, 1.0);
	}

	double firstBoatSpeed = 0;
	double secondBoatSpeed = 0;
	double thirdBoatSpeed = 0;

	@Override
	public void display(GLAutoDrawable glAutoDrawable) {
		GL gl = glAutoDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glPointSize(1);


		{

			{
				colorIT(135, 206, 234, gl);
				drawRectangle(0, 0, screenWidth, screenHeight, gl);
				colorIT(0, 102, 203, gl);
				drawRectangle(0, 0, screenWidth, (int) (screenHeight * 0.5), gl);
			}// sea

			{
				gl.glPushMatrix(); // saves the Drawing
				double upAndDown = Math.sin(firstBoatSpeed * 0.05) * 10.0;
				System.out.println(upAndDown);
				gl.glTranslated(0, upAndDown, 0);
				gl.glBegin(GL.GL_POINTS);
				colorIT(19, 125, 226, gl);
				for (int y = 50; y <= 250; y += 50)
					for (int x = 0; x < screenWidth; x++)
						gl.glVertex2d(x, (Math.sin(x / 30.0) * 20.0) + y);
				gl.glEnd();
				gl.glPopMatrix();
			}// waves

			gl.glPushMatrix(); // saves the Drawing
			double upAndDown3 = Math.sin(firstBoatSpeed * 0.05) * 10.0;
			gl.glTranslated((thirdBoatSpeed -= 0.5) + 150, upAndDown3 + 170, 0); // (x,y,x)moves 10 steps up
			gl.glScaled(0.3, 0.3, 1);//(x,y,z) max = 1 , y = -1 to rotate it anti way
			drawBoat(gl);//boat 1
			gl.glPopMatrix(); // get the saved Drawing

			gl.glPushMatrix(); // saves the Drawing
			double upAndDown1 = Math.sin(firstBoatSpeed * 0.05) * 10.0;
			gl.glTranslated((firstBoatSpeed++) - 150, upAndDown1 - 170, 0);
			gl.glScaled(0.9, 0.9, 1);//(x,y,z) max = 1 , y = -1 to rotate it anti way
			drawBoat(gl);//boat 2
			gl.glPopMatrix(); // get the saved Drawing

			{
				colorIT(255, 223, 0, gl); //(R,G,B)
				drawCircle(40, 0, 600, gl);
			}//^Sun
		}
		//^ a boat in the sea

	}

	public void drawBoat(GL gl) {
		colorIT(204, 126, 51, gl);
		drawRectangle((int) (screenWidth * 0.25), (int) (screenHeight * 0.5), 300, 30, gl);
		//Deck

		colorIT(153, 75, 26, gl);
		draw4Shape(170, 270, 420, 270, 450, 300, 150, 300, gl);
		//Hull

		colorIT(102, 50, 0, gl);
		drawRectangle(300, 330, 10, 200, gl);
		//Mast

		colorIT(255, 255, 255, gl);
		draw3Shape(310, 350, 410, 350, 310, 530, gl);
		//MainSail
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
