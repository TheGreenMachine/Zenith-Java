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
		ticks = (int)(degrees * 30);	//this constant of 30 is not correct, need to test to find degrees->ticks constant
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
			if(target - drivetrain.getMiddleRight().getEncPosition() < 3000 && target - drivetrain.getMiddleRight().getEncPosition() >= 1000)
				drivetrain.setDrivetrain(0.0, -velocity * 0.33);
			else if(target - drivetrain.getMiddleRight().getEncPosition() < 1000)
				drivetrain.setDrivetrain(0.0, 0.0);
			else
				drivetrain.setDrivetrain(0.0, -velocity);
		}
		else{
			if(target - drivetrain.getMiddleRight().getEncPosition() > -3000 && target - drivetrain.getMiddleRight().getEncPosition() <= -1000)
				drivetrain.setDrivetrain(0.0, velocity * 0.33);
			else if(target - drivetrain.getMiddleRight().getEncPosition() > -1000)
				drivetrain.setDrivetrain(0.0, 0.0);
			else
				drivetrain.setDrivetrain(0.0, velocity);
		}
			
	}

	@Override
	protected boolean isFinished() {
		return (Math.abs(target - drivetrain.getMiddleRight().getEncPosition()) < 1000);
	}

	@Override
	protected void end() {
		drivetrain.setDrivetrain(0.0, 0.0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
