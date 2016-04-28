package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;
import com.edinarobotics.zenith.subsystems.Drivetrain;
import com.edinarobotics.zenith.subsystems.Vision;
import com.edinarobotics.zenith.subsystems.VisionHorizontal;
import com.edinarobotics.zenith.subsystems.VisionVertical;

import edu.wpi.first.wpilibj.command.Command;

public class VisionCorrectAimCommand extends Command{

//	private VisionArchive visionArchive;
	private VisionHorizontal visionHorizontal;
	private VisionVertical visionVertical;
	private Drivetrain drivetrain;
	private Claw claw;
		
	public VisionCorrectAimCommand(){
		super("visioncorrectaimcommand");
		//visionArchive = Components.getInstance().visionArchive;
		
		visionHorizontal = Components.getInstance().visionHorizontal;
		visionVertical = Components.getInstance().visionVertical;
		
		drivetrain = Components.getInstance().drivetrain;
		claw = Components.getInstance().claw;
		requires(drivetrain);
		requires(claw);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		//drivetrain.setBrakeMode(true);
		//visionArchive.setActive(true);
		
		System.out.println("Initializing...");
		
		visionHorizontal.setSetpoint(237);
		visionVertical.setSetpoint(95);
		
		visionVertical.enable();
		visionHorizontal.enable();
	}

	@Override
	protected void execute() {
		//visionArchive.update();
	}

	@Override
	protected boolean isFinished() {
		//return visionArchive.isOnTarget();
		return visionHorizontal.onTarget() && visionVertical.onTarget();
	}

	@Override
	protected void end() {
		drivetrain.setDrivetrain(0.0, 0.0);
		claw.setTalon(0.0);
		
		drivetrain.setBrakeMode(false);
		
		//visionArchive.setActive(false);
		//visionArchive.offsetting = false;
	}

	@Override
	protected void interrupted() {
		end();
	}

}
