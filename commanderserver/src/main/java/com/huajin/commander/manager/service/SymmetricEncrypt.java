package com.huajin.commander.manager.service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.springcryptoutils.core.cipher.Mode;
import com.springcryptoutils.core.cipher.symmetric.Base64EncodedCipherer;
import com.springcryptoutils.core.cipher.symmetric.Base64EncodedCiphererImpl;
import com.springcryptoutils.core.cipher.symmetric.Base64EncodedKeyGeneratorImpl;

/**
 * 对称加密解密
 * 
 * @author wenqiang
 * 2015年5月21日 下午3:08:58
 */
@Component
public class SymmetricEncrypt {
	
	private static final String initkey = "key.init";//必须为8位
	
	@Value("${encryptkey}")
	private String key;

	/**
	 * 
	 * @return Base64EncodedCipherer
	 * @author wenqiang
	 * 2015年5月21日 下午3:15:35
	 */
	private Base64EncodedCipherer getInstance(Mode mode){
		Base64EncodedCiphererImpl cipherer = new Base64EncodedCiphererImpl();
		cipherer.setMode(mode);
		return cipherer;
	}
	
	/**
	 * 得到一个初始化向量，用于操作密码
	 * @return String
	 * @author wenqiang
	 * 2015年5月21日 下午3:19:42
	 */
	public static String getInitVector(){
		byte[] keyBytes = Base64.encodeBase64(initkey.getBytes());
		return new String(keyBytes);
	}
	
	/**
	 * 获取key
	 * @return String
	 * @author wenqiang
	 * 2015年5月21日 下午3:21:35
	 */
	public static String getKey(){
		Base64EncodedKeyGeneratorImpl keygenerator = new Base64EncodedKeyGeneratorImpl();
		try {
			keygenerator.afterPropertiesSet();
			String key = keygenerator.generate();
			return key;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	/**
	 * 加密字符串
	 * @return String
	 * @author wenqiang
	 * 2015年5月21日 下午3:24:20
	 */
	public String encryptStr(String message){
		String initializationVector = getInitVector();
		if(key == null)
			return null;
		Base64EncodedCipherer cipherer = getInstance(Mode.ENCRYPT); 
		String encryptStr = cipherer.encrypt(key, initializationVector, message);
		return encryptStr;
	}
	
	/**
	 * 解密字符串
	 * @return String
	 * @author wenqiang
	 * 2015年5月21日 下午3:27:51
	 */
	public String decryptStr(String message){
		String initializationVector = getInitVector();
		if(key == null)
			return null;
		Base64EncodedCipherer cipherer = getInstance(Mode.DECRYPT); 
		String decryptStr = cipherer.encrypt(key, initializationVector, message);
		return decryptStr;
	}
}
