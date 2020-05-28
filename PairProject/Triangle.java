import java.awt.*;
import java.awt.image.*;

public class Triangle {
	private Vector vert1, vert2, vert3;
	private Vector tex1, tex2, tex3;
//	private Color color;
	private BufferedImage texture;
	private double depth;
	
//	public Color getColor () { return color; }
//	public void setColor (Color color) { this.color = color; }
	public BufferedImage getTexture () { return texture; }
	public void setTexture (BufferedImage texture) { this.texture = texture; }
	
	public double getDepth () { return depth; }
	public void setDepth (double depth) { this.depth = depth; }
	
	public Vector getVert1 () { return vert1; }
	public Vector getVert2 () { return vert2; }
	public Vector getVert3 () { return vert3; }
	public Vector[] getVerts () {
		return new Vector[] {vert1, vert2, vert3};
	}
	public void setVerts (Vector[] verts) {
		vert1 = verts[0];
		vert2 = verts[1];
		vert3 = verts[2];
	}
	public Vector getTex1 () { return tex1; }
	public void setTex1 (Vector tex1) { this.tex1 = tex1; }
	public Vector getTex2 () { return tex2; }
	public void setTex2 (Vector tex2) { this.tex2 = tex2; }
	public Vector getTex3 () { return tex3; }
	public void setTex3 (Vector tex3) { this.tex3 = tex3; }
	public Vector[] getTex () {
		return new Vector[] {tex1, tex2, tex3};
	}
	
	public String toString () {
		return "Tri: " + vert1 + ", " + vert2 + ", " + vert3;
	}
	
	public Triangle (Vector vert1, Vector vert2, Vector vert3) {
		this.vert1 = vert1;
		this.vert2 = vert2;
		this.vert3 = vert3;
//		this.color = Color.white;
	}
	public Triangle (Vector vert1, Vector vert2, Vector vert3, Vector[] tex) {
		this.vert1 = vert1;
		this.vert2 = vert2;
		this.vert3 = vert3;
		this.tex1 = tex[0];
		this.tex2 = tex[1];
		this.tex3 = tex[2];
	}
	public Triangle (Vector vert1, Vector vert2, Vector vert3, Vector[] tex, double depth, BufferedImage texture) {
		this.vert1 = vert1;
		this.vert2 = vert2;
		this.vert3 = vert3;
		this.tex1 = tex[0];
		this.tex2 = tex[1];
		this.tex3 = tex[2];
		this.depth = depth;
		this.texture = texture;
	}
	
	public Triangle clone () {
		return new Triangle(vert1.clone(), vert2.clone(), vert3.clone(), new Vector[] {tex1.clone(), tex2.clone(), tex3.clone()}, depth, texture);
	}
	
	public Triangle translate (Vector offset) {
		return new Triangle(vert1.plus(offset), vert2.plus(offset), vert3.plus(offset));
	}
}
