package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Collector;

import edu.wpi.first.wpilibj.command.Command;

public class RunCollectorCommand extends Command {

	private Collector collector;
	private double velocity;
	
	public RunCollectorCommand(double velocity) {
		super("runcollectorcommand");
		collector = Components.getInstance().collector;
		this.velocity = velocity;
		requires(collector);
	}
	
	@Override
	protected void initialize() {
		collector.setVelocity(velocity);
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
