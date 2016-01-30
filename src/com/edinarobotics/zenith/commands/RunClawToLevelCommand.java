package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Claw.ClawAngle;

import edu.wpi.first.wpilibj.command.Command;

public class RunClawToLevelCommand extends Command {

	private Claw claw;
	private Claw.ClawAngle angle;
	
	public RunClawToLevelCommand(ClawAngle angle) {
		super("runclawtolevelcommand");
		this.angle = angle;
		claw = Components.getInstance().claw;
		requires(claw);
	}
	
	@Override
	protected void initialize() {
		claw.setClawAngle(angle);
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
