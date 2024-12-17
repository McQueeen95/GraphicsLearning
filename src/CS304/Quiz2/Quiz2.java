package CS304.Quiz2;


import CS304.quiz.Quiz;
import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;

public class Quiz2 extends JFrame{
	private GLCanvas glcanvas;
	static FPSAnimator animator =null;

	private Quiz2Eventlistener listener = new Quiz2Eventlistener();


	public static void main(String[] args) {

		new Quiz2();
		animator.start();
	}
	public Quiz2(){

		//set the JFrame title
		super("First Circle Using Sine and Cosine");
		//kill the process when the JFrame is closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		glcanvas = new GLCanvas();
		// initialize the animator with the GLCanvas and the frame rate
		animator = new FPSAnimator(glcanvas, 60);
		// add the GLEventListener to the GLCanvas
		glcanvas.addGLEventListener(listener);
		// add the mouse motion listener to the GLCanvas
		//add the GLCanvas just like we would any Component
		getContentPane().add(glcanvas, BorderLayout.CENTER);
		// set the focus to the GLCanvas
		glcanvas.setFocusable(true);
		// set the size of the JFrame
		setSize(800, 500);
		// set the location of the JFrame
		setLocationRelativeTo(this);
		// make the JFrame visible
		setVisible(true);
	}
}
