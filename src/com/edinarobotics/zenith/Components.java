package com.edinarobotics.zenith;

import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Collector;
import com.edinarobotics.zenith.subsystems.Drivetrain;
import com.edinarobotics.zenith.subsystems.Shooter;
import com.edinarobotics.zenith.subsystems.Vision;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.SPI;

public class Components {

	private static Components instance = null;
	public Drivetrain drivetrain;
	public Claw claw;
	public Shooter shooter;
	public Collector collector;
	public AHRS navX;
	public Compressor compressor;
	public Vision vision;

	// CAN Constants
		// Drivetrain Constants
		private static final int TOP_LEFT_CANTALON = 3;
		private static final int TOP_RIGHT_CANTALON = 7;
		private static final int MIDDLE_LEFT_CANTALON = 2;
		private static final int MIDDLE_RIGHT_CANTALON = 6;
		private static final int BOTTOM_LEFT_CANTALON = 1;
		private static final int BOTTOM_RIGHT_CANTALON = 5;
		// End Drivetrain Constants
		
		//Claw Constants
		private static final int CLAW_CANTALON = 4;
		//End Claw Constants
		
		//Collector Constants
		private static final int FRONT_COLLECTOR_CANTALON = 8;
		private static final int SIDE_COLLECTOR_CANTALON = 12;
		//End Collector Constants
		
	// End CAN Constants //188
		
	//Analog Constants
	private static final int POTENTIOMETER = 0;
	//End Analog Constants
	
	//DIO Constants
	private static final int LIMIT_SWITCH = 0;
	//End DIO Constants
	
	//Pneumatic Constants	
		//Drivetrain Constants
		private static final int SHIFTING_PCM_ID_1 = 0;
		private static final int SHIFTING_PCM_ID_2 = 1;
		//End Drivetrain Constants
		
		//Shooter Constants
		private static final int SHOOTER_PCM_1 = 2;
		private static final int SHOOTER_PCM_2 = 3;
		private static final int LOW_POWER_PCM = 4;
		private static final int BRAKE_PCM = 5;
		//End Shooter Constants
		
		//Pneumatic Control Module
		private static final int PCM_NODE_ID = 10;
		//End Pneumatic Control Module
		
	//End Pneumatic Constants
		
	
	private Components() {
		drivetrain = new Drivetrain(TOP_LEFT_CANTALON, TOP_RIGHT_CANTALON, 
				MIDDLE_LEFT_CANTALON, MIDDLE_RIGHT_CANTALON, BOTTOM_LEFT_CANTALON, 
				BOTTOM_RIGHT_CANTALON, PCM_NODE_ID, SHIFTING_PCM_ID_1, SHIFTING_PCM_ID_2);
		claw = new Claw(CLAW_CANTALON, POTENTIOMETER, PCM_NODE_ID, BRAKE_PCM, LIMIT_SWITCH);
		shooter = new Shooter(PCM_NODE_ID, SHOOTER_PCM_1, SHOOTER_PCM_2);
		collector = new Collector(FRONT_COLLECTOR_CANTALON, SIDE_COLLECTOR_CANTALON);
		vision = new Vision();
		
		//visionHorizontal = new VisionHorizontal();
		//visionVertical = new VisionVertical();
		
		compressor = new Compressor(PCM_NODE_ID);
		compressor.start();	
		
		navX = new AHRS(SPI.Port.kMXP); 
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
