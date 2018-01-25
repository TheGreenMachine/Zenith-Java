package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveXTimeCommand extends Command {

	private Drivetrain drivetrain;
	private double velocity;
	
	public DriveXTimeCommand(double velocity, double time) {
		super("drivextimecommand");
		drivetrain = Components.getInstance().drivetrain;
		this.velocity = velocity;
		setTimeout(time);
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		drivetrain.setDrivetrain(velocity, 0.0);
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
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
