package modelo;

import modelo.Mail;
import controle.Principal;
import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import static java.lang.Thread.sleep;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import javax.mail.MessagingException;
import static modelo.Mail.sendMail;

public class Serial implements SerialPortEventListener   {
    public String nomeUsuario;
    public String emailUsuario;
    String temperatura;
    int Status=0;
	SerialPort serialPort;
        /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = {
			"COM3", // Windows
			};
	/** Buffered input stream from the port */
	private InputStream input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public void initialize(String nome, String email) {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// iterate through, looking for the port
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}

		if (portId == null) {
			System.out.println("LOG: PORTA COM NÃO ENCONTRADA!");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println("LOG: "+e.toString());
		}
                nomeUsuario=nome;
                emailUsuario=email;
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
            
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int available = input.available();
				byte chunk[] = new byte[available];
				input.read(chunk, 0, available);
                                temperatura=(new String(chunk));

                                temperaturaAtual(temperatura);

                                close();
                              
                                
                             
                                
			} catch (Exception e) {
				System.err.println("LOG: "+e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}
        
        public void temperaturaAtual(String temperatura) throws MessagingException, InterruptedException {
            String temperaturaSaida;

            System.out.println(temperatura); 
            Calendar c = Calendar.getInstance();
            if (temperatura.length()==1){
                temperaturaSaida=("0"+temperatura.charAt(0)+".0");
            }  else if (temperatura.length()==2){
                temperaturaSaida=(temperatura.charAt(0)+temperatura.charAt(1)+".0");
        }
            else {
                temperaturaSaida=(temperatura.charAt(0)+temperatura.charAt(1)+"."+temperatura.charAt(2));
        }
           // DecimalFormat df = new DecimalFormat("0.#");
            sendMail("smtp.gmail.com", 465, "mailtemperature@gmail.com",
                    "ifsphto123", "mailtemperature@gmail.com",
                    emailUsuario, "[MailTemperature] "+c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+
                            "/"+c.get(Calendar.YEAR)+ " "+c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE),
                    "<html><body><b><font size=\"12\">Ol&aacute; "+nomeUsuario+", <br>Temperatura Atual:</font>"
                            + " </b><font size=\"12\" color=\"red\">"+
                            temperaturaSaida+"&ordm;C</font></b><font size=\"12\" color=\"black\">"+
                            "<br>Data: </body></html>"+c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH)+1)+
                            "/"+c.get(Calendar.YEAR)+"<br>Hora: "+c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE));
            System.out.println("LOG: E-MAIL ENVIADO");

           
        }
        
  
}


