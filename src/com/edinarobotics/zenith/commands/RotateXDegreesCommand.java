package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;

public class RotateXDegreesCommand extends Command {
	
	private Drivetrain drivetrain;
	private AHRS navX;
	
	private int degrees;
	private int endingDegree;
	
	public RotateXDegreesCommand(int degrees) {
		super("rotatexdegreescommand");
		drivetrain = Components.getInstance().drivetrain;
		navX = Components.getInstance().navX;
		this.degrees = degrees;
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		endingDegree = (int) navX.getAngle() + degrees;
	}

	@Override
	protected void execute() {
		if (degrees > 0) {
			if (Math.abs(endingDegree - navX.getAngle()) > 20) {
				drivetrain.setDrivetrain(0.0, 0.5);
			} else {
				drivetrain.setDrivetrain(0.0, 0.3); 	
			}
		} else {
			if (Math.abs(endingDegree - navX.getAngle()) > 20) {
				drivetrain.setDrivetrain(0.0, -0.3);
			} else {
				drivetrain.setDrivetrain(0.0, -0.5);
			}
		} 
	}

	@Override
	protected boolean isFinished() {
		return endingDegree == (int) navX.getAngle();
	}

	@Override
	protected void end() {
		drivetrain.setDrivetrain(0.0, 0.0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
