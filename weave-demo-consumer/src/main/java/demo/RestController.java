package demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

/**
 * Created by khazrak on 2015-06-27.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String getFromService()
    {
        ResourceBundle properties = ResourceBundle.getBundle("service");
        RestTemplate restTemplate = new RestTemplate();

        String serviceHostname = properties.getString("servicename");

        String res = restTemplate.getForObject("http://"+serviceHostname+":8080/rest/hostname", String.class);

        return "FROM "+getLocalHostname() + ": " + res;
    }

    private String getLocalHostname()
    {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return "Unknown";
    }

}
