package com.edinarobotics.zenith.subsystems;


import com.edinarobotics.utils.subsystems.Subsystem1816;
import com.edinarobotics.zenith.Components;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision extends Subsystem1816 {
	
	private final double SHOT_X = 237;
//	private final double SHOT_Y = 181; //Range from 2ft from wall to inches off the ramp
	private final double SHOT_Y = 95; //Range from Lots of feet to 2ft 
	private final double OFFSET = -30;
	public boolean offsetting = false;
	
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
		}
		
	}
	
	public void updateImage(){
		table = NetworkTable.getTable("SmartDashboard");
		
		if (table.getNumberArray("BLOBS") != new double[0]) {
			values = table.getNumberArray("BLOBS");
		} else {
			values[0] = SHOT_X;
			values[1] = SHOT_Y;
		}	
        
	}
	
	public void correctAim(){
		
		Drivetrain drivetrain = Components.getInstance().drivetrain;
		Claw claw = Components.getInstance().claw;
		
		double shotYTarget;
		
		if (offsetting)
			shotYTarget = SHOT_Y + OFFSET;
		else
			shotYTarget = SHOT_Y;

		try { //BANG BANG
			
			if (Math.abs(values[0] - SHOT_X) <= 8)  //PID CALCULATIONS
				drivetrain.setDrivetrain(0.0, 0.0);
			else if(values[0] < SHOT_X - 8){
				if(values[0] < SHOT_X - 30)
					drivetrain.setDrivetrain(0.0, -0.25);
				else
					drivetrain.setDrivetrain(0.0, -0.15);
			}
			else if(values[0] > SHOT_X + 8){
				if(values[0] > SHOT_X + 30)
					drivetrain.setDrivetrain(0.0, 0.25);
				else
					drivetrain.setDrivetrain(0.0, 0.15);
			}
			
			if (Math.abs(values[1] - shotYTarget) <= 8) 
				claw.setTalon(0.0);
			else if(values[1] < shotYTarget - 8){
				if(values[1] < shotYTarget - 30)
					claw.setTalon(0.3);
				else
					claw.setTalon(0.2);
			}
			else if(values[1] > shotYTarget + 8){
				if(values[1] > shotYTarget + 30)
					claw.setTalon(-0.3);
				else
					claw.setTalon(-0.2);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// check to see if potentiometer is less than number (-30) / check to see algorithm value
		// add an offset to the target goal
			// offset can be decided by either algorithm or constant value
		// aim towards the new offset
		// when you reach the new offset, SHOOT!
		// end task
		
		if(isOnTarget()) {
			if(claw.getCurrentPosition() - 641 < -225) {
				offsetting = true;
			}
		}
		
		
	}
	
	public boolean isOnTarget(){
		if(!offsetting) {
			return Math.abs(values[0] - SHOT_X) <= 8 && Math.abs(values[1] - SHOT_Y) <= 8;
		}
		else
			return Math.abs(values[0] - SHOT_X) <= 8 && Math.abs(values[1] - SHOT_Y - OFFSET) <= 8;
	}
	
	public void setActive(boolean active) { 
		this.active = active;
	}
	
	public void end() {
		Components.getInstance().drivetrain.setDrivetrain(0.0, 0.0);
		Components.getInstance().claw.setTalon(0.0);
	}

}
