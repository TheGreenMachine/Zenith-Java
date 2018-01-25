package com.edinarobotics.zenith.commands;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.Controls;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Claw.ClawSpeed;

import edu.wpi.first.wpilibj.command.Command;

public class CalibratePotentiometerCommand extends Command {

	private Claw claw;
	
	public CalibratePotentiometerCommand(){
		super("calibratepotentiometercommand");
		claw = Components.getInstance().claw;
		requires(claw);
	}
	
	@Override
	protected void initialize() {
		claw.moveDown(ClawSpeed.INSANE);
	}

	@Override
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		return !claw.getLimitSwitch();
	}

	@Override
	protected void end() {
		claw.stopClaw();
		claw.resetZero();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
