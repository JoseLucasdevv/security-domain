package app.security.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class GenerateExpirationDate {


    public static Instant genExpirationDate(Long minutes){


        return LocalDateTime.now().plusMinutes(minutes).toInstant(ZoneOffset.of("-03:00"));
    }
}
