package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Drivetrain;
import com.edinarobotics.zenith.subsystems.Vision;

import edu.wpi.first.wpilibj.command.Command;

public class VisionAutoAimCommand extends Command {

	public Vision vision;
	public Claw claw;
	public Drivetrain drivetrain;
	
	public VisionAutoAimCommand() {
		super("visionautoaimcommand");
		this.vision = Components.getInstance().vision;
		this.claw = Components.getInstance().claw;
		this.drivetrain = Components.getInstance().drivetrain;
		requires(vision);
		requires(claw);
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		vision.setActive(true);
	}

	@Override
	protected void execute() {
		vision.update();
	}

	@Override
	protected boolean isFinished() {
		return vision.isOnTarget();
	}

	@Override
	protected void end() {
		vision.end();
		
		vision.setActive(false);
		vision.offsetting = false;
	}

	@Override
	protected void interrupted() {
		end();
	}

}
