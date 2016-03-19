package com.edinarobotics.zenith;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zenith.commands.AutonomousCommand;
import com.edinarobotics.zenith.commands.AutonomousCommand.AutonomousMode;
import com.edinarobotics.zenith.commands.GamepadDriveCommand;
import com.edinarobotics.zenith.commands.RunClawManualCommand;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Collector;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Zenith extends IterativeRobot {
	
	private Drivetrain drivetrain;
	private Claw claw;
	private Collector collector;
	
	private SendableChooser autoChooser;
	private Preferences preferences;

	public void robotInit() {
		Components.getInstance();
		Controls.getInstance();

		drivetrain = Components.getInstance().drivetrain;
		claw = Components.getInstance().claw;
		collector = Components.getInstance().collector;
		
		//Dashboard
		autoChooser = new SendableChooser();
		preferences = Preferences.getInstance();
		
		autoChooser.addDefault("Low Bar", AutonomousMode.LOW_BAR_WAIT);
		autoChooser.addObject("Generic breach", AutonomousMode.GENERAL_BREACH);
		autoChooser.addObject("Do Nothing", AutonomousMode.NOTHING);
		autoChooser.addObject("Test Auto", AutonomousMode.TEST_AUTO);
		
		SmartDashboard.putData("Auto Chooser", autoChooser);
	}   

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		Command cmd = new AutonomousCommand((AutonomousMode) autoChooser.getSelected());
		cmd.start();
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
		updateDashboard();
	}

	public void testPeriodic() {

	}
	
	public void updateDashboard() {
		//Claw
		SmartDashboard.putNumber("Current position: ", (claw.getCurrentPosition() - 820));	
		SmartDashboard.putBoolean("Claw Brake Active? ", claw.getBrakeSolenoid().get());

		//Drivetrain
		SmartDashboard.putBoolean("Drivetrain Brake mode? ", drivetrain.getBrakeMode());
		SmartDashboard.putBoolean("High gear? ", !drivetrain.getToggled());
		
		//Etc
		SmartDashboard.putNumber("Battery voltage: ", DriverStation.getInstance().getBatteryVoltage());
	}

	public void stop() {
		drivetrain.setDrivetrain(0.0, 0.0);
		collector.setVelocity(0.0);
	}
}
