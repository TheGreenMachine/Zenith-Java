package com.edinarobotics.zenith;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zenith.commands.AutonomousCommand;
import com.edinarobotics.zenith.commands.AutonomousCommand.DefensePosition;
import com.edinarobotics.zenith.commands.AutonomousCommand.ScoringOption;
import com.edinarobotics.zenith.commands.AutonomousCommand.StartingPosition;
import com.edinarobotics.zenith.commands.GamepadDriveCommand;
import com.edinarobotics.zenith.commands.RunClawManualCommand;
import com.edinarobotics.zenith.commands.VisionUpdateCommand;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Collector;
import com.edinarobotics.zenith.subsystems.Drivetrain;
import com.edinarobotics.zenith.subsystems.Vision;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Zenith extends IterativeRobot {
	private Drivetrain drivetrain;
	private Claw claw;
	private Collector collector;
	private Vision vision;

	//Autonomous
	private StartingPosition startingPosition;
	private DefensePosition defenseOption;
	private ScoringOption scoringOption;
	
	private SendableChooser startingChooser;
	private SendableChooser defenseChooser;
	private SendableChooser scoringChooser;
	
	public void robotInit() {
		Controls.getInstance();
		Components.getInstance();

		drivetrain = Components.getInstance().drivetrain;
		claw = Components.getInstance().claw;
		collector = Components.getInstance().collector;
		vision = Components.getInstance().vision;
		
		startingChooser = new SendableChooser();
		defenseChooser = new SendableChooser();
		scoringChooser = new SendableChooser();
		
		startingChooser.addDefault("Left", StartingPosition.LEFT);
		startingChooser.addObject("Middle", StartingPosition.MIDDLE);
		startingChooser.addObject("Right", StartingPosition.RIGHT);
		SmartDashboard.putData("Starting Position Chooser", startingChooser);
		
		defenseChooser.addDefault("Low Bar", DefensePosition.LOW_BAR);
		SmartDashboard.putData("Defense Chooser", defenseChooser);
		
		scoringChooser.addDefault("Nothing", ScoringOption.NONE);
		scoringChooser.addObject("Low Goal", ScoringOption.LOW_GOAL);
		scoringChooser.addObject("High Goal", ScoringOption.HIGH_GOAL);
		SmartDashboard.putData("Scoring Chooser", scoringChooser);
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		startingPosition = (StartingPosition) startingChooser.getSelected();
		defenseOption = (DefensePosition) defenseChooser.getSelected();
		scoringOption = (ScoringOption) scoringChooser.getSelected();
		
		Command command = new AutonomousCommand(startingPosition, defenseOption, scoringOption);
		command.start();
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
		updateDashboard();
	}

	public void testPeriodic() {

	}
	
	public void updateDashboard() {
		//Claw
		SmartDashboard.putBoolean("Brake Solenoid? ", claw.getBrakeSolenoid().get());
		SmartDashboard.putNumber("Potentiometer reading: ", claw.getCurrentPosition());
		SmartDashboard.putNumber("Potentiometer target: ", claw.getTarget());
		SmartDashboard.putBoolean("Claw below target: ", claw.isBelow());
		SmartDashboard.putBoolean("Claw above target: ", claw.isAbove());
		SmartDashboard.putBoolean("Claw at target: ", claw.isAtTarget());
		
		//Shooter
		SmartDashboard.putBoolean("High powered shot? ", Components.getInstance().shooter.lowPowerSolenoid.get());
	}

	public void stop() {
		drivetrain.setDrivetrain(0.0, 0.0);
		collector.setVelocity(0.0);
	}
}
