package com.edinarobotics.zenith.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommand extends CommandGroup {

	public AutonomousCommand(StartingPosition startingPosition, 
			DefensePosition defensePosition, ScoringOption scoringOption) {

		if (defensePosition == defensePosition.LOW_BAR) {
			
			if (startingPosition == startingPosition.LEFT) {
				
				//Drive to base of Low Bar
				addSequential(new DriveXInchesCommand(58.75, .5));
				
				addSequential(new RotateXDegreesCommand(180, .5));
				
				//Drive through the Low Bar
				addSequential(new DriveXInchesCommand(-78.5, .5));
				
				addSequential(new RotateXDegreesCommand(180, .5));
				
				addSequential(new RotateXDegreesCommand(73.1535, .5));
				
				addSequential(new DriveXInchesCommand(153.5914, .5));
				
				addSequential(new RotateXDegreesCommand(-73.1535, .5));
				
				addSequential(new DriveXInchesCommand(48.3419, .5));
				
				if (scoringOption == scoringOption.LOW_GOAL) {
					
					addSequential(new DriveXInchesCommand(48, .5));
					
					addSequential(new RunCollectorCommand(.5), 2);
					
				} else if (scoringOption == scoringOption.HIGH_GOAL){
					
					// Vision
					
				} else {
					
				}
				
			} else if (startingPosition == startingPosition.MIDDLE) {
				
				addSequential(new RotateXDegreesCommand(-90, .5));
				
				addSequential(new DriveXInchesCommand(135, .5));
				
				addSequential(new RotateXDegreesCommand(90, .5));
				
				addSequential(new DriveXInchesCommand(58.75, .5));
				
				addSequential(new RotateXDegreesCommand(180, .5));
				
				//Drive through the Low Bar
				addSequential(new DriveXInchesCommand(-78.5, .5));
				
				addSequential(new RotateXDegreesCommand(180, .5));
				
				addSequential(new RotateXDegreesCommand(73.1535, .5));
				
				addSequential(new DriveXInchesCommand(153.5914, .5));
				
				addSequential(new RotateXDegreesCommand(-73.1535, .5));
				
				addSequential(new DriveXInchesCommand(48.3419, .5));
				
				if (scoringOption == scoringOption.LOW_GOAL) {
					
					addSequential(new DriveXInchesCommand(48, .5));
					
					addSequential(new RunCollectorCommand(.5), 2);
					
				} else if (scoringOption == scoringOption.HIGH_GOAL){
					
					// Vision
					
				} else {
					
				}
				
			} else {
				
				addSequential(new RotateXDegreesCommand(-90, .5));
				
				addSequential(new DriveXInchesCommand(270, .5));
				
				addSequential(new RotateXDegreesCommand(90, .5));
				
				addSequential(new DriveXInchesCommand(58.75, .5));
				
				addSequential(new RotateXDegreesCommand(180, .5));
				
				//Drive through the Low Bar
				addSequential(new DriveXInchesCommand(-78.5, .5));
				
				addSequential(new RotateXDegreesCommand(180, .5));
				
				addSequential(new RotateXDegreesCommand(73.1535, .5));
				
				addSequential(new DriveXInchesCommand(153.5914, .5));
				
				addSequential(new RotateXDegreesCommand(-73.1535, .5));
				
				addSequential(new DriveXInchesCommand(48.3419, .5));
				
				if (scoringOption == scoringOption.LOW_GOAL) {
					
					addSequential(new DriveXInchesCommand(48, .5));
					
					addSequential(new RunCollectorCommand(.5), 2);
					
				} else if (scoringOption == scoringOption.HIGH_GOAL){
					
					// Vision
					
				} else {
					
				}
				
			}
			
		}
		

	}

	public enum StartingPosition {
		LEFT, MIDDLE, RIGHT;
	}
	
	public enum ScoringOption {
		LOW_GOAL, HIGH_GOAL, NONE;
	}
	
	public enum DefensePosition {
		LOW_BAR;
	}

}
