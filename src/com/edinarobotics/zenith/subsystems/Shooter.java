package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;

public class Shooter extends Subsystem1816 {

	private Solenoid solenoid;
	private boolean extended = false;
	
	public Shooter(int pcmId, int solenoidPCM) {
		solenoid = new Solenoid(pcmId, solenoidPCM);
		solenoid.set(!extended);
	}
	
	@Override
	public void update() {
		solenoid.set(extended);
	}
	
	public void toggleShooter() {
		extended = !extended;
		update();
	}

}
