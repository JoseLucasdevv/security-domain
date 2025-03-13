package app.security.utils;


import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;



public class GenerateKeyEncoded {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = StandardCharsets.US_ASCII;
    public static String getKeyEncodedToken(){
        return new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII);
    }

}
