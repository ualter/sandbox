package br.ujr.sandbox.webservices.jse;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class WebServicesJSE {
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/WebServicesJSE", new WebServicesJSE());
	}

	public double sum(int value1, int value2) {
		return value1 + value2;
	}

}
