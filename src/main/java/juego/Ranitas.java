package juego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;


public class Ranitas extends ElementoBasicoFrogger {
	private BufferedImage img;

    public Ranitas(int posicionX, int posicionY, int velocidadX, int velocidadY, int ancho, int largo, Color color) {
        super(posicionX, posicionY, velocidadX, velocidadY, ancho, largo, color);
        try {
          	 String path = Paths.get(Frogg.class.getClassLoader().getResource("imagenes/ranita.png").toURI()).toString();
          	 this.img = ImageIO.read(new File(path));
        } catch (IOException e) {
             throw new RuntimeException(e);
        } catch (URISyntaxException e) {
          	 e.printStackTrace();
        }
          
    }

    public void dibujarse(Graphics graphics) {
  	   try {
 	         graphics.drawImage(img, getPosicionX(), getPosicionY(), this.getAncho(), this.getLargo(), null);
 	   } catch (Exception e1) {
 	         throw new RuntimeException(e1);
 	   }
    }

}