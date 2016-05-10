package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;
import com.edinarobotics.zenith.Components;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;

public class Claw extends Subsystem1816 {

	private CANTalon talon;
	private AnalogPotentiometer potentiometer;
	private DigitalInput limitSwitch;
	
	private static Preferences preferences;
	
	private Solenoid brakeSolenoid;
	
	private double target = 860;
	private double zero = 0;
	public boolean preset = false;
	public boolean brake = false;
	
	private double clawSpeed = 0.0;
	
	private final double P = 0.8;
	private final double I = 0.0001;
	private final double D = 0.0001;
	
	public Claw(int talon, int potentiometer, int pcmId, int solenoidPcmId, int limitSwitch) {
		this.talon = new CANTalon(talon);
		this.potentiometer = new AnalogPotentiometer(potentiometer);
		this.limitSwitch = new DigitalInput(limitSwitch);
		
		preferences = Preferences.getInstance();	
				
		brakeSolenoid = new Solenoid(pcmId, solenoidPcmId);
	}
	
	
	@Override
	public void update() {
		
//		if (!preset) {
//			if (!limitSwitch.get()) {
//				talon.set(0.0);
//			} else {
//				talon.set(clawSpeed);
//			}
//		}
		
		talon.set(clawSpeed);
		
//		System.out.println("Potentiometer value: " + getCurrentPosition());
		
			
//		System.out.println("Potentiometer Value: " + getCurrentPosition());
		System.out.println("Limit Switch: " + limitSwitch.get());

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
			return -preferences.getDouble(name, 0) + Components.getInstance().claw.getZero();
		}
		
	}
	
	public enum ClawSpeed {
		LUDICROUS(1.0),
		INSANE(0.7),
		FAST(0.5),
		SLOW(0.3),
		VISION_SLOW(0.2),
		STOP(0.0);
		
		private double speed;
		
		ClawSpeed(double speed) {
			this.speed = speed;
		}
		
		public double getSpeed() {
			return speed;
		}
	}
	
	public void moveUp(ClawSpeed clawSpeed) {
		this.clawSpeed = -clawSpeed.getSpeed();
		update();
	}
	
	public void moveDown(ClawSpeed clawSpeed) {
		this.clawSpeed = clawSpeed.getSpeed();
		update();
	}
	
	public void stopClaw() {
		clawSpeed = 0.0;
		update();
	}
	
	public void setTarget(ClawTarget target) {
		this.target = target.getTarget();
		update();
	}
	
	public void setClawSpeed(double clawSpeed) {
		this.clawSpeed = -clawSpeed * .75;
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
	
	public boolean getLimitSwitch(){
		return limitSwitch.get();
	}
	
	public void resetZero(){
		zero = getCurrentPosition();
	}
	
	public double getZero(){
		return zero;
	}

}