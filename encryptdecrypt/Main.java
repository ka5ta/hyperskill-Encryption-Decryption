package encryptdecrypt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {


    public static void main(String[] args) throws IOException {
        cypherMessage(args);
    }


    public static void cypherMessage(String[] args) throws IOException {

        Mode mode = Mode.enc;
        Algorithm algorithm = Algorithm.SHIFT;
        String message = "";
        int secret = 0;
        String outputFilename = "";

        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-mode":
                    mode = Mode.valueOf(args[i + 1]);
                    break;
                case "-key":
                    secret = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    message = args[i + 1].replace("\"", "");
                    break;
                case "-in":
                    message = Files.readString(Path.of(args[i + 1]));
                    break;
                case "-out":
                    outputFilename = args[i + 1];
                    break;
                case "-alg":
                    algorithm = Algorithm.valueOf(args[i + 1].toUpperCase());
                    break;
                default:
                    break;
            }
        }

        Cipher cipher = new Cipher();
        if (algorithm == Algorithm.UNICODE) {
            cipher.setCipherMethod(new CipherUnicode());
        } else {
            cipher.setCipherMethod(new CipherShift());
        }

        String convertedMessage = cipher.messageCipher(message, mode, secret);
        Files.writeString(Path.of(outputFilename), convertedMessage);
    }
}












