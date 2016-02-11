package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveXInchesCommand extends Command {

	private Drivetrain drivetrain;
	private double distance, velocity, ticks;
	
	public DriveXInchesCommand(double distance, double velocity) {
		super("drivexinchescommand");
		this.distance = distance;
		convertInchesToTicks();
		this.velocity = velocity;
		drivetrain = Components.getInstance().drivetrain;
		requires(drivetrain);
	}
	
	public void convertInchesToTicks() {
		ticks = (distance * 0.09662876322);
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		drivetrain.setDrivetrain(velocity, 0.0);
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
		
	}

	@Override
	protected void interrupted() {
		
	}

}
