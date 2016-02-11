package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Shooter extends Subsystem1816 {

	private DoubleSolenoid solenoid;
	private boolean extended;
	
	public Shooter(int pcmId, int solenoidPCM1, int solenoidPCM2) {
		solenoid = new DoubleSolenoid(pcmId, solenoidPCM1, solenoidPCM2);
		solenoid.set(Value.kForward);
	}
	
	@Override
	public void update() {
		if (extended) {
			solenoid.set(Value.kForward);
		} else {
			solenoid.set(Value.kReverse);
		}
	}
	
	public void toggleShooter() {
		extended = !extended;
		update();
	}

}
