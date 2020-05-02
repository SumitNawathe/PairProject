import java.io.*;
import java.util.*;

public class Mesh {
	private ArrayList<Triangle> tris;
	public ArrayList<Triangle> getTris () {
		return tris;
	}
	
	public Mesh () {
		tris = new ArrayList<Triangle>();
	}
	
	public Mesh translate (Vector vec) {
		for (Triangle tri : tris)
			tri.translate(vec);
		return this;
	}
	
	public static Mesh loadFromObjFile (String filePath) throws IOException {
		Mesh mesh = new Mesh();
		BufferedReader file = new BufferedReader(new FileReader(filePath));
		//System.out.println("d");
		//String line = file.readLine();
		String line;
		//System.out.println("e");
		ArrayList<Vector> vectorPool = new ArrayList<Vector>();
		//while (!line.equals("")) {
		//System.out.println("f");
		//int counter = 1;
		while ((line = file.readLine()) != null) {
			//System.out.println(counter);
			//counter++;
			//System.out.println(line);
			StringTokenizer st = new StringTokenizer(line);
			String starting = st.nextToken();
			if (starting.equals("v")) {
				//System.out.println("adding vector");
				vectorPool.add(new Vector(
						Double.parseDouble(st.nextToken()), 
						Double.parseDouble(st.nextToken()), 
						Double.parseDouble(st.nextToken()) ));
			} else if (starting.equals("f")) {
				//System.out.println("adding triangle");
				mesh.getTris().add(new Triangle(
						vectorPool.get(Integer.parseInt(st.nextToken())-1),
						vectorPool.get(Integer.parseInt(st.nextToken())-1),
						vectorPool.get(Integer.parseInt(st.nextToken())-1)));
			}
//			try {
//				line = file.readLine();
//			} catch (Exception e) { break; }
		}
		file.close();
		//System.out.println("e");
		//System.out.println("mesh size: " + mesh.getTris().size());
		return mesh;
	}
}