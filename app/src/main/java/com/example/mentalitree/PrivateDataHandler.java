package com.example.mentalitree;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import android.util.Log;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PrivateDataHandler {
    private static PrivateDataHandler single_instance = null;
    SharedPreferences sharedPreferences;
    Context mContext;
    private static Application instance;
    private static final String TAG = "PRIVATEDATA";

    private PrivateDataHandler() {
        mContext = MyApplication.getInstance();
    }


    // Static method
    // Static method to create instance of Singleton class
    public static synchronized PrivateDataHandler getInstance() {
        if (single_instance == null) {
            single_instance = new PrivateDataHandler();
        }
        return single_instance;
    }

    public void testEncryption(){

        try {
            // Generating objects of KeyGenerator &
            // SecretKey
            KeyGenerator keygenerator
                    = KeyGenerator.getInstance("DES");
            SecretKey myDesKey = keygenerator.generateKey();

            String mStringKey = getKeyAsString(myDesKey);
            SecretKey mSecretKey = getStringAsKey(mStringKey);
            Log.w(TAG, "Personal key is: "+mStringKey);

            // Creating object of Cipher
            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");

            // Creating byte array to store string
            String secretMessage = "No body can see me.";
            byte[] text
                    = secretMessage.getBytes("UTF8");

            Log.w(TAG, "Secret message is : "+secretMessage);

            // Encrypting text
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            byte[] textEncrypted = desCipher.doFinal(text);

            // Converting encrypted byte array to string
            String s = new String(textEncrypted);
            Log.w(TAG, "Encrypted message is: "+s);

            // Decrypting text
            desCipher.init(Cipher.DECRYPT_MODE, mSecretKey);
            byte[] textDecrypted
                    = desCipher.doFinal(textEncrypted);

            // Converting decrypted byte array to string
            s = new String(textDecrypted);
            Log.w(TAG, "Decrypted string is: "+s);
        }
        catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public String getKeyAsString(SecretKey key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public SecretKey getStringAsKey(String keyString){
        // decode the base64 encoded string
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        // rebuild key using SecretKeySpec
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        return  originalKey;
    }
}
