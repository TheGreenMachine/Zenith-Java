package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Command;

public class Claw extends Subsystem1816 {

	private CANTalon talon;
	private AnalogPotentiometer potentiometer;
	
	private double target = 0.0;
	public boolean preset = false;
	
	private final double P = 0.8;
	private final double I = 0.0001;
	private final double D = 0.0001;
	
	public Claw(int talon, int potentiometer) {
		this.talon = new CANTalon(talon);
		this.potentiometer = new AnalogPotentiometer(potentiometer);
				
		this.talon.setP(P);
		this.talon.setI(I);
		this.talon.setD(D);		
		
		target = this.talon.get();
	}
	
	@Override
	public void update() {
		int currentReading = (int) (potentiometer.get() * 1000);
		int targetReading = (int) (target + 400);
		
		if (preset) {
			if (currentReading > (targetReading + 2)) {
				talon.set(0.5);
			} else if ((targetReading - 2) > currentReading) {
				talon.set(-0.5);
			} else {
				talon.set(0.0);
				preset = false;
			}
		} else {
			talon.set(target);
		}
		
		System.out.println("Current state: (preset or not)" + preset);
		System.out.println("Potentiometer value: "+ potentiometer.get()*1000);
		System.out.println("Current reading: " + currentReading);
		System.out.println("Current target: " + targetReading);
		System.out.println("Current target value: " + target);
		System.out.println("Current motor value: " + talon.get());
	}
	
	public enum ClawTarget {
		
		BOTTOM(0),
		SHOOT(22),	
		TOP(26),
		UP_AGAINST_TOWER(24.5),
		BACKWARDS(33);
		
		public double target;
		
		ClawTarget(double target) {
			this.target = target;
		}
		
	}
	
	public void setTarget(ClawTarget a) {
		preset = true;
		target = a.target;
		update();
	}
	
	public void setTarget(double target) {
		this.target = -target*.75;
		update();
	}
	
	public void setDefaultCommand(Command command) {
		if (getDefaultCommand() != null) {
			super.getDefaultCommand().cancel();
		}
		super.setDefaultCommand(command);
	}
	
	public double getCurrentPosition() {
		return potentiometer.get() * 1000;
	}

}
