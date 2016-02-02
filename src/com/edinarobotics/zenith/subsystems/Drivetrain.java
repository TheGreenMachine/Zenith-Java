package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Command;

public class Drivetrain extends Subsystem1816 {

	private RobotDrive robotDrive;
	private CANTalon topLeft, topRight, middleLeft, middleRight, bottomLeft, bottomRight;
	private double verticalStrafe, rotation;
	

	private boolean lowGear;
	private final double LOW_GEAR_SPEED = 0.75;

	public Drivetrain(int topLeft, int topRight, int middleLeft, int middleRight, 
			int bottomLeft, int bottomRight) {
		this.topLeft = new CANTalon(topLeft);
		this.topRight = new CANTalon(topRight);
		this.middleLeft = new CANTalon(middleLeft);
		this.middleRight = new CANTalon(middleRight);
		this.bottomLeft = new CANTalon(bottomLeft);
		this.bottomRight = new CANTalon(bottomRight);
		
		robotDrive = new RobotDrive(this.topLeft, this.bottomLeft, this.topRight, 
				this.bottomRight);
		
		this.middleLeft.changeControlMode(TalonControlMode.Follower);
		this.middleRight.changeControlMode(TalonControlMode.Follower);
		
		this.middleLeft.set(3);
		this.middleRight.set(7);
	}

	@Override
	public void update() {
		robotDrive.arcadeDrive(-verticalStrafe, -rotation);
	}

	public void setDefaultCommand(Command command) {
		if (getDefaultCommand() != null) {
			super.getDefaultCommand().cancel();
		}
		super.setDefaultCommand(command);
	}

	public void setDrivetrain(double verticalStrafe, double rotation) {
		if (lowGear) {
			verticalStrafe *= LOW_GEAR_SPEED;
			rotation *= LOW_GEAR_SPEED;
		}

		this.verticalStrafe = verticalStrafe;
		this.rotation = rotation;
		update();
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
		update();
	}

	public void setVerticalStrafe(double verticalStrafe) {
		this.verticalStrafe = verticalStrafe;
		update();
	}

	public void setLowGear(boolean lowGear) {
		this.lowGear = lowGear;
		update();
	}

}
