package com.edinarobotics.zenith.subsystems;

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
	private boolean toggled = true;
	
	private boolean orientationSwapped = false;

	private final double P = 0.8;
	private final double I = 0.0001;
	private final double D = 0.0001;
	
	private boolean brakeMode = false;
	private boolean lowGear;
	private final double LOW_GEAR_SPEED = 0.50;
	private final double RAMP_RATE = 100;

	public Drivetrain(int topLeft, int topRight, int middleLeft, int middleRight, 
			int bottomLeft, int bottomRight, int pcmId, int shiftingPcmId, int shiftingPcmId2) {
		
		this.topLeft = new CANTalon(topLeft);
		this.topRight = new CANTalon(topRight);
		this.middleLeft = new CANTalon(middleLeft);
		this.middleRight = new CANTalon(middleRight);
		this.bottomLeft = new CANTalon(bottomLeft);
		this.bottomRight = new CANTalon(bottomRight);
		
		this.topLeft.setVoltageRampRate(RAMP_RATE);
		this.topRight.setVoltageRampRate(RAMP_RATE);
		this.middleLeft.setVoltageRampRate(RAMP_RATE);
		this.middleRight.setVoltageRampRate(RAMP_RATE);
		this.bottomLeft.setVoltageRampRate(RAMP_RATE);
		this.bottomRight.setVoltageRampRate(RAMP_RATE);
		
		this.middleLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.middleRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		this.middleLeft.configEncoderCodesPerRev(256);
		this.middleRight.configEncoderCodesPerRev(256);
		
		leftSide = new SpeedControllerWrapper(this.topLeft, this.middleLeft, this.bottomLeft);
		rightSide = new SpeedControllerWrapper(this.topRight, this.middleRight, this.bottomRight);
		
		leftSide.setPID(P, I, D);
		rightSide.setPID(P, I, D);
		
		leftSide.setInverted(true); 
		
		solenoid = new DoubleSolenoid(pcmId, shiftingPcmId, shiftingPcmId2);  // NJL Fault here
		
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
		
		if (isOrientationSwapped()) {
			leftVelocity = -leftVelocity * 1.04;
			rightVelocity = -rightVelocity;
		} else {
			leftVelocity = leftVelocity * 1.04;
		}
		
		leftSide.set(signum(leftVelocity, 1.0, -1.0));
		rightSide.set(signum(rightVelocity, 1.0, -1.0));
			
		leftSide.enableBrakeMode(brakeMode);
		rightSide.enableBrakeMode(brakeMode);
		
		if (toggled)
			solenoid.set(Value.kForward);
		else
			solenoid.set(Value.kReverse);
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
		this.rotation = rotation*1.0;
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
	
	public void setOrientationSwapped(boolean orientation) {
		this.orientationSwapped = orientation;
		update();
	}
	
	public boolean isOrientationSwapped() {
		return orientationSwapped;
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
	
	public void setLeft(double verticalStrafe, double rotation){
		double leftVelocity = verticalStrafe;
		
		if (rotation > 0) {
			leftVelocity += rotation;
		} else if (rotation < 0) {
			leftVelocity += rotation;
		}
		
		if (isOrientationSwapped()) {
			leftSide.set(-leftVelocity * 1.04);
		} else {
			leftSide.set(leftVelocity * 1.04);
		}
			
		leftSide.enableBrakeMode(brakeMode);
		
		if (toggled)
			solenoid.set(Value.kForward);
		else
			solenoid.set(Value.kReverse);
	}
	
	public void setRight(double verticalStrafe, double rotation){
		double rightVelocity = verticalStrafe;
		
		if (rotation > 0) {
			rightVelocity -= rotation;
		} else if (rotation < 0) {
			rightVelocity -= rotation;
		}
		
		if (isOrientationSwapped()) {
			rightSide.set(-rightVelocity);
		} else {
			rightSide.set(rightVelocity);
		}
			
		rightSide.enableBrakeMode(brakeMode);
		
		if (toggled)
			solenoid.set(Value.kForward);
		else
			solenoid.set(Value.kReverse);
	}
	
	private double signum(double value, double max, double min) {		
		if (value > max) {
			value = max;
		} else if (value < min) {
			value = min;
		}
		
		return value;
	}
	
}
