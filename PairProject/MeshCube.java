public class MeshCube extends Mesh {
	public MeshCube () {
		super();
		double[][] triCoords =  {
				// SOUTH
				{ 0.0d, 0.0d, 0.0d,    0.0d, 1.0d, 0.0d,    1.0d, 1.0d, 0.0d },
				{ 0.0d, 0.0d, 0.0d,    1.0d, 1.0d, 0.0d,    1.0d, 0.0d, 0.0d },

				// EAST                                                      
				{ 1.0d, 0.0d, 0.0d,    1.0d, 1.0d, 0.0d,    1.0d, 1.0d, 1.0d },
				{ 1.0d, 0.0d, 0.0d,    1.0d, 1.0d, 1.0d,    1.0d, 0.0d, 1.0d },

				// NORTH                                                     
				{ 1.0d, 0.0d, 1.0d,    1.0d, 1.0d, 1.0d,    0.0d, 1.0d, 1.0d },
				{ 1.0d, 0.0d, 1.0d,    0.0d, 1.0d, 1.0d,    0.0d, 0.0d, 1.0d },

				// WEST                                                      
				{ 0.0d, 0.0d, 1.0d,    0.0d, 1.0d, 1.0d,    0.0d, 1.0d, 0.0d },
				{ 0.0d, 0.0d, 1.0d,    0.0d, 1.0d, 0.0d,    0.0d, 0.0d, 0.0d },

				// TOP                                                       
				{ 0.0d, 1.0d, 0.0d,    0.0d, 1.0d, 1.0d,    1.0d, 1.0d, 1.0d },
				{ 0.0d, 1.0d, 0.0d,    1.0d, 1.0d, 1.0d,    1.0d, 1.0d, 0.0d },

				// BOTTOM                                                    
				{ 1.0d, 0.0d, 1.0d,    0.0d, 0.0d, 1.0d,    0.0d, 0.0d, 0.0d },
				{ 1.0d, 0.0d, 1.0d,    0.0d, 0.0d, 0.0d,    1.0d, 0.0d, 0.0d }
		};
		for (double[] triCoord : triCoords)
			getTris().add(new Triangle(
					new Vector(triCoord[0], triCoord[1], triCoord[2]),
					new Vector(triCoord[3], triCoord[4], triCoord[5]),
					new Vector(triCoord[6], triCoord[7], triCoord[8])));
	}
}
