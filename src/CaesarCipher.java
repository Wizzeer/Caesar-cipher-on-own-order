import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.Scanner;

public class CaesarCipher {
	public static void main(String[] args) {
		int shift;
		String userInput;
		Scanner sc = new Scanner(System.in);

		System.out.print("Podaj iloœæ przesuniêæ: ");
		shift = sc.nextInt();

		char[] alphabet = "eldtrcgmksqvuapnfhiojbxwzy".toCharArray();
		char[] cesar = "eldtrcgmksqvuapnfhiojbxwzy".toCharArray();
		char[] upperAlphabet = "ELDTRCGMKSQVUAPNFHIOJBXWZY".toCharArray();
		char[] upperCesar = "ELDTRCGMKSQVUAPNFHIOJBXWZY".toCharArray();
		
		encryptCesarArrays(shift, alphabet, upperAlphabet, cesar, upperCesar);
	

		System.out.println("\n1. Szyfrowanie w konsoli");
		System.out.println("2. Szyfrowanie pomiêdzy plikami");
		System.out.print("Wybierz 1 lub 2: ");
		int choice;
		choice = sc.nextInt();

		switch (choice) {
		case 1:
			choice = printOptionsAndGetUserInput(sc);
			userInput = getTextToModify(sc);
			StringBuilder sb = new StringBuilder(userInput);

			switch (choice) {
			case 1:
				encryptText(alphabet, cesar, upperAlphabet, upperCesar, sb);
				System.out.println("Zaszyfrowany tekst: " + sb.toString());
				break;

			case 2:
				decryptText(alphabet, cesar, upperAlphabet, upperCesar, sb);
				System.out.println("Odszyfrowany tekst: " + sb.toString());
			}
			
			break;
		case 2:
			System.out.print("\n1. Podaj nazwe pliku (np. plik.txt): ");
			sc.nextLine();
			userInput = sc.nextLine();
			choice = printOptionsAndGetUserInput(sc);
			switch (choice) {
			case 1:
				try {
					encryptFile(userInput, alphabet, cesar, upperAlphabet, upperCesar, "encryptedText.txt");
					System.out.println("Zaszyfrowany tekst zosta³ zapisany do pliku \'encryptedText.txt\'");
				} catch (IOException e) {
					System.out.println("Nie znaleziono podanego pliku");
				}
				break;
			case 2:
				try {
					decryptFile(userInput, alphabet, cesar, upperAlphabet, upperCesar, "decryptedText.txt");
					System.out.println("Odszyfrowany tekst zosta³ zapisany do pliku \'decryptedText.txt\'");
				} catch (IOException e) {
					System.out.println("Nie znaleziono podanego pliku");
				}
			}

		}
		sc.close();
	}




	private static void decryptFile(String userInput, char[] alphabet, char[] cesar, char[] upperAlphabet, char[] upperCesar, String fileName)
	throws IOException, FileNotFoundException {
		String encryptedText = readFile(userInput, StandardCharsets.UTF_8);
		StringBuilder sbf = new StringBuilder(encryptedText);
		decryptText(alphabet, cesar, upperAlphabet, upperCesar, sbf);
		Formatter file = new Formatter(fileName);
		file.format(sbf.toString());
		file.close();
	}




	private static void encryptFile(String userInput, char[] alphabet, char[] cesar, char[] upperAlphabet, char[] upperCesar, String fileName)
	throws IOException, FileNotFoundException {
		String encryptedText = readFile(userInput, StandardCharsets.UTF_8);
		StringBuilder sbf = new StringBuilder(encryptedText);
		encryptText(alphabet, cesar, upperAlphabet, upperCesar, sbf);
		Formatter file = new Formatter(fileName);
		file.format(sbf.toString());
		file.close();
	}
	
	
	

	private static String getTextToModify(Scanner sc) {
		String userInput;
		System.out.print("\nPodaj tekst: ");
		sc.nextLine();
		userInput = sc.nextLine();
		return userInput;
	}

	private static int printOptionsAndGetUserInput(Scanner sc) {
		int choice;
		System.out.println("\n1. Zaszyfruj tekst");
		System.out.println("2. Odszyfruj tekst");
		System.out.print("Wybierz 1 lub 2: ");
		choice = sc.nextInt();
		return choice;
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	
	
	private static void decryptText(char[] alphabet, char[] cesar, char[] upperAlphabet, char[] upperCesar, StringBuilder sb) {
		decryptLowerCaseText( alphabet, cesar, sb);
		decryptUpperCaseText(upperAlphabet, upperCesar,sb);
	}
	
	
	private static void decryptUpperCaseText(char[] upperAlphabet, char[] upperCesar, StringBuilder sb) {
		for (int i = 0; i < sb.length(); i++) {
			for (int j = 0; j < upperAlphabet.length; j++) {
				 if (sb.charAt(i) == upperCesar[j]) {
					sb.setCharAt(i, upperAlphabet[j]);
					break;
				}
			}
		}
	}
	
	private static void decryptLowerCaseText(char[] alphabet, char[] cesar, StringBuilder sb) {
		for (int i = 0; i < sb.length(); i++) {
			for (int j = 0; j < alphabet.length; j++) {
				if (sb.charAt(i) == cesar[j]) {
					sb.setCharAt(i, alphabet[j]);
					break;
				}
			}
		}
	}
	
	private static void encryptText(char[] alphabet, char[] cesar, char[] upperAlphabet, char[] upperCesar, StringBuilder sb) {
		encryptLowerCaseText( alphabet, cesar, sb);
		encryptUpperCaseText(upperAlphabet, upperCesar,sb);
	}
	
	private static void encryptLowerCaseText(char[] alphabet, char[] cesar, StringBuilder sb) {
		for (int i = 0; i < sb.length(); i++) {
			for (int j = 0; j < alphabet.length; j++) {
				if (sb.charAt(i) == alphabet[j]) {
					sb.setCharAt(i, cesar[j]);
					break;
				}
			}
		}
	}
	
	private static void encryptUpperCaseText(char[] upperAlphabet, char[] upperCesar, StringBuilder sb) {
		for (int i = 0; i < sb.length(); i++) {
			for (int j = 0; j < upperAlphabet.length; j++) {
				if (sb.charAt(i) == upperAlphabet[j]) {
					sb.setCharAt(i, upperCesar[j]);
					break;
				}
			}
		}
	}
	

	

	// moving chars in Cesar-arrays by shift
	private static void encryptLowerCaseCesarArrayToLastAlphabetLetter(int shift, char[] alphabet, char[] cesar) {
		for (int i = 0; i < alphabet.length - shift; i++) {
			cesar[i] = alphabet[i + shift];
		}
	}
	
	private static void encryptLowerCaseCesarArrayLoopFromBegginingOfAlphabet(int shift, char[] alphabet, char[] cesar) {
		int indexOfAlphabet = 0;
		for (int i = shift; i > 0; i--) {
			cesar[alphabet.length - i] = alphabet[indexOfAlphabet];
			indexOfAlphabet++;
		}
	}
	
	private static void encryptUpperCaseCesarArrayToLastAlphabetLetter(int shift, char[] upperAlphabet, char[] upperCesar) {
		for (int i = 0; i < upperAlphabet.length - shift; i++) {
			upperCesar[i] = upperAlphabet[i + shift];
		}
	}
	
	
	private static void encryptUpperCaseCesarArrayLoopFromBegginingOfAlphabet(int shift, char[] upperAlphabet, char[] upperCesar) {
		int indexOfAlphabet = 0;
		for (int i = shift; i > 0; i--) {
			upperCesar[upperAlphabet.length  - i] = upperAlphabet[indexOfAlphabet];
			indexOfAlphabet++;
		}
	}
	
	private static void encryptCesarArrays(int shift, char[] alphabet, char[] upperAlphabet, char[] cesar, char[] upperCesar) {
		encryptLowerCaseCesarArrayToLastAlphabetLetter(shift, alphabet, cesar);
		encryptLowerCaseCesarArrayLoopFromBegginingOfAlphabet(shift, alphabet, cesar);
		encryptUpperCaseCesarArrayToLastAlphabetLetter(shift, upperAlphabet, upperCesar);
		encryptUpperCaseCesarArrayLoopFromBegginingOfAlphabet(shift, upperAlphabet, upperCesar);
	}
	

}
