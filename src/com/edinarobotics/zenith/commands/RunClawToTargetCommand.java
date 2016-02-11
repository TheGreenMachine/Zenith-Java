package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Claw.ClawTarget;

import edu.wpi.first.wpilibj.command.Command;

public class RunClawToTargetCommand extends Command {

	private Claw claw;
	private ClawTarget clawTarget;

	public RunClawToTargetCommand(ClawTarget clawTarget) {
		super("runclawtotargetcommand");
		claw = Components.getInstance().claw;
		this.clawTarget = clawTarget;
		requires(claw);
	}

	@Override
	protected void initialize() {
		claw.setTarget(clawTarget);
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
