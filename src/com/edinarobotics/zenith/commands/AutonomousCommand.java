package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Controls;
import com.edinarobotics.zenith.subsystems.Claw.ClawTarget;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutonomousCommand extends CommandGroup {

	public AutonomousCommand(AutonomousMode mode) {
		
		switch(mode) {
			
			case LOW_BAR_WAIT:
				
				addParallel(new RunCollectorCommand(-0.375));
				
				addSequential(new RunClawToTargetCommand(ClawTarget.LOW_BAR, Controls.getInstance().gamepad1));
				
				addSequential(new WaitCommand(1));
				
				addSequential(new DriveXTimeCommand(-.5, 8.0)); 
				
				addSequential(new RunCollectorCommand(0.0));
				
				break;
				
			case GENERAL_BREACH:
				
				addParallel(new RunCollectorCommand(-0.375));
				
				addSequential(new RunClawToTargetCommand(ClawTarget.AUTO, Controls.getInstance().gamepad1));
				
				addSequential(new WaitCommand(1));
				
				addSequential(new DriveXTimeCommand(0.75, 4.0));
				
				addSequential(new RunCollectorCommand(0.0));
				
				break;
				
			case TEST_AUTO:
				
				addSequential(new ToggleBrakeModeCommand());
				
				addParallel(new RunCollectorCommand(-0.375));
				
				addSequential(new RunClawToTargetCommand(ClawTarget.LOW_BAR, Controls.getInstance().gamepad1));
				
				addSequential(new DriveXInchesCommand(-220, 0.375));
				
				addSequential(new RunClawToTargetCommand(ClawTarget.HIGH_POWER, Controls.getInstance().gamepad1));
				
				addSequential(new RotateXDegreesEncoderCommand(115, 0.75));
				
				addSequential(new DriveXInchesCommand(48, 0.5));
				
				break;
				
			case NOTHING:
				
				break;
				
			default:
				
				break;
		
		}
		
	}
	
	public enum AutonomousMode {
		LOW_BAR_WAIT,
		GENERAL_BREACH,
		LOW_BAR_HIGH_GOAL,
		TEST_AUTO,
		NOTHING;
	}
	
}
