package encryptdecrypt;

public class Cipher {

    private CipherMethod cipherMethod;

    public void setCipherMethod(CipherMethod cipherMethod) {
        this.cipherMethod = cipherMethod;
    }

    public String messageCipher(String message, Mode mode, int secret) {
        return this.cipherMethod.messageCipher(message, mode, secret);
    }

}
