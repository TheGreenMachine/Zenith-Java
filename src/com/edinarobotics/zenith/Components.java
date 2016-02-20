package com.edinarobotics.zenith;

import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Collector;
import com.edinarobotics.zenith.subsystems.Drivetrain;
import com.edinarobotics.zenith.subsystems.Shooter;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Components {

	private static Components instance;
	public Drivetrain drivetrain;
	public Compressor compressor;
	public Claw claw;
	public Shooter shooter;
	public Collector collector;
	public Gyro gyro;

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
		private static final int COLLECTOR_CANTALON = 8;
		//End Collector Constants
	// End CAN Constants
		
	//Potentiometer Constants
	private static final int POTENTIOMETER = 0;
	//End Potentiometer Constants
	
	//Pneumatic Constants	
		//Drivetrain Constants
		private static final int SHIFTING_PCM_ID_1 = 0;
		private static final int SHIFTING_PCM_ID_2 = 1;
		//End Drivetrain Constants
		
		//Shooter Constants
		private static final int SHOOTER_PCM_1 = 2;
		private static final int SHOOTER_PCM_2 = 3;
		private static final int SHOOTER_PCM_3 = 4;
		
		private static final int PRESSURE_SENSOR = 1;
		//End Shooter Constants
		
		//Pneumatic Control Module
		private static final int PCM_NODE_ID = 10;
		//End Pneumatic Control Module
		
	//End Pneumatic Constants
		
	
	private Components() {
		drivetrain = new Drivetrain(TOP_LEFT_CANTALON, TOP_RIGHT_CANTALON, 
				MIDDLE_LEFT_CANTALON, MIDDLE_RIGHT_CANTALON, BOTTOM_LEFT_CANTALON, 
				BOTTOM_RIGHT_CANTALON, PCM_NODE_ID, SHIFTING_PCM_ID_1, SHIFTING_PCM_ID_2);
		claw = new Claw(CLAW_CANTALON, POTENTIOMETER);
		shooter = new Shooter(PCM_NODE_ID, SHOOTER_PCM_1, SHOOTER_PCM_2, SHOOTER_PCM_3, PRESSURE_SENSOR);
		collector = new Collector(COLLECTOR_CANTALON);

		compressor = new Compressor(PCM_NODE_ID);
		compressor.start();	
		
		gyro = new ADXRS450_Gyro();
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
