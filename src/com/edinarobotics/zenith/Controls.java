package com.edinarobotics.zenith;

import java.util.ArrayList;
import java.util.List;

import com.edinarobotics.utils.gamepad.FilteredGamepad;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.gamepad.gamepadfilters.DeadzoneFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilterSet;
import com.edinarobotics.utils.gamepad.gamepadfilters.PowerFilter;
import com.edinarobotics.zenith.commands.DisableClawBrakeCommand;
import com.edinarobotics.zenith.commands.EnableClawBrakeCommand;
import com.edinarobotics.zenith.commands.FireShooterCommand;
import com.edinarobotics.zenith.commands.PullInSolenoidCommand;
import com.edinarobotics.zenith.commands.RunClawToTargetCommand;
import com.edinarobotics.zenith.commands.RunCollectorCommand;
import com.edinarobotics.zenith.commands.SetLowGearCommand;
import com.edinarobotics.zenith.commands.ToggleBrakeModeCommand;
import com.edinarobotics.zenith.commands.ToggleDriveOrientationCommand;
import com.edinarobotics.zenith.commands.ToggleShiftingCommand;
import com.edinarobotics.zenith.subsystems.Claw.ClawTarget;

public class Controls {

	private static Controls instance;

	public final Gamepad gamepad0;
	public final Gamepad gamepad1;

	private Controls() {
		List<GamepadFilter> gamepadFilters = new ArrayList<GamepadFilter>();
		gamepadFilters.add(new DeadzoneFilter(0.05));
		gamepadFilters.add(new PowerFilter(1));
		GamepadFilterSet driveGamepadFilterSet = new GamepadFilterSet(gamepadFilters);
		gamepad0 = new FilteredGamepad(0, driveGamepadFilterSet);
		gamepad1 = new FilteredGamepad(1, driveGamepadFilterSet);

		gamepad0.rightTrigger().whenPressed(new SetLowGearCommand(true));
		gamepad0.rightTrigger().whenReleased(new SetLowGearCommand(false));

		gamepad0.rightBumper().whenPressed(new ToggleShiftingCommand());
		
		gamepad0.leftBumper().whenPressed(new ToggleBrakeModeCommand());
		gamepad0.leftBumper().whenReleased(new ToggleBrakeModeCommand());
		
		gamepad0.middleRight().whenPressed(new ToggleDriveOrientationCommand());
		
		gamepad1.diamondUp().whenPressed(new RunClawToTargetCommand(ClawTarget.BOTTOM, gamepad1));
		gamepad1.diamondRight().whenPressed(new RunClawToTargetCommand(ClawTarget.LOW_POWER, gamepad1));
		gamepad1.diamondDown().whenPressed(new RunClawToTargetCommand(ClawTarget.HIGH_POWER, gamepad1));	
		
		gamepad1.dPadUp().whenPressed(new RunCollectorCommand(0.5));
		gamepad1.dPadUp().whenReleased(new RunCollectorCommand(0));
		
		gamepad1.dPadDown().whenPressed(new RunCollectorCommand(-0.5));
		gamepad1.dPadDown().whenReleased(new RunCollectorCommand(0));
		
		gamepad1.leftBumper().whenPressed(new FireShooterCommand(false));
		gamepad1.leftTrigger().whenPressed(new FireShooterCommand(true));
		
		gamepad1.middleRight().whenPressed(new RunCollectorCommand(0.2));
		gamepad1.middleRight().whenReleased(new RunCollectorCommand(0));
		
		gamepad1.middleLeft().whenPressed(new PullInSolenoidCommand());
		
		gamepad1.rightBumper().whenPressed(new DisableClawBrakeCommand());
		gamepad1.rightBumper().whenReleased(new EnableClawBrakeCommand());
		
		gamepad1.rightTrigger().whenPressed(new RunCollectorCommand(-0.375));
		gamepad1.rightTrigger().whenReleased(new RunCollectorCommand(0.0));
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
