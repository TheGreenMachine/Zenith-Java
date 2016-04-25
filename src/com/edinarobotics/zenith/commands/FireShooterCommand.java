package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class FireShooterCommand extends Command {

	private Shooter shooter;
	private boolean lowPower;
	
	public FireShooterCommand(boolean lowPower) {
		super("fireshootercommand");
		shooter = Components.getInstance().shooter;
		this.lowPower = lowPower;
		requires(shooter);
	}
	
	@Override
	protected void initialize() {
		shooter.toggleShooter(lowPower);
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
