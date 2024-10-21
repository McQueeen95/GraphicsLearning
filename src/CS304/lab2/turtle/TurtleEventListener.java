package CS304.lab2.turtle;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;


public class TurtleEventListener implements GLEventListener{
    int screenHeight = 600;
    int screenWidth = 600;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) { // when running this first thing starts, it just make the structure of our GUI

        GL gl = glAutoDrawable.getGL();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // changing the color of the background(GLCanvas)

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

        {
            colorIT(0,230,0,gl);
            drawCircle(100,300,300,gl);
            colorIT(0,255,0,gl);
            drawCircle(80,300,300,gl);
            colorIT(0,200,0,gl);
            drawCircle(50,400,400,gl);
            drawCircle(50,400,200,gl);
            drawCircle(50,200,200,gl);
            drawCircle(50,200,400,gl);
        }
        //^Turtle
    }
    public void drawBoat(GL gl){
        colorIT(204, 126, 51, gl);
        drawRectangle((int) (screenWidth * 0.25), (int) (screenHeight * 0.5), 300, 30, gl);
        //Deck

        colorIT(153,75,26,gl);
        draw4Shape(170,270,420,270,450,300,150,300,gl);
        //Hull

        colorIT(102, 50, 0, gl);
        drawRectangle(300, 330, 10, 200, gl);
        //Mast

        colorIT(255,255,255,gl);
        draw3Shape(310,350,410,350,310,530,gl);
        //MainSail
    }
    public void colorIT(int r,int g,int b,GL gl){
        gl.glColor3f((float) r / 255, (float) g / 255, (float) b / 255); //(R,G,B)
    }
    public void drawRectangle(int startX,int startY,int width,int height,GL gl){

        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(startX,startY);
        gl.glVertex2d((width+startX),startY);

        gl.glVertex2d((width+startX),startY);
        gl.glVertex2d((width+startX),(height+startY));

        gl.glVertex2d((width+startX),(height+startY));
        gl.glVertex2d(startX,(height+startY));

        gl.glVertex2d(startX,(height+startY));
        gl.glVertex2d(startX,startY);

        gl.glEnd();
    }
    public void draw3Shape(int x1,int y1,int x2,int y2,int x3,int y3,GL gl){

        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);

        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);

        gl.glVertex2d(x3, y3);
        gl.glVertex2d(x1, y1);

        gl.glEnd();
    }
    public void draw4Shape(int x1 , int y1 ,int x2, int y2, int x3,int y3,int x4,int y4,GL gl){

        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1,y1);
        gl.glVertex2d(x2,y2);

        gl.glVertex2d(x2,y2);
        gl.glVertex2d(x3,y3);

        gl.glVertex2d(x3,y3);
        gl.glVertex2d(x4,y4);

        gl.glVertex2d(x4,y4);
        gl.glVertex2d(x1,y1);

        gl.glEnd();

    }

    public void drawCircle(double rad,int scaleX,int scaleY,GL gl){

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
