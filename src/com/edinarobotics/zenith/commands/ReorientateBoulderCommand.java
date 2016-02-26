package com.edinarobotics.zenith.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ReorientateBoulderCommand extends CommandGroup {
	
	public ReorientateBoulderCommand() {
		addSequential(new RunCollectorCommand(0.5), 0.2);
		
		addSequential(new WaitCommand(0.1));
		
		addSequential(new RunCollectorCommand(-0.5), 0.3);
	}

}
