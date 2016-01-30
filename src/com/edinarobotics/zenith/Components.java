package com.edinarobotics.zenith;

import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Components {

	private static Components instance;
	public Drivetrain drivetrain;
	public AnalogPotentiometer potentiometer;
	public Claw claw;

	// CAN Constants
		// Drivetrain Constants
		private static final int TOP_LEFT_CANTALON = 3;
		private static final int TOP_RIGHT_CANTALON = 7;
		private static final int MIDDLE_LEFT_CANTALON = 2;
		private static final int MIDDLE_RIGHT_CANTALON = 6;
		private static final int BOTTOM_LEFT_CANTALON = 1;
		private static final int BOTTOM_RIGHT_CANTALON = 5;
		// End Drivetrain Constants
		
		//Potentiometer Constants
		private static final int POTENTIOMETER_CHANNEL = 1;
		private static final int POTENTIOMETER_RANGE = 135;
		private static final int POTENTIOMETER_OFFSET = 0;
		//End Potentiometer Constants
		
		//Claw Constants
		private static final int CLAW_CANTALON = 4;
		//End Claw Constants
		
	// End CAN Constants

	private Components() {
		drivetrain = new Drivetrain(TOP_LEFT_CANTALON, TOP_RIGHT_CANTALON, 
				MIDDLE_LEFT_CANTALON, MIDDLE_RIGHT_CANTALON, BOTTOM_LEFT_CANTALON, 
				BOTTOM_RIGHT_CANTALON);
		potentiometer = new AnalogPotentiometer(POTENTIOMETER_CHANNEL, 
				POTENTIOMETER_RANGE, POTENTIOMETER_OFFSET);
		claw = new Claw(CLAW_CANTALON);
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
