package com.edinarobotics.zenith;

import java.util.ArrayList;
import java.util.List;

import com.edinarobotics.utils.gamepad.FilteredGamepad;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.gamepad.gamepadfilters.DeadzoneFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilterSet;
import com.edinarobotics.utils.gamepad.gamepadfilters.PowerFilter;
import com.edinarobotics.zenith.commands.SetLowGearCommand;
import com.edinarobotics.zenith.commands.ToggleShiftingCommand;

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
