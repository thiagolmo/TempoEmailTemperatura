/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modelo.Serial;
import modelo.Usuario;
import visão.Tela;

/**
 *
 * @author Thiago
 */
public class Principal {
    public String nomeUsuario;
    public String emailUsuario;
    public int horaUsuario;
    public int minutoUsuario;
    public int portaUsuario;
    public int aux;
    public String temperaturaPublico;
    
    
    public static void main(String[] args) throws Exception {
        Tela tela = new Tela();
        tela.setVisible(true);
}
    public void iniciar( final Usuario usuario){
        nomeUsuario=usuario.nome;
        emailUsuario=usuario.email;
        horaUsuario=usuario.hora;
        minutoUsuario=usuario.minuto;
        
     int delay = 1500; // delay 
		int interval = ((horaUsuario*3600*1000)+(60*minutoUsuario*1000)); // intervalo.
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
		Serial main = new Serial();
		main.initialize(nomeUsuario,emailUsuario);
                System.out.println("LOG: GET TEMPERATURA...");
               

                
                        }
                }, delay, interval);
	}
    
    public static boolean validar(String email)  
    {  
        boolean isEmailIdValid = false;  
        if (email != null && email.length() > 0) {  
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);  
            Matcher matcher = pattern.matcher(email);  
            if (matcher.matches()) {  
                isEmailIdValid = true;  
            }  
        }  
        return isEmailIdValid;  
    }  
    
   
    
    
}
