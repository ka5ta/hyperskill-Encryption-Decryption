package encryptdecrypt;

/**
 * <h3>The Interface manage two methods for encryption and description of message. </h3>
 * <b>"Shift"</b> method - Alphabet narrowed. Letter changes to next/previous (secret) letter within the english alphabet.
 * When 'end' (or 'star' for dec) of alphabet is reached, the remaining secret value starts from the 'beginning' (or 'end' going backwards) of the alphabet. <br>
 * <b>"Unicode"</b> method - changes to next/previous (secret) letter, number or symbol, depends on secret value. <br><br>
 *
 * <b>charCipher()</b> - performs the letter (char) swap.
 */

public interface CipherMethod {

    String messageCipher(String message, Mode mode, int secret);
    char charCipher(char ch, Mode mode, int secret);
}

