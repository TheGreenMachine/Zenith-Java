package com.edinarobotics.zenith;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;


public class DrawControllers {
	public static void main(String[] args) {
		DrawControllers draw = new DrawControllers();
		draw.drawGamepad();
	}
	
	private void drawGamepad() {
		File currDir = new File(".");
		File controlFile = findFile(currDir, "Controls.java");
		if (controlFile == null) {
			System.out.println("Controls.java file not found");
			System.exit(0);
		}

		List<String> commands = readCommands(controlFile);
		List<CommandParts> parts = new ArrayList<CommandParts>();
		for (String cmd : commands) {
			CommandParts cmdParts = parseCommand(cmd);
			System.out.println("   got command: " + cmd + "    " + cmdParts);
			parts.add(cmdParts);
		}
		createPicture(parts);
	}
	
	private void createPicture(List<CommandParts> commandPartList) {
		final String picFileName = "controller.png";
		File picFile = findFile(new File("."), picFileName);
		if (picFile == null || !picFile.exists()) {
			System.out.println("Can't find picture file: " + picFileName);
			return;
		}
		try {
			BufferedImage image = ImageIO.read(picFile);
			System.out.println("Image loaded: " + image.getWidth() + "x" + image.getHeight());
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	private List<String> readCommands(File file) {
		List<String> commands = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String ln;
			while ((ln = reader.readLine()) != null) {
				ln = ln.trim();
				if (isCommand(ln)) {
					commands.add(ln);
				}
			}
		} catch (IOException ie) {
			System.out.println("Error while ready file: " + ie.getMessage());
		} finally {
			if (reader != null) {
				try { reader.close(); } catch (Exception e) {}
			}
		}
		
		return commands;
	}
	
	private File findFile(File baseDir, String fileName) {
		File files[] = baseDir.listFiles();
		for (File f : files) {
			if (f.isFile() && f.getName().equals(fileName)) {
				return f;
			}
			if (f.isDirectory()) {
				if (!f.getName().equals(".git") && !f.getName().equals("bin") && !f.getName().equals("build")) {
					File testFile = findFile(f, fileName);
					if (testFile != null) {
						return testFile;
					}
				}
			}
		}
		return null;
	}
		
	private boolean isCommand(String str) {
		if (str.startsWith("gamepad") && str.endsWith(";")) {
			for (GamepadControl control : GamepadControl.values()) {
				if (str.contains(control.commandStr)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private CommandParts parseCommand(String ln) {
		Pattern pattern = Pattern.compile("(\\w+)\\.(\\w+)\\(\\)\\.(\\w+)\\(\\s*new\\s+(\\w+\\(.*?\\)).*");
		Matcher matcher = pattern.matcher(ln);
		CommandParts commandParts = new CommandParts();
		if (matcher.matches()) {
			commandParts.controller = matcher.group(1);
			String gameCtrl = matcher.group(2);
			commandParts.control = GamepadControl.findControl(gameCtrl);
			commandParts.action = matcher.group(3);
			commandParts.command = matcher.group(4);
		}
		return commandParts;
	}
	
	class CommandParts {
		String controller;
		GamepadControl control;
		String action;
		String command;
		
		public String toString() {
			return "controller: " + controller 
					+ "; control: " + control.name()
					+ "; action: " + action
					+ "; command: " + command;
		}
	}
	
	enum GamepadControl {
		LeftBumper(".leftBumper()."),
		RightBumper(".rightBumper()."),
		LeftTrigger(".leftTrigger()."),
		RightTrigger(".rightTrigger()."),
		DiamondLeft(".diamondLeft()."),
		DiamondDown(".diamondDown()."),
		DiamondRight(".diamondRight()."),
		DiamondUp(".diamondUp()."),
		MiddleLeft(".middleLeft()."),
		MiddleRight(".middleRight()."),
		LeftJoystickButton(".leftJoystickButton()."),
		RightJoystickButton(".rightJoystickButton()."),
		DPadLeft(".dPadLeft()."),
		DPadDown(".dPadDown()."),
		DPadRight(".dPadRight()."),
		DPadUp(".dPadUp()."),
		GetLeftJoystick(".getLeftJoystick()."),
		GetRightJoystick(".getRightJoystick().");

		String commandStr;
		
		GamepadControl(String str) {
			commandStr = str;
		}
		
		static GamepadControl findControl(String str) {
			for (GamepadControl control : values()) {
				if (control.name().equalsIgnoreCase(str)) {
					return control;
				}
			}
			return null;
		}
	}
}
