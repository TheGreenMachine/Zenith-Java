package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class RotateXDegreesCommand extends Command {

	private Drivetrain drivetrain;
	private Gyro gyro;
	private double degrees, velocity;
	
	public RotateXDegreesCommand(double degrees, double velocity) {
		super("rotatexdegreescommand");
		drivetrain = Components.getInstance().drivetrain;
		gyro = Components.getInstance().gyro;
		this.degrees = degrees;
		this.velocity = velocity;
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		drivetrain.setDrivetrain(0, velocity);
	}

	@Override
	protected boolean isFinished() {
		return gyro.getAngle() == (gyro.getAngle() + degrees);
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}

}
