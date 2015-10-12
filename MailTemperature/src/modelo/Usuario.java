/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Thiago
 */
public class Usuario {
    public String nome;
    public String email;
    public int hora;
    public int minuto;
    


   /* public Usuario(String nome, String email, int hora, int minuto, int porta){
        this.nome=nome;
        this.email=email;
        this.hora=hora;
        this.minuto=minuto;
        this.porta=porta;
        
    }*/

   
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
   

   
    
}
