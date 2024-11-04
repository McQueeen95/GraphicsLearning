package CS304.lab4;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.awt.event.*;

public class DrawingShapesEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	int screenHeight = 600;
	int screenWidth = 600;
	int xMax = screenWidth / 2;
	int xMin = -(screenWidth / 2);
	int yMax = screenHeight / 2;
	int yMin = -(screenHeight / 2);
	int xArrows,yArrows;
	int zoom;
	double xMousePosition,yMousePosition;
	double xMouseMotion,yMouseMotion;

	@Override
	public void init(GLAutoDrawable glAutoDrawable) {
		GL gl = glAutoDrawable.getGL();
		gl.glClearColor(223/255.0f, 184/255f, 224/255f, 1.0f);
		gl.glLoadIdentity();
		gl.glOrtho(yMin, yMax, xMin, xMax, -1, 1);
	}

	@Override
	public void display(GLAutoDrawable glAutoDrawable) {
		GL gl = glAutoDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glPointSize(10);

		gl.glPushMatrix(); // saves the Drawing
		gl.glTranslated(xArrows,yArrows,0); // (x,y,x)moves 10 steps up
//		gl.glRotated(0,0,0,1); // rotate the axis with 90 anti-clockwise
//		gl.glScaled(zoom,zoom,1);//(x,y,z) max = 1 , y = -1 to rotate it anti way


//		drawSquare(xArrows,yArrows,gl);
//		drawHouse(0,0,gl);
		drawSquare(xMouseMotion,yMouseMotion,gl);

		gl.glPopMatrix();


	}
	public void drawHouse(double xM,double yM,GL gl){
		colorIT(123,42,2,gl);
		drawRectangle((int)xM,(int)yM,30,30,gl);
		draw3Shape((int)xM-3,(int)yM+30,(int)xM+33,(int)yM+30,(int)xM+15,(int)yM+50,gl);
	}
	public void drawSquare(double xM, double yM, GL gl) {
		colorIT(220,24,4,gl);
		drawRectangle((int)xM,(int)yM,40,40,gl);
	}
	public void drawTriangle(double xM, double yM,GL gl){
		colorIT(124,152,0,gl);
		draw3Shape((int)xM,(int)yM,(int)xM+30,(int)yM,(int)xM+15,(int)yM+30,gl);
	}
	public void drawFish(double xM, double yM, GL gl) {
		colorIT(4,106,167,gl);
		draw3Shape((int)xM,(int)yM,(int)xM,(int)yM+20,(int)xM+10,(int)yM+10,gl);
		draw4Shape((int)xM+9,(int)yM+10,(int)xM+35,(int)yM-5,(int)xM+50,(int)yM+10,(int)xM+35,(int)yM+25,gl);
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
	public double convertX(double x,double width){
		return ((x * (xMax - xMin)) / width) + xMin;
	}
	public double convertY(double y,double height){
		return ((y * (yMax - yMin)) / height) + yMin;
	}
	@Override
	public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

	}

	@Override
	public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

	}
/////////////////////////////////////v KeyBoard
	@Override
	public void keyTyped(KeyEvent e) {

	}
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode(); // getting the key hexa code

		if ((keyCode == KeyEvent.VK_EQUALS)) {
			zoom++;
		}
		if (keyCode == KeyEvent.VK_MINUS) {
			zoom--;
		}
		switch (keyCode) {
			case KeyEvent.VK_UP:
				yArrows += 10;
				break;
			case KeyEvent.VK_DOWN:
				yArrows -= 10;
				break;
			case KeyEvent.VK_RIGHT:
				xArrows += 10;
				break;
			case KeyEvent.VK_LEFT:
				xArrows -= 10;
				break;
//			case KeyEvent.VK_H:
		}

	}
	@Override
	public void keyReleased(KeyEvent e) {

	}
	/////////////////////////////////////////////////v Mouse
	@Override
	public void mouseClicked(MouseEvent e) {
		xMousePosition = convertX(e.getX(),e.getComponent().getWidth());
		yMousePosition = -convertY(e.getY(),e.getComponent().getHeight());
		System.out.println(e.getX()+" "+e.getY());
		System.out.println(xMousePosition+" "+yMousePosition);
	}
	@Override
	public void mousePressed(MouseEvent e) {

	}
	@Override
	public void mouseReleased(MouseEvent e) {

	}
	@Override
	public void mouseEntered(MouseEvent e) {

	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseDragged(MouseEvent e) {

	}
	@Override
	public void mouseMoved(MouseEvent e) {
		xMouseMotion = convertX(e.getX(),e.getComponent().getWidth());
		yMouseMotion = -convertY(e.getY(),e.getComponent().getHeight());
		System.out.println(e.getX()+" "+e.getY());
		System.out.println(xMouseMotion+" "+yMouseMotion);
	}
}

