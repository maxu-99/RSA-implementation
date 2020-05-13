import java.util.*;

public class RSA
{

	//Works for (11,13) (33,41) (35,41)
	public static final long MINPRIME = 10000;
	public static final long MAXPRIME = 100000;

//	public static final long MINPRIME = 11;
//	public static final long MAXPRIME = 13;


	private long p;
	private long q;

	private long n;
	private long phi;

	private long publicE;
	private long privateD;

	private String cipher;
	private String output;

	public RSA(String line)
	{
		p = this.generateRandomPrime(MINPRIME, MAXPRIME);
		q = this.generateRandomPrime(MINPRIME, MAXPRIME);

		while(p == q)
		{
			p = this.generateRandomPrime(MINPRIME, MAXPRIME);
			q = this.generateRandomPrime(MINPRIME, MAXPRIME);
		}

		n = p * q;
		phi = (p - 1) * (q - 1);

		System.out.println("Value of p : " + p + " , value of q : " + q);
		System.out.println("Value of n : " + n + " , value of phi : " + phi);

		//Generate random odd e values from 1 < e < phi
		publicE = this.getPublicKey();
		System.out.println("Public key e : " + publicE);

		privateD = Number.gcdExtended(publicE, phi)[1];
		System.out.println("Private key d : " + privateD);

		cipher = encrypt(line);
		output = decrypt(cipher);
		System.out.println("Encrypted : " + encrypt(line));
		System.out.println("Decrypted : " + decrypt(encrypt(line)));
	}

	public RSA()
	{
	}

	public String getEncrypt()
	{
		return new String(cipher);
	}

	public String getDecrypt()
	{
		return new String(output);
	}

	public String encrypt(String line)
	{
		
		String encrypted = new String();
		for(int ii = 0; ii < line.length(); ii++)
		{
			long plain = (long)line.charAt(ii);
			System.out.println("Value of " + line.charAt(ii) + " is " + plain);	
			if(plain > n)
			{
				System.out.println("Hello plain big :" + plain);
			}
			
			long value = Number.modularExponent(plain, publicE, n);
			System.out.println("Afer encryption value : " + value + " with character " + (char)value);
			//System.out.println("Value : " + value);
			if(value > n)
			{
				System.out.println("Hello value big : ");
			}
	

			encrypted += Long.toString(value) + " ";
		}

		return encrypted;
		
	}
	
	public String decrypt(String line)
	{
		String[] array = line.split(" ");
		String decrypt = new String();
		for(int ii = 0; ii < array.length; ii++)
		{
			long value = Long.parseLong(array[ii]);
			
			long d = Number.modularExponent(value, privateD, n);

			decrypt += (char)d;
		}

		return decrypt;
	
	}

	public long generateRandomPrime(long minPrime, long maxPrime)
	{
		int min = (int)minPrime;
		int max = (int)maxPrime;
		Random rand = new Random();
		boolean surePrime = false;
		long prime = 0;

		while(!(surePrime))
		{
			prime = (long)rand.nextInt((max - min) + 1) + min;
			if(Number.checkifPrime(prime))
			{
				surePrime = true;
			}
		}

		return prime;
	}
		

	// 1 < e < phi
	public long getPublicKey()
	{
		
	
		Random rand = new Random();
		boolean valid = false;
		long e = 0;

		while(!(valid))
		{
			//e = (long)rand.nextInt((int)phi - 1) + 2;
			e = (rand.nextLong() + 2) % phi;
			if((e % 2 != 0) && (Number.gcdExtended(e, phi)[0] == 1))
			{
				valid = true;
			}
		}

		return e;


	
/*
	
		  long publicKey = 0;
  
          // One of these is guaranteed to go through
          if (Number.gcdExtended(11, phi)[0] == 1)
              publicKey = 11;
          else if (Number.gcdExtended(13, phi)[0] == 1)
              publicKey = 13;
          else if (Number.gcdExtended(17, phi)[0] == 1)
              publicKey = 17;
  
          return publicKey;

 */

	}
	
}
