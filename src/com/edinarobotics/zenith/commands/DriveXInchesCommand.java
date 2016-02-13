package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveXInchesCommand extends Command {

	private Drivetrain drivetrain;
	private double velocity, ticks;
	
	public DriveXInchesCommand(double distance, double velocity) {
		super("drivexinchescommand");
		ticks = (distance * 10.258030311);
		this.velocity = velocity;
		drivetrain = Components.getInstance().drivetrain;
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		if (ticks > 0) {
			if (ticks - drivetrain.getMiddleLeft().getEncPosition() < 100) {
				drivetrain.setDrivetrain(velocity * .33, 0.0);
			} else {
				drivetrain.setDrivetrain(velocity, 0.0);
			}
		} else if (ticks < 0) {
			if (drivetrain.getMiddleLeft().getEncPosition() - ticks < 100) {
				drivetrain.setDrivetrain(velocity * .33, 0.0);
			} else {
				drivetrain.setDrivetrain(velocity, 0.0);
			}
		}
	}

	@Override
	protected boolean isFinished() {
		if (ticks > 0) {
			return (drivetrain.getMiddleLeft().getEncPosition() > 
			(drivetrain.getMiddleLeft().getEncPosition() + ticks));
		}
		
		return (drivetrain.getMiddleLeft().getEncPosition() < 
		(drivetrain.getMiddleLeft().getEncPosition() + ticks));
	}

	@Override
	protected void end() {
		drivetrain.setDrivetrain(0.0, 0.0);
	}

	@Override
	protected void interrupted() {
		
	}

}
