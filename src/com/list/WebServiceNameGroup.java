package com.list;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WebServiceNameGroup extends DefaultHandler {

	
	
	private static String NAMESPACE = "http://nmc.com/";
	//Webservice URL - WSDL File location	
	private static String URL = "http://10.200.177.209:8080/WebServiceNmc/Service?WSDL";
	//SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://nmc.com/";
	
	public static String[] getGroup(String webMethName, String LOGIN, String MOTDEPASSE) {
		String resTxt[] = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo login = new PropertyInfo();
		// Set Name
		login.setName("user");
		// Set Value
		login.setValue(LOGIN);
		// Set dataType
		login.setType(String.class);
		
		PropertyInfo motdepasse = new PropertyInfo();
		// Set Name
		motdepasse.setName("password");
		// Set Value
		motdepasse.setValue(MOTDEPASSE);
		// Set dataType
		motdepasse.setType(String.class);
		
		// Add the property to request object
		request.addProperty(login);
		request.addProperty(motdepasse);
		
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		SoapObject response;
		System.setProperty("http.keepAlive", "false");
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invoke web service
			androidHttpTransport.call(NAMESPACE+webMethName, envelope);
			// Get the response
			response = (SoapObject) envelope.bodyIn;
			
			String [] test = new String[response.getPropertyCount()];
			for (int i = 0; i < response.getPropertyCount(); i++) {
				test[i] = response.getProperty(i).toString(); 
			}
			resTxt = test;

		}catch (HttpResponseException e){
			e.getMessage();
			
		} catch (IOException e){
			e.getMessage();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return resTxt;
	}
	
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
	}
}
