package com.edinarobotics.zenith.subsystems;


import com.edinarobotics.utils.subsystems.Subsystem1816;
import com.edinarobotics.zenith.Components;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision extends Subsystem1816 {
	
	private final double SHOT_X = 237; //need to test to find actual xCenter
//	private final double SHOT_Y = 181; //Range from 2ft from wall to inches off the ramp
	private final double SHOT_Y = 95; //Range from Lots of feet to 2ft 
	
	private double offset = 0;
	
	private NetworkTable table;
	private double[] values;
	double[] defaultValue = {SHOT_X, SHOT_Y};
	
	private boolean active = false;
	
	public Vision() {
		table = NetworkTable.getTable("SmartDashboard");	
	}
	
	
	@Override
	public void update() {
		
		if (active) {
			
			updateImage();
			
			correctAim();
			
			checkOffset();
			
		}
		
	}
	
	public void updateImage(){
		
		table = NetworkTable.getTable("SmartDashboard");
		
		if (table.getNumberArray("BLOBS") != new double[0]) {
			values = table.getNumberArray("BLOBS");
		} else {
			values = defaultValue;
		}
        
	}
	
	public void correctAim(){
		
		Drivetrain drivetrain = Components.getInstance().drivetrain;
		Claw claw = Components.getInstance().claw;
		
		
//			if (values[0] < SHOT_X - 40)
//				drivetrain.setDrivetrain(0.0, -0.3);
//			else if(values[0] < SHOT_X - 70 /*tolerance of not 5*/ )
//				drivetrain.setDrivetrain(0.0, -0.6);
//			else if (values[0] > SHOT_X + 40)
//				drivetrain.setDrivetrain(0.0, 0.3);
//			else if(values[0] > SHOT_X + 70 /*tolerance of not 5*/ )
//				drivetrain.setDrivetrain(0.0, 0.6);
//			else
//				drivetrain.setDrivetrain(0.0, 0.0);
		
		if(values[0] < SHOT_X - 10 /*tolerance of not 5*/ )
			drivetrain.setDrivetrain(0.0, -0.35);
		else if(values[0] < SHOT_X - 40)
			drivetrain.setDrivetrain(0.0,-0.45);
		else if(values[0] > SHOT_X + 10 /*tolerance of not 5*/ )
			drivetrain.setDrivetrain(0.0, 0.35);
		else if(values[0] > SHOT_X + 40)
			drivetrain.setDrivetrain(0.0, 0.45);
		else
			drivetrain.setDrivetrain(0.0, 0.0);
		
		
//			if (values[1] < SHOT_Y - 15)
//				claw.setTalon(0.2);
//			else if(values[1] < SHOT_Y - 50/*tolerance of 5*/)
//				claw.setTalon(0.5);
//			else if (values[1] > SHOT_Y + 15) 
//				claw.setTalon(-0.2);
//			else if(values[1] > SHOT_Y + 50/*tolerance of 5*/)
//				claw.setTalon(-0.5);
//			else
//				claw.setTalon(0.0);
		
		if(values[1] < SHOT_Y + offset - 10/*tolerance of 5*/)
			claw.setTalon(0.3);
		else if(values[1] < SHOT_Y + offset - 40)
			claw.setTalon(0.4);
		else if(values[1] > SHOT_Y + offset + 10/*tolerance of 5*/)
			claw.setTalon(-0.3);
		else if(values[1] > SHOT_Y + offset + 40)
			claw.setTalon(-0.4);
		else
			claw.setTalon(0.0);
		
	}
	
	public void checkOffset(){
		if(isOnTarget()){
			offset = getOffset();
		}
	}
	
	public double getOffset(){
		Claw claw = Components.getInstance().claw;
		return (claw.getCurrentPosition() - 180) * 0.1; //180 is zero of pot 	// 0.1 is experimental multiplier
	}
	
	public boolean isOnTarget(){
		return Math.abs(values[0] - SHOT_X) <= 8 && Math.abs(values[1] - SHOT_Y + offset) <= 8;
	}
	
	public void setActive(boolean active) { 
		this.active = active;
	}

}
