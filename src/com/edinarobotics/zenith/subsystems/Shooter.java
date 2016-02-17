package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Timer;

public class Shooter extends Subsystem1816 {

	private DoubleSolenoid solenoid;
	private boolean toggle = false;
	
	public Shooter(int pcmId, int solenoidPCM1, int solenoidPCM2) {
		solenoid = new DoubleSolenoid(pcmId, solenoidPCM1, solenoidPCM2);
		solenoid.set(Value.kOff);
	}
	
	@Override
	public void update() {
		if (toggle) {
			solenoid.set(Value.kOff);
			
			Timer.delay(0.1);
			
			solenoid.set(Value.kReverse);
			
			Timer.delay(0.5);
			
			solenoid.set(Value.kForward);
			
			toggle = false;
		}		
	}
	
	public void toggleShooter() {
		toggle = true;
		update();
	}
	
	public void pullIn() {
		solenoid.set(Value.kForward);
	}

}
