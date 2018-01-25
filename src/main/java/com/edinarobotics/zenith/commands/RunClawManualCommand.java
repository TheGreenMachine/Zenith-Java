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
		double velocity = 0;
		
		if(gamepad.getRightJoystick().getY() > 0.025)
			velocity = 0.2 + 0.8 * 1.1 * gamepad.getRightJoystick().getY() * Math.abs(Math.pow(gamepad.getRightJoystick().getY(), 1));
		else if(gamepad.getRightJoystick().getY() < -0.025)
			velocity = -0.15 + 0.85 * 1.1 * gamepad.getRightJoystick().getY() * Math.abs(Math.pow(gamepad.getRightJoystick().getY(), 1));
		else
			velocity = 0;
		
//		double velocity = 1.1 * gamepad.getRightJoystick().getY() * Math.abs(Math.pow(gamepad.getRightJoystick().getY(), 1));
			
		claw.setClawSpeed(velocity);
		
//		System.out.println("Claw Joystick Val: " + gamepad.getRightJoystick().getY());
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
		end();
	}

}
