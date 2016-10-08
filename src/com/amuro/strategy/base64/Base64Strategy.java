package com.amuro.strategy.base64;

import java.util.Base64;

import com.amuro.strategy.IStrategy;

/**
 * Base64算法基于64个基本字符，加密后的string中只包含这64个字符
 * @author Amuro
 *
 */
public class Base64Strategy implements IStrategy
{
	public String encode(String src)
	{
		byte[] encodeBytes = Base64.getEncoder().encode(src.getBytes());
		return new String(encodeBytes);
	}

	public String decode(String src)
	{
		byte[] decodeBytes = Base64.getDecoder().decode(src.getBytes());
		return new String(decodeBytes);
	}
}
