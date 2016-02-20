package com.edinarobotics.zenith.commands;



import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;
import com.edinarobotics.zenith.subsystems.Vision;

import edu.wpi.first.wpilibj.command.Command;

public class VisionDrivetrainManualCommand extends Command {
	
	private Drivetrain drivetrain;
	private Vision vision;
	
	public VisionDrivetrainManualCommand() {
		super("visiondrivetrainmanualcommand");
		drivetrain = Components.getInstance().drivetrain;
		vision = Components.getInstance().vision;
		requires(drivetrain);
	}
	
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		if(vision.canShoot()) {
			drivetrain.setLowGear(true);
			drivetrain.setRotation(vision.calculateXSpeed());
		}
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
