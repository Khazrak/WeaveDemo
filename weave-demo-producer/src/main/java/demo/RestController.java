package demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/**
 * Created by khazrak on 2015-06-27.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {

    /**
     * Returns the Time as JSON
     * @return
     */
    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public LocalDateTime getTime()
    {
        return LocalDateTime.now();
    }

    /**
     * Returns the Hostname of the "machine"
     * @return
     */
    @RequestMapping(value = "/hostname", method = RequestMethod.GET)
    public String host()
    {
        String hostname = "Unknown";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return hostname;
    }

}
