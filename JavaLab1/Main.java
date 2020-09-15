import java.lang.Math;

public class Main {

	public static void main(String[] args) {

		short[] f = new short[10];
		int idx = 0;
		for (short i = 4; i <= 22; i += 2)
			f[idx++] = i;


		float[] x = new float[14];

		for (int i = 0; i < 14; i++)
			x[i] = -6.f + (float)Math.random() * 12.f;


		float[][] d = new float[10][14];

		for (int i = 0; i < d.length; i++) {

			for (int j = 0; j < d[i].length; j++) {
				switch (f[i]) {
					case 22:
						d[i][j] = (float)Math.pow((Math.pow(x[j] / (0.6666f - x[j]), 2.f) + Math.PI) * 0.25f,
						 2.f * Math.asin(0.75f * x[j] * 0.08333f));
						break;
					case 4:
					case 6:
					case 8:
					case 14:
					case 20:
						d[i][j] = (float)Math.atan(Math.pow(0.333f * x[j] * 0.08333f, 2.f));
						break;
					default:
						d[i][j] = (float)Math.tan(Math.atan(Math.exp(Math.pow(Math.pow((-2.f - (float)Math.abs(x[j]))
						 / (float)Math.abs(x[j]), 2.f), 0.3333f))));
				}

				System.out.printf("%.3f ", d[i][j]);
			}

			System.out.print("\n");
		}

	}

}