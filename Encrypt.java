
public class Encrypt {

    // Encrypting a message
    public String encryptData(String str) {

        int key = 6;
        String x = "";

        char[] chars = str.toCharArray();

        for (char c : chars) {
            c += key;
            x += c;
        }

        return x;
    }

    // Decrypting a message
    public String decryptData(String str) {

        int key = 6;
        String x = "";

        char[] chars = str.toCharArray();

        for (char c : chars) {
            c -= key;
            x += c;
        }

        return x;
    }

    // public static void main(String[] args) {

    // String text = "Hi, how are you?";
    // String encrypted_string = "";

    // Encrypt en = new Encrypt();

    // encrypted_string = en.encryptData(text);
    // System.out.println(encrypted_string);

    // System.out.println(en.decryptData(encrypted_string));
    // }
}