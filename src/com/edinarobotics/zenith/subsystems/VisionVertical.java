package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.zenith.Components;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionVertical extends PIDSubsystem {

	private static final double P = 0.01;
	private static final double I = 0.0;
	private static final double D = 0.0;
	
	private NetworkTable table;
	private double[] values = new double[2];
	
	private final double DEFAULT_SHOT_X = 237;
	private final double DEFAULT_SHOT_Y = 95;
	
	public VisionVertical() {
		super("VisionVertical", P, I, D);
		setAbsoluteTolerance(10);
	}

	@Override
	protected double returnPIDInput() {
		updateImage();	
		
		if (isOnTarget()) {
			disable();
			Components.getInstance().claw.setTalon(0.0);		
		}
		
		return values[1];
	}

	@Override
	protected void usePIDOutput(double output) {
		Components.getInstance().claw.setTalon(output * 1.10);		
	}

	@Override
	protected void initDefaultCommand() {
		
	}
	
	private void updateImage() {
		table = NetworkTable.getTable("SmartDashboard");
		
		if (table.getNumberArray("BLOBS") != new double[0]) {
			values = table.getNumberArray("BLOBS");
		} else {
			values[0] = DEFAULT_SHOT_X;
			values[1] = DEFAULT_SHOT_Y;
		}			
	}
	
	public boolean isOnTarget(){
		return Math.abs(values[1] - 95) <= 3;
	}

}
