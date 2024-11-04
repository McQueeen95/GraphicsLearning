package CS304.lab3.BouncingCircle;

import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class BouncingCircle extends JFrame {
	static FPSAnimator animator = null;

	public static void main(String[] args) {
		new BouncingCircle();
		animator.start();
	}
	public BouncingCircle(){
		super("this is a Bouncing Triangle inside a Circle");
		GLCanvas glCanvas = new GLCanvas();
		glCanvas.addGLEventListener(new BouncingEventListener());
		getContentPane().add(glCanvas, BorderLayout.CENTER);
		animator = new FPSAnimator(glCanvas,60);

		setSize(600,600);
		setLocationRelativeTo(this);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
