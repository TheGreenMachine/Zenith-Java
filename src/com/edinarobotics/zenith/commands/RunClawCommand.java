package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

public class RunClawCommand extends Command {
	
	private Claw claw;
	private boolean on;
	
	public RunClawCommand(boolean on) {
		super("runclawcommand");
		claw = Components.getInstance().claw;
		this.on = on;
		requires(claw);
	}
	
	@Override
	protected void initialize() {
		if(on) {
			claw.setTarget(-0.5);
		}
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
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
