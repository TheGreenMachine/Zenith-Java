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
		double verticalStrafe = gamepad.getLeftJoystick().getY();
		double rotation = gamepad.getRightJoystick().getX();

		if (drivetrain.isOrientationSwapped()) {
			drivetrain.setDrivetrain(-verticalStrafe, rotation);
		} else {
			drivetrain.setDrivetrain(verticalStrafe, rotation);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		drivetrain.setDrivetrain(0.0, 0.0);
	}

	@Override
	protected void interrupted() {
		end();	
	}

}
