package com.edinarobotics.zenith;

import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Compressor;

public class Components {

	private static Components instance;
	public Drivetrain drivetrain;
	public Compressor compressor;

	// CAN Constants
		// Drivetrain Constants
		private static final int TOP_LEFT_CANTALON = 3;
		private static final int TOP_RIGHT_CANTALON = 7;
		private static final int MIDDLE_LEFT_CANTALON = 2;
		private static final int MIDDLE_RIGHT_CANTALON = 6;
		private static final int BOTTOM_LEFT_CANTALON = 1;
		private static final int BOTTOM_RIGHT_CANTALON = 5;
		
		private static final int SHIFTING_PCM_ID_1 = 0;
		private static final int SHIFTING_PCM_ID_2 = 1;
		// End Drivetrain Constants
		
		//Potentiometer Constants
		//private static final int POTENTIOMETER_CHANNEL = 1;
		//private static final int POTENTIOMETER_RANGE = 135;
		//private static final int POTENTIOMETER_OFFSET = 0;
		//End Potentiometer Constants
		
		//Claw Constants
		//private static final int CLAW_CANTALON = 4;
		//End Claw Constants
		
		private static final int PCM_NODE_ID = 10;
		
	// End CAN Constants

	private Components() {
		drivetrain = new Drivetrain(TOP_LEFT_CANTALON, TOP_RIGHT_CANTALON, 
				MIDDLE_LEFT_CANTALON, MIDDLE_RIGHT_CANTALON, BOTTOM_LEFT_CANTALON, 
				BOTTOM_RIGHT_CANTALON, SHIFTING_PCM_ID_1, SHIFTING_PCM_ID_2);
		
		compressor = new Compressor(PCM_NODE_ID);
		compressor.start();
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
