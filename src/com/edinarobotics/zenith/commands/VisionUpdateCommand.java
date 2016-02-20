package com.edinarobotics.zenith.commands;



import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Vision;

import edu.wpi.first.wpilibj.command.Command;

public class VisionUpdateCommand extends Command {
	
	private Vision vision;
	
	public VisionUpdateCommand() {
		super("visionupdatecommand");
		vision = Components.getInstance().vision;
		requires(vision);
	}
	
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		vision.update();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}

}
