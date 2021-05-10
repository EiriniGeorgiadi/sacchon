package security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.restlet.Request;


public class Authentication {
    private final String username;
    private final String password;


    public Authentication(Request request) {
        final String[] values;
        final String authorization = request.getHeaders().getFirstValue("Authorization");
        authorization.toLowerCase().startsWith("basic");
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        values = credentials.split(":", 2);
        username = values[0];
        password = values[1];
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
