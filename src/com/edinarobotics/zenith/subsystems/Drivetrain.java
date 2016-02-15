package com.edinarobotics.zenith.subsystems;

import com.edinarobotics.utils.*;
import com.edinarobotics.utils.controllers.SpeedControllerWrapper;
import com.edinarobotics.utils.subsystems.Subsystem1816;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

public class Drivetrain extends Subsystem1816 {

	private SpeedControllerWrapper leftSide, rightSide;
	private CANTalon topLeft, topRight, middleLeft, middleRight, bottomLeft, bottomRight;
	private double verticalStrafe, rotation;

	private DoubleSolenoid solenoid;
	private boolean toggled = false;

	private final double P = 0.8;
	private final double I = 0.0001;
	private final double D = 0.0001;
	
	private boolean brakeMode = false;
	private boolean lowGear;
	private final double LOW_GEAR_SPEED = 0.50;

	public Drivetrain(int topLeft, int topRight, int middleLeft, int middleRight, 
			int bottomLeft, int bottomRight, int pcmId, int shiftingPcmId, int shiftingPcmId2) {
		
		this.topLeft = new CANTalon(topLeft);
		this.topRight = new CANTalon(topRight);
		this.middleLeft = new CANTalon(middleLeft);
		this.middleRight = new CANTalon(middleRight);
		this.bottomLeft = new CANTalon(bottomLeft);
		this.bottomRight = new CANTalon(bottomRight);
		
		this.middleLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.middleRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		this.middleLeft.configEncoderCodesPerRev(256);
		this.middleRight.configEncoderCodesPerRev(256);
		
		leftSide = new SpeedControllerWrapper(this.topLeft, this.middleLeft, this.bottomLeft);
		rightSide = new SpeedControllerWrapper(this.topRight, this.middleRight, this.bottomRight);
		
		
		leftSide.setPID(P, I, D);
		rightSide.setPID(P, I, D);
		
		leftSide.setInverted(true); 
		
		solenoid = new DoubleSolenoid(pcmId, shiftingPcmId, shiftingPcmId2);
		
	}

	@Override
	public void update() {
		double leftVelocity = verticalStrafe;
		double rightVelocity = verticalStrafe;
		
		if (rotation > 0) {
			rightVelocity -= rotation;
			leftVelocity += rotation;
		} else if (rotation < 0) {
			leftVelocity += rotation;
			rightVelocity -= rotation;
		}
		
		leftSide.set(leftVelocity);
		rightSide.set(rightVelocity);
		
		leftSide.enableBrakeMode(brakeMode);
		rightSide.enableBrakeMode(brakeMode);
		
		if (toggled)
			solenoid.set(Value.kForward);
		else
			solenoid.set(Value.kReverse);

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
	
	public void setBrakeMode(boolean brakeMode) {
		this.brakeMode = brakeMode;
		update();
	}
	
	public boolean getBrakeMode() {
		return brakeMode;
	}

	public boolean getToggled() {
		return toggled;
	}

	public CANTalon getMiddleLeft() {
		return middleLeft;
	}

	public CANTalon getMiddleRight() {
		return middleRight;
	}
	
}
