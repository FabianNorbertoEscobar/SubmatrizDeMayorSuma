package submatriz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SubmatrizDeMayorSuma extends EjercicioOIA {

	private int[][] matriz;
	private int[][] sumas;
	private int filas;
	private int columnas;

	private int sumaMax;
	private int iInicial;
	private int jInicial;
	private int iFinal;
	private int jFinal;

	public SubmatrizDeMayorSuma(File entrada, File salida) {
		super(entrada, salida);
		this.levantarEntrada();
	}

	private void levantarEntrada() {
		try {
			Scanner scan = new Scanner(new FileReader(this.entrada));
			this.filas = scan.nextInt();
			this.columnas = scan.nextInt();
			this.matriz = new int[this.filas][this.columnas];
			this.sumas = new int[this.filas][this.columnas];
			for (int i = 0; i < this.filas; i++) {
				for (int j = 0; j < this.columnas; j++) {
					this.matriz[i][j] = scan.nextInt();
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error al levantar archivo de entrada");
			e.printStackTrace();
		}
	}

	@Override
	public void resolver() {
		this.calcularSumasAcumuladas();
		this.encontrarSubmatriz();
		this.grabarSalida();
	}

	private void calcularSumasAcumuladas() {
		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				this.sumas[i][j] += this.matriz[i][j];
				if (j > 0) {
					this.sumas[i][j] += this.sumas[i][j - 1];
				}
				if (i > 0) {
					this.sumas[i][j] += this.sumas[i - 1][j];
				}
				if (i > 0 && j > 0) {
					this.sumas[i][j] -= this.sumas[i - 1][j - 1];
				}
			}
		}
	}

	private void encontrarSubmatriz() {
		int sumaSubmatriz;
		for (int iInicial = 0; iInicial < this.filas; iInicial++) {
			for (int jInicial = 0; jInicial < this.columnas; jInicial++) {
				for (int iFinal = iInicial + 1; iFinal < this.filas; iFinal++) {
					for (int jFinal = jInicial; jFinal < this.columnas; jFinal++) {
						sumaSubmatriz = this.sumas[iFinal][jFinal];
						if (jInicial - 1 > 0) {
							sumaSubmatriz -= this.sumas[iFinal][jInicial - 1];
						}
						if (iInicial - 1 > 0) {
							sumaSubmatriz -= this.sumas[iInicial - 1][jFinal];
						}
						if (iInicial - 1 > 0 && jInicial - 1 > 0) {
							sumaSubmatriz += this.sumas[iInicial - 1][jInicial - 1];
						}
						if (sumaSubmatriz > this.sumaMax) {
							this.sumaMax = sumaSubmatriz;
							this.iInicial = iInicial;
							this.jInicial = jInicial;
							this.iFinal = iFinal;
							this.jFinal = jFinal;
						}
					}
				}
			}
		}
	}

	private void grabarSalida() {
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(this.salida));
			buffer.write(String.valueOf(this.sumaMax));
			buffer.newLine();
			buffer.write(this.iInicial + " " + this.jInicial);
			buffer.newLine();
			buffer.write(this.iFinal + " " + this.jFinal);
			buffer.newLine();

			for (int i = this.iInicial; i <= this.iFinal; i++) {
				for (int j = this.jInicial; j <= this.jFinal; j++) {
					buffer.write(this.matriz[i][j] + " ");
				}
				buffer.newLine();
			}
			buffer.close();
		} catch (IOException e) {
			System.out.println("Error al grabar el archivo de salida");
			e.printStackTrace();
		}

	}

}
