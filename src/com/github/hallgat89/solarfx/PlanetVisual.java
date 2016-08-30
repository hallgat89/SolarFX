package com.github.hallgat89.solarfx;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class PlanetVisual {

	public PlanetVisual(String imageURL, int size, int radius, double period) {
		super();

		this.image=new Image(imageURL,size,size,false,false);
		this.size = size;
		this.radius = radius;
		this.period = period;
		
		System.out.println(toString());
	}
	
	@Override
	public String toString() {
		return "PlanetVisual [image=" + image + ", size=" + size + ", radius=" + radius + ", period=" + period + "]";
	}

	Image image;
	int size;
	int radius;
	double period;
	
	public Image getImage() {
		return image;
	}
	public int getSize() {
		return size;
	}
	public int getRadius() {
		return radius;
	}
	public double getPeriod() {
		return period;
	}
	
	public double getAngularSpeed()
	{
		return ((2*Math.PI)/period); //1/nap
	}
	
	public double getAnglePerDay()
	{
		return 360/period;
	}
	
	public Point2D getRelativeRotatedPosition(double angle)
	{
		//kiindulópont: X=0, Y=radius (nap fölött)
		
		angle=angle%360;
		// X'=X cos(a) - Y*sin(a)
		double newX=-radius*Math.sin(angle);
		
		// Y'=X*sin(a)+Y*cos(a)
		double newY=radius*Math.cos(angle);
		
		return new Point2D(newX,newY);
	}

	
	public Point2D rotateADay(int days)
	{
		return getRelativeRotatedPosition(getAnglePerDay()*days);
	}
}
