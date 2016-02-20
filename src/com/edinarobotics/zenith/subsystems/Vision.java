package com.edinarobotics.zenith.subsystems;



import com.edinarobotics.utils.subsystems.Subsystem1816;
import com.edinarobotics.zenith.Components;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision extends Subsystem1816 {
	
	private NetworkTable table;
	private double[] areas, xValues, yValues;
	private double[] targetCords;
	private boolean canShoot = false;
	private final double SHOT_X = 120;
	private final double SHOT_Y = 300;
	private final double X_TOLERANCE = 10;
	private final double Y_TOLERANCE = 10;
	private final double X_SPEED_COEFFICIENT = 0.5;
	private final double Y_TARGET_COEFFICIENT = 1.0;
	private final double X_MIN_SPEED = 0.05;
	private final double Y_MIN_SPEED = 0.05;
	
	public Vision() {
		table = NetworkTable.getTable("GRIP/myContourReport");
	}
	
	@Override
	public void update() {
		double[] defaultValue = new double[0];
		areas = table.getNumberArray("area", defaultValue);
		xValues = table.getNumberArray("centerX", defaultValue);
		yValues = table.getNumberArray("centerY", defaultValue);
		findTarget();
		
		System.out.print("areas: ");
		for(double area : areas)
			System.out.print(area);
		System.out.println();
		
		System.out.print("x: ");
		for(double x : xValues)
			System.out.print(x);
		System.out.println();
		
		System.out.print("y: ");
		for(double y : yValues)
			System.out.print(y);
		System.out.println();
	}
	
	public void setDefaultCommand(Command command) {
		if (getDefaultCommand() != null) {
			super.getDefaultCommand().cancel();
		}
		super.setDefaultCommand(command);
	}
	
	private double[] findTarget() {
		double targetArea = 0.0;
		targetCords = new double[2];
        for (int i = 0; i < areas.length; i++) {
            if (areas[i] > targetArea) {
                targetArea = areas[i];
                targetCords[0] = xValues[i];
                targetCords[1] = yValues[i];
            }
        }
        
        if(targetArea != 0.0)
        	canShoot = true;
        else
        	canShoot = false;
        
        return targetCords;
	}
	
	public boolean canShoot() {
		return canShoot;
	}
	
	private double[] calculateXYDistance() {
		double[] vector = new double[2];
		update();
		
		vector[0] = -1.0 * (targetCords[0] - SHOT_X);
		vector[1] = -1.0 * (targetCords[1] - SHOT_Y);
		
		if(Math.abs(vector[0]) < X_TOLERANCE)
			vector[0] = 0.0;
		if(Math.abs(vector[1]) < Y_TOLERANCE)
			vector[1] = 0.0;
		
		return vector;
	}
	
	private double[] calculateDirection() {
		double[] direction = new double[2];
		update();
		if(canShoot) {
			if(calculateXYDistance()[0] > 0.0)
				direction[0] = 1;
			else if(calculateXYDistance()[0] < 0.0)
				direction[0] = -1;
			else
				direction[0] = 0;
			
			if(calculateXYDistance()[1] > 0.0)
				direction[1] = 1;
			else if(calculateXYDistance()[1] < 0.0)
				direction[1] = -1;
			else
				direction[1] = 0;
		}
		else {
			direction[0] = 0;
			direction[1] = 0;
		}
		
		return direction;
	}
	
	public double calculateXSpeed() {
		double speed = (calculateXYDistance()[0] / 380) * X_SPEED_COEFFICIENT;
		if(Math.abs(speed) > X_MIN_SPEED) {
			if(speed > 0)
				speed = X_MIN_SPEED;
			else
				speed = -X_MIN_SPEED;
		}
		return speed;
	}
	
	public double calculateYSpeed() {
		double speed = (calculateXYDistance()[0] / 240) * Y_TARGET_COEFFICIENT;
		if(Math.abs(speed) > Y_MIN_SPEED) {
			if(speed > 0)
				speed = Y_MIN_SPEED;
			else
				speed = -Y_MIN_SPEED;
		}
		return speed;
	}
	
}
