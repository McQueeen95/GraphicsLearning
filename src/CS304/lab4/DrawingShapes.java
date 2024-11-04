package CS304.lab4;

import com.sun.opengl.util.FPSAnimator;
import sun.security.rsa.RSAUtil;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class DrawingShapes extends JFrame {
	private GLCanvas glCanvas;
	static FPSAnimator animator = null;
	private DrawingShapesEventListener listener = new DrawingShapesEventListener();

	public static void main(String[] args) {
		new DrawingShapes();
		animator.start();
	}
	public DrawingShapes(){
		super("Draw any Shape with zoom in/out");
		glCanvas = new GLCanvas();
		animator = new FPSAnimator(glCanvas,60);
		glCanvas.addGLEventListener(listener);
		glCanvas.addKeyListener(listener);
		glCanvas.addMouseListener(listener);
		glCanvas.addMouseMotionListener(listener);
		getContentPane().add(glCanvas, BorderLayout.CENTER);
		glCanvas.setFocusable(true);
		setSize(600,600);
		setLocationRelativeTo(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
