package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.CANTalon;

public class Collector extends Subsystem1816 {

	private CANTalon talon;
	private double velocity;
	
	public Collector(int talon) {
		this.talon = new CANTalon(talon);
	}
	
	@Override
	public void update() {
		talon.set(velocity);
	}
	
	public void setVelocity(double velocity) {
		this.velocity = velocity;
		update();
	}

}
