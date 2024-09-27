/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package calculadoraservicioweb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CalculadoraServicioWeb {

    // Método para consumir la operación de suma
    public String sumar(int numeroUno, int numeroDos) throws Exception {
        String url = "http://localhost:8081/calculadora/sumar?numeroUno=" + numeroUno + "&numeroDos=" + numeroDos;
        return sendGetRequest(url);
    }

    // Método para consumir la operación de resta
    public String restar(int numeroUno, int numeroDos) throws Exception {
        String url = "http://localhost:8081/calculadora/restar?numeroUno=" + numeroUno + "&numeroDos=" + numeroDos;
        return sendGetRequest(url);
    }
    
    // Método para consumir la operación de multiplicar
    public String multiplicar(int numeroUno, int numeroDos) throws Exception {
        String url = "http://localhost:8081/calculadora/multiplicar?numeroUno=" + numeroUno + "&numeroDos=" + numeroDos;
        return sendGetRequest(url);
    }
    
    // Método para consumir la operación de multiplicar
    public String dividir(int numeroUno, int numeroDos) throws Exception {
        String url = "http://localhost:8081/calculadora/dividir?numeroUno=" + numeroUno + "&numeroDos=" + numeroDos;
        return sendGetRequest(url);
    }

    // Método que realiza la petición GET y devuelve la respuesta
    private String sendGetRequest(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Configurar el método de petición
        con.setRequestMethod("GET");

        // Obtener la respuesta
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Devolver la respuesta como String
        return response.toString();
    }

    public static void main(String[] args) {
        try {
            CalculadoraServicioWeb client = new CalculadoraServicioWeb();
            APICalculadora calculadora = new APICalculadora();
            calculadora.setVisible(true);
            /*System.out.println("Resultado de la suma: " + client.sumar(3, 4));
            System.out.println("Resultado de la resta: " + client.restar(10, 5));
            System.out.println("Resultado de la resta: " + client.multiplicar(10, 5));
            System.out.println("Resultado de la resta: " + client.dividir(10, 5));
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}