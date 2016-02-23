package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Claw.ClawTarget;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

public class RunClawToTargetCommand extends Command {

	private Claw claw;
	private ClawTarget clawTarget;
	private CANTalon talon;
	private double targetPosition;
	private double currentPosition;

	public RunClawToTargetCommand(ClawTarget clawTarget) {
		super("runclawtotargetcommand");
		claw = Components.getInstance().claw;
		this.clawTarget = clawTarget;
		requires(claw);
	}

	@Override
	protected void initialize() {
		targetPosition = clawTarget.getTarget() + 400;
		talon = claw.getTalon();
		claw.preset = true;
	}

	@Override
	protected void execute() {
		currentPosition = claw.getCurrentPosition();
 
		if ((currentPosition - 1) > targetPosition) {
			System.out.println("going down: curr=" + currentPosition + "; target=" + targetPosition);
			talon.set(0.5);
		} else if ((targetPosition - 1) > currentPosition) {
			talon.set(-0.5); 
			System.out.println("going up: curr=" + currentPosition + "; target=" + targetPosition);
		} else {
			talon.set(0.0);
		} 
	}

	@Override
	protected boolean isFinished() {
//		if (!((currentPosition - 1) > targetPosition) && !((targetPosition - 1) > currentPosition))
//			return true;
//
//		return false;
		
		return Math.abs(currentPosition - targetPosition) < 1;
	}

	@Override
	protected void end() {
		claw.preset = false;
		talon.set(0.0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
