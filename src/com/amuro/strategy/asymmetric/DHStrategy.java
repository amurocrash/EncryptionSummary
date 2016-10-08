package com.amuro.strategy.asymmetric;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Hex;

import com.amuro.strategy.IStrategy;

public class DHStrategy implements IStrategy
{
	private Cipher cipher;
	private SecretKey receiverSecretKey;

	public String encode(String src)
	{
		try
		{
			//初始化发送方密钥
			KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			senderKeyPairGenerator.initialize(512);
			KeyPair senderkeyPair = senderKeyPairGenerator.generateKeyPair();
			PrivateKey senderPrivateKey = senderkeyPair.getPrivate();
			byte[] senderPublicKeyBytes = senderkeyPair.getPublic().getEncoded();//发送方的公钥
			
			//初始化接收方密钥,用发送方的公钥
			KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyBytes);
			PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);
			DHParameterSpec dhParameterSpec = 
					((DHPublicKey)receiverPublicKey).getParams();
			KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			receiverKeyPairGenerator.initialize(dhParameterSpec);
			KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();
			PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();
			byte[] receiverPublicKeyBytes = receiverKeyPair.getPublic().getEncoded();
			
			KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
			receiverKeyAgreement.init(receiverPrivateKey);
			receiverKeyAgreement.doPhase(receiverPublicKey, true);
			receiverSecretKey = receiverKeyAgreement.generateSecret("DES");
			
			//发送方拿到接收方的public key就可以做加密了
			KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
			x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyBytes);
			PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
			KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
			senderKeyAgreement.init(senderPrivateKey);
			senderKeyAgreement.doPhase(senderPublicKey, true);
			SecretKey senderSecretKey = senderKeyAgreement.generateSecret("DES");
			
			if(Objects.equals(receiverSecretKey, senderSecretKey))
			{
				cipher = Cipher.getInstance("DES");
				cipher.init(Cipher.ENCRYPT_MODE, senderSecretKey);
				byte[] result = cipher.doFinal(src.getBytes());
				return Hex.encodeHexString(result);
			}
			
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
			cipher.init(Cipher.DECRYPT_MODE, receiverSecretKey);
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
