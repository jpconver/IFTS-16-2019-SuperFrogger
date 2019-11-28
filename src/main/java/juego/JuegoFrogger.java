package juego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;



// Implemento KeyListener para poder leer en los metods keyPressed y keyReleased los codigos de tecla que apreto el usuario
// Implemento Runnable para crear un Threads que ejecute en paralelo con mi programa
public class JuegoFrogger extends JComponent implements KeyListener, Runnable {

	private static final long serialVersionUID = 1L;
	private int anchoJuego;
	private int largoJuego;
	private int tiempoDeEsperaEntreActualizaciones;
	private ElementoBasicoFrogger frogg;
	private PuntajeFrogger puntajeFrogger;
	private VidasFrogger vidasFrogger;
	private NombreJuegoFrogger nombreJuegoFrogger;
	private ElementoBasicoFrogger premio;
	private Reloj reloj;
	private ElementoBasicoFrogger fondo;
	private List<EnemigoAuto> enemigosAuto;
	private List<EnemigoCamion> enemigosCamion;
	private List<EnemigoSerpiente> enemigosSerpiente;
	private List<Ranitas> ranitas;
	protected boolean pararJuego;
	private boolean juegoCorriendo;
	private boolean pararJuego2;
	private Sonidos sonidos;


	public JuegoFrogger(int anchoJuego, int largoJuego, int tiempoDeEsperaEntreActualizaciones) {
		this.anchoJuego = anchoJuego;
		this.largoJuego = largoJuego;
		this.fondo = new Fondo(0,0, 0, 0, 600, 640,null);
		this.reloj = new Reloj(100,25,new Font("Impact",8,20), Color.orange);
		this.frogg = new Frogg(280,580, 0, 0, 40, 40, Color.green);
		this.premio = new Premio(new Random().nextInt(400),new Random().nextInt(400), 0, 0, 40, 40,null);
		this.puntajeFrogger = new PuntajeFrogger(490, 25, new Font("Impact", 8, 20), Color.magenta);
		this.enemigosAuto = new ArrayList<EnemigoAuto>();
		this.enemigosCamion = new ArrayList<EnemigoCamion>();
		this.ranitas= new ArrayList<Ranitas>();
		this.enemigosSerpiente = new ArrayList<EnemigoSerpiente>();
		this.vidasFrogger = new VidasFrogger(10, 25, new Font("Impact", 8, 20), Color.magenta, 3);
		this.nombreJuegoFrogger = new NombreJuegoFrogger(200,30, new Font("Impact", 8, 30), Color.green, 3);
		this.juegoCorriendo = true;
		this.pararJuego = false;
		this.tiempoDeEsperaEntreActualizaciones = tiempoDeEsperaEntreActualizaciones;
		reloj.setPuntaje(20);
		cargarSonidos();
		//sonidos.tocarSonido("polca");
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(anchoJuego, largoJuego);
	}
	/*
	 * Actualizar la actualizacion y el dibujado del juego de esta forma no es
	 * recomendable dado que tendra distintas velocidades en distinto hardware Se
	 * hizo asi por simplicidad para facilitar el aprendizaje Lo recomendado es
	 * separar la parte de dibujado de la de actualizacion y usar interpolation
	 */
	@Override
	public void run() {
		while (juegoCorriendo) {
			actualizarJuego();
			dibujarJuego();
			esperar(tiempoDeEsperaEntreActualizaciones);
		}
	}
	private void cargarSonidos() {
		try {
			sonidos = new Sonidos();
			sonidos.agregarSonido("polca", "sonidos/polca.wav");
			sonidos.agregarSonido("salto", "sonidos/salto.wav");
			sonidos.agregarSonido("diamante", "sonidos/diamante.wav");
			sonidos.agregarSonido("muerte", "sonidos/muerte.wav");
			sonidos.agregarSonido("rescate", "sonidos/rescate.wav");
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// derecha 
		if (arg0.getKeyCode() == 39) {
			frogg.setPosicionX(frogg.getPosicionX()+30);
			sonidos.tocarSonido("salto");

		}   
		//  izquierda 
		if (arg0.getKeyCode() == 37) {
			frogg.setPosicionX(frogg.getPosicionX()-30);
			sonidos.tocarSonido("salto");
		}
		//	arriba
		if (arg0.getKeyCode() == 38) {
			frogg.setPosicionY(frogg.getPosicionY()-30);
			sonidos.tocarSonido("salto");
		}
		//	abajo
		//	if (arg0.getKeyCode() == 40) {
		//	frogg.setPosicionY(frogg.getPosicionY()+30);
		//	sonidos.tocarSonido("salto");
		//}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	protected void paintComponent(Graphics g) {
		limpiarPantalla(g);
		// si el juego no esta parado entonces dibujar todos los elementos y los enemigos
		if (!pararJuego) {
			fondo.dibujarse(g);
			frogg.dibujarse(g);
			premio.dibujarse(g);
			reloj.dibujarse(g);
			puntajeFrogger.dibujarse(g);
			vidasFrogger.dibujarse(g);
			nombreJuegoFrogger.dibujarse(g);
			dibujarEnemigos(g);	
		} else {
			// si el juego esta parado entonces dibujar el fin del juego y cambiar el atributo juegoCorriendo a false
			dibujarFinJuego(g);
			juegoCorriendo = false;
		}
		if (pararJuego2) {
			// si el juego esta parado entonces dibujar el fin del juego y cambiar el atributo juegoCorriendo a false
			dibujarFinJuego2(g);{
				juegoCorriendo = false;
			}
		}
	}
	// En este metodo se actualiza el estado de todos los elementos del juego
	private void actualizarJuego() {
		verificarEstadoAmbiente();
		premio.moverse();
		frogg.moverse();
		moverEnemigos();
	}
	private void dibujarJuego() {
		this.repaint();
	}
	public void agregarEnemigoAuto(EnemigoAuto enemigo) {
		this.enemigosAuto.add(enemigo);
	}
	public void agregarEnemigoCamion(EnemigoCamion enemigo) {
		this.enemigosCamion.add(enemigo);
	}

	public void agregarEnemigoSerpiente(EnemigoSerpiente enemigo) {
		this.enemigosSerpiente.add(enemigo);
	}

	public void agregarRanitas(Ranitas enemigo) {
		this.ranitas.add(enemigo);

	}
	private void mostrarMensaje(Graphics g, String mensaje, int x, int y) {
		this.limpiarPantalla(g);
		g.setColor(Color.magenta);
		g.setFont(new Font("Impact", 8, 30));
		g.drawString(mensaje, x, y);
	}
	// dibujar el fin del juego
	private void dibujarFinJuego(Graphics g) {
		mostrarMensaje(g, "GAME OVER ",230, 250);
	}
	private void dibujarFinJuego2(Graphics g) {
		mostrarMensaje(g, "CONGRATULATIONS YOU HAVE SAVED THE FROGS",25,250);
	}
	// se hace una iteracion de todos los enemigos cargados en la lista de enemigos y se le dice a cada uno que ejecute el metodo moverse(). 
	// moverse() actualiza la posicionX y posicionY del elemento en base a la direccion que tenia para X e Y 
	private void moverEnemigos() {
		for (EnemigoAuto enemigo : enemigosAuto) {
			enemigo.moverse();
		}
		for (EnemigoCamion enemigo : enemigosCamion) {
			enemigo.moverse();
		}
		for (EnemigoSerpiente enemigo : enemigosSerpiente) {
			enemigo.moverse();
		}
	}
	// se hace una iteracion en la lista de enemigos y se ejecuta el metodo dibujarse()
	private void dibujarEnemigos(Graphics g) {
		for (EnemigoAuto enemigo : enemigosAuto) {
			enemigo.dibujarse(g);
		}
		for (EnemigoCamion enemigo : enemigosCamion) {
			enemigo.dibujarse(g);
		}
		for (EnemigoSerpiente enemigo : enemigosSerpiente) {
			enemigo.dibujarse(g);
		}
		for (Ranitas ranita : ranitas) {
			ranita.dibujarse(g);
		}
	}
	// En este metodo verifico las colisiones, los rebotes de la pelota contra las paredes, la colision entre enemigos y el fin de juego
	private void verificarEstadoAmbiente() {
		verificarReboteEnemigosContraParedIzquierda();
		verificarReboteEnemigosContraParedDerecha();
		verificarCollisionFroggParedes();
		verificarColisionEntreEnemigoCamionYFrogg();
		verificarColisionEntreEnemigoAutoYFrogg();
		verificarColisionEntreEnemigoSerpienteYFrogg();
		verificarColisionConRanitas();
		verificarFinDeJuego();
		verificarColisionPremio();
	}
	// metodo para limpiar la pantalla
	private void limpiarPantalla(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, anchoJuego, largoJuego);
	}
	// metodo para esperar una cantidad de milisegundos
	private void esperar(int milisegundos) {
		try {
			Thread.sleep(milisegundos);
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
	// se verifica si hay colision de cada enemigo contra las paredes laterales, si hay colision se cambia la direccion del enemigo en el eje X
	private void verificarReboteEnemigosContraParedDerecha() {
		for (EnemigoAuto enemigo : enemigosAuto) {
			if (enemigo.getPosicionX() <= 0 || enemigo.getPosicionX() + enemigo.getAncho() >= anchoJuego+40) {
				enemigo.setPosicionX(1);
			}
		}
		for (EnemigoSerpiente enemigo : enemigosSerpiente) {
			if (enemigo.getPosicionX() <= 0 || enemigo.getPosicionX() + enemigo.getAncho() >= anchoJuego+40) {
				enemigo.setPosicionX(1);
				reloj.restarPunto();
			}
		}
	}
	private void verificarReboteEnemigosContraParedIzquierda() {
		for (EnemigoCamion enemigo : enemigosCamion) {
			if (enemigo.getPosicionX() <= 0 || enemigo.getPosicionX() + enemigo.getAncho() >= anchoJuego+90) {
				enemigo.setPosicionX(600);
			}
		}
	}
	private void verificarColisionEntreEnemigoCamionYFrogg() {
		Iterator<EnemigoCamion> iterador = enemigosCamion.iterator();
		while (iterador.hasNext()) {
			EnemigoCamion enemigo = iterador.next();
			if (enemigo.hayColision(frogg)) {
				frogg.setPosicionY(580);
				frogg.setPosicionX(280);
				vidasFrogger.perderVida();
				sonidos.tocarSonido("muerte");
				mostrarMensaje(this.getGraphics(), "TRY AGAIN", 240, 250);
				esperar(2000);
			}
		}
	}
	private void verificarColisionEntreEnemigoAutoYFrogg() {
		Iterator<EnemigoAuto> iterador = enemigosAuto.iterator();
		while (iterador.hasNext()) {
			EnemigoAuto enemigo = iterador.next();
			if (enemigo.hayColision(frogg)) {
				frogg.setPosicionY(580);
				frogg.setPosicionX(280);
				vidasFrogger.perderVida();
				sonidos.tocarSonido("muerte");
				mostrarMensaje(this.getGraphics(), "TRY AGAIN", 240, 250);
				esperar(2000);
			}
		}
	}
	private void verificarColisionEntreEnemigoSerpienteYFrogg() {
		Iterator<EnemigoSerpiente> iterador = enemigosSerpiente.iterator();
		while (iterador.hasNext()) {
			EnemigoSerpiente enemigo = iterador.next();
			if (enemigo.hayColision(frogg)) {
				frogg.setPosicionY(580);
				frogg.setPosicionX(280);
				vidasFrogger.perderVida();
				sonidos.tocarSonido("muerte");
				mostrarMensaje(this.getGraphics(), "TRY AGAIN", 240, 250);
				esperar(2000);
			}
		}
	}
	private void verificarColisionConRanitas() {
		Iterator<Ranitas> iterador = ranitas.iterator();
		while (iterador.hasNext()) {
			Ranitas ranita = iterador.next();
			if ( ranita.hayColision(frogg)) {
				iterador.remove();
				frogg.setPosicionY(580);
				frogg.setPosicionX(280);
				puntajeFrogger.sumarPunto();
				reloj.setPuntaje(20);
				sonidos.tocarSonido("rescate");
			}
		}
	}
	private void verificarCollisionFroggParedes() {
		if(frogg.getPosicionX()>=600||frogg.getPosicionX()<=0||frogg.getPosicionY()>=640||frogg.getPosicionY()<=40) {
			vidasFrogger.perderVida();
			frogg.setPosicionY(580);
			frogg.setPosicionX(280);
			sonidos.tocarSonido("muerte");
		}
	}
	private void verificarColisionPremio() {
		if(frogg.hayColision(premio)) {
			this.premio = new Premio(new Random().nextInt(400),new Random().nextInt(400), 0, 0, 40, 40, null);
			puntajeFrogger.sumarPunto();
			sonidos.tocarSonido("diamante");
		}
	}
	private void verificarFinDeJuego() {
		if (ranitas.size() == 0) {
			pararJuego2 = true;
		}
		if (vidasFrogger.getVidas() == 0) {
			pararJuego = true;
		}
		if (reloj.getPuntaje()== 0) {
			pararJuego = true;
		}
	}
}

