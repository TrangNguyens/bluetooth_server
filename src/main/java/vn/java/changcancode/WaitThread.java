package vn.java.changcancode;

import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class WaitThread implements Runnable{

	public static UUID uuid = new UUID(80087355);
	public WaitThread() {
	}
	
	@Override
	public void run() {
		waitForConnection();
	}

	private void waitForConnection() {
		LocalDevice local = null;
		StreamConnectionNotifier notifier = null;
		StreamConnection connection = null;
		try {
			local = LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);
			System.out.println("uuid: " + uuid.toString());
			String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
			notifier = (StreamConnectionNotifier) Connector.open(url);
		} catch (BluetoothStateException e) {
			System.out.println("Error while getting device. " + e);
		} catch (IOException e) {
			System.out.println("Error while connection. " + e);
		}
		while(true) {
			System.out.println("waiting for connection...");
			try {
				connection = notifier.acceptAndOpen();
				Thread processThread = new Thread(new ProcessConnectionThread(connection));
				processThread.start();
			} catch (IOException e) {
				System.out.println("Error while Accept and open. " + e);
			}
		}
	}
	
}
