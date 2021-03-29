package turntabl.io.client_connectivity.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import turntabl.io.clientconnectivity.wsdl.GetOrderRequest;
import turntabl.io.clientconnectivity.wsdl.GetOrderResponse;
import turntabl.io.clientconnectivity.wsdl.SoapOrder;

import java.math.BigInteger;

public class SoapClient extends WebServiceGatewaySupport {
    public GetOrderResponse orderResponse(SoapOrder order){
        GetOrderRequest request = new GetOrderRequest();
        request.setOrder(order);
//        send to reporting service
        new SoapActionCallback("http://turntabl.io/get-client-order/GetOrderRequest");

        return (GetOrderResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/soapOrders",request);
    }
}
