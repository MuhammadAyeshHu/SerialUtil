import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TooManyListenersException;

public class TestingSerial {
    public static void main(String[] args) {
        try {
            SerialWrapper wrapper = new SerialWrapper("COM100");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        init(wrapper);
                    } catch (IOException | TooManyListenersException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            System.out.println("Started");
            Thread.sleep(100000);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private static void init(SerialWrapper wrapper) throws IOException, TooManyListenersException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(wrapper.getInputStream()));
        // add event listeners
        wrapper.addEventListener(new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                    try {
                        String inputLine = bufferedReader.readLine();
                        System.out.println(inputLine);
                    } catch (Exception e) {
                        System.err.println(e.toString());
                    }
                }
            }
        });
        wrapper.notifyOnDataAvailable(true);
    }
}
