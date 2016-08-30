package com.github.hallgat89.solarfx;

public enum Planet {
	
	SUN("Sun",0,0),MERCURY("Mercury",88,58),VENUS("Venus",224.7,108),EARTH("Earth",365.2,150),MARS("Mars",687,228),JUPITER("Jupiter",4332,778),SATURNUS("Saturn",10760,1429),URANUS("Uranus",30700,2871),NEPTUNUS("Neptune",60200,4504),PLUTO("Pluto",90600,5913);
	
	private Planet(String name,double orbit, int distance)
	{
		this.name=name;
		this.distanceFromSun=distance;
		this.orbitTime=orbit;
	}
	
	String name;
	Double orbitTime;
	int distanceFromSun;

}
