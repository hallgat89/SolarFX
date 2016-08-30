package com.github.hallgat89.solarfx;

public enum Planet {

	SUN(0,"Sun", 0, 0,1391400), MERCURY(1,"Mercury", 88, 58,4878), VENUS(2,"Venus", 224.7, 108,12104), EARTH(3,"Earth", 365.2, 150,12760), MARS(4,"Mars",
			687, 228,6787), JUPITER(5,"Jupiter", 4332, 778,139822), SATURNUS(6,"Saturn", 10760,
					1433,120500), URANUS(7,"Uranus", 30700, 2872,51120), NEPTUNUS(8,"Neptune", 60200, 4495,49530 ), PLUTO(9,"Pluto", 90600, 5906,2301);

	private Planet(int order,String name, double orbit, int distance,int diameter) {
		this.order=order;
		this.name = name;
		this.distanceFromSun = distance;
		this.orbitTime = orbit;
		this.diameter=diameter;
	}

	public String getName() {
		return name;
	}

	public Double getOrbitTime() {
		return orbitTime;
	}

	// Km
	public int getDistanceFromSun() {
		return distanceFromSun*1000;
	}
	
	// KM
	public int getDiameter() {
		return diameter;
	}
	
	public int getOrder()
	{
		return order;
	}

	int order;
	String name;
	Double orbitTime;
	int distanceFromSun;
	int diameter;

}
