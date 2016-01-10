package com.edinarobotics.zenith;

public class Components {
	
	private static Components instance;
	
	// CAN Constants
			// Drivetrain Constants
			
			// End Drivetrain Constants
	
	// End CAN Constants
	
	
	private Components() {
		
	}
	
	public static Components getInstance() {
		if (instance == null) {
			instance = new Components();
		}
		return instance;
	}

}
