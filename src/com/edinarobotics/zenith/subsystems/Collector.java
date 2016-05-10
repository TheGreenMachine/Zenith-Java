package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.CANTalon;

public class Collector extends Subsystem1816 {

	private CANTalon frontTalon, sideTalon;
	private double forwardVelocity, sideVelocity;
	
	public Collector(int frontTalon, int sideTalon) {
		this.frontTalon = new CANTalon(frontTalon);
		this.sideTalon = new CANTalon(sideTalon);

	}
	
	@Override
	public void update() {
		frontTalon.set(forwardVelocity);
		sideTalon.set(sideVelocity);
	}
	
	public void setForwardVelocity(double velocity) {
		this.forwardVelocity = velocity;
		update();
	}
	public void setSideVelocity(double velocity) {
		this.sideVelocity = velocity;
		update();
	}
	
	public double getForwardVelocity() {
		return forwardVelocity;
	}
	
	public double getSideVelocity(){
		return sideVelocity;
	}

}
