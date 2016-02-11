package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class Claw extends Subsystem1816 {

	private CANTalon talon;
	
	private double target = 578;
	
	private final double P = 0.8;
	private final double I = 0.0001;
	private final double D = 0.0001;
	
	public Claw(int talon, int potentiometer) {
		this.talon = new CANTalon(talon);
		
		this.talon.changeControlMode(TalonControlMode.Position);
		this.talon.setFeedbackDevice(FeedbackDevice.AnalogPot);
		
		this.talon.setP(P);
		this.talon.setI(I);
		this.talon.setD(D);
	}
	
	@Override
	public void update() {
		talon.set(target);
	}
	
	public enum ClawTarget {
		
		BOTTOM(578),
		SHOOT(591),
		TOP(603);
		
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
		this.target += target*.15;
		update();
	}
	
	public void setDefaultCommand(Command command) {
		if (getDefaultCommand() != null) {
			super.getDefaultCommand().cancel();
		}
		super.setDefaultCommand(command);
	}

}
