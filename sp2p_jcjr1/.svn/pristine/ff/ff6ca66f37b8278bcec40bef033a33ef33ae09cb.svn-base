package com.sp2p.util;


import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/** *//**
 * <p>
 * RSA公钥/私钥/签名工具�?
 * </p>
 * <p>
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密�?度极其缓慢，�?��文件不使用它来加密�?是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安�?
 * </p>
 * 
 * @author huan
 * @version 1.0
 */
public class RSAUtil {

    /** *//**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    
    /** *//**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** *//**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    
    /** *//**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    
    /** *//**
     * RSA�?��加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    
    /** *//**
     * RSA�?��解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /** *//**
     * <p>
     * 生成密钥�?公钥和私�?
     * </p>
     * 
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() {
        Map<String, Object> keyMap = null;
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			keyMap = new HashMap<String, Object>(2);
			keyMap.put(PUBLIC_KEY, publicKey);
			keyMap.put(PRIVATE_KEY, privateKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
        return keyMap;
    }
    
    /** *//**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     * 
     * @param data 已加密数
     * @param privateKey 私钥(BASE64编码)
     * 
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) {
        String text = null;
		try {
			byte[] keyBytes = Base64.decodeBase64(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateK);
			signature.update(data);
			text = Base64.encodeBase64String(signature.sign());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
        return text;
    }

    /** *//**
     * <p>
     * 校验数字签名
     * </p>
     * 
     * @param data 已加密数�?
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     * 
     * @return
     * @throws Exception
     * 
     */
    public static boolean verify(byte[] data, String publicKey, String sign) {
        boolean flag = false;
		try {
			byte[] keyBytes = Base64.decodeBase64(publicKey);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey publicK = keyFactory.generatePublic(keySpec);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(publicK);
			signature.update(data);
			flag = signature.verify(Base64.decodeBase64(sign));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
        return flag;
    }

    /** *//**
     * <P>
     * 私钥解密
     * </p>
     * 
     * @param encryptedData 已加密数�?
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) {
        byte[] decryptedData = null;
		try {
			byte[] keyBytes = Base64.decodeBase64(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解�?
			while (inputLen - offSet > 0) {
			    if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
			        cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			    } else {
			        cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			    }
			    out.write(cache, 0, cache.length);
			    i++;
			    offSet = i * MAX_DECRYPT_BLOCK;
			}
			decryptedData = out.toByteArray();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
        return decryptedData;
    }

    /** *//**
     * <p>
     * 公钥解密
     * </p>
     * 
     * @param encryptedData 已加密数�?
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) {
        byte[] decryptedData = null;
		try {
			byte[] keyBytes = Base64.decodeBase64(publicKey);
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicK = keyFactory.generatePublic(x509KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicK);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解�?
			while (inputLen - offSet > 0) {
			    if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
			        cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			    } else {
			        cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			    }
			    out.write(cache, 0, cache.length);
			    i++;
			    offSet = i * MAX_DECRYPT_BLOCK;
			}
			decryptedData = out.toByteArray();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
        return decryptedData;
    }

    /** *//**
     * <p>
     * 公钥加密
     * </p>
     * 
     * @param data 源数�?
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) {
        byte[] encryptedData = null;
		try {
			byte[] keyBytes = Base64.decodeBase64(publicKey);
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicK = keyFactory.generatePublic(x509KeySpec);
			// 对数据加�?
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicK);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加�?
			while (inputLen - offSet > 0) {
			    if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
			        cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			    } else {
			        cache = cipher.doFinal(data, offSet, inputLen - offSet);
			    }
			    out.write(cache, 0, cache.length);
			    i++;
			    offSet = i * MAX_ENCRYPT_BLOCK;
			}
			encryptedData = out.toByteArray();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
        return encryptedData;
    }

    /** *//**
     * <p>
     * 私钥加密
     * </p>
     * 
     * @param data 源数�?
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) {
        byte[] encryptedData = null;
		try {
			byte[] keyBytes = Base64.decodeBase64(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateK);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加�?
			while (inputLen - offSet > 0) {
			    if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
			        cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			    } else {
			        cache = cipher.doFinal(data, offSet, inputLen - offSet);
			    }
			    out.write(cache, 0, cache.length);
			    i++;
			    offSet = i * MAX_ENCRYPT_BLOCK;
			}
			encryptedData = out.toByteArray();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
        return encryptedData;
    }

    /** *//**
     * <p>
     * 获取私钥
     * </p>
     * 
     * @param keyMap 密钥�?
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /** *//**
     * <p>
     * 获取公钥
     * </p>
     * 
     * @param keyMap 密钥�?
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }
    
    /**
     * 私钥加密
     * @param text
     * @param privateKey
     * @return
     */
    public static String encodeByPrivateKey(String text, String privateKey) {
    	byte[] encodedData = RSAUtil.encryptByPrivateKey(text.toString().getBytes(), privateKey);
		String cipherText = new String(Base64.encodeBase64(encodedData));
		return cipherText;
    }
    
    /**
     * 公钥解密
     * @param text
     * @param publicKey
     * @return
     */
    public static String decodeByPublicKey(String text, String publicKey) {
    	byte[] decodedData = RSAUtil.decryptByPublicKey(Base64.decodeBase64(text), publicKey);
        String plainText = new String(decodedData);
		return plainText;
    }
    
    /**
     * 公钥加密
     * @param text
     * @param publicKey
     * @return
     */
    public static String encodeByPublicKey(String text, String publicKey) {
    	byte[] encodedData = RSAUtil.encryptByPublicKey(text.toString().getBytes(), publicKey);
		String cipherText = new String(Base64.encodeBase64(encodedData));
		return cipherText;
    }
    
    /**
     * 私钥解密
     * @param text
     * @param privateKey
     * @return
     */
    public static String decodeByPrivateKey(String text, String privateKey) {
    	byte[] decodedData = RSAUtil.decryptByPrivateKey(Base64.decodeBase64(text), privateKey);
        String plainText = new String(decodedData);
		return plainText;
    }

   /* public static void main(String[] args) {
		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIWq/tBZsfme6VnRh9DscOOGEYK6onSMJKDfFB6CchuebLitfpPTA/rjbu/ONGS6TXdUbjnprN86YGYz5lV6eKo2cW6wV1qov0XjHrk1MfNEpnT6H19awWpnLlRsBK3Y3sxDO9UfvFa1D4bdpuARZOKyTXRsaPv4Jtb3FLhG9xl1AgMBAAECgYBpp4GGen99Caj1P/OUpm+MyDorFQ64BNYm+m1SuFUtk4noZ+p3OgIZW5AiqOEtBT2aJviBej0UrXXpEvjrc8TP0Xzo70WwPqByKMtelzLYcQp9PVhrffJTBdWG7Mzj+/Fh4eqC7SzG7yVSVUeMVKkKtUzWEDOYkTOFg/TBjp5aeQJBALzlugbdlMWOntFNFeaDe7S0MsVWGsN8eYOqAaQh/muALSzpDlpSwX59kSDH+meVoRLeeZZ1OABckJo+HJJajRsCQQC1JriQsaYANJelD11+zvd6lTYUYQJ0536uR7PMZz4lH0v/ZqD6Ik9xomi7kQ3ouRcSRodUI+VP2Ch09pq9cCyvAkEAnr7gywxEDpKA3ZxOn144A/C8CiMGcuqUpBWnM2xuB6G8VVW2fim1+rtDz6y/v3V73AOjYtI3sfYYf8da85OtqQJANrweZEnpGILLlyoPW/N5P2a6UmoFbgFMe6haoVZIprrQIbmGxk17p6Ak+ReZFkpmuDTrSvcVoIx1nPGuS7MLLQJAGkgIa3aAG/dUu028OYiscuvhzTTfHmqb6NoQTMU4UmYwJQCpxZwV/BmGxKkeTcSjAqd/32MWr6FbNprKOiDW9g=="; 
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFqv7QWbH5nulZ0YfQ7HDjhhGCuqJ0jCSg3xQegnIbnmy4rX6T0wP6427vzjRkuk13VG456azfOmBmM+ZVeniqNnFusFdaqL9F4x65NTHzRKZ0+h9fWsFqZy5UbASt2N7MQzvVH7xWtQ+G3abgEWTisk10bGj7+CbW9xS4RvcZdQIDAQAB";
		String text = encodeByPublicKey("{\"date\":\"1431311590\"}", publicKey);
		System.out.println(text);
		//System.out.println(decodeByPrivateKey(text, privateKey));
    }*/
    
}
