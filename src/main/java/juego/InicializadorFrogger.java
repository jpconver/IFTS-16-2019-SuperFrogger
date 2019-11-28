
package juego;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class InicializadorFrogger extends JComponent implements KeyListener, Runnable {

	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		// Propiedades del Juego
		int anchoVentana = 600;
		int largoVentana = 640;
		int tiempoDeEsperaEntreActualizaciones = 5;
		int enemigosPorLinea = 5;
		int filasDeEnemigos = 2;

		// Activar aceleracion de graficos en 2 dimensiones
		System.setProperty("sun.java2d.opengl", "true");

		// Crear un objeto de tipo JFrame que es la ventana donde va estar el juego
		JFrame ventana = new JFrame("S U P E R F R O G G E R");

		// Cerrar la aplicacion cuando el usuario hace click en la 'X'
		ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Abrir la ventana en el centro de la pantalla
		ventana.setLocationRelativeTo(null);

		// Mostrar la ventana
		ventana.setVisible(true);

		// Crear un "JComponent" llamado Juego y agregarlo a la ventana
		Inicio  inicio = new Inicio(anchoVentana, largoVentana);

		JButton button = new JButton();
		button.setText("START");	         
		button.setForeground(Color.red);
		button.setToolTipText("PRESS BUTTON TO START");
		button.setBackground(Color.green);
		inicio.add(button);
		ventana.add(inicio);
		ventana.pack();

		button.addActionListener(e -> {
			JuegoFrogger juegoFrogger = new JuegoFrogger(anchoVentana, largoVentana, tiempoDeEsperaEntreActualizaciones);
			ventana.add(juegoFrogger);
			inicio.setVisible(false);
			juegoFrogger.setVisible(true);
			ventana.addKeyListener(juegoFrogger);
			// Agregar enemigos al juego
			agregarEnemigos(juegoFrogger, enemigosPorLinea, filasDeEnemigos);

			// Achicar la ventana lo maximo posible para que entren los componentes
			ventana.pack();

			// Crear un thread y pasarle como parametro al juego que implementa la interfaz
			// "Runnable"
			Thread thread = new Thread(juegoFrogger);
			// Arrancar el juego
			thread.start();
		}); 
		button.setFocusable(false);
	}
	private static void agregarEnemigos(JuegoFrogger juego, int enemigosPorLinea, int filasDeEnemigos) {
		for (int x = 1; x < enemigosPorLinea; x++) {
			for (int y = 1; y < filasDeEnemigos; y++) {
				Color color = new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
				juego.agregarEnemigoAuto(new EnemigoAuto(0 + x * 200, 120 + y * 30, 0.5, 0, 60, 30, color));
				juego.agregarEnemigoSerpiente(new EnemigoSerpiente(0 + x * 200, 300 + y * 30, 0.5, 0, 60, 30, color));
				juego.agregarEnemigoAuto(new EnemigoAuto(0 + x * 200, 480 + y * 30, 0.5, 0, 60, 30, color));
			}
		}
		for (int x = 1; x <  enemigosPorLinea; x++) {
			for (int y = 1; y < filasDeEnemigos; y++) {
				Color color = new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
				juego.agregarEnemigoCamion(new EnemigoCamion(0 + x * 200, 220 + y * 30, -0.5, 0, 60, 30, color));            
				juego.agregarEnemigoCamion(new EnemigoCamion(0 + x * 200, 380 + y * 30, -0.5, 0, 60, 30, color));
			}
		}
		for (int x = 1; x < enemigosPorLinea; x++) {
			for (int y = 1; y < filasDeEnemigos; y++) {
				juego.agregarRanitas(new Ranitas(-45+ x * 130, 30 + y * 30, -1, 0, 40, 40, Color.green));            
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		if (arg0.getKeyCode() == 38) {
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == 38) {
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == 37) {
		}
	}
	@Override
	public void run() {
	}
}
