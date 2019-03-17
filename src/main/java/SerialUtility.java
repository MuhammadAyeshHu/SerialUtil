

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import java.util.Enumeration;

public class SerialUtility {
    private static int TIME_OUT = 2000;

    public static void setTimeOut(int timeOut) {
        TIME_OUT = timeOut;
    }

    public static SerialPort findPort(String portName) throws PortInUseException {
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            // open serial port, and use class name for the appName.
            if (equals(currPortId, portName)) return (SerialPort) currPortId.open("SerialUtilApp", TIME_OUT);
        }
        System.out.println("Could not find COM port.");
        return null;
    }

    public static SerialPort findPort(String[] portNames) throws PortInUseException {
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : portNames) {
                // open serial port, and use class name for the appName.
                if (equals(currPortId, portName)) return (SerialPort) currPortId.open("SerialUtilApp", TIME_OUT);
            }
        }
        System.out.println("Could not find COM port.");
        return null;
    }

    public static SerialPort findRaspberryPiPort(String portName) throws PortInUseException {
        // the next line is for Raspberry Pi and
        // gets us into the while loop and was suggested here was suggested
        // http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
        return findPort(portName);
    }

    public static SerialPort findRaspberryPiPort(String[] portNames) throws PortInUseException {
        // the next line is for Raspberry Pi and
        // gets us into the while loop and was suggested here was suggested
        // http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
        return findPort(portNames);
    }

    private static boolean equals(CommPortIdentifier currPortId, String portName) {
        return currPortId.getName().equals(portName);
    }


}
