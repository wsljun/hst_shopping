package com.test;

import com.cj.reocrd.api.UrlConstants;

import org.junit.Test;
import org.openqa.selenium.internal.Base64Encoder;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

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
//		String str = "MSsaOKyS62UCS9rEQKToOA3kKNYNfHd9W67UUPYxXHzuolZ4vxfrIKDZPAJxY0nGSALy/n3WZWUY\n" +
//				"KiBDCmdBP2vfmqYqDoeCtAb2MM/69gvKysQi3M4UXm+R6v3y45vfAwcRsppXj8ifoX8G1Ca1MA94\n" +
//				"j+w5Z2Ns78NPaXodoRhrrXvJR90A8zDNlNLI7xVwNM72us8KVp++DPIi9M34xXON7HDBe/p8+vyK\n" +
//				"+3PbENv1KpicDgE9ZToYajdIbgHChk42PPstOD9U+dTVCuv1p2u8snWvNwlW36qkPvo7u2WLJr7H\n" +
//				"v2KgY4QAIcBWnNi281VHu7oMCGaI+oFYeNS4Oeyyu1yzSM0kndxBe7nT0KsV33OERxb0Eav/yeu9\n" +
//				"JftAlCPMn7cxnZj6plZ00AYeP/weEOtGAZ2OcRBIsduBafkJ2l96ufijMPF4vsCCUj9tMCvrXkxm\n" +
//				"JxnyQvshkIe16kPPAOBhB9zylKm0MrLv8yQPEhQIL5vwWqC3aRMb/JFhLZ8z3nbb6NYLO0LRhef1\n" +
//				"XoHkc3+VxPNCzrNL/8NgyZs=";
//		String text = getdeCrypt(str,  "357714186790243");
//		System.out.println("解密-->>" + text);
		String str = "MSsaOKyS62UCS9rEQKToOA3kKNYNfHd9W67UUPYxXHzuolZ4vxfrIDSnaAOTPaVjc084cZgCxsmx\n" +
				"P6wbQVF6hS5I+MAJKvjO5f+rQC+8GAJaoXb3uOowRePewrAs0CpER7z1AKhF1M6tbWg1G3EebSlg\n" +
				"KwRhV/BAGCogQwpnQT9r35qmKg6HgrQG9jDP+vYLmkCxBbsVkiDvK6vBq7DL7ZvlnWzUpP2j5zHQ\n" +
				"81ZCs89dbw/GlirQlhrp/owPd2Dv0JBu1nyg0gkf4SQ2I+5qufCM0VB/4pPpdkO1GLO/7wk==";
		String text = getdeCrypt(str,  "357714186790243");
		System.out.println("解密-->>" + text);
	}


	/**
	 * {
	 "pagesize": "20 ",   //页宽
	 "pageno": "0"  //页码

	 }
	 *
	 * */
	@Test
	public void test_url201(){
        // 说明参数
		String pagesize = "20";
		int pageno = 1;
		// 封装cipher秘文
		String data = "{\"pagesize\":\"" + pagesize + "\",\"pageno\":\"" + pageno + "\"}";
//		String cipher = getDesede(data, UrlConstants.PID);
//		System.out.println("密文-->>" + getDesede(data, UrlConstants.PID));
		// 求情参数data
//		String sendData = "{\"pid\":\"" + UrlConstants.PID + "\",\"por\":\"" + 201 + "\",\"cipher\":\"" + cipher + "\"}";
//		System.out.println("请求参数-->>" + sendData);
	}

}
