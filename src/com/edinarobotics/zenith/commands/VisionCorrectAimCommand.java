package com.edinarobotics.zenith.commands;

import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.Controls;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Drivetrain;
import com.edinarobotics.zenith.subsystems.Vision;

import edu.wpi.first.wpilibj.command.Command;

public class VisionCorrectAimCommand extends Command{

	private Vision vision;
	private Drivetrain drivetrain;
	private Claw claw;
		
	public VisionCorrectAimCommand(){
		super("visioncorrectaimcommand");
		vision = Components.getInstance().vision;
		drivetrain = Components.getInstance().drivetrain;
		claw = Components.getInstance().claw;
		requires(drivetrain);
		requires(claw);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		drivetrain.setBrakeMode(true);
		vision.setActive(true);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		vision.update();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return vision.isOnTarget();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		drivetrain.setDrivetrain(0.0, 0.0);
		claw.setTalon(0.0);
		
		drivetrain.setBrakeMode(false);
		
		vision.setActive(false);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
