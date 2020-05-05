package com.list;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;

public class WebServiceSave {
	private static String NAMESPACE = "http://nmc.com/";
	//Webservice URL - WSDL File location	
	private static String URL = "http://10.200.177.209:8080/WebServiceNmc/Service?WSDL";
	//SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://nmc.com/";
	
	public static String CreateTicket(String webMethName, String user, String passwd, 
										String summary, String description, String processType, String groupType, 
										String faultType, String dispatchGroup, String individu, String classification, 
										String zone, String region, String district, String dateStart, String handoverMode) {
		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		
		// User
		PropertyInfo userCT = new PropertyInfo();
		userCT.setName("user");
		userCT.setValue(user);
		userCT.setType(String.class);
		
		// PassWord
		PropertyInfo passwordCT = new PropertyInfo();
		passwordCT.setName("passwd");
		passwordCT.setValue(passwd);
		passwordCT.setType(String.class);
		
		// Summary 
		PropertyInfo summaryCT = new PropertyInfo();
		summaryCT.setName("summary");
		summaryCT.setValue(summary);
		summaryCT.setType(String.class);
		
		// Description
		PropertyInfo descriptionCT = new PropertyInfo();
		descriptionCT.setName("description");
		descriptionCT.setValue(description);
		descriptionCT.setType(String.class);
		
		// processType
		PropertyInfo processTypeCT = new PropertyInfo();
		processTypeCT.setName("processType");
		processTypeCT.setValue(processType);
		processTypeCT.setType(String.class);
		
		// groupType
		PropertyInfo groupTypeCT = new PropertyInfo();
		groupTypeCT.setName("groupType");
		groupTypeCT.setValue(groupType);
		groupTypeCT.setType(String.class);
		
		// faultType
		PropertyInfo faultTypeCT = new PropertyInfo();
		faultTypeCT.setName("faultType");
		faultTypeCT.setValue(faultType);
		faultTypeCT.setType(String.class);
		
		// dispatchGroup
		PropertyInfo dispatchGroupCT = new PropertyInfo();
		dispatchGroupCT.setName("dispatchGroup");
		dispatchGroupCT.setValue(dispatchGroup);
		dispatchGroupCT.setType(String.class);
		
		// individu
		PropertyInfo individuCT = new PropertyInfo();
		individuCT.setName("individu");
		individuCT.setValue(individu);
		individuCT.setType(String.class);

		// classification
		PropertyInfo classificationCT = new PropertyInfo();
		classificationCT.setName("classification");
		classificationCT.setValue(classification);
		classificationCT.setType(String.class);
		
		// zone
		PropertyInfo zoneCT = new PropertyInfo();
		zoneCT.setName("zone");
		zoneCT.setValue(zone);
		zoneCT.setType(String.class);
		
		// region
		PropertyInfo regionCT = new PropertyInfo();
		regionCT.setName("region");
		regionCT.setValue(region);
		regionCT.setType(String.class);
		
		// district
		PropertyInfo districtCT = new PropertyInfo();
		districtCT.setName("district");
		districtCT.setValue(region);
		districtCT.setType(String.class);
		
		// dateStartCT
		PropertyInfo dateStartCT = new PropertyInfo();
		dateStartCT.setName("dateStart");
		dateStartCT.setValue(dateStart);
		dateStartCT.setType(String.class); 
		
		// handoverMode
		PropertyInfo handoverModeCT = new PropertyInfo();
		handoverModeCT.setName("handoverMode");
		handoverModeCT.setValue(handoverMode);
		handoverModeCT.setType(String.class);
		
		// Add the property to request object
		request.addProperty(userCT);
		request.addProperty(passwordCT);
		request.addProperty(summaryCT);
		request.addProperty(descriptionCT);
		request.addProperty(processTypeCT);
		request.addProperty(groupTypeCT);
		request.addProperty(faultTypeCT);
		request.addProperty(dispatchGroupCT);
		request.addProperty(individuCT);
		request.addProperty(classificationCT);
		request.addProperty(zoneCT);
		request.addProperty(regionCT);
		request.addProperty(districtCT);
		request.addProperty(dateStartCT);
		request.addProperty(handoverModeCT);
		
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
