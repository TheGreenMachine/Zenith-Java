package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class Shooter extends Subsystem1816 {

	public DoubleSolenoid solenoid;
	public Solenoid lowPowerSolenoid;
	public AnalogPotentiometer pressureSensor;
	public Claw claw;

	private boolean toggle = false;
	private boolean lowPower = false;

	public Shooter(int pcmId, int solenoidPCM1, int solenoidPCM2, 
			int solenoidPCM3, int pressureSensorChannel) {
		solenoid = new DoubleSolenoid(pcmId, solenoidPCM1, solenoidPCM2);
		lowPowerSolenoid = new Solenoid(pcmId, solenoidPCM3);
		pressureSensor = new AnalogPotentiometer(pressureSensorChannel);
		solenoid.set(Value.kOff);
	}

	@Override
	public void update() {
		if (pressureSensor.get() > .39) {
			if (toggle) {
				if (lowPower) {
					lowPowerSolenoid.set(true);
					solenoid.set(Value.kReverse);

					Timer.delay(0.5);

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

		System.out.println("Pressure switch value: " + pressureSensor.get());
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

}
