package CS304.lab5.NinjaSkyTypo;

import CS304.Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.BitSet;

public class NinjaSkyTypoEventListener implements GLEventListener, KeyListener {
    float screenHeight = 200;
    float screenWidth = 200;
    float xMax = screenWidth / 2f;
    float xMin = -(screenWidth / 2f);
    float yMax = screenHeight / 2f;
    float yMin = -(screenHeight / 2f);
    /////////////////////////////////////////////////      for screen
    int xSoldier = 0, ySoldier = (int)yMin+10;
    int animationIndex = 0, animationSpeed = 5,animationCounter = 0;
    //////////////////////////////////////////////////        ninja
    int xLetter = 0 , yLetter = (int)yMax;
    int letterIndex = 0;
    int movingLetterSpeed = 3;
    /////////////////////////////////////////////////         letter
    double xStar = 0, yStar = 0;
    float starRotate = 0;
    int movingStarSpeed = 3;
    ///////////////////////////////////////////////////         star
    int healthBarIndex = 0,maxHealth = 100,currHealth = maxHealth;
    float healthBarWidth = 100; // Set the width of the health bar
    float healthBarHeight = 10; // Set the height of the health bar
    float healthBarX = -60; // Position the health bar near the top-left
    float redHealthBarX = -60;
    float healthBarY = 85; // Position vertically near the top
    ////////////////////////////////////////////////////       health bar
    String assetsFolderName = "CS304//Assets//Alphabet";
    String[] textureNames = {"Man1.png","Man2.png","Man3.png","Man4.png","ninjaStar.png","HealthB.png","HealthA.png","Back.png"};
    String[] textureLettersNames = new String[26];
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    TextureReader.Texture[] textureLetter = new TextureReader.Texture[textureLettersNames.length];
    int[] textures = new int[textureNames.length];
    int[] texturesLetters = new int[textureLettersNames.length];
    /////////////////////////////////////////////////////////////////////textures
    private double starVelocityX = 0;
    private double starVelocityY = 0;
    private final double STAR_SPEED = 3.0; // Adjust this value to change star speed
    private final double LETTER_FALL_SPEED = 1.0; // This should match your movingLetterSpeed
    private enum StarState { MOVING_HORIZONTAL, MOVING_VERTICAL, IDLE }
    private StarState starState = StarState.IDLE;
    private double targetX;
    private final double HORIZONTAL_SPEED = 3.0; // Adjust this for horizontal movement speed
    private final double VERTICAL_SPEED = 3.0;   // Adjust this for vertical movement speed
    private final double CURVE_FACTOR = 200;     // Adjust this to change the curve intensity
    private double initialX; // To track starting position for curve calculation
    private final double CURVE_AMPLITUDE = 30.0; // Maximum curve deviation
    private final double CURVE_FREQUENCY = 0.05; // How fast the curve oscillates


    private void calculateStarTrajectory() {
        // Set initial state and target
        starState = StarState.MOVING_HORIZONTAL;
        targetX = xLetter;

        // Initialize position
        xStar = xSoldier;
        yStar = ySoldier;

        // Initial horizontal velocity
        if (xStar < targetX) {
            starVelocityX = HORIZONTAL_SPEED;
        } else {
            starVelocityX = -HORIZONTAL_SPEED;
        }
        starVelocityY = 0; // Start with no vertical movement
    }

    private void updateStarMovement() {
        if (!isStarFlying) return;

        switch (starState) {
            case MOVING_HORIZONTAL:
                // Move horizontally until we're close to the target X
                if (Math.abs(xStar - targetX) <= HORIZONTAL_SPEED) {
                    // We've reached the target X, switch to vertical movement
                    xStar = targetX; // Snap to exact position
                    starState = StarState.MOVING_VERTICAL;
                    starVelocityX = 0;
                    starVelocityY = VERTICAL_SPEED;
                }
                break;

            case MOVING_VERTICAL:
                // Add slight curve based on distance from target
                double distanceFromTarget = Math.abs(xStar - targetX);
                if (distanceFromTarget > 0) {
                    // Add slight horizontal movement for curve effect
                    starVelocityX = (targetX - xStar) * CURVE_FACTOR;
                }
                break;

            case IDLE:
                // Do nothing
                break;
        }

        // Update position
        xStar += starVelocityX;
        yStar += starVelocityY;

        // Check if star is out of bounds
        if (yStar > yMax - 10 || yStar < yMin + 10 ||
                xStar > xMax - 5 || xStar < xMin + 5) {
            isStarFlying = false;
            starState = StarState.IDLE;
        }
    }
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
        yLetter -= movingLetterSpeed; // here the letter going down
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        {
            DrawBackground(gl);
            handleKeyPress();
            DrawSprite(gl, xSoldier, ySoldier, textures, animationIndex, 0, 2,2); // draw the soldier
        }//^Soldier & back-ground
        {
            if (isStarFlying) {
                // Update star position using velocity
                updateStarMovement();
                DrawSprite(gl, (int)xStar, (int)yStar, textures, 4, starRotate += 30, 5, 5);

            }
        }//^Ninja Star
        {
            DrawSprite(gl, xLetter, yLetter, texturesLetters, letterIndex, 0, 1,1); // draw letters
            double dist = sqrdDistance((int)xStar,(int)yStar,xLetter,yLetter);
            double radii = Math.pow(0.5*0.1*yMax+0.5*0.1*yMax,2);
            boolean isCollided = dist<=radii;
            if (isCollided ) {
                // Collision happened here bro
                // Reset letter position and choose a new random letter with new random x
                xLetter = (int) (Math.random() * (screenWidth - 10) + 1) - (int) xMax + 5;
                yLetter = (int) yMax; // Reset to top of the screen
                letterIndex = (int) (Math.random() * texturesLetters.length); // Pick a new random letter
                isStarFlying = false; // Reset the star's state
            } else if (yLetter < yMin + 10) {
                // Letter missed by the ninja star, decrease health
                currHealth = Math.max(0, currHealth - 10); // Decrease health by 10, but not below 0
                if (currHealth == 0) {
                    System.out.println("GameOver");
                    JOptionPane.showMessageDialog((Component)null, "GameOver.", "GameOver", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
                // here we Reset letter position and choose a new random letter
                xLetter = (int) (Math.random() * (screenWidth - 10) + 1) - (int) xMax + 5;
                yLetter = (int) yMax; // Reset to top of the screen
                letterIndex = (int) (Math.random() * texturesLetters.length); // Pick a new random letter
                redHealthBarX -= 3;
            }
        }//^Letters
        {
            float healthPercentage = currHealth / (float) maxHealth;
            DrawSprite(gl,(int)healthBarX,(int)healthBarY,textures,5,0,6,0.6f);// draw the white health bar
            DrawSprite(gl,(int)redHealthBarX,(int)healthBarY,textures,6,0,6*healthPercentage,0.6f);// draw the red health bar
        }//^Health bar
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
    public void fillLetters(){
        for (int i = 0; i < 26; i++) {
            char letter = (char) ('a' + i);
            textureLettersNames[i] = letter + ".png";
        }
    }
    public double sqrdDistance(int x, int y, int x1, int y1){
        return Math.pow(x-x1,2)+Math.pow(y-y1,2);
    }
    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

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
    public void DrawSprite(GL gl,int x, int y,int[] textures, int index,float rotate, float scaleX, float scaleY){
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
    public void handleKeyPress() {
        boolean flag = false;
        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (xSoldier > xMin+5) {
                xSoldier--;
            }
            flag = true;
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (xSoldier < xMax-5) {
                xSoldier++;
            }
            flag = true;
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (ySoldier > yMin+10) {
                ySoldier--;
            }
            flag = true;
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (ySoldier < yMax-10) {
                ySoldier++;
            }
            flag = true;
        }
        if (flag) { // only increment if movement is detected
            animationCounter++;
            if (animationCounter >= animationSpeed) {
                animationIndex++;
                animationIndex %= 4;
                animationCounter = 0;
            }
        }
    }

    boolean isStarFlying = false;
    public BitSet keyBits = new BitSet(256);
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.set(keyCode);
        keyBits.set(e.getKeyChar());
        if ((e.getKeyChar() - 'a' == letterIndex || e.getKeyChar() - 'A' == letterIndex ) && !isStarFlying) {
            isStarFlying = true;
            // Calculate trajectory and set initial position
            calculateStarTrajectory();
            xStar = xSoldier;
            yStar = ySoldier + 10;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.clear(keyCode);
    }

    public boolean isKeyPressed(int keyCode) {
        return keyBits.get(keyCode);
    }
}
