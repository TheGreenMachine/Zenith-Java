package com.edinarobotics.zenith;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zenith.commands.GamepadDriveCommand;
import com.edinarobotics.zenith.commands.RunClawManualCommand;
import com.edinarobotics.zenith.commands.VisionUpdateCommand;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Collector;
import com.edinarobotics.zenith.subsystems.Drivetrain;
import com.edinarobotics.zenith.subsystems.Vision;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Zenith extends IterativeRobot {
	private Drivetrain drivetrain;
	private Claw claw;
	private Collector collector;
	private Vision vision;

	public void robotInit() {
		Controls.getInstance();
		Components.getInstance();

		drivetrain = Components.getInstance().drivetrain;
		claw = Components.getInstance().claw;
		collector = Components.getInstance().collector;
		vision = Components.getInstance().vision;
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
		vision.setDefaultCommand(new VisionUpdateCommand());
	}

	public void disabledInit() {
		stop();
		Components.getInstance().shooter.solenoid.set(Value.kOff);
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	public void testPeriodic() {

	}

	public void stop() {
		drivetrain.setDrivetrain(0.0, 0.0);
		collector.setVelocity(0.0);
	}
}
