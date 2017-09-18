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
	private int iMax;
	private int jMax;

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
				if (sumas[i][j] > this.sumaMax) {
					this.sumaMax = this.sumas[i][j];
					this.iMax = i;
					this.jMax = j;
				}
			}
		}
	}

	private void grabarSalida() {
		try {
			BufferedWriter buffer = new BufferedWriter(new FileWriter(this.salida));
			buffer.write(String.valueOf(this.sumaMax));
			buffer.newLine();
			buffer.write(String.valueOf(this.iMax) + " " + String.valueOf(this.jMax));
			buffer.newLine();

			for (int i = 0; i <= this.iMax; i++) {
				for (int j = 0; j <= this.jMax; j++) {
					buffer.write(this.sumas[i][j] + " ");
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
