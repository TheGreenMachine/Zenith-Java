package com.edinarobotics.zenith.commands;

import com.edinarobotics.zenith.Components;
import com.edinarobotics.zenith.subsystems.Claw;

import edu.wpi.first.wpilibj.command.Command;

public class FlashlightOnCommand extends Command{

	private Claw claw;
	private int count;
	
	public FlashlightOnCommand(){
		super("flashlightoncommand");
		this.claw = Components.getInstance().claw;
		requires(claw);
	}
	
	@Override
	protected void initialize() {
		count = 0;
	}

	@Override
	protected void execute() {
		if(count%2==0){
			if(claw.isFlashlightOn())
				claw.turnOffFlashlight();
			else
				claw.turnOnFlashlight();
		}
		count++;
	}

	@Override
	protected boolean isFinished() {
		return count>8;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
