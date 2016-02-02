package com.edinarobotics.zenith;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zenith.commands.GamepadDriveCommand;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Zenith extends IterativeRobot {
	private Drivetrain drivetrain;

	public void robotInit() {
		Controls.getInstance();
		Components.getInstance();
		
		drivetrain = Components.getInstance().drivetrain;
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
		
		drivetrain.setDefaultCommand(new GamepadDriveCommand(gamepad0));
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
