package com.edinarobotics.zenith.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class VisionAutoAimManualCommand extends CommandGroup {
	
	public VisionAutoAimManualCommand() {
		addParallel(new VisionDrivetrainManualCommand());
		addParallel(new VisionClawManualCommand());
	}
}
