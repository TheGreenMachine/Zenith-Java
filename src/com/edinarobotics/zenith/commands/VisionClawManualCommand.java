package com.edinarobotics.zenith.commands;


import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Vision;

import edu.wpi.first.wpilibj.command.Command;

public class VisionClawManualCommand extends Command {
	
	private Claw claw;
	private Vision vision;
	
	public VisionClawManualCommand() {
		super("visionclawmanualcommand");
		claw = Components.getInstance().claw;
		vision = Components.getInstance().vision;
		requires(claw);
	}
	
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		claw.setTarget(claw.getTarget() + vision.calculateYTarget());
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
