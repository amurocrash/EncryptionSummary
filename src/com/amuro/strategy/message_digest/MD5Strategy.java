package com.amuro.strategy.message_digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import com.amuro.strategy.IStrategy;

/**
 * 消息摘要算法
 * @author Amuro
 *
 */
public class MD5Strategy implements IStrategy
{

	public String encode(String src)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] encodeBytes = md.digest(src.getBytes());
			
			return Hex.encodeHexString(encodeBytes);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String decode(String src)
	{
		throw new RuntimeException("MD5 no decode");
	}

}
