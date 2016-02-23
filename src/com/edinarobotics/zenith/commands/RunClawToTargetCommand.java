package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Claw.ClawTarget;

import edu.wpi.first.wpilibj.CANTalon;
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
		claw.setPreset(true);
		claw.setTarget(clawTarget);
	}

	@Override
	protected void execute() {
		if (claw.isBelow()) {
			claw.runUp();
		} else if (claw.isAbove()) {
			claw.runDown();
		} else {
			claw.endTarget();
		}
	}

	@Override
	protected boolean isFinished() {
		return claw.isAtTarget();
	}

	@Override
	protected void end() {
		claw.endTarget();
	}

	@Override
	protected void interrupted() {
		end();
	}

}
