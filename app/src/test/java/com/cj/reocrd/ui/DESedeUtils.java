package com.test;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;
import org.openqa.selenium.internal.Base64Encoder;

@SuppressWarnings("all")
public class DESedeUtils {

	/**
	 * DES 加密算法
	 *
	 * @param src
	 *            需要加密的文本
	 * @param enKey
	 *            密钥
	 * @return 以byte[]形式返回加密后文本
	 */
	public static byte[] encrypt(byte[] src, byte[] enKey) {
		byte[] encryptedData = null;
		try {
			// 从原始密匙数据创建一个DESKeySpec对象
			DESedeKeySpec dks = new DESedeKeySpec(enKey);
			// 创建一个密匙工厂，然后用它把DESedeKeySpec对象转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DESede");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encryptedData = cipher.doFinal(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedData;
	}

	/**
	 * 3DES 解密算法
	 *
	 * @param text
	 *            加密的十六进制形式文本
	 * @param deKey
	 *            密钥
	 * @return 解密后的文本
	 */

	public static String deCrypt(byte[] text, byte[] deKey) {
		String strDe = null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DESede");
			DESedeKeySpec dks = new DESedeKeySpec(deKey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey sKey = keyFactory.generateSecret(dks);
			cipher.init(Cipher.DECRYPT_MODE, sKey);
			byte ciphertext[] = cipher.doFinal(text);
			strDe = new String(ciphertext, "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strDe;
	}



	public static String getDesede(String data, String imei) {
		String key = "haoshantang2018" + imei;
		byte[] arr = {};
		try {
			arr = encrypt(data.getBytes("utf-8"), key.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Base64Encoder base = new Base64Encoder();
		String sss = base.encode(arr);
		return sss;
	}

	public static String getdeCrypt(String data, String imei) {
		String key = "haoshantang2018" + imei;
		Base64Encoder base = new Base64Encoder();
		String deText = "";
		try {
			if (!data.contains("<!D")) {
				deText = deCrypt(base.decode(data), key.getBytes("utf-8"));
			} else {
				return data;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return deText;

	}

	@Test
	public  void main() {
		// 说明参数
		String phone = "15311780968";
		int type = 1;
		// 封装cipher秘文
		String data = "{\"phone\":\"" + phone + "\",\"type\":\"" + type + "\"}";
		String imei = "123222212121";
		String cipher = getDesede(data, imei);
		System.out.println("密文-->>" + getDesede(data, imei));
		// 求情参数data
		String sendData = "{\"pid\":\"" + imei + "\",\"por\":\"" + 101 + "\",\"cipher\":\"" + cipher + "\"}";
		System.out.println("请求参数-->>" + sendData);
//		// 解密
//		String response = "gGOy3BZWRo79TAJ57BFlNwWxDHkFKMGBjGZ011VcgRZ+CysnHF2RRoEABnd9jhrVV861fqI5A70=";
//		String text = getdeCrypt(response, imei);
//		System.out.println("解密-->>" + text);

	}

	@Test
	public void decode(){
		String str = "gGOy3BZWRo4kiXv1p/9XSQWxDHkFKMGBBdFGZ3aSh7fHggU1X8So/0soJIKWdmCI+65h5A02bd6R\n" +
				"+xmcu1p+F5r6ZN+naKTp";
		String text = getdeCrypt(str,  "123222212121");
		System.out.println("解密-->>" + text);
	}

}
