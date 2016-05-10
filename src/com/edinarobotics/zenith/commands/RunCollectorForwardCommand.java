package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Collector;

import edu.wpi.first.wpilibj.command.Command;

public class RunCollectorForwardCommand extends Command {

	private Collector collector;
	private double velocity;
	
	public RunCollectorForwardCommand(double velocity) {
		super("runcollectorforwardcommand");
		collector = Components.getInstance().collector;
		this.velocity = velocity;
		requires(collector);
	}
	
	@Override
	protected void initialize() {
		collector.setForwardVelocity(velocity);
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
