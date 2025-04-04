package app.security.utils;

public class GenerateCodeVerification {

    public static String GenerateCode(){
        int codeLength = 6;
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVXZKWY0123456789";
        StringBuilder code = new StringBuilder();

        for(int i =0 ; i < codeLength; i++){
            int numberSorted = (int) Math.floor(Math.random() * (CHARACTERS.length()) + 0);
            code.append(CHARACTERS.charAt(numberSorted));
        }
        return code.toString();
    }


}
