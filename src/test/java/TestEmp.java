
import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerRequestEvent;
import com.amazonaws.services.lambda.runtime.events.ApplicationLoadBalancerResponseEvent;
import com.google.gson.Gson;
import org.example.Handler;
import org.example.repo.Repository;
import org.example.services.ImplementService;
import org.junit.jupiter.api.Test;

public class TestEmp {

    @Test
    public void insertEmp() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setPath("/addEmp");
        requestEvent.setHttpMethod("POST");
        requestEvent.setBody("{\n" +
                "    \"firstName\": \"Naman\",\n" +
                "    \"lastName\": \"Singh\",\n" +
                "    \"phone\": \"123-456-7890\",\n" +
                "    \"jobTitle\": \"Flutter Developer\",\n" +
                "    \"email\": \"naman@example.com\"\n" +
                "  }");

        Handler handler = new Handler(new ImplementService(new Repository()));
        ApplicationLoadBalancerResponseEvent responseEvent = handler.handleRequest(requestEvent, null);
        System.out.println(responseEvent);
    }

    @Test
    public void getAllEmp() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();

        requestEvent.setHttpMethod("GET");
        requestEvent.setPath("/employees/getAllEmp");

        Handler handler = new Handler(new ImplementService(new Repository()));
        ApplicationLoadBalancerResponseEvent responseEvent = handler.handleRequest(requestEvent, null);

        System.out.println(responseEvent);
    }

    @Test
    public void getById() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setHttpMethod("GET");
        requestEvent.setPath("/employees/getEmp/5");

        Handler handler = new Handler(new ImplementService(new Repository()));
        ApplicationLoadBalancerResponseEvent responseEvent = handler.handleRequest(requestEvent, null);
        System.out.println(responseEvent);

    }

    @Test
    public void insertProducts() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setPath("/products/addProduct");
        requestEvent.setHttpMethod("POST");
        requestEvent.setBody(" {\n" +
                "    \"productId\": 102,\n" +
                "    \"productName\": \"Dell\"\n" +
                "  }");

        Handler handler = new Handler(new ImplementService(new Repository()));
        ApplicationLoadBalancerResponseEvent responseEvent = handler.handleRequest(requestEvent, null);
        System.out.println(responseEvent);
    }

    @Test
    public void getEmpProducts() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setPath("/getAssignEmpProducts");
        requestEvent.setHttpMethod("GET");

        Handler handler = new Handler(new ImplementService(new Repository()));
        ApplicationLoadBalancerResponseEvent responseEvent = handler.handleRequest(requestEvent, null);
        System.out.println(responseEvent.getBody());
    }

    @Test
    public void updateEmp() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setHttpMethod("PUT");
        requestEvent.setPath("/emp/update/1");
        requestEvent.setBody("{ " +
                "    \"jobTitle\": \"Web App Developer\"\n" +
                " }");

        Handler handler = new Handler(new ImplementService(new Repository()));
        ApplicationLoadBalancerResponseEvent responseEvent = handler.handleRequest(requestEvent, null);
        System.out.println(responseEvent);
    }

    @Test
    public void deleteEmp() {
        ApplicationLoadBalancerRequestEvent requestEvent = new ApplicationLoadBalancerRequestEvent();
        requestEvent.setPath("/emp/delete/4");
        requestEvent.setHttpMethod("DELETE");
        Handler handler = new Handler(new ImplementService(new Repository()));
        ApplicationLoadBalancerResponseEvent responseEvent = handler.handleRequest(requestEvent, null);
        System.out.println(responseEvent);
    }
}
