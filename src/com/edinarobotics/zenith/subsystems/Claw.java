package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

public class Claw extends Subsystem1816 {

	private CANTalon talon;
	private AnalogPotentiometer potentiometer;

	private double target = 0.0;
	public boolean preset = false;

	private final double P = 0.8;
	private final double I = 0.0001;
	private final double D = 0.0001;

	public Claw(int talon, int potentiometer) {
		this.talon = new CANTalon(talon);
		this.potentiometer = new AnalogPotentiometer(potentiometer);

		this.talon.setP(P);
		this.talon.setI(I);
		this.talon.setD(D);

	}

	@Override
	public void update() {
		if (!preset)
			talon.set(target);

		System.out.println("Current state: (preset or not)" + preset);
		System.out.println("Current reading: " + getCurrentPosition());
		System.out.println("Current target: " + (target + 400));
		System.out.println("Current motor value: " + talon.get());
	}

	public enum ClawTarget {

		BOTTOM(-1), 
		HIGH_POWER(20), 
		LOW_POWER(24),
		TOP(26);

		private double target;

		ClawTarget(double target) {
			this.target = target;
		}

		public double getTarget() {
			return target;
		}

	}

	public double getTarget() {
		return target;
	}

	public void resetTarget() {
		target = getCurrentPosition();
		update();
	}

	public void setTarget(ClawTarget level) {
		preset = true;
		target = level.getTarget();
		update();
	}

	public void setTarget(double target) {
		this.target = -target * .75;
		update();
	}

	public void setDefaultCommand(Command command) {
		if (getDefaultCommand() != null) {
			super.getDefaultCommand().cancel();
		}
		super.setDefaultCommand(command);
	}

	public int getCurrentPosition() {
		return (int) (potentiometer.get() * 1000);
	}

	public CANTalon getTalon() {
		return talon;
	}

}
