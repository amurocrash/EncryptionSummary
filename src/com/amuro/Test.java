package com.amuro;

import com.amuro.strategy.IStrategy;
import com.amuro.strategy.asymmetric.DHStrategy;
import com.amuro.strategy.des.AESStrategy;
import com.amuro.strategy.des.DESStrategy;
import com.amuro.strategy.des._3DESStrategy;
import com.amuro.strategy.message_digest.MACStrategy;
import com.amuro.strategy.message_digest.MD5Strategy;
import com.amuro.strategy.message_digest.SHAStrategy;
import com.amuro.strategy.pbe.PBEStrategy;

public class Test
{
	public static void main(String[] args)
	{
		IStrategy is = new DHStrategy();
		
		String src = "阿姆罗";
		
		String encodeStr = is.encode(src);
		
		System.out.println(encodeStr);
		System.out.println(is.decode(encodeStr));
	}
}
