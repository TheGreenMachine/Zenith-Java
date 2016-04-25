package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.sensors.PressureSensor;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import com.edinarobotics.zenith.Components;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class Shooter extends Subsystem1816 {

	private DoubleSolenoid solenoid;
	private Solenoid lowPowerSolenoid;
	private PressureSensor pressureSensor;
	
	private boolean toggle = false;
	private boolean lowPower = false;
	
	public Shooter(int pcmId, int solenoidPCM1, int solenoidPCM2, int lowPowerPCM) {
		solenoid = new DoubleSolenoid(pcmId, solenoidPCM1, solenoidPCM2);
		lowPowerSolenoid = new Solenoid(pcmId, lowPowerPCM);
		
		pressureSensor = new PressureSensor(1);
		
		solenoid.set(Value.kOff);
		lowPowerSolenoid.set(false);
	}
	
	@Override
	public void update() {
		if (toggle) {
			if (pressureSensor.getPressure() > 80) {
				
				if (lowPower) {
					
					lowPowerSolenoid.set(true);
					solenoid.set(Value.kReverse);
					
					Timer.delay(1);
					
					solenoid.set(Value.kForward);
					lowPowerSolenoid.set(false);
					
					Timer.delay(0.3);
					
					solenoid.set(Value.kOff);
				} else {
					
					solenoid.set(Value.kOff);
					
					Timer.delay(0.5);
					
					solenoid.set(Value.kReverse);
					
					Timer.delay(0.5);
					
					solenoid.set(Value.kForward);
					
					Timer.delay(0.3);
					
					solenoid.set(Value.kOff);
				}
				
				toggle = false;
			}
			
		}
	}
	
	public void toggleShooter(boolean lowPower) {
		toggle = true;
		this.lowPower = lowPower;
		update();
	}
	
	public void pullIn() {
		if (lowPower) {
			lowPowerSolenoid.set(false);
		}
		solenoid.set(Value.kForward);
	}

	public PressureSensor getPressureSensor() {
		return pressureSensor;
	}
	
	public boolean getLowPowerSolenoid() {
		return lowPowerSolenoid.get();
	}
	
	public Value getHighPowerSolenoid() {
		return solenoid.get();
	}
	
}
