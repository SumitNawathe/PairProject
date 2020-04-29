public class Matrix {
//	public double[][] matrix;
//	public Matrix () { matrix = new double[4][4]; }
//	public Matrix (double[][] matrix) { this.matrix = matrix; }
//	public double[][] getMatrix () { return matrix; }
	
	public static Vector multMatVec (double[][] matrix, Vector vec) {
		return new Vector(
			vec.getX()*matrix[0][0] + vec.getY()*matrix[1][0] + vec.getZ()*matrix[2][0] + vec.getW()*matrix[3][0],
			vec.getX()*matrix[0][1] + vec.getY()*matrix[1][1] + vec.getZ()*matrix[2][1] + vec.getW()*matrix[3][1],
			vec.getX()*matrix[0][2] + vec.getY()*matrix[1][2] + vec.getZ()*matrix[2][2] + vec.getW()*matrix[3][2],
			vec.getX()*matrix[0][3] + vec.getY()*matrix[1][3] + vec.getZ()*matrix[2][3] + vec.getW()*matrix[3][3]
		);
	}
	
	public static double[][] getIdentityMatrix () {
		return new double[][] {
			new double[] {1, 0, 0, 0},
			new double[] {0, 1, 0, 0},
			new double[] {0, 0, 1, 0},
			new double[] {0, 0, 0, 1}
		};
	}
	
	public static double[][] getRotMatX (double xAngle) {
		double[][] matRotX = new double[4][4];
		matRotX[0][0] = 1;
		matRotX[1][1] = Math.cos(xAngle);
		matRotX[1][2] = Math.sin(xAngle);
		matRotX[2][1] = -1*Math.sin(xAngle);
		matRotX[2][2] = Math.cos(xAngle);
		matRotX[3][3] = 1;
		return matRotX;
	}
	
	public static double[][] getRotMatY (double yAngle) {
		double[][] matRotY = new double[4][4];
		matRotY[0][0] = Math.cos(yAngle);
		matRotY[0][2] = Math.sin(yAngle);
		matRotY[2][0] = -1*Math.sin(yAngle);
		matRotY[1][1] = 1;
		matRotY[2][2] = Math.cos(yAngle);
		matRotY[3][3] = 1;
		return matRotY;
	}
	
	public static double[][] getRotMatZ (double zAngle) {
		double[][] matRotZ = new double[4][4];
		matRotZ[0][0] = Math.cos(zAngle);
		matRotZ[0][1] = Math.sin(zAngle);
		matRotZ[1][0] = -1*Math.sin(zAngle);
		matRotZ[1][1] = Math.cos(zAngle);
		matRotZ[2][2] = 1;
		matRotZ[3][3] = 1;
		return matRotZ;
	}
	
	public static double[][] getTranslationMatrix (Vector translation) {
		double[][] matrix = new double[4][4];
		matrix[0][0] = 1;
		matrix[1][1] = 1;
		matrix[2][2] = 1;
		matrix[3][0] = translation.getX();
		matrix[3][1] = translation.getY();
		matrix[3][2] = translation.getZ();
		return matrix;
	}
	
	public static double[][] getProjMatrix (double a, double f, double Z_NEAR, double Z_FAR) {
		double q = Z_FAR/(Z_FAR-Z_NEAR);
		double[][] projMatrix = new double[4][4];
		projMatrix[0][0] = a*f;
		projMatrix[1][1] = f;
		projMatrix[2][2] = q;
		projMatrix[2][3] = 1;
		projMatrix[3][2] = -1*Z_NEAR*q;
		projMatrix[3][3] = 0;
		return projMatrix;
	}
	
	public static double[][] mulMatMat (double[][] m1, double[][] m2) {
		double[][] matrix = new double[4][4];
		for (int c = 0; c < 4; c++)
			for (int r = 0; r < 4; r++)
				matrix[r][c] = m1[r][0] * m2[0][c] + m1[r][1] * m2[1][c] + m1[r][2] * m2[2][c] + m1[r][3] * m2[3][c];
		return matrix;
	}
}
