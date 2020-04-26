import java.util.*;

public abstract class Mesh {
	private ArrayList<Triangle> tris;
	public ArrayList<Triangle> getTris () {
		return tris;
	}
	
	public Mesh () {
		tris = new ArrayList<Triangle>();
	}
}
