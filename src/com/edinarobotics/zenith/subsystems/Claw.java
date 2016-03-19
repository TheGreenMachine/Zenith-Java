package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;

public class Claw extends Subsystem1816 {

	private CANTalon talon;
	private AnalogPotentiometer potentiometer;
	
	private static Preferences preferences;
	
	private Solenoid brakeSolenoid;
	
	private double target = 399;
	private double current;
	public boolean preset = false;
	
	private final double P = 0.8;
	private final double I = 0.0001;
	private final double D = 0.0001;
	
	public Claw(int talon, int potentiometer, int pcmId, int solenoidPcmId) {
		this.talon = new CANTalon(talon);
		this.potentiometer = new AnalogPotentiometer(potentiometer);
		
		preferences = Preferences.getInstance();
				
		this.talon.setP(P);
		this.talon.setI(I);
		this.talon.setD(D);		
		
		target = this.talon.get();
		
		brakeSolenoid = new Solenoid(pcmId, solenoidPcmId);
		brakeSolenoid.set(true);
	}
	
	
	@Override
	public void update() {
		if (!preset) {
			talon.set(target);
		}
		
		System.out.println("Potentiometer Value: " + getCurrentPosition());
	}
	
	public enum ClawTarget {
		
		BOTTOM("BottomPosition"),
		LOW_BAR("LowBarPosition"),
		AUTO("AutonomousPosition"),
		HIGH_POWER("HighPowerPosition"),	
		LOW_POWER("LowPowerPosition"),
		ELLINGTON("Ellington");
		
		private String name;
		
		ClawTarget(String name) {
			this.name = name;
		}
		
		public double getTarget(){
			return preferences.getDouble(name, 0) + 820;
		}
		
	}
	
	public void setTarget(ClawTarget target) {
		this.target = target.getTarget();
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
		return (int) (potentiometer.get() * 1000);
	}
	
	public CANTalon getTalon() {
		return talon;
	}
	
	public Solenoid getBrakeSolenoid() {
		return brakeSolenoid;
	}
	
	public void toggleBrakeSolenoid() {
		brakeSolenoid.set(!brakeSolenoid.get());
	}
	
	public void disableBrake() {
		brakeSolenoid.set(true);
	}
	
	public void enableBrake(){
		brakeSolenoid.set(false);
	}
	
	public boolean isBrakeEnabled(){
		return brakeSolenoid.get();
	}

}