package encryptdecrypt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        String message = "";
        Mode mode = Mode.enc;
        int secret = 0;
        String outputFilename = "";
        Algorithm algorithm = Algorithm.SHIFT;
        Cipher cipher = new Cipher();

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

        if (algorithm == Algorithm.UNICODE) {
            cipher.setCipher(new CipherUnicode());
        } else {
            cipher.setCipher(new CipherShift());
        }

        String convertedMessage = cipher.messageCipher(message, mode, secret);
        Files.writeString(Path.of(outputFilename), convertedMessage);

    }


/*    public static int charCipher(int number, Mode mode, int secret) {
        return number + (secret * mode.getValue());
    }*/

/*    public static String messageCipher(String message, Mode mode, int secret) {

        if (secret == 0) {
            return message;
        } else if (secret < 0) {
            throw new InputMismatchException("This is wrong secret value");
        }

        return message.chars()
                .mapToObj(ch -> {
                    int converted = charCipher(ch, mode, secret);
                    return String.valueOf((char) converted);
                })
                .collect(Collectors.joining());
    }*/
}

enum Mode {
    enc(1),
    dec(-1);

    private final int value;

     Mode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

enum Algorithm {
    SHIFT, UNICODE;
}

interface CipherMethod {

    String messageCipher(String message, Mode mode, int secret);
     char charCipher(char ch, Mode mode, int secret);
}


    class CipherUnicode implements CipherMethod {

        @Override
        public String messageCipher(String message, Mode mode, int secret) {
            if (secret == 0) {
                return message;
            } else if (secret < 0) {
                throw new InputMismatchException("This is wrong secret value");
            }

            return message.chars()
                    .mapToObj(ch -> {
                        int converted = charCipher( (char) ch, mode, secret);
                        return String.valueOf( (char) converted);
                    })
                    .collect(Collectors.joining());

        }
        @Override
        public char charCipher(char ch, Mode mode, int secret) {
            int transformedChar = ch + (secret * mode.getValue());
            return (char) transformedChar;
        }

    }

    class CipherShift implements CipherMethod {

        @Override
        public String messageCipher(String message, Mode mode, int secret) {

             String ciphered = message.codePoints()
                    .mapToObj(ch -> charCipher( (char) ch, mode, secret))
                    .collect(
                            StringBuilder::new,
                            StringBuilder::appendCodePoint,
                            StringBuilder::append
                    ).toString();

            return ciphered;
        }

        private boolean isLowerCaseLetter(char ch) {
            return ch >= 'a' && ch <= 'z';
        }

        private boolean isUpperCaseLetter(char ch) {
            return ch >= 'A' && ch <= 'Z';
        }

        public char charCipher(char ch, Mode mode, int secret) {

            char offset;
            if(isUpperCaseLetter(ch)) {
                offset = 'A';
            } else if (isLowerCaseLetter(ch)) {
                offset = 'a';
            } else {
                return ch;
            }

            int shift = secret * mode.getValue();
            int alphabetIndex = ch - offset;
            int shiftedAlphabetIndex = positiveModulo(alphabetIndex + shift, 26);
            char shiftedChar = (char) (shiftedAlphabetIndex + offset);

            return shiftedChar;
        }

        private int positiveModulo(int number, int modulus){
            int result = number % modulus;
            if(result < 0){
                result += modulus;
            }
        return result;
    }
}

class Cipher {

    private CipherMethod cipher;

    public void setCipher(CipherMethod cipher) {
        this.cipher = cipher;
    }

    public String messageCipher(String message, Mode mode, int secret) {
        return this.cipher.messageCipher(message, mode, secret);
    }

}