package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class PullInSolenoidCommand extends Command {

	private Shooter shooter;
	
	public PullInSolenoidCommand() {
		super("pullinsolenoidcommand");
		shooter = Components.getInstance().shooter;
		requires(shooter);
	}
	
	@Override
	protected void initialize() {
		shooter.pullIn();
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
