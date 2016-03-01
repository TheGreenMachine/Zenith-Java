package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Vision;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class VisionUpdateCommand extends Command {

	private Vision vision;
	private Accelerometer accelerometer;
	private Gyro gyro;
	private double[] acceleration;

	public VisionUpdateCommand() {
		super("visionupdatecommand");
		vision = Components.getInstance().vision;
		accelerometer = Components.getInstance().accelerometer;
		gyro = Components.getInstance().gyro;
		acceleration = new double[2];
		requires(vision);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		acceleration[0] = accelerometer.getX();
		acceleration[1] = accelerometer.getY();
		vision.setAcceleration(acceleration);
		vision.setAngle(gyro.getAngle());
		vision.update();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {

	}

}
