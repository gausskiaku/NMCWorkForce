package com.list;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WebServiceUpdate {


	private static String NAMESPACE = "http://nmc.com/";
	//Webservice URL - WSDL File location	
	private static String URL = "http://10.200.177.209:8080/WebServiceNmc/Service?WSDL";
	//SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://nmc.com/";
	
	public static String UpdateTicket(String webMethName, String entryId, String activity, String nomPropety1, String nomPropety2) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo id = new PropertyInfo();
		// Set Name
		id.setName(nomPropety1);
		// Set Value
		id.setValue(entryId);
		// Set dataType
		id.setType(String.class);
		
		
		PropertyInfo activt = new PropertyInfo();
		activt.setName(nomPropety2);
		activt.setValue(activity);
		activt.setType(String.class);
		
		
		// Add the property to request object
		request.addProperty(id);
		request.addProperty(activt);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		SoapPrimitive response;
		System.setProperty("http.keepAlive", "false");
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invoke web service
			androidHttpTransport.call(NAMESPACE+webMethName, envelope);
			// Get the response
			response =    (SoapPrimitive) envelope.getResponse();
			
			resTxt = response.toString();
		} catch (HttpResponseException e){
			e.getMessage();
			
		} catch (IOException e){
			e.getMessage();
			
		} catch (Exception e) {
			//Print error
			e.printStackTrace();
		} 
		
		return resTxt;
	}
	
}
