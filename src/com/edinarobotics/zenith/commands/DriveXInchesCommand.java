package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveXInchesCommand extends Command {

	private Drivetrain drivetrain;
	private double velocity, endingValue, change;
	
	public DriveXInchesCommand(double distance, double velocity) {
		super("drivexinchescommand");
		this.velocity = velocity;
		drivetrain = Components.getInstance().drivetrain;
		endingValue = drivetrain.getMiddleLeft().getEncPosition() + (distance * 10.258030311);
		change = (distance * 10.258030311);
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override 
	protected void execute() {
		if (change > 0) {
			
			if ((endingValue - drivetrain.getMiddleLeft().getEncPosition()) < 100)
				drivetrain.setDrivetrain(velocity * .33, 0.0);
			else
				drivetrain.setDrivetrain(velocity, 0.0);
		
		} else if (change < 0) {
			
			if ((drivetrain.getMiddleLeft().getEncPosition() - endingValue) < 100)
				drivetrain.setDrivetrain(-velocity * .33, 0.0);
			else
				drivetrain.setDrivetrain(-velocity, 0.0);
			
		} else {
			drivetrain.setDrivetrain(0.0, 0.0);
		}
	}

	@Override
	protected boolean isFinished() {
		return drivetrain.getMiddleLeft().getEncPosition() == endingValue;
	}

	@Override
	protected void end() {
		drivetrain.setDrivetrain(0.0, 0.0);
	}

	@Override
	protected void interrupted() {
		
	}

}
