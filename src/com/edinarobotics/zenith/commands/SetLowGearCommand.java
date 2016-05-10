package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class SetLowGearCommand extends Command {
	
	private Drivetrain drivetrain;
	private boolean lowGear;

	public SetLowGearCommand(boolean lowGear) {
		super("setlowgearcommand");
		drivetrain = Components.getInstance().drivetrain;
		this.lowGear = lowGear;
		requires(drivetrain);
	}

	@Override
	protected void initialize() {
		drivetrain.setLowGear(lowGear);

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
