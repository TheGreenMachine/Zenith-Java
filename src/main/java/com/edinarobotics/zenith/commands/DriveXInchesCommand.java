package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Drivetrain;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;

public class DriveXInchesCommand extends Command {

	private Drivetrain drivetrain;
	private AHRS navX;
	private double velocity;
	private int initialPosition, target, ticks;
	
	
	public DriveXInchesCommand(double inches, double velocity) {
		super("drivexinchescommand");
		this.velocity = velocity;
		drivetrain = Components.getInstance().drivetrain;
		navX = Components.getInstance().navX;
		ticks = (int)(inches * 389);
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		initialPosition = drivetrain.getMiddleRight().getEncPosition();
		target = ticks + initialPosition;
		navX.reset();
	}

	@Override 
	protected void execute() {
		
		double rotation = 0;
		
		if(navX.getAngle() > 3)
			rotation = -0.05;
		else if(navX.getAngle() < 3)
			rotation = 0.05;
		else
			rotation = 0.0;
		
		if (target > initialPosition) {
			if (target - drivetrain.getMiddleRight().getEncPosition() < 3000 && target - drivetrain.getMiddleRight().getEncPosition() > 1000)
				drivetrain.setDrivetrain(velocity * .33, rotation);
			else if(target - drivetrain.getMiddleRight().getEncPosition() <= 1000)
				drivetrain.setDrivetrain(0.0, 0.0);
			else
				drivetrain.setDrivetrain(velocity, rotation);
			
		} else if (target < initialPosition) {
			if (target - drivetrain.getMiddleRight().getEncPosition() > -3000 && target - drivetrain.getMiddleRight().getEncPosition() < -1000) {
				drivetrain.setDrivetrain(-velocity * .33, rotation);
			} else if(target - drivetrain.getMiddleRight().getEncPosition() >= -1000){
				drivetrain.setDrivetrain(0.0, 0.0);
			} else {
				drivetrain.setDrivetrain(-velocity, rotation);
			}
		}
		
		System.out.println("Encoder: " + drivetrain.getMiddleRight().getEncPosition());
		System.out.println("Target: " + target);
		
	}

	@Override
	protected boolean isFinished() {
		return (Math.abs(drivetrain.getMiddleRight().getEncPosition() - target) < 1000);
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
