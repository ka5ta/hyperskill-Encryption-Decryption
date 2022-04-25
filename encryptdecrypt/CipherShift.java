package encryptdecrypt;

public class CipherShift implements CipherMethod {

    @Override
    public String messageCipher(String message, Mode mode, int secret) {

        String ciphered = message.codePoints()
                .mapToObj(ch -> charCipher((char) ch, mode, secret))
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
        if (isUpperCaseLetter(ch)) {
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

    private int positiveModulo(int number, int modulus) {
        int result = number % modulus;
        if (result < 0) {
            result += modulus;
        }
        return result;
    }
}