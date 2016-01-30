package com.edinarobotics.zenith.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommand extends CommandGroup {

	public AutonomousCommand(AutonomousMode mode) {
		
		switch(mode) {
			
			case LOW_BAR:
				break;
		
		}
		
	}
	
	public enum AutonomousMode {
		LOW_BAR;
	}
	
}
