package com.amuro.strategy.message_digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import com.amuro.strategy.IStrategy;

/**
 * 安全散列算法
 * @author Amuro
 *
 */
public class SHAStrategy implements IStrategy
{

	public String encode(String src)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(src.getBytes());
			return Hex.encodeHexString(md.digest());
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String decode(String src)
	{
		throw new RuntimeException("SHA no decode");
	}

}
