package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleShiftingCommand extends Command {

	public Drivetrain drivetrain;
	
	public ToggleShiftingCommand() {
		super("setshiftingcommand");
		drivetrain = Components.getInstance().drivetrain;
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		drivetrain.setToggled(!drivetrain.getToggled());
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
