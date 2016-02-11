package com.edinarobotics.zenith;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zenith.commands.GamepadDriveCommand;
import com.edinarobotics.zenith.commands.RunClawManualCommand;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Zenith extends IterativeRobot {
	private Drivetrain drivetrain;
	private Claw claw;

	public void robotInit() {
		Controls.getInstance();
		Components.getInstance();

		drivetrain = Components.getInstance().drivetrain;
		claw = Components.getInstance().claw;
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {

	}

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		Gamepad gamepad0 = Controls.getInstance().gamepad0;
		Gamepad gamepad1 = Controls.getInstance().gamepad1;

		drivetrain.setDefaultCommand(new GamepadDriveCommand(gamepad0));
		claw.setDefaultCommand(new RunClawManualCommand(gamepad1));
	}

	public void disabledInit() {
		stop();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	public void testPeriodic() {

	}

	public void stop() {
		drivetrain.setDrivetrain(0.0, 0.0);
	}
}
