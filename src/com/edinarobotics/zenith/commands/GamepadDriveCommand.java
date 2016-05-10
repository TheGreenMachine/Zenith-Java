package com.edinarobotics.zenith.commands;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class GamepadDriveCommand extends Command {

	private Drivetrain drivetrain;
	private Gamepad gamepad;

	public GamepadDriveCommand(Gamepad gamepad) {
		super("gamepaddrivecommand");
		this.gamepad = gamepad;
		drivetrain = Components.getInstance().drivetrain;
		requires(drivetrain);
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		double verticalStrafe, rotation;
		
		if(gamepad.getLeftJoystick().getY() > 0.05)
			verticalStrafe = gamepad.getLeftJoystick().getY() * 0.85 + 0.15;
		else if(gamepad.getLeftJoystick().getY() < -0.05)
			verticalStrafe = gamepad.getLeftJoystick().getY() * 0.85 - 0.15;
		else
			verticalStrafe = 0;
		
		if(gamepad.getRightJoystick().getX() > 0.05)
			rotation = gamepad.getRightJoystick().getX() * 0.95 + 0.05;
		else if(gamepad.getRightJoystick().getX() < -0.05)
			rotation = gamepad.getRightJoystick().getX() * 0.95 - 0.05;
		else
			rotation = 0;
			
		
//		double verticalStrafe = gamepad.getLeftJoystick().getY();
//		double rotation = gamepad.getRightJoystick().getX();

		drivetrain.setDrivetrain(verticalStrafe, rotation);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {

	}

	@Override
	protected void interrupted() {

	}

}
