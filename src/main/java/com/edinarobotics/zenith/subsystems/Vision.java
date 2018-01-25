package com.edinarobotics.zenith.subsystems;


import com.edinarobotics.utils.subsystems.Subsystem1816;
import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw.ClawSpeed;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision extends Subsystem1816 {
	
	private final double SHOT_X = 210;
	private final double SHOT_Y = 95; 
	private double zero = 860;
	private double currentPosition = 660;
	public boolean offsetting = false;
	public boolean lost = false;
	
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
			try {
				updateImage();
				correctAim();
			} catch (Exception e) {
				
			}
		}
		
	}
	
	public void updateImage(){
/*
		try {
			table = NetworkTable.getTable("SmartDashboard");
			
			if (table.getNumberArray("BLOBS") != null) {

				values = table.getNumberArray("BLOBS", defaultValue);
				
				System.out.println("X off by: " + (values[0] - SHOT_X));
				System.out.println("Y off by: " + (values[1] - SHOT_Y));
				
			} else {
				values[0] = SHOT_X;
				values[1] = SHOT_Y;
				lost = true;
			}	
			
		} catch (Exception e) {

		}
 */       
	}
	
	public void correctAim(){
		Drivetrain drivetrain = Components.getInstance().drivetrain;
		Claw claw = Components.getInstance().claw;
		
		int currentX = (int) values[0];

		if (values != null) {
			if (Math.abs(currentX - SHOT_X) <= 3) {
				drivetrain.setDrivetrain(0.0, 0.0);
			} else if(currentX < SHOT_X - 3){
				if(currentX < SHOT_X - 25) {
					drivetrain.setDrivetrain(0.0, -0.35);
				} else {
					drivetrain.setDrivetrainPower(0.35, 0.0);
					
					if (!drivetrain.getBrakeMode()) {
						drivetrain.setBrakeMode(true);
					}
				}
			} else if(currentX > SHOT_X + 3){
				if(currentX > SHOT_X + 25) {
					drivetrain.setDrivetrain(0.0, 0.35);
				} else {
					drivetrain.setDrivetrainPower(-0.35, 0.0);
					
					if (!drivetrain.getBrakeMode()) {
						drivetrain.setBrakeMode(true);
					}
				}
			}
			
			if (claw.getCurrentPosition() > (708 + 1)) {
				claw.moveUp(ClawSpeed.VISION_SLOW);
			} else if (claw.getCurrentPosition() < (708 - 1)) {
				claw.moveDown(ClawSpeed.VISION_SLOW);
			} else {
				claw.stopClaw();
			}
			
		}
		
		/*
		 * check to see if potentiometer is less than number (-225)
		 * add a constant value for an offset to the target goal 
		 * aim towards the new offset
		 * when you reach the new offset, SHOOT!
		 * -> end task
		 */

	}
	
	public boolean isOnTarget(){
		try {
			return Math.abs(values[0] - SHOT_X) <= 3 && Math.abs(Components.getInstance().claw.getCurrentPosition() - 708) <= 1;
		} catch (Exception e) { }
		
		lost = true;
		return true;
	}
	
	public double getDistanceFromCenter() {
		try {
			return values[0] - SHOT_X;
		} catch (Exception e) { }
		
		return -9999;
	}
	
	public void setActive(boolean active) { 
		this.active = active;
	}
	
	public void end() {
		Components.getInstance().drivetrain.setDrivetrain(0.0, 0.0);
		Components.getInstance().claw.stopClaw();
	}

	public double getOffset(){
		return 10;
	}
	
}
