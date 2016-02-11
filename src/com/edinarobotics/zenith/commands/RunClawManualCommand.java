package com.edinarobotics.zenith.commands;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

public class RunClawManualCommand extends Command{

	private Claw claw;
	private Gamepad gamepad;
	
	public RunClawManualCommand(Gamepad gamepad){
		super("runclawmanualcommand");
		claw = Components.getInstance().claw;
		this.gamepad = gamepad;
		requires(claw);
	}
	
	@Override
	protected void initialize() {
		claw.setTarget(gamepad.getRightJoystick().getY());		
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
