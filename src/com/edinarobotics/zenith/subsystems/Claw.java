package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

public class Claw extends Subsystem1816 {

	private CANTalon talon;
	private AnalogPotentiometer potentiometer;
	
	private double target = 0.0;
	
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
		if (getCurrentPosition() < (target + 400)) {
			talon.set(0.25);
		} else if (getCurrentPosition() > (target + 400)) {
			talon.set(-0.25);
		} else {
			talon.set(0.0);
		}
		
		System.out.println(target);
		System.out.println("Potentiometer value: "+ potentiometer.get()*1000);
	}
	
	public enum ClawTarget {
		
		BOTTOM(0),
		SHOOT(21),	
		TOP(30);
		
		private double target;
		
		ClawTarget(double target) {
			this.target = target;
		}
		
		public double getTarget(){
			return target;
		}
		
	}
	
	public void setTarget(ClawTarget target) {
		this.target = target.getTarget();
		update();
	}
	
	public void setTarget(double target) {
		this.target += -target*.5;
		update();
	}
	
	public void setDefaultCommand(Command command) {
		if (getDefaultCommand() != null) {
			super.getDefaultCommand().cancel();
		}
		super.setDefaultCommand(command);
	}
	
	public double getCurrentPosition() {
		return potentiometer.get();
	}

}
