package com.example.mentalitree;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import android.util.Log;
import android.widget.Toast;

import com.google.common.base.Charsets;

import java.nio.charset.StandardCharsets;
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
    private SecretKey userSecretKey;

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

    public void setUserSecretKey(SecretKey userSecretKey) {
        this.userSecretKey = userSecretKey;
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
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");

        return  originalKey;
    }

    public String encryptString(String textToBeEncrypted){
        // Creating object of Cipher

        try{
            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");
            byte[] textAsBytes = textToBeEncrypted.getBytes(StandardCharsets.UTF_8);
            // Encrypting text
            desCipher.init(Cipher.ENCRYPT_MODE, userSecretKey);
            byte[] textEncryptedByteArray = desCipher.doFinal(textAsBytes);
            // Converting encrypted byte array to string
            byte[] encodedEncryptedByteArray = android.util.Base64.encode(textEncryptedByteArray, android.util.Base64.DEFAULT);
            //return Base64.getEncoder().encodeToString(textEncryptedByteArray);
            return new String(encodedEncryptedByteArray, Charsets.UTF_8);

        }catch (Exception e){
            return "Encryption failed: " + e;
        }

    }

    public String decryptString(String textToBeDecrypted){

        try{
            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");

            //Decoding the text
            byte[] decodedBytes = android.util.Base64.decode(textToBeDecrypted.getBytes(Charsets.UTF_8), android.util.Base64.DEFAULT);
            // Decrypting text
            desCipher.init(Cipher.DECRYPT_MODE, userSecretKey);


            byte[] textDecrypted
                    = desCipher.doFinal(decodedBytes);

            // Converting decrypted byte array to string
            return new String(textDecrypted, Charsets.UTF_8);
        }catch (Exception e){
            return "Decryption failed: "+ e;
        }

    }

    public void getUserKeyFromSharedPreferences(){
        sharedPreferences = mContext.getSharedPreferences("mUserPrefs", Context.MODE_PRIVATE);
        this.userSecretKey = getStringAsKey(sharedPreferences.getString("cryptoKey",""));
        Log.e(TAG, "I JUST RETRIEVED THE SECRET KEY LOOK: "+ getKeyAsString(this.userSecretKey));
    }

    public void createKeyForSharedPreferences(){
        try {
            KeyGenerator keygenerator
                    = KeyGenerator.getInstance("DES");
            SecretKey myDesKey = keygenerator.generateKey();
            this.userSecretKey = myDesKey;
            Log.e(TAG, "I JUST CREATED A SECRET KEY LOOK: "+ getKeyAsString(myDesKey));
            SharedPreferences.Editor editor;
            sharedPreferences = mContext.getSharedPreferences("mUserPrefs", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            Log.e(TAG, "Just created the shared prefs editor");
            editor.putString("cryptoKey", getKeyAsString(myDesKey));
            editor.apply();
            Log.e(TAG, "and now i saved it to userPrefs:  "+ getKeyAsString(myDesKey));

        }catch (Exception e){
            Log.e(TAG, "fuck: "+ e);
            Toast.makeText(mContext, "Error with generating private encryption key: "+e, Toast.LENGTH_SHORT).show();
        }
    }
}
