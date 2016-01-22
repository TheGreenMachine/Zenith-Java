package com.edinarobotics.zenith;

import com.edinarobotics.zenith.subsystems.Drivetrain;

public class Components {

	private static Components instance;
	public Drivetrain drivetrain;

	// CAN Constants
		// Drivetrain Constants
		private static final int TOP_LEFT_CANTALON = 1;
		private static final int TOP_RIGHT_CANTALON = 2;
		private static final int BOTTOM_LEFT_CANTALON = 3;
		private static final int BOTTOM_RIGHT_CANTALON = 4;
		// End Drivetrain Constants

	// End CAN Constants

	private Components() {
		drivetrain = new Drivetrain(TOP_LEFT_CANTALON, TOP_RIGHT_CANTALON, 
				BOTTOM_LEFT_CANTALON, BOTTOM_RIGHT_CANTALON);
	}

	/**
	 * Returns the proper instance of Components. This method creates a new
	 * Components object the first time it is called and returns that object for
	 * each subsequent call.
	 *
	 * @return The current instance of Components.
	 */
	public static Components getInstance() {
		if (instance == null) {
			instance = new Components();
		}
		return instance;
	}

}
