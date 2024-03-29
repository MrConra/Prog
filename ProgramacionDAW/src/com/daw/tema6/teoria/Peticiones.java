package com.daw.tema6.teoria;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;


public class Peticiones {


	public static void realizarPetGet(String peticion, String ruta) throws IOException {
		
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
		    HttpGet httpGet = new HttpGet(peticion);
			// The underlying HTTP connection is still held by the response object
		    // to allow the response content to be streamed directly from the network socket.
		    // In order to ensure correct deallocation of system resources
		    // the user MUST call CloseableHttpResponse#close() from a finally clause.
		    // Please note that if response content is not fully consumed the underlying
		    // connection cannot be safely re-used and will be shut down and discarded
		    // by the connection manager.
		    try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
		        System.out.println(response1.getCode() + " " + response1.getReasonPhrase());

		        //Get the body of the response
		        HttpEntity entity1 = response1.getEntity();
		        
		        //Lee el cuerpo de la respuesta byte a byte.
		        //Como getContent() devuelve un InputStream, entonces debemos usar la clase InputStream
		        //para despu�s convertirlo a un BufferedReader (si queremos).
		        InputStream is = entity1.getContent();
		        //Abrimos/Creamos nuestro flujo de lectura
		        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8), 8);
		        //Abrimos/Creamos nuestro flujo de escritura
		        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(ruta)));
		        
		        //Procedemos a leer el contenido de la respuesta (ya almacenado en un BufferedReader
		        StringBuilder sb = new StringBuilder();
		        String linea = br.readLine();
				while(linea != null) {
					//Para ahorrar tiempo de ejecuci�n, primero vamos actualizando nuestro StringBuilder
					//y posteriormente lo escribiremos en el archivo
					sb.append(linea+"\n");
					linea = br.readLine();
				}
				//Escribimos el contenido del StringBuilder en nuestro archivo de texto plano
				bw.write(sb.toString());
				
				
				//5� Cerramos el flujo de lectura y de escritura
				br.close();
				bw.close();

		        // do something useful with the response body
		        // and ensure it is fully consumed
		        EntityUtils.consume(entity1);
		    }
		}
	}
}
