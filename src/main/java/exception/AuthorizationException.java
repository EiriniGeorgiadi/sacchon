package exception;

import org.restlet.resource.ResourceException;

public class AuthorizationException extends ResourceException {
    public AuthorizationException() {
        super(400, "Not Authorized");
    }
}
