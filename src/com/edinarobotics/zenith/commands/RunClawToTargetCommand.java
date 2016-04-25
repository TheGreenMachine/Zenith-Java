package com.edinarobotics.zenith.commands;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Claw.ClawTarget;

import edu.wpi.first.wpilibj.command.Command;

public class RunClawToTargetCommand extends Command {

	private Claw claw;
	private ClawTarget clawTarget;
	private Gamepad gamepad;
	
	private double targetPosition;
	private double currentPosition;

	public RunClawToTargetCommand(ClawTarget clawTarget, Gamepad gamepad) {
		super("runclawtotargetcommand");
		claw = Components.getInstance().claw;
		this.clawTarget = clawTarget;
		this.gamepad = gamepad;
		requires(claw);
	}

	@Override
	protected void initialize() {
		targetPosition = clawTarget.getTarget();
		claw.preset = true;
	}

	@Override
	protected void execute() {
		currentPosition = claw.getCurrentPosition();
		//Code for practice bot
//		if ((currentPosition - 1) > targetPosition) {
//			if(Math.abs(currentPosition - targetPosition) < 20)
//				claw.setTalon(-0.2);
//			else
//				claw.setTalon(-0.65);
//		} else if ((targetPosition - 1) > currentPosition) {
//			if(Math.abs(currentPosition - targetPosition) < 20)
//				claw.setTalon(0.2);
//			else
//				claw.setTalon(0.65); 
//		} else {
//			claw.setTalon(0.0);
//		}
		
		if ((currentPosition - 1) < targetPosition) {
			if(Math.abs(currentPosition - targetPosition) < 20)
				claw.setTalon(-0.4);
			else
				claw.setTalon(-0.75);
		} else if ((targetPosition - 1) < currentPosition) {
			if(Math.abs(currentPosition - targetPosition) < 20)
				claw.setTalon(0.4);
			else
				claw.setTalon(0.75); 
		} else {
			claw.setTalon(0.0);
		}
		
		System.out.println("Target position: " + targetPosition);
		System.out.println("Current position: " + currentPosition);
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(currentPosition - targetPosition) < 3 || Math.abs(gamepad.getRightJoystick().getY()) > 0.05;
	}

	@Override
	protected void end() {
		claw.setTalon(0.0);
		claw.preset = false;
	}

	@Override
	protected void interrupted() {
		end();
	}

}