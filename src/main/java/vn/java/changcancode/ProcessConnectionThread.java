package vn.java.changcancode;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.StreamConnection;

public class ProcessConnectionThread implements Runnable {

	private static final int EXIT_CMD = -1;
	private static final int KEY_RIGHT = 1;
	private static final int KEY_LEFT = 2;
	private StreamConnection connection;
	
	public ProcessConnectionThread(StreamConnection connection) {
		System.out.println("create ProcessConnectinThread");
		this.connection = connection;
	}
	@Override
	public void run() {
		try {
			InputStream inputStream = connection.openInputStream();
			System.out.println("Waiting for input");
			while(true) {
				int cmd = inputStream.read();
				if(cmd == EXIT_CMD) {
					System.out.println("Quit!!");
					break;
				}
				processCommand(cmd);
			}
		} catch (IOException e) {
			System.out.println("Error while open input stream. " + e);
		}
	}
	private void processCommand(int cmd) {
		Robot robot;
		try {
			robot = new Robot();
			switch (cmd) {
			case KEY_RIGHT:
				robot.keyPress(KeyEvent.VK_RIGHT);
				System.out.println("Right!");
				break;
			case KEY_LEFT:
				robot.keyPress(KeyEvent.VK_LEFT);
				System.out.println("Left");
				break;
			}
		} catch (AWTException e) {
			System.out.println("Error while create robot. " + e);
		}
	}

}
