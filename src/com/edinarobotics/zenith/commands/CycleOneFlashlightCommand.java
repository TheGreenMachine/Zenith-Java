package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

public class CycleOneFlashlightCommand extends Command{

	private Claw claw;
	private int count;
	
	public CycleOneFlashlightCommand(){
		super("cycleoneflashlightcommand");
		this.claw = Components.getInstance().claw;
		requires(claw);
	}
	
	@Override
	protected void initialize() {
		claw.turnOnFlashlight();
		count=0;
	}

	@Override
	protected void execute() {
		count++;
	}

	@Override
	protected boolean isFinished() {
		return count>4;
	}

	@Override
	protected void end() {
		claw.turnOffFlashlight();
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
