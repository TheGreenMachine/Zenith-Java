package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleBrakeModeCommand extends Command {

	private Drivetrain drivetrain;
	private boolean toggle;
	
	public ToggleBrakeModeCommand(boolean toggle) {
		super("togglebrakemodecommand");
		drivetrain = Components.getInstance().drivetrain;
		this.toggle = toggle;
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		if(toggle) {
			drivetrain.setBrakeMode(true);
		}
		else {
			drivetrain.setBrakeMode(false);
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
