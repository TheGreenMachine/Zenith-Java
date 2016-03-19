package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

public class DisableClawBrakeCommand extends Command{

private Claw claw;
	
	public DisableClawBrakeCommand() {
		super("disableclawbrakecommand");
		claw = Components.getInstance().claw;
		requires(claw);
	}
	
	@Override
	protected void initialize() {
		claw.disableBrake();
		System.out.println("Brake Disabled");
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
