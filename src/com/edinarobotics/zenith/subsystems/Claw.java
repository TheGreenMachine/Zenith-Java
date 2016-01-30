package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;
import com.edinarobotics.zenith.Components;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Command;

public class Claw extends Subsystem1816 {

	private CANTalon talon;
	private AnalogPotentiometer potentiometer;
	private double target = 0.0;
	
	public Claw(int talonChannel) {
		talon = new CANTalon(talonChannel);
		talon.changeControlMode(TalonControlMode.Position);
		potentiometer = Components.getInstance().potentiometer;
	}
	
	@Override
	public void update() {
		if (target > getAngle()) {
			talon.set(0.2);
		} else if (target < getAngle()) {
			talon.set(-0.2);
		} else {
			talon.set(0.0);
		}
	}
	
	public enum ClawAngle {
		FLOOR(0.0),
		SHOOT(45.0),
		TOP(90.0);
		
		public double angle;
		
		private ClawAngle(double angle) {
			this.angle = angle;
		}
		
		public double getAngle() {
			return angle;
		}
	}
	
	public void setClawAngle(ClawAngle angle) {
		target = angle.getAngle();
		update();
	}
	
	public void setClawAngle(double joystickValue) {		
		target += joystickValue * 3;
		update();
	}
	
	public void setDefaultCommand(Command command) {
		if (getDefaultCommand() != null) {
			super.getDefaultCommand().cancel();
		}
		super.setDefaultCommand(command);
	}
	
	public double getAngle() {
		return potentiometer.get();
	}
	
	public double getTarget() {
		return target;
	}
	
	public void setTarget(double angle) {
		target = angle;
	}

}
