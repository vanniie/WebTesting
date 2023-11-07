package com.utility;


import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class RandomStringGenerator {
	public static String getRandomAlphabetString() {
		// create a string of all characters
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// create random string builder
		StringBuilder sb = new StringBuilder();

		// create an object of Random class
		Random random = new Random();

		// specify length of random string
		int length = 7;

		for (int i = 0; i < length; i++) {

			// generate random index number
			int index = random.nextInt(alphabet.length());

			// get character specified by index
			// from the string
			char randomChar = alphabet.charAt(index);

			// append the character to string builder
			sb.append(randomChar);
		}

		String randomString = sb.toString();
		System.out.println("Random String is: " + randomString);
		return randomString;

	}

	public static String getRandomNumberString() {
		// create a string of all characters
		String numbers = "0123456789";

		// create random string builder
		StringBuilder sb = new StringBuilder();

		// create an object of Random class
		Random random = new Random();

		// specify length of random string
		int length = 7;

		for (int i = 0; i < length; i++) {

			// generate random index number
			int index = random.nextInt(numbers.length());

			// get character specified by index
			// from the string
			char randomChar = numbers.charAt(index);

			// append the character to string builder
			sb.append(randomChar);
		}

		String randomString = sb.toString();
		System.out.println("Random String is: " + randomString);
		return randomString;
	}

	public static String getUDIDRandomNumber() {
		String numbers = "0123456789";

		// create random string builder
		StringBuilder sb = new StringBuilder();

		// create an object of Random class
		Random random = new Random();

		// specify length of random string
		int length = 4;

		for (int i = 0; i < length; i++) {
			// generate random index number
			int index = random.nextInt(numbers.length());
			char randomChar = numbers.charAt(index);

			// append the character to string builder
			sb.append(randomChar);
		}

		String randomString = sb.toString();
		System.out.println("Random String is: " + randomString);
		return randomString;
		}


	public static String getUDIDRandomNumber1() {
		Random random = new Random();
		// Generates random integers 0 to 10000
		int w = random.nextInt(10000);
		String sw = String.valueOf(w);
		System.out.println(sw);
		// Generates random integers 0 to 20000
		int x = random.nextInt(10000);
		String sx = String.valueOf(x);
		System.out.println(sx);
		// Generates random integers 0 to 20000
		int y = random.nextInt(10000);
		String sy = String.valueOf(y);
		System.out.println(sy);
		// Generates random integers 0 to 20000
		int z = random.nextInt(10000);
		String sz = String.valueOf(z);
		System.out.println(sz);
		String udid = sw.concat("-").concat(sx).concat("-").concat(sy).concat("-").concat(sz);
		System.out.println(udid);
		return udid;
	}

	public static String generateRandomString(int size) {
		String strNumbers = "abcdefghijklmnopqrstuvwxyzacvbe";
		Random rnd = new Random();
		StringBuilder strRandomNumber = new StringBuilder(9);
		strRandomNumber.append(strNumbers.charAt(rnd.nextInt(strNumbers.length())));
		String s1 = strRandomNumber.toString().toUpperCase();
		for (int i = 1; i < size; i++) {
			strRandomNumber.append(strNumbers.charAt(rnd.nextInt(strNumbers.length())));
		}
		return s1 + strRandomNumber.toString();
	}
}

