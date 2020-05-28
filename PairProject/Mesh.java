import java.io.*;
import java.util.*;

public class Mesh {
	private ArrayList<Triangle> tris;
	public ArrayList<Triangle> getTris () { return tris; }
	public void setTris (ArrayList<Triangle> tris) { this.tris = tris; }
	
	public Mesh () {
		tris = new ArrayList<Triangle>();
	}
	
	public Mesh translate (Vector vec) {
		//System.out.println("translate");
		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {tri.getVert1().plus(vec), tri.getVert2().plus(vec), tri.getVert3().plus(vec)});
		return this;
	}
	
	public static Mesh loadFromObjFileNoTexture (String filePath) throws IOException {
		Mesh mesh = new Mesh();
		BufferedReader file = new BufferedReader(new FileReader(filePath));
		String line;
		ArrayList<Vector> vectorPool = new ArrayList<Vector>();
		ArrayList<Vector> texturePool = new ArrayList<Vector>();
		while ((line = file.readLine()) != null) {
			System.out.println(vectorPool.size() + " " + texturePool.size());
			StringTokenizer st = new StringTokenizer(line);
			String starting = st.nextToken();
			if (starting.equals("v")) {
				//System.out.println("adding vector");
				vectorPool.add(new Vector(
						Double.parseDouble(st.nextToken()), 
						Double.parseDouble(st.nextToken()), 
						Double.parseDouble(st.nextToken()) ));
			} else if (starting.equals("f")) {
				try {
				mesh.getTris().add(new Triangle(vectorPool.get(Integer.parseInt(st.nextToken())-1), 
						vectorPool.get(Integer.parseInt(st.nextToken())-1), 
						vectorPool.get(Integer.parseInt(st.nextToken())-1), 
						new Vector[] {new Vector(0, 0), new Vector(0, 0), new Vector(0, 0)}));
				} catch (Exception e) {}
			}
		}
		file.close();
		//System.out.println("e");
		//System.out.println("mesh size: " + mesh.getTris().size());
		return mesh;
	}
	
	public static Mesh loadFromObjFile (String filePath) throws IOException {
		Mesh mesh = new Mesh();
		BufferedReader file = new BufferedReader(new FileReader(filePath));
		//System.out.println("d");
		//String line = file.readLine();
		String line;
		//System.out.println("e");
		ArrayList<Vector> vectorPool = new ArrayList<Vector>();
		ArrayList<Vector> texturePool = new ArrayList<Vector>();
		//while (!line.equals("")) {
		//System.out.println("f");
		//int counter = 1;
		while ((line = file.readLine()) != null) {
			System.out.println(vectorPool.size() + " " + texturePool.size());
			//System.out.println(counter);
			//counter++;
			//System.out.println(line);
			StringTokenizer st = new StringTokenizer(line);
			String starting = st.nextToken();
			if (starting.equals("vt")) {
				texturePool.add(new Vector(
						Double.parseDouble(st.nextToken()), 
						1-Double.parseDouble(st.nextToken())));
			} else if (starting.equals("v")) {
				//System.out.println("adding vector");
				vectorPool.add(new Vector(
						Double.parseDouble(st.nextToken()), 
						Double.parseDouble(st.nextToken()), 
						Double.parseDouble(st.nextToken()) ));
			} else if (starting.equals("f")) {
				try {
				//System.out.println("adding triangle");
					int[][] pairs = new int[3][2];
					for (int i = 0; i < 3; i++) {
						String[] str = st.nextToken().split("/");
						pairs[i][0] = Integer.parseInt(str[0]);
						pairs[i][1] = Integer.parseInt(str[1]);
					}
					mesh.getTris().add(new Triangle(
								vectorPool.get(pairs[0][0]-1),
								vectorPool.get(pairs[1][0]-1),
								vectorPool.get(pairs[2][0]-1),
							new Vector[] {
								texturePool.get(pairs[0][1]-1),
								texturePool.get(pairs[1][1]-1),
								texturePool.get(pairs[2][1]-1) 
					}));
				} catch (Exception e) {}
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