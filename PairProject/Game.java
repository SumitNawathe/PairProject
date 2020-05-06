import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

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
	BufferedImage texture;
	double[][] depthArray = new double[SCREEN_HEIGHT][SCREEN_WIDTH];
	
	public Game () {
		meshList = new ArrayList<Mesh>();
		try {
			System.out.println("a");
			meshList.add(Mesh.loadFromObjFile("Models/artisans.obj"));
			//meshList.add(new MeshCube());
			System.out.println("b");
		} catch (Exception e) {
			System.out.println(e);
		}
		
		try {
			texture = ImageIO.read(new File("Textures/High.png"));
		} catch (Exception e) { System.out.println("Texture reading failed."); }
		
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
					yAngle -= Math.PI/(18*3);
				else if (event.getKeyCode() == KeyEvent.VK_LEFT)
					yAngle += Math.PI/(18*3);
				else if (event.getKeyCode() == KeyEvent.VK_UP)
					xAngle -= Math.PI/(18*3);
				else if (event.getKeyCode() == KeyEvent.VK_DOWN)
					xAngle += Math.PI/(18*3);
				
				if (event.getKeyCode() == KeyEvent.VK_W)
					cameraPos = cameraPos.plus(cameraForward.scale(0.5));
				else if (event.getKeyCode() == KeyEvent.VK_S)
					cameraPos = cameraPos.minus(cameraForward.scale(0.5));
				else if (event.getKeyCode() == KeyEvent.VK_D)
					cameraPos = cameraPos.minus(cameraRight.scale(-0.5));
				else if (event.getKeyCode() == KeyEvent.VK_A)
					cameraPos = cameraPos.minus(cameraRight.scale(0.5));
				
				panel.getIgnoreRepaint();
			}
			public void keyReleased (KeyEvent event) {}
			public void keyTyped (KeyEvent event) {}
		});
		
		timer = new java.util.Timer();
		timer.scheduleAtFixedRate(new TimerTask () {
			public void run () {
				depthArray = new double[SCREEN_HEIGHT][SCREEN_WIDTH];
				
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
				
				light_direction = cameraForward.scale(-1);
				
				panel.repaint();
			}
		}, 100, 20);
	}
	
	public void paintComponent (Graphics panelG) {
		super.paintComponent(panelG);
		BufferedImage bufferedImage = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bufferedImage.getGraphics();
		g.setColor(Color.white);
		ArrayList<Triangle> drawnTriangles = new ArrayList<Triangle>();		
		
		for (Mesh mesh : meshList) {
			//System.out.println(mesh.getTris().size());
			for (Triangle tri : mesh.getTris()) {
				Triangle transformedTri = new Triangle(Matrix.multMatVec(worldMatrix, tri.getVert1()), 
						Matrix.multMatVec(worldMatrix, tri.getVert2()), Matrix.multMatVec(worldMatrix, tri.getVert3()),
						tri.getTex());
				transformedTri.getVert1().trim();
				transformedTri.getVert2().trim();
				transformedTri.getVert3().trim();
				
				Vector line1 = transformedTri.getVert2().minus(transformedTri.getVert1());
				Vector line2 = transformedTri.getVert3().minus(transformedTri.getVert1());
				Vector normal = line1.cross(line2).unit();
				
				if (normal.dot(transformedTri.getVert1().minus(cameraPos)) < 0) {
					double shadingValue = normal.dot(light_direction.unit());
//					shadingValue += 0.4;
					if (shadingValue < 0)
						shadingValue = 0;
					if (shadingValue > 1)
						shadingValue = 1;
					//shadingValue = 1; //TODO remove
					shadingValue /= 4;
					shadingValue += 0.75;
					Color color = new Color((int) (255*shadingValue), (int) (255*shadingValue), (int) (255*shadingValue));
					
					//for (Triangle clippedTri : clipAgainstPlane(cameraPos.clone().plus(cameraForward.clone().unit().scale(10*Z_NEAR)), cameraForward.clone().unit(), transformedTri)) {
						Triangle triViewed = new Triangle(Matrix.multMatVec(viewMatrix, transformedTri.getVert1()), 
								Matrix.multMatVec(viewMatrix, transformedTri.getVert2()), Matrix.multMatVec(viewMatrix, transformedTri.getVert3()),
								transformedTri.getTex());
					for (Triangle clippedTri : clipAgainstPlane(new Vector(0, 0, Z_NEAR), new Vector(0, 0, 1), triViewed)) {
						Triangle projectedTri = new Triangle(Matrix.multMatVec(projMatrix, clippedTri.getVert1()), 
								Matrix.multMatVec(projMatrix, clippedTri.getVert2()), Matrix.multMatVec(projMatrix, clippedTri.getVert3()), clippedTri.getTex());
						double depth = (clippedTri.getVert1().getZ() + clippedTri.getVert2().getZ() + clippedTri.getVert3().getZ())/3.0;
//						System.out.println("proj w: " + projectedTri.getVert1().getW() + " " + projectedTri.getVert2().getW() + " " + projectedTri.getVert3().getW());
						projectedTri.setTex1(projectedTri.getTex1().scale(1/projectedTri.getVert1().getW()));
						projectedTri.getTex1().setW(1/projectedTri.getVert1().getW());
						projectedTri.setTex2(projectedTri.getTex2().scale(1/projectedTri.getVert2().getW()));
						projectedTri.getTex2().setW(1/projectedTri.getVert2().getW());
						projectedTri.setTex3(projectedTri.getTex3().scale(1/projectedTri.getVert3().getW()));
						projectedTri.getTex3().setW(1/projectedTri.getVert3().getW());
//						System.out.println("out w: " + projectedTri.getVert3().getW());
						//System.out.println(projectedTri.getTex()[0])
						
						projectedTri = new Triangle(projectedTri.getVert1().scale(1/projectedTri.getVert1().getW()), 
								projectedTri.getVert2().scale(1/projectedTri.getVert2().getW()), projectedTri.getVert3().scale(1/projectedTri.getVert3().getW()), projectedTri.getTex(), depth);
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
						case 1:	trisToAdd = clipAgainstPlane(new Vector(0.0, 1.0, 0.0), new Vector(0.0, -1.0, 0.0), triToClip); break;
						case 2:	trisToAdd = clipAgainstPlane(new Vector(-1.0, 0.0, 0.0), new Vector(1.0, 0.0, 0.0), triToClip); break;
						case 3:	trisToAdd = clipAgainstPlane(new Vector(1.0, 0.0, 0.0), new Vector(-1.0, 0.0, 0.0), triToClip); break;
					}
					for (Triangle tri : trisToAdd) {
						listTriangle.add(tri);
					}
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
//				if (tri.getColor() != null)
//					g.setColor(tri.getColor());
//				else
//					g.setColor(Color.white);
//				g.fillPolygon(xCoords, yCoords, 3);
				
				drawTexturedTriangle(tri, bufferedImage);
				
//				g.setColor(Color.red);
//				g.drawPolygon(xCoords, yCoords, 3);
			}
		}
//		bufferedImage = texture;
		
		panelG.drawImage(bufferedImage, 0, 0, null);
	}
	
	private void drawTexturedTriangle (Triangle tri, BufferedImage image) {
		int x1 = (int) ((tri.getVert1().getX()+1) * 0.5 * SCREEN_WIDTH), 
				x2 = (int) ((tri.getVert2().getX()+1) * 0.5 * SCREEN_WIDTH), 
				x3 = (int) ((tri.getVert3().getX()+1) * 0.5 * SCREEN_WIDTH), 
				y1 = (int) ((tri.getVert1().getY()+1) * 0.5 * SCREEN_HEIGHT), 
				y2 = (int) ((tri.getVert2().getY()+1) * 0.5 * SCREEN_HEIGHT),
				y3 = (int) ((tri.getVert3().getY()+1) * 0.5 * SCREEN_HEIGHT);
		double u1 = tri.getTex1().getX(), 
				v1 = tri.getTex1().getY(),
				w1 = tri.getTex1().getW(),
				u2 = tri.getTex2().getX(),
				v2 = tri.getTex2().getY(),
				w2 = tri.getTex2().getW(),
				u3 = tri.getTex3().getX(),
				v3 = tri.getTex3().getY(),
				w3 = tri.getTex3().getW();
//		System.out.println("w: " + w1 + " " + w2 + " " + w3);
//		System.out.println(tri.getTex1());
//		System.out.println(tri.getTex2());
//		System.out.println(tri.getTex3());
		
		if (y2 < y1) {
			int tempa = y1; y1 = y2; y2 = tempa;
			int tempb = x1; x1 = x2; x2 = tempb;
			double tempc = u1; u1 = u2; u2 = tempc;
			double tempd = v1; v1 = v2; v2 = tempd;
			double tempe = w1; w1 = w2; w2 = tempe;
		}
		if (y3 < y1) {
			int tempa = y1; y1 = y3; y3 = tempa;
			int tempb = x1; x1 = x3; x3 = tempb;
			double tempc = u1; u1 = u3; u3 = tempc;
			double tempd = v1; v1 = v3; v3 = tempd;
			double tempe = w1; w1 = w3; w3 = tempe;
		}
		if (y3 < y2) {
			int tempa = y2; y2 = y3; y3 = tempa;
			int tempb = x2; x2 = x3; x3 = tempb;
			double tempc = u2; u2 = u3; u3 = tempc;
			double tempd = v2; v2 = v3; v3 = tempd;
			double tempe = w2; w2 = w3; w3 = tempe;
		}

		int dy1 = y2 - y1;
		int dx1 = x2 - x1;
		double dv1 = v2 - v1;
		double du1 = u2 - u1;
		double dw1 = w2 - w1;

		int dy2 = y3 - y1;
		int dx2 = x3 - x1;
		double dv2 = v3 - v1;
		double du2 = u3 - u1;
		double dw2 = w3 - w1;

		double tex_u, tex_v, tex_w;

		double dax_step = 0, dbx_step = 0,
			du1_step = 0, dv1_step = 0,
			du2_step = 0, dv2_step = 0,
			dw1_step=0, dw2_step=0;

		if (dy1 != 0) dax_step = dx1 / (double)Math.abs(dy1);
		if (dy2 != 0) dbx_step = dx2 / (double)Math.abs(dy2);

		if (dy1 != 0) du1_step = du1 / (double)Math.abs(dy1);
		if (dy1 != 0) dv1_step = dv1 / (double)Math.abs(dy1);
		if (dy1 != 0) dw1_step = dw1 / (double)Math.abs(dy1);

		if (dy2 != 0) du2_step = du2 / (double)Math.abs(dy2);
		if (dy2 != 0) dv2_step = dv2 / (double)Math.abs(dy2);
		if (dy2 != 0) dw2_step = dw2 / (double)Math.abs(dy2);

		if (dy1 != 0) {
			for (int i = y1; i <= y2; i++) {
				int ax = (int) (x1 + (double)(i - y1) * dax_step);
				int bx = (int) (x1 + (double)(i - y1) * dbx_step);

				double tex_su = u1 + (double)(i - y1) * du1_step;
				double tex_sv = v1 + (double)(i - y1) * dv1_step;
				double tex_sw = w1 + (double)(i - y1) * dw1_step;

				double tex_eu = u1 + (double)(i - y1) * du2_step;
				double tex_ev = v1 + (double)(i - y1) * dv2_step;
				double tex_ew = w1 + (double)(i - y1) * dw2_step;

				if (ax > bx) {
					int temp1 = ax; ax = bx; bx = temp1;
					double temp2 = tex_su; tex_su = tex_eu; tex_eu = temp2;
					double temp3 = tex_sv; tex_sv = tex_ev; tex_ev = temp3;
					double temp4 = tex_sw; tex_sw = tex_ew; tex_ew = temp4;
				}

				tex_u = tex_su;
				tex_v = tex_sv;
				tex_w = tex_sw;

				double tstep = 1.0d / ((double)(bx - ax));
				double t = 0.0d;

				for (int j = ax; j < bx; j++) {
					tex_u = (1.0d - t) * tex_su + t * tex_eu;
					tex_v = (1.0d - t) * tex_sv + t * tex_ev;
					tex_w = (1.0d - t) * tex_sw + t * tex_ew;
					//if (tex_w > pDepthBuffer[i*ScreenWidth() + j])
					//{
						//image.setRGB(j, i, texture.getRGB((int) (tex_u/tex_w), (int) (tex_v/tex_w)));
//					System.out.println("u, v: " + tex_u + " " + tex_v);
//					if (Math.random() < 0.001)
//						System.out.println("tex_w: " + tex_w);
//					if (1/tex_w > depthArray[i][j]) {
						try {
							int colorRGB;
							if (texture != null)
								colorRGB = texture.getRGB((int) (tex_u*texture.getWidth()/tex_w), (int) (tex_v*texture.getHeight()/tex_w));
							else {
								double myTexW = 2*tex_w;
								if (myTexW < 0)
									myTexW = 0;
								if (myTexW > 1)
									myTexW = 1;
								colorRGB = (new Color((int) (255*myTexW), (int) (255*myTexW), (int) (255*myTexW))).getRGB();
							}
							image.setRGB(j, i, colorRGB);
						} catch (Exception e) {}
//						depthArray[i][j] = 1/tex_w;
//					}
						//pDepthBuffer[i*ScreenWidth() + j] = tex_w;
					//}
					t += tstep;
				}

			}
		}

		dy1 = y3 - y2;
		dx1 = x3 - x2;
		dv1 = v3 - v2;
		du1 = u3 - u2;
		dw1 = w3 - w2;

		if (dy1 != 0) dax_step = dx1 / (double)Math.abs(dy1);
		if (dy2 != 0) dbx_step = dx2 / (double)Math.abs(dy2);

		du1_step = 0; dv1_step = 0;
		if (dy1 != 0) du1_step = du1 / (double)Math.abs(dy1);
		if (dy1 != 0) dv1_step = dv1 / (double)Math.abs(dy1);
		if (dy1 != 0) dw1_step = dw1 / (double)Math.abs(dy1);

		if (dy1 != 0) {
			for (int i = y2; i <= y3; i++) {
				int ax = (int) (x2 + (double)(i - y2) * dax_step);
				int bx = (int) (x1 + (double)(i - y1) * dbx_step);

				double tex_su = u2 + (double)(i - y2) * du1_step;
				double tex_sv = v2 + (double)(i - y2) * dv1_step;
				double tex_sw = w2 + (double)(i - y2) * dw1_step;

				double tex_eu = u1 + (double)(i - y1) * du2_step;
				double tex_ev = v1 + (double)(i - y1) * dv2_step;
				double tex_ew = w1 + (double)(i - y1) * dw2_step;

				if (ax > bx) {
					int temp1 = ax; ax = bx; bx = temp1;
					double temp2 = tex_su; tex_su = tex_eu; tex_eu = temp2;
					double temp3 = tex_sv; tex_sv = tex_ev; tex_ev = temp3;
					double temp4 = tex_sw; tex_sw = tex_ew; tex_ew = temp4;
				}

				tex_u = tex_su;
				tex_v = tex_sv;
				tex_w = tex_sw;

				double tstep = 1.0d / ((double)(bx - ax));
				double t = 0.0d;

				for (int j = ax; j < bx; j++) {
					tex_u = (1.0d - t) * tex_su + t * tex_eu;
					tex_v = (1.0d - t) * tex_sv + t * tex_ev;
					tex_w = (1.0d - t) * tex_sw + t * tex_ew;
					//if (tex_w > pDepthBuffer[i*ScreenWidth() + j])
					//{
						//image.setRGB(j, i, texture.getRGB((int) (tex_u/tex_w), (int) (tex_v/tex_w)));
					//System.out.println("RGB:" + texture.getRGB((int) (tex_u*texture.getWidth()), (int) (tex_v*texture.getHeight())));
					try {
						int colorRGB;
						if (texture != null)
							colorRGB = texture.getRGB((int) (tex_u*texture.getWidth()/tex_w), (int) (tex_v*texture.getHeight()/tex_w));
						else {
							double myTexW = 2*tex_w;
							if (myTexW < 0)
								myTexW = 0;
							if (myTexW > 1)
								myTexW = 1;
							colorRGB = (new Color((int) (255*myTexW), (int) (255*myTexW), (int) (255*myTexW))).getRGB();
						}
						image.setRGB(j, i, colorRGB);
					} catch (Exception e) {}
						//pDepthBuffer[i*ScreenWidth() + j] = tex_w;
					//}
					t += tstep;
				}
			}	
		}
	}
	
	private Triangle[] clipAgainstPlane(Vector planePos, Vector planeNorm, Triangle tri) {
		planeNorm = planeNorm.unit();
		ArrayList<Vector> insidePoints = new ArrayList<Vector>(), outsidePoints = new ArrayList<Vector>();
		ArrayList<Vector> insideTex = new ArrayList<Vector>(), outsideTex = new ArrayList<Vector>();

//		System.out.println("old tex1: " + tri.getTex1() + " tex2: " + tri.getTex2() + " tex3: " + tri.getTex3());
		
//		for (Vector point : tri.getVerts())
//			if (planeNorm.dot(point.clone().minus(planePos)) >= 0) {
//				insidePoints.add(point);
//				
//			} else
//				outsidePoints.add(point);
		if (planeNorm.dot(tri.getVert1().minus(planePos)) >= 0) { insidePoints.add(tri.getVert1()); insideTex.add(tri.getTex1()); }
		else { outsidePoints.add(tri.getVert1()); outsideTex.add(tri.getTex1()); }
		if (planeNorm.dot(tri.getVert2().minus(planePos)) >= 0) { insidePoints.add(tri.getVert2()); insideTex.add(tri.getTex2()); }
		else { outsidePoints.add(tri.getVert2()); outsideTex.add(tri.getTex2()); }
		if (planeNorm.dot(tri.getVert3().minus(planePos)) >= 0) { insidePoints.add(tri.getVert3()); insideTex.add(tri.getTex3()); }
		else { outsidePoints.add(tri.getVert3()); outsideTex.add(tri.getTex3()); }

		if (insidePoints.size() == 0) {
			return new Triangle[] {};
		} else if (insidePoints.size() == 3)
			return new Triangle[] {tri};
		else if (insidePoints.size() == 1) {
			Vector vert1 = insidePoints.get(0);
			Vector tex1 = insideTex.get(0);
			Vector vert2 = Vector.VecPlaneIntersect(planePos, planeNorm, insidePoints.get(0), outsidePoints.get(0));
			double t2 = Vector.VecPlaneIntersectGetT(planePos, planeNorm, insidePoints.get(0), outsidePoints.get(0));
			Vector tex2 = insideTex.get(0).plus( outsideTex.get(0).minus(insideTex.get(0)).scale(t2) );
			tex2.setW( insideTex.get(0).getW() + (outsideTex.get(0).getW() - insideTex.get(0).getW())*t2 );
			//vert2.trim();
			Vector vert3 = Vector.VecPlaneIntersect(planePos, planeNorm, insidePoints.get(0), outsidePoints.get(1));
			double t3 = Vector.VecPlaneIntersectGetT(planePos, planeNorm, insidePoints.get(0), outsidePoints.get(1));
			Vector tex3 = insideTex.get(0).plus( outsideTex.get(1).minus(insideTex.get(0)).scale(t3) );
			tex3.setW( insideTex.get(0).getW() + (outsideTex.get(1).getW() - insideTex.get(0).getW())*t3 );
			//vert3.trim();
			
//			System.out.println("new tex1: " + tex1 + " tex2: " + tex2 + " tex3: " + tex3);
			//System.out.println("w1: " + vert1.getW() + " w2: " + vert2.getW() + " w3: " + vert3.getW());
			
//			vert2.trim();
//			vert3.trim();
			
			return new Triangle[] {
				new Triangle(vert1, vert2, vert3, new Vector[] {tex1, tex2, tex3})	
			};
		} else if (insidePoints.size() == 2) {
			Vector vert1 = insidePoints.get(0);
			Vector tex1 = insideTex.get(0);
			Vector vert2 = insidePoints.get(1);
			Vector tex2 = insideTex.get(1);
			Vector vert3 = Vector.VecPlaneIntersect(planePos, planeNorm, insidePoints.get(0), outsidePoints.get(0));
			double t3 = Vector.VecPlaneIntersectGetT(planePos, planeNorm, insidePoints.get(0), outsidePoints.get(0));
			Vector tex3 = insideTex.get(0).plus( outsideTex.get(0).minus(insideTex.get(0)).scale(t3) );
			tex3.setW( insideTex.get(0).getW() + (outsideTex.get(0).getW() - insideTex.get(0).getW())*t3 );
			//vert3.trim();
			Vector vert4 = Vector.VecPlaneIntersect(planePos, planeNorm, insidePoints.get(1), outsidePoints.get(0));
			double t4 = Vector.VecPlaneIntersectGetT(planePos, planeNorm, insidePoints.get(1), outsidePoints.get(0));
			Vector tex4 = insideTex.get(1).plus( outsideTex.get(0).minus(insideTex.get(1)).scale(t4) );
			tex4.setW( insideTex.get(1).getW() + (outsideTex.get(0).getW() - insideTex.get(1).getW())*t4 );;
			//vert4.trim();
			
//			System.out.println("new tex1: " + tex1 + " tex2: " + tex2 + " tex3: " + tex3 + " tex4: " + tex4);
			//System.out.println("w1: " + vert1.getW() + " w2: " + vert2.getW() + " w3: " + vert3.getW() + " w4: " + vert4.getW());
			
//			vert3.trim();
//			vert4.trim();
			
			return new Triangle[] {
					new Triangle(vert1, vert2, vert4, new Vector[] {tex1, tex2, tex4}),
					new Triangle(vert1, vert4, vert3, new Vector[] {tex1, tex4, tex3})
			};
		}
		return new Triangle[] {};
	}
	
	public static void main (String[] args) {
		Game game = new Game();
	}
}