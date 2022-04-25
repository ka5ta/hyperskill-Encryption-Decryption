package encryptdecrypt;

import java.util.InputMismatchException;
import java.util.stream.Collectors;

public class CipherUnicode implements CipherMethod {

    @Override
    public String messageCipher(String message, Mode mode, int secret) {
        if (secret == 0) {
            return message;
        } else if (secret < 0) {
            throw new InputMismatchException("This is wrong secret value");
        }

        return message.chars()
                .mapToObj(ch -> {
                    int converted = charCipher((char) ch, mode, secret);
                    return String.valueOf((char) converted);
                })
                .collect(Collectors.joining());

    }

    @Override
    public char charCipher(char ch, Mode mode, int secret) {
        int transformedChar = ch + (secret * mode.getValue());
        return (char) transformedChar;
    }

}
