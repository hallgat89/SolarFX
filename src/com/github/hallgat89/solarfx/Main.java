package com.github.hallgat89.solarfx;

import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	Group root;
	Scene theScene;
	Canvas solarCanvas;
	GraphicsContext gc;
	List<PlanetVisual> planets;
	int window_x = 1000;
	int window_y = 800;
	Point2D center_point = new Point2D(400, 400);

	final boolean LINETRACE = true;
	final boolean NORMALISATION = true;
	final boolean SIMPLE_DIAMETER = false;
	final boolean SIMPLE_RADIUS = false;

	final int REFRESH_RATE = 50;
	final int MIN_PLANET_SIZE = 10;
	final int MAX_PLANET_SIZE = 50;
	final int DIAMETER_DIVIDER = 1000;
	final int DISTANCE_DIVIDER = 1000;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage theStage) throws Exception {
		theStage.setTitle("Solar system");

		initGraphics(theStage);
		initPlanets();
		mainLoop(gc);

		theStage.show();
	}

	private void initGraphics(Stage theStage) {
		root = new Group();
		theScene = new Scene(root);

		theScene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				window_x = newValue.intValue();
				center_point = new Point2D(window_x / 2, window_y / 2);
				solarCanvas.setWidth(window_x);
			}
		});

		theScene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				window_y = newValue.intValue();
				center_point = new Point2D(window_x / 2, window_y / 2);
				solarCanvas.setHeight(window_y);
			}
		});

		theStage.setScene(theScene);
		solarCanvas = new Canvas(window_x, window_y);
		root.getChildren().add(solarCanvas);
		gc = solarCanvas.getGraphicsContext2D();
	}

	private void initPlanets() {
		planets = new ArrayList<>();

		// PlanetVisual(Image image, int size, int radius, int period)
		planetAdder("01sun.png", Planet.SUN);
		planetAdder("02mercury.png", Planet.MERCURY);
		planetAdder("03venus.png", Planet.VENUS);
		planetAdder("04earth.png", Planet.EARTH);
		planetAdder("06mars.png", Planet.MARS);
		planetAdder("07jupiter.png", Planet.JUPITER);
		planetAdder("08saturn.png", Planet.SATURNUS);
		planetAdder("09uranus.png", Planet.URANUS);
		planetAdder("10neptune.png", Planet.NEPTUNUS);
		planetAdder("11pluto.png", Planet.PLUTO);

	}

	private void planetAdder(String imageRes, Planet type) {

		int diameter = 0;
		int radius = 0;

		if (SIMPLE_DIAMETER) {
			diameter = 20;
		} else {
			diameter = type.getDiameter() / DIAMETER_DIVIDER;
			if (NORMALISATION) {
				diameter = Math.min(diameter, MAX_PLANET_SIZE);
				diameter = Math.max(diameter, MIN_PLANET_SIZE);
			}
		}

		if (SIMPLE_RADIUS) {
			radius = type.getOrder() * 40;
		} else {
			radius = type.getDistanceFromSun() / DISTANCE_DIVIDER;
		}

		planets.add(new PlanetVisual(imageRes, diameter, radius, type.getOrbitTime()));
	}

	private void mainLoop(GraphicsContext gc) {
		new AnimationTimer() {

			final long startNanoTime = System.nanoTime();
			int days = 0;

			@Override
			public void handle(long now) {
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, window_x, window_y);
				// double t = (now - startNanoTime) / SPEED;
				days++;

				for (PlanetVisual v : planets) {

					if (v.getRadius() > 0) {
						Point2D newRelativeLoc = v.rotateADay(days);
						Point2D newLoc = new Point2D(newRelativeLoc.getX() + center_point.getX(),
								newRelativeLoc.getY() + center_point.getY());

						if (LINETRACE) {
							drawTrace(gc,center_point.getX()-v.getRadius() , center_point.getY()-v.getRadius(),v.getRadius()*2);
						}
						gc.drawImage(v.getImage(), newLoc.getX() - v.getSize() / 2, newLoc.getY() - v.getSize() / 2);

					} else {
						gc.drawImage(v.getImage(), center_point.getX() - v.getSize() / 2,
								center_point.getY() - v.getSize() / 2);
					}
				}
				try {
					Thread.sleep(REFRESH_RATE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

	private void drawTrace(GraphicsContext gc, double x,double y, int radius) {
		gc.setStroke(Color.GRAY);
		gc.setLineWidth(1);
		gc.strokeOval(x,y, radius, radius);

	}

}
