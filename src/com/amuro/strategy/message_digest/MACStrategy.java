package com.amuro.strategy.message_digest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.amuro.strategy.IStrategy;

/**
 * 含有密钥的消息散列算法,融合MD，MAC
 * @author Amuro
 *
 */
public class MACStrategy implements IStrategy
{

	public String encode(String src)
	{
		try
		{
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
			
//			byte[] keyBytes = Hex.decodeHex(new char[]{'a', 'a', 'a', 'a', 'a','a', 'a', 'a', 'a', 'a'});
			
			SecretKey restoreKey = new SecretKeySpec(keyBytes, "HmacMD5");
			Mac mac = Mac.getInstance(restoreKey.getAlgorithm());
			mac.init(restoreKey);
			byte[] macBytes = mac.doFinal(src.getBytes());
			
			return Hex.encodeHexString(macBytes);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	public String decode(String src)
	{
		throw new RuntimeException("Hmac no decode");
	}

}
