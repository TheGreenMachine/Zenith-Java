package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.zenith.Components;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionHorizontal extends PIDSubsystem {
	
	private static final double P = 0.001;
	private static final double I = 0.0;
	private static final double D = 0.0;
	
	private NetworkTable table;
	private double[] values = new double[2];
		
	private final double DEFAULT_SHOT_X = 237;
	private final double DEFAULT_SHOT_Y = 95;
	
	public VisionHorizontal() {
		super("VisionHorizontal", P, I, D);
		setAbsoluteTolerance(10);
	}
	
	@Override
	protected double returnPIDInput() {
		updateImage();	
		return values[0];
	}

	@Override
	protected void usePIDOutput(double output) {
			Components.getInstance().drivetrain.setDrivetrain(0.0, output /** 3.0*/);

		System.out.println("PIDOutput: " + output);
		
		if (onTarget()) {
			disable();
			Components.getInstance().drivetrain.setDrivetrain(0.0, 0.0);
		}
		
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
		
//		System.out.println("Updating image!");
	}
	
//	public boolean isOnTarget(){
//		return Math.abs(values[0] - 237) <= 5;
//	}

}
