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
	double[][] projMatrix = new double[4][4], worldMatrix = new double[4][4], viewMatrix = new double[4][4];
	Vector translationVector = new Vector(0, 0, 8);
	JPanel panel = this;
	java.util.Timer timer;
	double theta = 0;
	double yAngle = 0, xAngle = 0;
	Vector cameraPos = new Vector(0, 0, 0), cameraForward = new Vector(0, 0, 1), cameraRight = new Vector(1, 0, 0);
	Vector light_direction = new Vector(0, 0, -1);
	
	public Game () {
		meshList = new ArrayList<Mesh>();
		try {
			System.out.println("a");
			meshList.add(Mesh.loadFromObjFile("Models/VideoShip.obj"));
			System.out.println("b");
		} catch (Exception e) {
			System.out.println(e);
		}
		
		projMatrix = Matrix.getProjMatrix((double) SCREEN_HEIGHT/SCREEN_WIDTH, 1/Math.tan(FOV_ANGLE/2), Z_NEAR, Z_FAR);
		
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
			public void keyPressed (KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_SPACE)
					cameraPos = cameraPos.plus(new Vector(0, 1, 0));
				else if (event.getKeyCode() == KeyEvent.VK_SHIFT)
					cameraPos = cameraPos.plus(new Vector(0, -1, 0));
				
				if (event.getKeyCode() == KeyEvent.VK_RIGHT)
					yAngle -= Math.PI/18;
				else if (event.getKeyCode() == KeyEvent.VK_LEFT)
					yAngle += Math.PI/18;
				else if (event.getKeyCode() == KeyEvent.VK_UP)
					xAngle -= Math.PI/18;
				else if (event.getKeyCode() == KeyEvent.VK_DOWN)
					xAngle += Math.PI/18;
				
				if (event.getKeyCode() == KeyEvent.VK_W)
					cameraPos = cameraPos.plus(cameraForward);
				else if (event.getKeyCode() == KeyEvent.VK_S)
					cameraPos = cameraPos.minus(cameraForward);
				else if (event.getKeyCode() == KeyEvent.VK_D)
					cameraPos = cameraPos.minus(cameraRight.scale(-1));
				else if (event.getKeyCode() == KeyEvent.VK_A)
					cameraPos = cameraPos.minus(cameraRight);
				
				panel.getIgnoreRepaint();
			}
			public void keyReleased (KeyEvent event) {}
			public void keyTyped (KeyEvent event) {}
		});
		
		timer = new java.util.Timer();
		timer.scheduleAtFixedRate(new TimerTask () {
			public void run () {
				//theta += Math.PI/(18*18);
				double[][] matRotZ = Matrix.getRotMatZ(theta);
				double[][] matRotX = Matrix.getRotMatX(theta/2);
				double[][] transMatrix = Matrix.getTranslationMatrix(translationVector);
				worldMatrix = Matrix.mulMatMat(matRotZ, matRotX);
				worldMatrix = Matrix.mulMatMat(worldMatrix, transMatrix);
				
				viewMatrix = Matrix.getTranslationMatrix(cameraPos.clone().scale(-1));
				cameraForward = Matrix.multMatVec(Matrix.getRotMatX(xAngle), new Vector(0, 0, 1));
				cameraForward = Matrix.multMatVec(Matrix.getRotMatY(yAngle), cameraForward);
				cameraRight = Matrix.multMatVec(Matrix.getRotMatX(xAngle), new Vector(1, 0, 0));
				cameraRight = Matrix.multMatVec(Matrix.getRotMatY(yAngle), cameraRight);
				viewMatrix = Matrix.mulMatMat(viewMatrix, Matrix.getRotMatrix(cameraForward, cameraRight));
				viewMatrix = Matrix.mulMatMat(viewMatrix, Matrix.getTranslationMatrix(cameraPos.clone()));
				
				light_direction = cameraForward.clone().scale(-1);
				
				panel.repaint();
			}
		}, 100, 20);
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		ArrayList<Triangle> drawnTriangles = new ArrayList<Triangle>();		
		
		for (Mesh mesh : meshList) {
			//System.out.println(mesh.getTris().size());
			for (Triangle tri : mesh.getTris()) {
				Triangle transformedTri = new Triangle(Matrix.multMatVec(worldMatrix, tri.getVert1()), 
						Matrix.multMatVec(worldMatrix, tri.getVert2()), Matrix.multMatVec(worldMatrix, tri.getVert3()));
				transformedTri.getVert1().trim();
				transformedTri.getVert2().trim();
				transformedTri.getVert3().trim();
				
				Vector line1 = transformedTri.getVert2().clone().minus(transformedTri.getVert1());
				Vector line2 = transformedTri.getVert3().clone().minus(transformedTri.getVert1());
				Vector normal = line1.cross(line2).unit();
				
				if (normal.dot(transformedTri.getVert1().clone().minus(cameraPos)) < 0) {
					double shadingValue = normal.dot(light_direction.unit());
					shadingValue += 0.1;
					if (shadingValue < 0)
						shadingValue = 0;
					if (shadingValue > 1)
						shadingValue = 1;
					//shadingValue = 1; //TODO remove
					Color color = new Color((int) (255*shadingValue), (int) (255*shadingValue), (int) (255*shadingValue));
					
					//for (Triangle clippedTri : clipAgainstPlane(cameraPos.clone().plus(cameraForward.clone().unit().scale(10*Z_NEAR)), cameraForward.clone().unit(), transformedTri)) {
						Triangle triViewed = new Triangle(Matrix.multMatVec(viewMatrix, transformedTri.getVert1()), 
								Matrix.multMatVec(viewMatrix, transformedTri.getVert2()), Matrix.multMatVec(viewMatrix, transformedTri.getVert3()));
					for (Triangle clippedTri : clipAgainstPlane(new Vector(0, 0, 1), new Vector(0, 0, 0.00000000001), triViewed)) {
						Triangle projectedTri = new Triangle(Matrix.multMatVec(projMatrix, clippedTri.getVert1()), 
								Matrix.multMatVec(projMatrix, clippedTri.getVert2()), Matrix.multMatVec(projMatrix, clippedTri.getVert3()));
						double depth = (clippedTri.getVert1().getZ() + clippedTri.getVert2().getZ() + clippedTri.getVert3().getZ())/3.0;
						projectedTri = new Triangle(projectedTri.getVert1().scale(1/projectedTri.getVert1().getW()), 
								projectedTri.getVert2().scale(1/projectedTri.getVert2().getW()), projectedTri.getVert3().scale(1/projectedTri.getVert3().getW()), color, depth);
						
//						int[] xCoords = new int[] {
//								(int) ((projectedTri.getVert1().getX()+1) * 0.5 * SCREEN_WIDTH),
//								(int) ((projectedTri.getVert2().getX()+1) * 0.5 * SCREEN_WIDTH),
//								(int) ((projectedTri.getVert3().getX()+1) * 0.5 * SCREEN_WIDTH)
//						};
//						int[] yCoords = new int[] {
//								(int) ((projectedTri.getVert1().getY()+1) * 0.5 * SCREEN_HEIGHT),
//								(int) ((projectedTri.getVert2().getY()+1) * 0.5 * SCREEN_HEIGHT),
//								(int) ((projectedTri.getVert3().getY()+1) * 0.5 * SCREEN_HEIGHT)
//						};
//						double depth = (clippedTri.getVert1().getZ() + clippedTri.getVert2().getZ() + clippedTri.getVert3().getZ())/3.0;
//						
//						drawnTriangles.add(new DrawnTriangle(xCoords, yCoords, color, depth));
						drawnTriangles.add(projectedTri);
					}
				}
			}
		}
		
		Collections.sort(drawnTriangles, new Comparator<Triangle>() {
		    public int compare(Triangle tri1, Triangle tri2) {
		    	if (tri1.getDepth() > tri2.getDepth())
		    		return -1;
		    	else if (tri1.getDepth() < tri2.getDepth())
		    		return 1;
		    	return 0;
		    }
		});
		
		for (Triangle triToDraw : drawnTriangles) {
			ArrayList<Triangle> listTriangle = new ArrayList<Triangle>();
			listTriangle.add(triToDraw);
			int newTris = 1;
			for (int p = 0; p < 4; p++) {
				while (newTris > 0) {
					Triangle triToClip = listTriangle.remove(0);
					Triangle[] trisToAdd = null;
					switch (p) {
						case 0:	trisToAdd = clipAgainstPlane(new Vector(0.0, -1.0, 0.0), new Vector(0.0, 1.0, 0.0), triToClip); break;
						case 1:	trisToAdd = clipAgainstPlane(new Vector(0.0, 1, 0.0), new Vector(0.0f, -1.0f, 0.0f), triToClip); break;
						case 2:	trisToAdd = clipAgainstPlane(new Vector(-1.0, 0.0, 0.0), new Vector(1.0, 0.0, 0.0), triToClip); break;
						case 3:	trisToAdd = clipAgainstPlane(new Vector(1.0, 0.0, 0.0), new Vector(-1.0, 0.0, 0.0), triToClip); break;
					}
					for (Triangle tri : trisToAdd)
						listTriangle.add(tri);
					newTris--;
				}
				newTris = listTriangle.size();
			}
			
			for (Triangle tri : listTriangle) {
				int[] xCoords = new int[] {
						(int) ((tri.getVert1().getX()+1) * 0.5 * SCREEN_WIDTH),
						(int) ((tri.getVert2().getX()+1) * 0.5 * SCREEN_WIDTH),
						(int) ((tri.getVert3().getX()+1) * 0.5 * SCREEN_WIDTH)
				};
				int[] yCoords = new int[] {
						(int) ((tri.getVert1().getY()+1) * 0.5 * SCREEN_HEIGHT),
						(int) ((tri.getVert2().getY()+1) * 0.5 * SCREEN_HEIGHT),
						(int) ((tri.getVert3().getY()+1) * 0.5 * SCREEN_HEIGHT)
				};
				g.setColor(tri.getColor());
				g.fillPolygon(xCoords, yCoords, 3);
				g.setColor(Color.black);
				g.drawPolygon(xCoords, yCoords, 3);
			}
		}
		
//		for (Triangle triToDraw : drawnTriangles) {
//			ArrayList<Triangle> listTriangles = new ArrayList<Triangle>();
//			
//			// Add initial triangle
//			listTriangles.add(triToDraw);
//			int nNewTriangles = 1;
//
//			for (int p = 0; p < 4; p++)
//			{
//				int nTrisToAdd = 0;
//				while (nNewTriangles > 0)
//				{
//					// Take triangle from front of queue
//					triangle test = listTriangles.front();
//					listTriangles.pop_front();
//					nNewTriangles--;
//
//					// Clip it against a plane. We only need to test each 
//					// subsequent plane, against subsequent new triangles
//					// as all triangles after a plane clip are guaranteed
//					// to lie on the inside of the plane. I like how this
//					// comment is almost completely and utterly justified
//					switch (p)
//					{
//					case 0:	nTrisToAdd = Triangle_ClipAgainstPlane({ 0.0f, 0.0f, 0.0f }, { 0.0f, 1.0f, 0.0f }, test, clipped[0], clipped[1]); break;
//					case 1:	nTrisToAdd = Triangle_ClipAgainstPlane({ 0.0f, (float)ScreenHeight() - 1, 0.0f }, { 0.0f, -1.0f, 0.0f }, test, clipped[0], clipped[1]); break;
//					case 2:	nTrisToAdd = Triangle_ClipAgainstPlane({ 0.0f, 0.0f, 0.0f }, { 1.0f, 0.0f, 0.0f }, test, clipped[0], clipped[1]); break;
//					case 3:	nTrisToAdd = Triangle_ClipAgainstPlane({ (float)ScreenWidth() - 1, 0.0f, 0.0f }, { -1.0f, 0.0f, 0.0f }, test, clipped[0], clipped[1]); break;
//					}
//
//					// Clipping may yield a variable number of triangles, so
//					// add these new ones to the back of the queue for subsequent
//					// clipping against next planes
//					for (int w = 0; w < nTrisToAdd; w++)
//						listTriangles.push_back(clipped[w]);
//				}
//				nNewTriangles = listTriangles.size();
//			}
//			
//			int[] xCoords = new int[] {
//					(int) ((tri.getVert1().getX()+1) * 0.5 * SCREEN_WIDTH),
//					(int) ((tri.getVert2().getX()+1) * 0.5 * SCREEN_WIDTH),
//					(int) ((tri.getVert3().getX()+1) * 0.5 * SCREEN_WIDTH)
//			};
//			int[] yCoords = new int[] {
//					(int) ((tri.getVert1().getY()+1) * 0.5 * SCREEN_HEIGHT),
//					(int) ((tri.getVert2().getY()+1) * 0.5 * SCREEN_HEIGHT),
//					(int) ((tri.getVert3().getY()+1) * 0.5 * SCREEN_HEIGHT)
//			};
//			g.setColor(tri.getColor());
//			g.fillPolygon(xCoords, yCoords, 3);
////			g.setColor(Color.black);
////			g.drawPolygon(xCoords, yCoords, 3);
//		}
	}
	
//	private class DrawnTriangle {
//		public int[] xCoords, yCoords;
//		public Color color;
//		public double depth;
//		public DrawnTriangle (int[] xCoords, int[] yCoords, Color color, double depth) {
//			this.xCoords = xCoords;
//			this.yCoords = yCoords;
//			this.color = color;
//			this.depth = depth;
//		};
//	}
	
	private Triangle[] clipAgainstPlane(Vector planePos, Vector planeNorm, Triangle tri) {
		planeNorm = planeNorm.unit();
		ArrayList<Vector> insidePoints = new ArrayList<Vector>(), outsidePoints = new ArrayList<Vector>();

		for (Vector point : tri.getVerts())
			if (planeNorm.dot(point.clone().minus(planePos)) >= 0)
				insidePoints.add(point);
			else
				outsidePoints.add(point);

		if (insidePoints.size() == 0) {
			//System.out.println("none");
			return new Triangle[] {};
		} else if (insidePoints.size() == 3)
			return new Triangle[] {tri};
		else if (insidePoints.size() == 1) {
			//System.out.println("1");
			return new Triangle[] {
					new Triangle(insidePoints.get(0), 
						Vector.VecPlaneIntersect(planePos, planeNorm, insidePoints.get(0), outsidePoints.get(0)),
						Vector.VecPlaneIntersect(planePos, planeNorm, insidePoints.get(0), outsidePoints.get(1)), tri.getColor())	
			};
		} else if (insidePoints.size() == 2)
			return new Triangle[] {
					new Triangle(insidePoints.get(0),
							insidePoints.get(1),
							Vector.VecPlaneIntersect(planePos, planeNorm, insidePoints.get(1), outsidePoints.get(0)), tri.getColor()),
					new Triangle(insidePoints.get(0),
							Vector.VecPlaneIntersect(planePos, planeNorm, insidePoints.get(1), outsidePoints.get(0)),
							Vector.VecPlaneIntersect(planePos, planeNorm, insidePoints.get(0), outsidePoints.get(0)), tri.getColor())
			};
		return new Triangle[] {};
	}
	
	public static void main (String[] args) {
		Game game = new Game();
	}
}