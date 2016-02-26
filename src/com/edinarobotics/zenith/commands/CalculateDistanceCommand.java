package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class CalculateDistanceCommand extends Command{
	
	private Accelerometer accelerometer;
	private Drivetrain drivetrain;
	
	public CalculateDistanceCommand() {
		super("calculatedistancecommand");
		accelerometer = Components.getInstance().accelerometer;
		drivetrain = Components.getInstance().drivetrain;
		requires(drivetrain);
	}
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
