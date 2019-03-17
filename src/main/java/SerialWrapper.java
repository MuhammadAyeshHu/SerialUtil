import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

public class SerialWrapper implements Closeable {
    private final static int DATA_RATE = 9600;
    private SerialPort serialPort;
    private InputStream inputStream;
    private OutputStream outputStream;


    public SerialWrapper(String portName) throws UnsupportedCommOperationException,
            PortInUseException, NullPointerException {

        this.serialPort = SerialUtility.findPort(portName);
        // set port parameters
        serialPort.setSerialPortParams(DATA_RATE,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
    }

    public SerialWrapper(String[] portNames) throws UnsupportedCommOperationException,
            PortInUseException, NullPointerException {

        this.serialPort = SerialUtility.findPort(portNames);
        // set port parameters
        serialPort.setSerialPortParams(DATA_RATE,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
    }

    public SerialWrapper(int DATA_RATE, int DATABITS, int STOPBITS, int PARITY, String portName)
            throws PortInUseException, UnsupportedCommOperationException, NullPointerException {
        this.serialPort = SerialUtility.findPort(portName);
        // set port parameters
        this.serialPort.setSerialPortParams(DATA_RATE, DATABITS, STOPBITS, PARITY);
    }

    public SerialWrapper(int DATA_RATE, int DATABITS, int STOPBITS, int PARITY, String[] portNames)
            throws PortInUseException, UnsupportedCommOperationException, NullPointerException {
        this.serialPort = SerialUtility.findPort(portNames);
        // set port parameters
        this.serialPort.setSerialPortParams(DATA_RATE, DATABITS, STOPBITS, PARITY);
    }

    public InputStream getInputStream() throws IOException, NullPointerException {
        return this.inputStream = this.serialPort.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException, NullPointerException {
        return this.outputStream = this.serialPort.getOutputStream();
    }

    public void addEventListener(SerialPortEventListener portEventListener) throws TooManyListenersException {
        this.serialPort.addEventListener(portEventListener);
    }

    public void notifyOnDataAvailable(boolean b) {
        this.serialPort.notifyOnDataAvailable(b);
    }


    /**
     * This should be called when you stop using the port.
     * This will prevent port locking on platforms like Linux.
     */
    public synchronized void close() throws IOException {
        if (this.serialPort != null) {
            if (this.inputStream != null) this.inputStream.close();
            if (this.outputStream != null) this.outputStream.close();
            this.serialPort.removeEventListener();
            this.serialPort.close();
        }
    }
}
