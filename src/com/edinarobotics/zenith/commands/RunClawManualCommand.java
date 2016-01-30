package com.edinarobotics.zenith.commands;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

public class RunClawManualCommand extends Command {

	private Claw claw;
	private Gamepad gamepad;
	
	public RunClawManualCommand(Gamepad gamepad) {
		super("runclawmanualcommand");
		this.gamepad = gamepad;
		claw = Components.getInstance().claw;
		requires(claw);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		double value = gamepad.getLeftJoystick().getY();
		claw.setClawAngle(value);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
