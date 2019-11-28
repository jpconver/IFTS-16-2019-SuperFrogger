package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class VidasFrogger {
	// Clase para mostrar la cantidad de vidas que le quedan al usuario

	    private int posicionX;
	    private int posicionY;
	    private Font font;
	    private Color color;
	    private int vidas;

	    public VidasFrogger(int posicionX, int posicionY, Font font, Color color, int vidas) {
	        this.posicionX = posicionX;
	        this.posicionY = posicionY;
	        this.font = font;
	        this.color = color;
	        this.vidas = vidas;
	    }

	    public void dibujarse(Graphics g) {
	        g.setColor(color);
	        g.setFont(font);
	        g.drawString("FROG   " + String.valueOf(vidas), posicionX, posicionY);
	    }

	    public void perderVida() {
	        vidas--;
	    }

	    public int getVidas() {
	        return vidas;
	    }
}
