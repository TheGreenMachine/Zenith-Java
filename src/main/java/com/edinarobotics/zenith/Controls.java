package com.edinarobotics.zenith;

import java.util.ArrayList;
import java.util.List;

import com.edinarobotics.utils.gamepad.FilteredGamepad;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.gamepad.gamepadfilters.DeadzoneFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilterSet;
import com.edinarobotics.utils.gamepad.gamepadfilters.PowerFilter;
import com.edinarobotics.zenith.commands.CalibratePotentiometerCommand;
import com.edinarobotics.zenith.commands.DisableClawBrakeCommand;
import com.edinarobotics.zenith.commands.EnableClawBrakeCommand;
import com.edinarobotics.zenith.commands.FireShooterCommand;
import com.edinarobotics.zenith.commands.PullInSolenoidCommand;
import com.edinarobotics.zenith.commands.RotateXDegreesCommand;
import com.edinarobotics.zenith.commands.RunClawToTargetCommand;
import com.edinarobotics.zenith.commands.RunCollectorForwardCommand;
import com.edinarobotics.zenith.commands.RunCollectorSideCommand;
import com.edinarobotics.zenith.commands.SetLowGearCommand;
import com.edinarobotics.zenith.commands.ToggleBrakeModeCommand;
import com.edinarobotics.zenith.commands.ToggleDriveOrientationCommand;
import com.edinarobotics.zenith.commands.ToggleShiftingCommand;
import com.edinarobotics.zenith.commands.VisionAutoAimCommand;
import com.edinarobotics.zenith.subsystems.Claw.ClawTarget;

public class Controls {

	private static Controls instance;

	public final Gamepad gamepad0;
	public final Gamepad gamepad1;

	private Controls() {
		List<GamepadFilter> gamepadFilter0 = new ArrayList<GamepadFilter>();
		gamepadFilter0.add(new DeadzoneFilter(0.05));
		gamepadFilter0.add(new PowerFilter(1));
		GamepadFilterSet driveGamepadFilterSet0 = new GamepadFilterSet(gamepadFilter0);
		
		List<GamepadFilter> gamepadFilter1 = new ArrayList<GamepadFilter>();
		gamepadFilter1.add(new DeadzoneFilter(0.025));
		gamepadFilter1.add(new PowerFilter(1));
		GamepadFilterSet driveGamepadFilterSet1 = new GamepadFilterSet(gamepadFilter1);
		
		gamepad0 = new FilteredGamepad(0, driveGamepadFilterSet0);
		gamepad1 = new FilteredGamepad(1, driveGamepadFilterSet1);

		gamepad0.rightTrigger().whenPressed(new SetLowGearCommand(true));
		gamepad0.rightTrigger().whenReleased(new SetLowGearCommand(false));

		gamepad0.rightBumper().whenPressed(new ToggleShiftingCommand());
		
		gamepad0.leftBumper().whenPressed(new ToggleBrakeModeCommand());
		gamepad0.leftBumper().whenReleased(new ToggleBrakeModeCommand());
		
		gamepad0.middleRight().whenPressed(new ToggleDriveOrientationCommand());
		
		gamepad0.middleLeft().whenPressed(new VisionAutoAimCommand(true));
		gamepad0.diamondRight().whenPressed(new RotateXDegreesCommand(90));
		
		gamepad1.diamondUp().whenPressed(new RunClawToTargetCommand(ClawTarget.BOTTOM, gamepad1));
		gamepad1.diamondRight().whenPressed(new RunClawToTargetCommand(ClawTarget.LOW_POWER, gamepad1));
		gamepad1.diamondDown().whenPressed(new RunClawToTargetCommand(ClawTarget.HIGH_POWER, gamepad1));	
		
		gamepad1.dPadUp().whenPressed(new RunCollectorForwardCommand(-0.8));
		gamepad1.dPadUp().whenReleased(new RunCollectorForwardCommand(0));
		
		gamepad1.dPadDown().whenPressed(new RunCollectorForwardCommand(0.8));
		gamepad1.dPadDown().whenReleased(new RunCollectorForwardCommand(0));
		
		gamepad1.dPadRight().whenPressed(new RunCollectorSideCommand(0.8));
		gamepad1.dPadRight().whenReleased(new RunCollectorSideCommand(0));
		
		gamepad1.dPadLeft().whenPressed(new RunCollectorSideCommand(-0.8));
		gamepad1.dPadLeft().whenReleased(new RunCollectorSideCommand(0));
		
		gamepad1.leftTrigger().whenPressed(new FireShooterCommand());
		
		gamepad1.middleLeft().whenPressed(new PullInSolenoidCommand());
		
		gamepad1.rightBumper().whenPressed(new DisableClawBrakeCommand());
		gamepad1.rightBumper().whenReleased(new EnableClawBrakeCommand());
		
		gamepad1.diamondLeft().whenPressed(new CalibratePotentiometerCommand());
		
	}

	/**
	 * Returns the proper instance of Controls. This method creates a new
	 * Controls object the first time it is called and returns that object for
	 * each subsequent call.
	 *
	 * @return The current instance of Controls.
	 */
	public static Controls getInstance() {
		if (instance == null) {
			instance = new Controls();
		}
		return instance;
	}

}
