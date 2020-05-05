package com.list;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;

public class WebServiceUser {

	private static String NAMESPACE = "http://nmc.com/";
	//Webservice URL - WSDL File location	
	private static String URL = "http://10.200.177.209:8080/WebServiceNmc/Service?WSDL";
	//SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://nmc.com/";
	
	public static String ConnexionRemedy(String webMethName, String login, String password) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo connexionLogin = new PropertyInfo();
		// Set Name
		connexionLogin.setName("login");
		// Set Value
		connexionLogin.setValue(login);
		// Set dataType
		connexionLogin.setType(String.class);
		
		PropertyInfo connexionPass = new PropertyInfo();
		connexionPass.setName("password");
		connexionPass.setValue(password);
		connexionPass.setType(String.class);
		
		
		// Add the property to request object
		request.addProperty(connexionLogin);
		request.addProperty(connexionPass);
		
		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invoke web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to resTxt variable static variable
			resTxt = response.toString();

		}catch (HttpResponseException e){
			e.getMessage();
			
		} catch (IOException e){
			e.getMessage();
			
		} catch (Exception e) {
			//Print error
			e.printStackTrace();
			resTxt = e.getMessage();
		}
		//Return resTxt to calling object
		return resTxt;
	}
	
	
}
