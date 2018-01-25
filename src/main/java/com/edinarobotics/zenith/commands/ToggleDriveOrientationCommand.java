package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleDriveOrientationCommand extends Command {

	private Drivetrain drivetrain;
	
	public ToggleDriveOrientationCommand() {
		super("toggledriveorientationcommand");
		drivetrain = Components.getInstance().drivetrain;
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		drivetrain.setOrientationSwapped(!drivetrain.isOrientationSwapped());
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}

}
