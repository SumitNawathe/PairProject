import java.awt.*;

public class Triangle {
	private Vector vert1, vert2, vert3;
	private Color color;
	private double depth;
	
	public Color getColor () { return color; }
	public void setColor (Color color) { this.color = color; }
	
	public double getDepth () { return depth; }
	public void setDepth (double depth) { this.depth = depth; }
	
	public Vector getVert1 () { return vert1; }
	public Vector getVert2 () { return vert2; }
	public Vector getVert3 () { return vert3; }
	public Vector[] getVerts () {
		Vector[] verts = {vert1, vert2, vert3};
		return verts;
	}
	
	public String toString () {
		return "Tri: " + vert1 + ", " + vert2 + ", " + vert3;
	}
	
	public Triangle (Vector vert1, Vector vert2, Vector vert3) {
		this.vert1 = vert1;
		this.vert2 = vert2;
		this.vert3 = vert3;
		this.color = Color.white;
	}
	public Triangle (Vector vert1, Vector vert2, Vector vert3, Color color) {
		this.vert1 = vert1;
		this.vert2 = vert2;
		this.vert3 = vert3;
		this.color = color;
	}
	public Triangle (Vector vert1, Vector vert2, Vector vert3, Color color, double depth) {
		this.vert1 = vert1;
		this.vert2 = vert2;
		this.vert3 = vert3;
		this.color = color;
		this.depth = depth;
	}
	
	public Triangle clone () {
		return new Triangle(vert1.clone(), vert2.clone(), vert3.clone());
	}
	
	public Triangle translate (Vector offset) {
		return new Triangle(vert1.plus(offset), vert2.plus(offset), vert3.plus(offset));
	}
}
