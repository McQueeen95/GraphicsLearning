package CS304.quiz;

import CS304.Texture.TextureReader;
import CS304.lab4_2.KeyTypeEventListener;
import CS304.lab5.MonsterRush.MonsterRushEventListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class QuizEventListener implements GLEventListener, KeyListener {
	float screenHeight = 200;
	float screenWidth = 200;
	float xMax = screenWidth / 2f;
	float xMin = -(screenWidth / 2f);
	float yMax = screenHeight / 2f;
	float yMin = -(screenHeight / 2f);
	/////////////////////////////////////////////////      for screen
	String assetsFolderName = "CS304//Assets//Alphabet";
	String[] textureNames = {"..png","Back1.png"};
	String[] textureLettersNames = new String[26];
	TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
	TextureReader.Texture[] textureLetter = new TextureReader.Texture[textureLettersNames.length];
	int[] textures = new int[textureNames.length];
	int[] texturesLetters = new int[textureLettersNames.length];
	/////////////////////////////////////////////////////////////////////textures
	int blinking = 0;
	int letterWidth = 17, letterHeight=27;
	int marginBetweenLetters=10;
	int dotX = (int)xMin + 10;
	int dotY = (int)yMax - 15;
	int indexCursor = 1;
	List<Letter> letters = new ArrayList<>();
	int xLetter,yLetter;
	int numberOfLetter;

	/////////////////////////////////////////////////////////////////////^letters
	@Override
	public void init(GLAutoDrawable glAutoDrawable) {
		fillLetters();
		GL gl = glAutoDrawable.getGL();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE_MINUS_SRC_ALPHA);
		generateTextures(textureNames,texture,textures,gl);
		generateTextures(textureLettersNames,textureLetter,texturesLetters,gl);
	}
	@Override
	public void display(GLAutoDrawable glAutoDrawable) {

		GL gl = glAutoDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
		{
			DrawBackground(gl);
		}//^back-ground
		blinkingDot(gl,dotX,dotY);
//		DrawSprite(gl, dotX+indexCursor*marginBetweenLetters, dotY, texturesLetters, 0, 0, 1f,1.588f);
		DrawSprite(gl,xLetter,yLetter,texturesLetters,numberOfLetter,0, 1f,1.588f);
//		handleKeyPress(gl);
		writeLetters(gl,dotX ,dotY,2);
	}

	@Override
	public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
	}
	@Override
	public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

	}
	public void fillLetters(){
		for (int i = 0; i < 26; i++) {
			char letter = (char) ('a' + i);
			textureLettersNames[i] = letter + ".png";
		}
	}
	public void blinkingDot(GL gl,double x , double y){
		if (blinking >=10 && blinking < 20) {
			DrawSprite(gl,x,y,textures,0,0,1f,1.588f);
			blinking++;
		} else if (blinking == 20) {
			blinking = 0;
		}else {
			blinking++;
		}
	}
	public void writeLetters(GL gl, double x, double y,int numberIndex) {
//		Iterator<QuizEventListener.Letter> iterator = letters.iterator();
//		while (iterator.hasNext()) {
//			QuizEventListener.Letter letter = iterator.next();
//			letter.x += 20;
//			if(letter.y == screenWidth){
//				letter.y += 30;
//			}
		DrawSprite(gl, dotX, dotY, texturesLetters, numberIndex, 0, 1f,1.588f);

//		indexCursor++;
//		}
	}
	public BitSet keyBits = new BitSet(256);
	public boolean isKeyPressed(int keyCode) {
		return keyBits.get(keyCode);
	}
	public void handleKeyPress(GL gl){
		if (isKeyPressed(KeyEvent.VK_A)) {
			dotX = dotX+indexCursor*marginBetweenLetters;
			indexCursor++;
			writeLetters(gl,dotX ,dotY,2);
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keyBits.set(keyCode);
		keyBits.set(e.getKeyChar());
		if ((e.getKeyChar() - 'a' == 0 )) {
//			letters.add(new Letter(dotX+20,dotY+30,e.getKeyChar() - 'a'));
//			writeLetters(gl,dotX ,dotY,2);
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keyBits.clear();
	}
	public void generateTextures(String[] textureNames, TextureReader.Texture[] texture, int[] textures, GL gl) {
		gl.glGenTextures(textureNames.length,textures,0);
		for (int i = 0; i < textureNames.length; i++) {
			try {
				texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i] , true);
				gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);
				new GLU().gluBuild2DMipmaps(
								GL.GL_TEXTURE_2D,
								GL.GL_RGBA, // Internal Texel Format,
								texture[i].getWidth(), texture[i].getHeight(),
								GL.GL_RGBA, // External format from image,
								GL.GL_UNSIGNED_BYTE,
								texture[i].getPixels() // Imagedata
				);
			} catch( IOException e ) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}
	public void DrawSprite(GL gl,double x, double y,int[] textures, int index,float rotate, float scaleX, float scaleY){
		gl.glEnable(GL.GL_BLEND);
		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

		gl.glPushMatrix();
		gl.glTranslated( x/xMax, y/yMax, 0);
		gl.glScaled((0.05*scaleX), (0.05*scaleY), 1);
		gl.glRotated(rotate,0,0,1);
		drawFullScreenQuad(gl);
		gl.glPopMatrix();

		gl.glDisable(GL.GL_BLEND);
	}
	public void DrawBackground(GL gl){
		gl.glEnable(GL.GL_BLEND);	// Turn Blending On
		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textures.length-1]);
		drawFullScreenQuad(gl);
		gl.glDisable(GL.GL_BLEND);  // Disable blending after drawing
	}
	public void drawFullScreenQuad(GL gl){
		gl.glBegin(GL.GL_QUADS);
		// Set each corner to align with the orthographic view boundaries
		// Map texture coordinates from 0 to 1 for the entire image
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom-left corner of the screen

		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom-right corner

		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top-right corner

		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top-left corner
		gl.glEnd();
	}
	class Letter{
		double x,y;
		int letterNumber;
		public Letter(double x, double y, int letterNumber) {
			this.x = x;
			this.y = y;
			this.letterNumber = letterNumber;
		}
		public void drawLetter(GL gl) {
//				writeLetters(gl,dotX+20,dotY+20);
		}
	}
}
