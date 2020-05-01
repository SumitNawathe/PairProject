public class Vector {
	private double x, y, z, w = 1;
	public double getX () { return x; }
	public double getY () { return y; }
	public double getZ () { return z; }
	public double getW () { return w; }
	
	public Vector () { x = 0; y = 0; z = 0; }
	public Vector (double x, double y) { this.x = x; this.y = y; this.z = 0; }
	public Vector (double x, double y, double z) { this.x = x; this.y = y; this.z = z; }
	public Vector (double x, double y, double z, double w) { this.x = x; this.y = y; this.z = z; this.w = w;}
	
	public Vector plus (Vector pos) {
		return new Vector(x+pos.getX(), y+pos.getY(), z+pos.getZ());
	}
	public Vector scale (double factor) {
		x *= factor;
		y *= factor;
		z *= factor;
		return this;
	}
	public Vector minus (Vector pos) {
		return (this.clone().plus(pos.clone().scale(-1)));
	}
	public Vector unit () {
		return this.clone().scale(1/this.magnitude());
	}
	
	public Vector clone () {
		return new Vector(x, y, z);
	}
	public double magnitude () {
		return Math.sqrt(x*x + y*y + z*z);
	}
	public String toString () {
		return "(" + x + ", " + y + ", " + z + ")";
	}
	public String toRoundedString() {
		return "(" + (int) x + ", " + (int) y + ", " + (int) z + ")";
	}
	
	public double dot (Vector pos) {
		return x*pos.getX() + y*pos.getY() + z*pos.getZ();
	}
	public Vector cross (Vector pos) {
		return new Vector(y*pos.getZ()-z*pos.getY(), -1*(x*pos.getZ()-z*pos.getX()), x*pos.getY()-y*pos.getX());
	}
	public Vector projOnto (Vector pos) {
		return pos.clone().scale(this.dot(pos)/(pos.magnitude()*pos.magnitude()));
	}
	
	public Vector trim () {
		w = 1;
		return this;
	}
	public void setW (double w) {
		this.w = w;
	}
	
	public static Vector VecPlaneIntersect (Vector planePoint, Vector planeNorm, Vector lineStart, Vector lineEnd) {
		planeNorm = planeNorm.unit();
		double plane_d = -1*planePoint.dot(planeNorm);
		double ad = lineStart.dot(planeNorm);
		double bd = lineEnd.dot(planeNorm);
		double t = (-plane_d - ad) / (bd - ad);
		Vector lineStartToEnd = lineEnd.minus(lineStart);
		Vector lineToIntersect = lineStartToEnd.scale(t);
		Vector answer = lineStart.plus(lineToIntersect);
		answer.setW(t);
		return answer;
	}
}
