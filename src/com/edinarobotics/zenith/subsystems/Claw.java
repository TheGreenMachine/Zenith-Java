package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Claw extends Subsystem1816 {

	private CANTalon talon, flashlightTalon;
	private AnalogPotentiometer potentiometer;
	
	private static Preferences preferences;
	
	private Solenoid brakeSolenoid;
	
	private double target = 399;
	public boolean preset = false;
	public boolean brake = false;
	public boolean flashlight = false;
	
	private final double P = 0.8;
	private final double I = 0.0001;
	private final double D = 0.0001;
	
	public Claw(int talon, int potentiometer, int pcmId, int solenoidPcmId, int flashlightPcmId, int flashlightTalon) {
		this.talon = new CANTalon(talon);
		this.flashlightTalon = new CANTalon(flashlightTalon);
		this.potentiometer = new AnalogPotentiometer(potentiometer);
		
		preferences = Preferences.getInstance();
				
		this.talon.setP(P);
		this.talon.setI(I);
		this.talon.setD(D);		
		
		target = this.talon.get();
		
		brakeSolenoid = new Solenoid(pcmId, solenoidPcmId);
	}
	
	
	@Override
	public void update() {
		if (!preset)
			talon.set(target);
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
			return -preferences.getDouble(name, 0) + 641;
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
	
	public void setTalon(double talonSet) {
		talon.set(talonSet);
	}
	
	public Solenoid getBrakeSolenoid() {
		return brakeSolenoid;
	}
	
	public void toggleBrakeSolenoid() {
		brakeSolenoid.set(!brakeSolenoid.get());
	}
	
	public void disableBrake() {
		if(brake){
			brakeSolenoid.set(true);
			brake = false;
		}
	}
	
	public void enableBrake(){
		if(!brake){
			brakeSolenoid.set(false);
			brake = true;
		}
	}
	
	public boolean isBrakeEnabled(){
		return brakeSolenoid.get();
	}
	
	public void turnOnFlashlight(){
		if(!flashlight){
			flashlightTalon.set(1.0);
			flashlight = true;
		}
	}
	
	public void turnOffFlashlight(){
		if(flashlight){
			flashlightTalon.set(0.0);
			flashlight = false;
		}
	}
	
	public boolean isFlashlightOn(){
		return flashlight;
	}

}