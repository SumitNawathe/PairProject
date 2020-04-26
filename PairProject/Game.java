import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;

public class Game extends JPanel {
	JFrame frame;
	ArrayList<Mesh> meshList;
	final int SCREEN_WIDTH = 1200, SCREEN_HEIGHT = 900;
	double FOV_ANGLE = Math.PI/2;
	double Z_NEAR = 0.1, Z_FAR = 1000.0;
	double[][] projMatrix, matRotX, matRotZ;
	Vector translationVector = new Vector(0, 0, 3);
	JPanel panel = this;
	java.util.Timer timer;
	double theta = 0;
	
	public Game () {
		meshList = new ArrayList<Mesh>();
		meshList.add(new MeshCube());
		
		double a = (double) SCREEN_HEIGHT/SCREEN_WIDTH;
		//System.out.println("a: " + a);
		double f = 1/Math.tan(FOV_ANGLE/2);
		//System.out.println("f: " + f);
		double q = Z_FAR/(Z_FAR-Z_NEAR);
		projMatrix = new double[4][4];
		projMatrix[0][0] = a*f;
		projMatrix[1][1] = f;
		projMatrix[2][2] = q;
		projMatrix[2][3] = 1;
		projMatrix[3][2] = -1*Z_NEAR*q;
		
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setFocusable(true);
		this.setBackground(Color.black);
		this.setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
		
		frame.addKeyListener(new KeyListener () {
			public void keyPressed (KeyEvent event) {}
			public void keyReleased (KeyEvent event) {
				System.out.println("event");
				if (event.getKeyCode() == KeyEvent.VK_D)
					translationVector = translationVector.plus(new Vector(1, 0, 0));
				else if (event.getKeyCode() == KeyEvent.VK_A)
					translationVector = translationVector.plus(new Vector(-1, 0, 0));
				else if (event.getKeyCode() == KeyEvent.VK_I)
					translationVector = translationVector.plus(new Vector(0, 0, -1));
				else if (event.getKeyCode() == KeyEvent.VK_O)
					translationVector = translationVector.plus(new Vector(0, 0, 1));
				panel.repaint();
			}
			public void keyTyped (KeyEvent event) {}
		});
		timer = new java.util.Timer();
		timer.scheduleAtFixedRate(new TimerTask () {
			public void run () {
				theta += Math.PI/(18*6);
				
				matRotZ = new double[4][4];
				matRotZ[0][0] = Math.cos(theta);
				matRotZ[0][1] = Math.sin(theta);
				matRotZ[1][0] = -1*Math.sin(theta);
				matRotZ[1][1] = Math.cos(theta);
				matRotZ[2][2] = 1;
				matRotZ[3][3] = 1;
				
				matRotX = new double[4][4];
				matRotX[0][0] = 1;
				matRotX[1][1] = Math.cos(theta/2);
				matRotX[1][2] = Math.sin(theta/2);
				matRotX[2][1] = -1*Math.sin(theta/2);
				matRotX[2][2] = Math.cos(theta/2);
				matRotX[3][3] = 1;
				
				panel.repaint();
			}
		}, 0, 20);
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		System.out.println("painting");
//		g.setColor(Color.red);
//		g.fillRect(10, 10, 100, 50);
		g.setColor(Color.white);
		
		for (Mesh mesh : meshList) {
			//int counter = 0;
			for (Triangle tri : mesh.getTris()) {
				Triangle triangle = tri.clone();
				triangle = matrixMult(triangle, matRotZ);
				triangle = matrixMult(triangle, matRotX);
				Triangle translatedTri = triangle.translate(translationVector);
				int[] xCoords = new int[3], yCoords = new int[3];
				for (int i = 0; i < 3; i++) {
					Vector vec = matrixMult(translatedTri.getVerts()[i], projMatrix);
					System.out.println("projectedXCoord: " + vec.getX());
					xCoords[i] = (int) ((vec.getX() + 1) * 0.5 * SCREEN_WIDTH);
					yCoords[i] = (int) ((vec.getY() + 1) * 0.5 * SCREEN_HEIGHT);
				}
				g.drawPolygon(xCoords, yCoords, 3);
				//System.out.println(counter + ": " + translatedTri.toString());
				//counter++;
			}
		}
	}
	
	public Triangle matrixMult(Triangle tri, double[][] matrix) {
		return new Triangle(matrixMult(tri.getVert1(), matrix), matrixMult(tri.getVert2(), matrix), matrixMult(tri.getVert3(), matrix));
	}
	
	public Vector matrixMult (Vector vec, double[][] matrix) {
		System.out.println(vec.getX());
		Vector returnVec = new Vector(
				vec.getX()*matrix[0][0] + vec.getY()*matrix[1][0] + vec.getZ()*matrix[2][0] + matrix[3][0],
				vec.getX()*matrix[0][1] + vec.getY()*matrix[1][1] + vec.getZ()*matrix[2][1] + matrix[3][1],
				vec.getX()*matrix[0][2] + vec.getY()*matrix[1][2] + vec.getZ()*matrix[2][2] + matrix[3][2]);
		System.out.println(returnVec.getX());
		double w = vec.getX()*matrix[0][3] + vec.getY()*matrix[1][3] + vec.getZ()*matrix[2][3] + matrix[3][3];
		if (w != 0)
			returnVec.scale(1/w);
		return returnVec;
	}
	
//	public Vector projection (Vector vec) {
//		double a = SCREEN_HEIGHT/SCREEN_WIDTH, f = 1/Math.tan(FOV_ANGLE/2), q = Z_FAR/(Z_FAR-Z_NEAR);
//		if (vec.getZ() != 0)
//			return new Vector(a*f*vec.getX()/vec.getZ(), f*vec.getY()/vec.getZ(), q*(vec.getZ()-Z_NEAR)/vec.getZ());
//		else
//			return new Vector(a*f*vec.getX(), f*vec.getY(), q*(vec.getZ()-Z_NEAR));
//	}
	
	public static void main (String[] args) {
		Game game = new Game();
	}
}
