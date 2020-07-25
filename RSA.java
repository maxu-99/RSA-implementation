/* Author : Mahmudul Hossain (19303235)
 * Purpose : Perform RSA encryption and decryption for 
 * 			 a given input file.
 * Date modified : 17/05/2020
 */

import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.math.BigInteger;


public class RSA
{

	//Minimum range for prime number to be generated
	public static final long MINPRIME = 10000;
	//Maximum range for prime number to be generated
	public static final long MAXPRIME = 100000;


	//Prime numbers p and q
	private long p;
	private long q;

	//n = p * q
	//Used as the modulus for both encryption
	//and decryption
	private long n;

	//Euler Totient value
	// phi = (p-1) * (q-1)
	private long phi;

	//Public key for encryption
	private long publicE;

	//Private key for decryption
	private long privateD;


	public RSA(String filename)
	{
		//Generate random prime numbers for p and q 
		//within the specified range
		p = Number.generateRandomPrime(MINPRIME, MAXPRIME);
		q = Number.generateRandomPrime(MINPRIME, MAXPRIME);

		//If by any chance p and q are same, this will generate
		//weak keys, which should be avoided for good practice
		while(p == q)
		{
			p = Number.generateRandomPrime(MINPRIME, MAXPRIME);
			q = Number.generateRandomPrime(MINPRIME, MAXPRIME);
		}

		//Calculate n to be used in encryption and decryption
		n = p * q;

		//Calculate Euler Totient value to be used to generate keys
		phi = (p - 1) * (q - 1);


		//Generate public key for encryption
		//in range 1 < publicE < phi where
		//publicE and phi are co-prime to each other
		publicE = this.getPublicKey();

		//Generate private key, finding the modular multiplicative
		//inverse of publicE mod phi
		//it satifies the condition :
		//(privateD * publicE) mod phi = 1
		privateD = Number.gcdExtended(publicE, phi)[1];

		//Perfrom encryption and decryption on file
		this.fileOp(filename);



	}

	//Perform rsa encryption to the imported line
	//by encrypting each character M in the expression generating C : 
	//C = (M^publicKey) mod n   where M < n
	//Returns encrypted line containing numeric value
	public String encrypt(String line)
	{
		
		String encrypted = new String();
		for(int ii = 0; ii < line.length(); ii++)
		{
			//Convert each character to ASCII equivalent
			long plain = (long)line.charAt(ii);
			
			//Calculates (plain ^ publicE) mod n
			//BigInteger datatype used here to prevent overflow of numbers
			//since the returned expression might be a very large value 
			//where primitive data type might fail to store
			BigInteger value = Number.modularExponent(plain, publicE, n);
			
			//Store each calculated large value as string separated by <space>
			//making it easier for decryption process
			encrypted += value.toString() + " ";
		}

		return encrypted;
		
	}

	//Perform rsa decryption to the imported line containing numeric values
	//by decrypting each value C in the expression generating M :
	//M = (C ^ privateKey) mod n where M < n
	//Return decrypted line of characters
	public String decrypt(String line)
	{
		//Store each encrypted values in a string array
		String[] array = line.split(" ");
		String decrypt = new String();
		for(int ii = 0; ii < array.length; ii++)
		{
			//Obtain the encrypted value C for each character
			long value = Long.parseLong(array[ii]);
			
			//Calculates (value ^ privateKey) mod n
			//BigInteger datatype used here to prevent overflow of numbers
			//since the returned expression might be a very large value 
			//where primitive data type might fail to store
			BigInteger d = Number.modularExponent(value, privateD, n);
	
			//The decrypted value d will be same as the ASCII value of plain character
			long c = d.longValue();

			//Map the ASCII number to appropriate character
			decrypt += (char)c;

		}

		return decrypt;
	
	}

	//Generate public key such that : 
	//1 < public key < phi     AND
	//public key is co-prime to phi
	public long getPublicKey()
	{
		
		Random rand = new Random();
		boolean valid = false;

		//Public key to be determined
		long e = 0;

		//Generate e until it is avalid public key
		while(!(valid))
		{
			//Generate random values of e in range : 1 < e < phi
			e = (rand.nextLong() + 2) % phi;

			//Ensure that e is odd, co-prime to phi and positive to ensure that e
			//avoids any overflow during random generation
			if((e % 2 != 0) && (Number.gcdExtended(e, phi)[0] == 1) && (e > 0))
			{
				//Valid public key
				valid = true;
			}
		}

		return e;
 
	}


	//Open and read the input filename
	//Encrypts each line read which is saved into encrypted.txt
	//Decrypts each encrypted line which is saved into decrypted.txt
	//Code adjusted from assignment 1
	public void fileOp(String filename)
	{
		Scanner sc;
		File fileToOpen;
		String line = new String();
		File encryptedOutput, decryptedOutput;
		PrintWriter pwEncrypted, pwDecrypted;
		int lineNum  = 1;

		try
		{
			encryptedOutput = new File("encrypted.txt");
			decryptedOutput = new File("decrypted.txt");

			pwEncrypted = new PrintWriter(encryptedOutput);
			pwDecrypted = new PrintWriter(decryptedOutput);

			//Open the plain text file for rsa operation
			fileToOpen = new File(filename);
			sc = new Scanner(fileToOpen);

			//Read every line of input file 
			//Perform RSA encryption and decryption
			while(sc.hasNextLine())
			{
				//Plain text to encrypt
				line = sc.nextLine();

				//Ignore empty lines and print a new line
				if(line.isEmpty())
				{
					pwEncrypted.println(" ");
					pwDecrypted.println(" ");
				}
				else
				{
					//Obtain the encrypted text 
					String text = this.encrypt(line);

					//Obtain the decrypted text
					String decipher = this.decrypt(text);

					//Compare the plain text and decrypted line to ensure successful
					//rsa encryption and decryption
					//If an error occurs, output the line number where the rsa failed
					if(!(line.compareTo(decipher) == 0))
					{
						System.out.println("ERROR at line number : " + lineNum);
					}

					//Print the encrypted and decrypted texts into appropriate files
					pwEncrypted.println(text);
					pwDecrypted.println(decipher);
				}
				lineNum++;

			}

			pwEncrypted.close();
			pwDecrypted.close();
			sc.close();
		}
		catch(IOException e)
		{
			System.err.println(e.getMessage());
		}

	}
	
}
