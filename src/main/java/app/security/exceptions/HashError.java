package app.security.exceptions;

import java.util.HashMap;

public class HashError {


public static HashMap<String,String> createHashErrorOutput(String message){
    HashMap<String,String> errors = new HashMap<>();
    errors.put("errorMessage",message);
    return errors;
}


}
