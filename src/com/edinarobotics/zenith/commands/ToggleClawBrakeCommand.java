package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleClawBrakeCommand extends Command{

private Claw claw;
	
	public ToggleClawBrakeCommand() {
		super("toggleclawbrakecommand");
		claw = Components.getInstance().claw;
		requires(claw);
	}
	
	@Override
	protected void initialize() {
		claw.toggleBrakeSolenoid();
		System.out.println("Brake Toggled");
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
