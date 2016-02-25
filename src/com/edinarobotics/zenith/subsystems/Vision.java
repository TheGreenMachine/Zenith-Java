package com.edinarobotics.zenith.subsystems;


import com.edinarobotics.utils.subsystems.Subsystem1816;
import com.edinarobotics.zenith.Components;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision extends Subsystem1816 {
	
	private NetworkTable table;
	private Accelerometer accelerometer;
	private Gyro gyro;
	private double[] areas, xValues, yValues;
	private double[] targetCords;
	private boolean canShoot = false;
	private final double SHOT_X = 120;
	private double shotY = 190;
	private final double X_TOLERANCE = 10;
	private final double Y_TOLERANCE = 10;
	private final double X_SPEED_COEFFICIENT = 0.5;
	private final double Y_SPEED_COEFFICIENT = 1.0;
	private final double X_MIN_SPEED = 0.05;
	private final double Y_MIN_SPEED = 0.05;
	private final double Z_CALC_FACTOR = .3;
	private final double GOAL_HEIGHT = 60;
	private double[] absolutePosition;
	private double[] relativeVelocity;
	private double[] relativePosition;
	private double rotation;
	
	public Vision() {
		table = NetworkTable.getTable("GRIP/myContourReport");
		accelerometer = Components.getInstance().accelerometer;
		gyro = Components.getInstance().gyro;
		absolutePosition = new double[2];
		relativeVelocity = new double[2];
		relativePosition = new double[2];
	}
	
	@Override
	public void update() {
		
		rotation = getRelativeAngle();
		
		relativeVelocity[0] += (accelerometer.getX() * 32.174 * 12) * 0.02;
		relativeVelocity[1] += (accelerometer.getY() * 32.174 * 12) * 0.02;
		relativePosition[0] += relativeVelocity[0] * 0.02;
		relativePosition[1] += relativeVelocity[1] * 0.02;
		absolutePosition[0] += (relativePosition[0] * Math.cos(rotation)) - (relativePosition[1] * Math.sin(rotation));
		absolutePosition[1] += (relativePosition[0] * Math.sin(rotation)) + (relativePosition[1] * Math.cos(rotation));
		
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
	
	public enum GoalPosition {

		LEFT(100, 100), 
		MIDDLE(300, 200), 
		RIGHT(500, 100);

		private double x, y;

		GoalPosition(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		public double[] getPosition() {
			double[] position = new double[2];
			position[0] = x;
			position[1] = y;
			return position;
		}

	}
	
	public void setPosition(double[] position) {
		this.absolutePosition = position;
	}
	
	public double[] getPosition() {
		return absolutePosition;
	}
	
	public double getRelativeAngle() {
		return gyro.getAngle() % 360;
	}
	
	private double getZ(GoalPosition goal) {
		update();
		return Math.sqrt(Math.pow(goal.getPosition()[0] - absolutePosition[0], 2) + 
				Math.pow(goal.getPosition()[1] - absolutePosition[1], 2));
	}
	
	private GoalPosition getNearestGoal() {
		double[] goals = new double[3];
		goals[0]= getZ(GoalPosition.LEFT);
		goals[1] = getZ(GoalPosition.MIDDLE);
		goals[2] = getZ(GoalPosition.RIGHT);
		double value = Integer.MAX_VALUE;
		int closest = 0;
		
		for(int i = 0; i < goals.length; i++) {
			if(goals[i] < value) {
				value = goals[i];
				closest = i;
			}
		}
		
		switch(closest) {
			case 0: return GoalPosition.LEFT;
			case 1: return GoalPosition.MIDDLE;
			case 2: return GoalPosition.RIGHT;
			default: return GoalPosition.MIDDLE;
		}
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
	
	private void calculateY(GoalPosition goal) {
		update();
		shotY = getZ(goal) * Z_CALC_FACTOR;
	}
	
	private double getGoalAngle(GoalPosition goal) {
		update();
		double[] relativeGoalPosition = new double[2];
		relativeGoalPosition[0] = goal.getPosition()[0] - absolutePosition[0];
		relativeGoalPosition[1] = goal.getPosition()[1] - absolutePosition[1];
		double thetaRad = Math.atan2(relativeGoalPosition[1], relativeGoalPosition[0]);
		double thetaDeg = (thetaRad / Math.PI * 180); // + (thetaRad > 0 ? 0 : 360);
		double thetaBearing = 0;
		if(thetaDeg > 0) {
			if(thetaDeg < 90) {
				thetaBearing = 90 - thetaDeg;
			}
			else if(thetaDeg > 90) {
				thetaBearing = -1 * (thetaDeg - 90);
			}
			else if(thetaDeg == 90) {
				thetaBearing = 0;
			}
		}
		else if(thetaDeg < 0) {
			if(thetaDeg > -90) {
				thetaBearing = -thetaDeg + 90;
			}
			else if(thetaDeg < -90) {
				thetaBearing = 180 - (-1 * (thetaDeg - 90));
			}
			else if(thetaDeg == -90) {
				thetaBearing = 180;
			}
		}
		else if(thetaDeg == 0) {
			thetaBearing = 90;
		}
		return thetaBearing;
	}
	
	private double[] calculateXYDistance() {
		double[] vector = new double[2];
		calculateY(getNearestGoal());
		
		vector[0] = targetCords[0] - SHOT_X;
		vector[1] = targetCords[1] - shotY;
		
		if(Math.abs(vector[0]) < X_TOLERANCE)
			vector[0] = 0.0;
		if(Math.abs(vector[1]) < Y_TOLERANCE)
			vector[1] = 0.0;
		
		return vector;
	}
	
	private double[] calculateDirection() {
		update();
		double[] direction = new double[2];
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
		double speed = 0;
		if(canShoot) {
			speed = (calculateXYDistance()[0] / 380) * X_SPEED_COEFFICIENT;
		}
		else {
			speed = (getGoalAngle(getNearestGoal()) / 180) * X_SPEED_COEFFICIENT;
		}
		if(Math.abs(speed) > X_MIN_SPEED) {
			if(speed > 0)
				speed = X_MIN_SPEED;
			else
				speed = -X_MIN_SPEED;
		}
		return speed;
	}
	
	public double calculateYSpeed() {
		double speed = (calculateXYDistance()[0] / 240) * Y_SPEED_COEFFICIENT;
		if(Math.abs(speed) > Y_MIN_SPEED) {
			if(speed > 0)
				speed = Y_MIN_SPEED;
			else
				speed = -Y_MIN_SPEED;
		}
		return speed;
	}
	
}
