public class MeshCube extends Mesh {
	public MeshCube () {
		super();
		double[][] triCoords =  {
				// SOUTH
				{ 0.0f, 0.0f, 0.0f, 1.0f,    0.0f, 1.0f, 0.0f, 1.0f,    1.0f, 1.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 1.0f,}, 
				{ 0.0f, 0.0f, 0.0f, 1.0f,    1.0f, 1.0f, 0.0f, 1.0f,    1.0f, 0.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		1.0f, 0.0f, 1.0f,		1.0f, 1.0f, 1.0f,},
								  																			   
				// EAST           																			   
				{ 1.0f, 0.0f, 0.0f, 1.0f,    1.0f, 1.0f, 0.0f, 1.0f,    1.0f, 1.0f, 1.0f, 1.0f,		0.0f, 1.0f, 1.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 1.0f,},
				{ 1.0f, 0.0f, 0.0f, 1.0f,    1.0f, 1.0f, 1.0f, 1.0f,    1.0f, 0.0f, 1.0f, 1.0f,		0.0f, 1.0f, 1.0f,		1.0f, 0.0f, 1.0f,		1.0f, 1.0f, 1.0f,},
								   																			   
				// NORTH           																			   
				{ 1.0f, 0.0f, 1.0f, 1.0f,    1.0f, 1.0f, 1.0f, 1.0f,    0.0f, 1.0f, 1.0f, 1.0f,		0.0f, 1.0f, 1.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 1.0f,},
				{ 1.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f, 1.0f, 1.0f,    0.0f, 0.0f, 1.0f, 1.0f,		0.0f, 1.0f, 1.0f,		1.0f, 0.0f, 1.0f,		1.0f, 1.0f, 1.0f,},
								   																			   
				// WEST            																			   
				{ 0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f, 1.0f, 1.0f,    0.0f, 1.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 1.0f,},
				{ 0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 1.0f, 0.0f, 1.0f,    0.0f, 0.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		1.0f, 0.0f, 1.0f,		1.0f, 1.0f, 1.0f,},
								   																			   
				// TOP             																			   
				{ 0.0f, 1.0f, 0.0f, 1.0f,    0.0f, 1.0f, 1.0f, 1.0f,    1.0f, 1.0f, 1.0f, 1.0f,		0.0f, 1.0f, 1.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 1.0f,},
				{ 0.0f, 1.0f, 0.0f, 1.0f,    1.0f, 1.0f, 1.0f, 1.0f,    1.0f, 1.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		1.0f, 0.0f, 1.0f,		1.0f, 1.0f, 1.0f,},
								   																			  
				// BOTTOM          																			  
				{ 1.0f, 0.0f, 1.0f, 1.0f,    0.0f, 0.0f, 1.0f, 1.0f,    0.0f, 0.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 1.0f,},
				{ 1.0f, 0.0f, 1.0f, 1.0f,    0.0f, 0.0f, 0.0f, 1.0f,    1.0f, 0.0f, 0.0f, 1.0f,		0.0f, 1.0f, 1.0f,		1.0f, 0.0f, 1.0f,		1.0f, 1.0f, 1.0f,},
		};
		for (double[] triCoord : triCoords)
			getTris().add(new Triangle(
					new Vector(triCoord[0], triCoord[1], triCoord[2], triCoord[3]),
					new Vector(triCoord[4], triCoord[5], triCoord[6], triCoord[7]),
					new Vector(triCoord[8], triCoord[9], triCoord[10], triCoord[11]),
					new Vector[] {new Vector(triCoord[12], triCoord[13]), new Vector(triCoord[15], triCoord[16]), new Vector(triCoord[18], triCoord[19])}));
	}
}
