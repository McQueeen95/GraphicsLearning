package CS304;

import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import com.sun.opengl.util.FPSAnimator;

public class task_2_1 extends JFrame {
    static FPSAnimator animator = null;

    public static void main(String[] args) {
        final task_2_1 app = new task_2_1();
        animator.start();
    }

    public task_2_1() {
        super("Animated JOGL Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GLCanvas glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(new AnimatedGLEventListener5());
        animator = new FPSAnimator(glcanvas, 60);
        add(glcanvas, BorderLayout.CENTER);
        setSize(600, 300);
        centerWindow();
        setVisible(true);
    }

    public void centerWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
        if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;
        this.setLocation(
                (screenSize.width - frameSize.width) >> 1,
                (screenSize.height - frameSize.height) >> 1
        );
    }
}

class AnimatedGLEventListener5 implements GLEventListener {
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



    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-300.0, 300.0, -150.0, 150.0, -1.0, 1.0);
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
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

        // Draw circle
        gl.glColor3f(217/255f, 176/255f, 0.0f);
        drawCircle(gl, 0, 0, RADIUS);

        // Draw triangle
        gl.glColor3f(217/255f, 176/255f, 0.0f);
        drawTriangle(gl, 0, 0, 28);

        gl.glPopMatrix();
    }

     private void drawCircle(GL gl, float x, float y, float radius) {
        gl.glBegin(GL.GL_LINE_LOOP); // Use GL_LINE_LOOP for an empty circle
        for (int i = 0; i <= 360; i++) {
            double angle = Math.toRadians(i);
            gl.glVertex2d(x + Math.cos(angle) * radius, y + Math.sin(angle) * radius);
        }
        gl.glEnd();
    }

    private void drawTriangle(GL gl, float x, float y, float size) {
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex2f(x, y + size);
        gl.glVertex2f(x - size * 0.866f, y - size * 0.5f);
        gl.glVertex2f(x + size * 0.866f, y - size * 0.5f);
        gl.glEnd();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
    public void dispose(GLAutoDrawable arg0) {}
}