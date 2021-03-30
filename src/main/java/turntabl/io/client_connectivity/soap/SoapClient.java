package turntabl.io.client_connectivity.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import turntabl.io.clientconnectivity.wsdl.GetOrderRequest;
import turntabl.io.clientconnectivity.wsdl.GetOrderResponse;


public class SoapClient extends WebServiceGatewaySupport {
    public GetOrderResponse orderResponse(int orderId, int clientId, int productId, int portfolioId){
        GetOrderRequest request = new GetOrderRequest();
        request.setOrderId(orderId);
        request.setClientId(clientId);
        request.setProductId(productId);
        request.setPortfolioId(portfolioId);
//        send to reporting service
        new SoapActionCallback("http://turntabl.io/get-client-order/GetOrderRequest");

        return (GetOrderResponse) getWebServiceTemplate()
                .marshalSendAndReceive("https://simba-order-validation-service.herokuapp.com/ws/soapOrders",request);
    }
}
