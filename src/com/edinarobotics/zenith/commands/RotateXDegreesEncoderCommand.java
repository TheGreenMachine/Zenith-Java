package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class RotateXDegreesEncoderCommand extends Command{

	private Drivetrain drivetrain;
	private int ticks, target, initialPosition;
	private double velocity;
	
	public RotateXDegreesEncoderCommand(double degrees, double velocity) {
		super("rotatexdegreesencodercommand");
		drivetrain = Components.getInstance().drivetrain;
		ticks = (int)(degrees * 141.1);  //double check value of 141.1 - very close but not exact for degrees -> ticks
		this.velocity = velocity;
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		target = drivetrain.getMiddleRight().getEncPosition() + ticks;
		initialPosition = drivetrain.getMiddleRight().getEncPosition();
	}

	@Override
	protected void execute() {
		if(initialPosition < target){
			if(target - drivetrain.getMiddleRight().getEncPosition() < 2500 && target - drivetrain.getMiddleRight().getEncPosition() >= 1000)
				drivetrain.setDrivetrain(0.0, -velocity * 0.4);
			else
				drivetrain.setDrivetrain(0.0, -velocity);
		}
		else{
			if((target - drivetrain.getMiddleRight().getEncPosition()) > -2500 && target - drivetrain.getMiddleRight().getEncPosition() <= -1000)
				drivetrain.setDrivetrain(0.0, velocity * 0.4);
			else
				drivetrain.setDrivetrain(0.0, velocity);
		}
		
		System.out.println("Tick target: " + target);
		System.out.println("Current ticks: " + drivetrain.getMiddleRight().getEncPosition());
			
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(target - drivetrain.getMiddleRight().getEncPosition()) <= 1100;
	}

	@Override
	protected void end() {
		drivetrain.setDrivetrain(0.0, 0.0);
		System.out.println("RotateXDegreesEncoderCommand completed!\n\n\n\n\n\n\n\n\n\n");
	}

	@Override
	protected void interrupted() {
		end();
	}

}
