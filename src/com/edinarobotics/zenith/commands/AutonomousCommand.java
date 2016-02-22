package com.edinarobotics.zenith.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommand extends CommandGroup {

	public AutonomousCommand(AutonomousMode mode) {

		switch (mode) {

			case LOW_BAR_WAIT:
	
				break;
	
		}
		

	}

	public enum AutonomousMode {
		LOW_BAR_WAIT, LOW_BAR_LOW_GOAL, LOW_BAR_HIGH_GOAL;
	}

}
