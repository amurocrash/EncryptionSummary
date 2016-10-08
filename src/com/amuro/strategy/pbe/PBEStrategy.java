package com.amuro.strategy.pbe;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Hex;

import com.amuro.strategy.IStrategy;

/**
 * 基于口令的加密（password），对称 + 消息摘要
 * @author Amuro
 *
 */
public class PBEStrategy implements IStrategy
{
	private Cipher cipher;
	private SecretKey generateKey;
	private PBEParameterSpec pbeParameterSpec;
	
	public String encode(String src)
	{
		try
		{
			SecureRandom secureRandom = new SecureRandom();
			byte[] salt = secureRandom.generateSeed(8);
			
			String password = "amuro";
			PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
			generateKey = secretKeyFactory.generateSecret(pbeKeySpec);
			
			pbeParameterSpec = new PBEParameterSpec(salt, 100);
			cipher = Cipher.getInstance("PBEWITHMD5andDES");
			cipher.init(Cipher.ENCRYPT_MODE, generateKey, pbeParameterSpec);
			byte[] resultBytes = cipher.doFinal(src.getBytes());
			return Hex.encodeHexString(resultBytes);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	public String decode(String src)
	{
		try
		{
			cipher.init(Cipher.DECRYPT_MODE, generateKey, pbeParameterSpec);
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
