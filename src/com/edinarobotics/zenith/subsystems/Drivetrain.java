package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;

public class Drivetrain extends Subsystem1816 {

	private RobotDrive robotDrive;
	private CANTalon topLeft, topRight, middleLeft, middleRight, bottomLeft, bottomRight;
	private double verticalStrafe, rotation;
	
	private DoubleSolenoid solenoid;
	private boolean toggled = false;
	
	private final double P = 0.8;
	private final double I = 0.0001;
	private final double D = 0.0001;
	private boolean lowGear;
	private final double LOW_GEAR_SPEED = 0.50;

	public Drivetrain(int topLeft, int topRight, int middleLeft, int middleRight, 
			int bottomLeft, int bottomRight, int pcmId, int pcmId2) {
		this.topLeft = new CANTalon(topLeft);
		this.topRight = new CANTalon(topRight);
		this.middleLeft = new CANTalon(middleLeft);
		this.middleRight = new CANTalon(middleRight);
		this.bottomLeft = new CANTalon(bottomLeft);
		this.bottomRight = new CANTalon(bottomRight);
		
		this.topLeft.setPID(P, I, D);
		this.topRight.setPID(P, I, D);
		this.bottomLeft.setPID(P, I, D);
		this.bottomRight.setPID(P, I, D);
		
		robotDrive = new RobotDrive(this.topLeft, this.bottomLeft, this.topRight, 
				this.bottomRight);
		
		this.middleLeft.changeControlMode(TalonControlMode.Follower);
		this.middleRight.changeControlMode(TalonControlMode.Follower);
		
		this.middleLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.middleRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		this.middleLeft.configEncoderCodesPerRev(256);
		this.middleRight.configEncoderCodesPerRev(256);
		
		this.middleLeft.set(3);
		this.middleRight.set(7);
		
		solenoid = new DoubleSolenoid(10, pcmId, pcmId2);
		solenoid.set(Value.kForward);
	}

	@Override
	public void update() {
		robotDrive.arcadeDrive(-verticalStrafe, rotation);
		
		if (toggled)
			solenoid.set(Value.kForward);
		else
			solenoid.set(Value.kReverse); 
		
		System.out.println("Encoder Velocity: " + middleLeft.getEncVelocity());
		
		System.out.println("Solenoid value: " + solenoid.get().toString());
	}

	public void setDefaultCommand(Command command) {
		if (getDefaultCommand() != null) {
			super.getDefaultCommand().cancel();
		}
		super.setDefaultCommand(command);
	}

	public void setDrivetrain(double verticalStrafe, double rotation) {
		if (lowGear) {
			verticalStrafe *= LOW_GEAR_SPEED;
			rotation *= LOW_GEAR_SPEED;
		}

		this.verticalStrafe = verticalStrafe;
		this.rotation = rotation;
		update();
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
		update();
	}

	public void setVerticalStrafe(double verticalStrafe) {
		this.verticalStrafe = verticalStrafe;
		update();
	}

	public void setLowGear(boolean lowGear) {
		this.lowGear = lowGear;
		update();
	}
	
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
		update();
	}
	
	public boolean getToggled() {
		return toggled;
	}

}
