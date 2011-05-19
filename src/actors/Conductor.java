package actors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import other.ChargedParticle;
import other.DataHandler;
import other.Vector;

public class Conductor implements ChargedParticle {
	static final int DISTANCE_INFLUENCED = 40;
	final double[][] emMap;
	final BufferedImage shadow;
	final double charge;
	int xpos, ypos;

	public Conductor(int xpos, int ypos, int width, int height, double charge) {
		this.xpos = xpos;
		this.ypos = ypos;
		this.charge = charge;

		emMap = new double[width+2*DISTANCE_INFLUENCED][height+2*DISTANCE_INFLUENCED];
		for(int x = 0; x < emMap.length; x++) {
			for(int y = 0; y < emMap[x].length; y++) {
				if(x >= DISTANCE_INFLUENCED && x < width+DISTANCE_INFLUENCED
						&& y >= DISTANCE_INFLUENCED && y < height+DISTANCE_INFLUENCED)
					emMap[x][y] = charge; 
				else {
					double xdiff;
					if(x < DISTANCE_INFLUENCED)
						xdiff = DISTANCE_INFLUENCED - x;
					else if (x >= width+DISTANCE_INFLUENCED)
						xdiff = x - width - DISTANCE_INFLUENCED;
					else
						xdiff = 0;

					double ydiff;
					if(y < DISTANCE_INFLUENCED)
						ydiff = DISTANCE_INFLUENCED - y;
					else if (y >= height+DISTANCE_INFLUENCED)
						ydiff = y - height - DISTANCE_INFLUENCED;
					else
						ydiff = 0;

					double diff = Math.sqrt(xdiff*xdiff + ydiff*ydiff);
					if(diff > DISTANCE_INFLUENCED)
						continue;

					emMap[x][y] = DataHandler.calcEMForce(charge, diff, DISTANCE_INFLUENCED);
				}
			}
		}

		shadow = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)shadow.getGraphics();
		g.setColor(new Color(0f, 0f, 0f, 0.5f));
		g.fillRect(0, 0, width, height);
	}

	@Override
	public double getCharge() {
		return charge;
	}
	@Override
	public double[][] getEMMap() {
		return emMap;
	}
	@Override
	public BufferedImage getShadow() {
		return shadow;
	}

	@Override
	public int getX() {
		return xpos;
	}
	@Override
	public int getY() {
		return ypos;
	}

	@Override
	public Vector getLocation() {
		return new Vector(xpos, ypos);
	}
	@Override
	public Vector getVelocity() {
		return null;
	}

	@Override
	public void act(double[][] map) {
		//TODO ???
	}
}
