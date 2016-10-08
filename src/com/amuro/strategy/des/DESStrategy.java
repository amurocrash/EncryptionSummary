package com.amuro.strategy.des;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.amuro.strategy.IStrategy;

/**
 * 
 * @author Amuro
 *
 */
public class DESStrategy implements IStrategy
{
	private Cipher cipher;
	private SecretKey generateKey;

	public String encode(String src)
	{
		try
		{
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
			keyGenerator.init(56);//size
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
			
			DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
			generateKey = secretKeyFactory.generateSecret(desKeySpec);
			
			cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, generateKey);
			byte[] resultBytes = cipher.doFinal(src.getBytes());
			
			return Hex.encodeHexString(resultBytes);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	public String decode(String src)
	{
		try
		{
			cipher.init(Cipher.DECRYPT_MODE, generateKey);
			byte[] result = Hex.decodeHex(src.toCharArray());
			return new String(cipher.doFinal(result));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

}
