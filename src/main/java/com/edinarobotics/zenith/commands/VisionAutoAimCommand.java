package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Drivetrain;
import com.edinarobotics.zenith.subsystems.Shooter;
import com.edinarobotics.zenith.subsystems.Vision;

import edu.wpi.first.wpilibj.command.Command;

public class VisionAutoAimCommand extends Command {

	public Vision vision;
	public Claw claw;
	public Drivetrain drivetrain;
	public Shooter shooter;
	
	private boolean shootAtEnd = false;
	
	public VisionAutoAimCommand(boolean shootAtEnd) {
		super("visionautoaimcommand");
		this.vision = Components.getInstance().vision;
		this.claw = Components.getInstance().claw;
		this.drivetrain = Components.getInstance().drivetrain;
		this.shooter = Components.getInstance().shooter;
		this.shootAtEnd = shootAtEnd;
		requires(vision);
		requires(claw);
		requires(drivetrain);
		requires(shooter);
	}
	
	@Override
	protected void initialize() {
		vision.setActive(true);
		vision.lost = false;
	}

	@Override
	protected void execute() {
		vision.update();
	}

	@Override
	protected boolean isFinished() {
		return vision.isOnTarget() || vision.lost;
	}

	@Override
	protected void end() {
		drivetrain.setDrivetrain(0.0, 0.0);
		claw.stopClaw();
		
		if (!vision.lost && vision.getDistanceFromCenter() != -9999) {
			if (Math.abs(vision.getDistanceFromCenter()) > 5) {
				int degrees = (int) (vision.getDistanceFromCenter() / 1.8);
				
				new RotateXDegreesCommand(degrees);
			}
		}
		
		if(shootAtEnd && !vision.lost)
			shooter.toggleShooter();
		
		vision.setActive(false);
		vision.offsetting = false;
		vision.lost = false;
		
		drivetrain.setBrakeMode(false);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
