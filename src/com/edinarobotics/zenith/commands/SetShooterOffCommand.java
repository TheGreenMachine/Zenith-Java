package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Shooter;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class SetShooterOffCommand extends Command {

	private Shooter shooter;
	
	public SetShooterOffCommand() {
		super("setshooteroffcommand");
		shooter = Components.getInstance().shooter;
		requires(shooter);
	}
	
	@Override
	protected void initialize() {
		if (!shooter.solenoid.get().equals(Value.kOff)) {
			shooter.solenoid.set(Value.kOff);
		}
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
		
	}

}
