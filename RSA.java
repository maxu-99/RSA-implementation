import java.util.*;

public class RSA
{
	public static final long MINPRIME = 3;
	public static final long MAXPRIME = 17;

	private long p;
	private long q;

	private long n;
	private long phi;

	private long publicE;
	private long privateD;

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
		System.out.println("Public key : " + publicE);

		privateD = this.gcdExtended(publicE, phi)[1];
		System.out.println("Private key : " + privateD);
	}

	public RSA()
	{
	}

	public String encrypt(String line)
	{

		String encrypted = new String();
		for(int ii = 0; ii < line.length(); ii++)
		{
			long plain = (long)line.charAt(ii);
			long value = modularExponent(plain, publicE, n);
			char cipher = (char)value;
			encrypted += cipher;
		}

		return encrypted;
	}
	
	public String decrypt(String line)
	{

		String decrypted = new String();
		for(int ii = 0; ii < line.length(); ii++)
		{
			long plain = (long)line.charAt(ii);
			long value = modularExponent(plain, privateD, n);
			char cipher = (char)value;
			decrypted += cipher;
		}

		return decrypted;
	}
	
	public long modularExponent(long base, long exponent, long modulus)
	{
		String expBits = convertToBinary(exponent);

		long c = 0, f = 1;

		for(int i = 0; i < expBits.length(); i++)
		{
			c = 2 * c;

			f = (f * f) % modulus; 

			if(expBits.charAt(i) == '1')
			{
				c++;
				f = (f * base) % modulus;
			}

		}

		return f;
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
		//	prime = (rand.nextLong() % max) + min; 
			if(checkifPrime(prime))
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
			//e = rand.nextLong(phi - 1) + 2;
		//	e = (rand.nextLong() % (phi - 1)) + 2;
			e = (long)rand.nextInt((int)phi - 1) + 2;
			if((e % 2 != 0) && (gcdExtended(e, phi)[0] == 1))
			{
				valid = true;
			}
		}

		return e;
	}
	

	public long[] gcdExtended(long a, long n)
	{
		//0 index stores the gcd value
		//1 index stores the value of x
		long[] array = new long[2];
		long x = 0, y = 1;
		long finalX = 1, finalY = 0;
		long quotient, remainder, temp;	
		long initial = n;

		while(n != 0)
		{
			quotient = a / n;
			remainder = a % n;

			a = n;
			n = remainder;

			temp = x;
			x = finalX - (quotient * x);
			finalX = temp;

			temp = y;
			y = finalY - (quotient * y);
			finalY = temp;
		}
		
		if(finalX < 0)
		{
			finalX = finalX + initial;
		}
		array[0] = a;
		array[1] = finalX;

		return array;
	}
    public boolean checkifPrime(long num)
    {
        boolean flag = false;
        
        int t = 1;

        if( num % 2 == 0)
        {
            flag = false;
        }
        else
        {
            while( t < 50)
            {
                Random rand = new Random();
                //long randomNum = rand.nextLong(((num - 1) - 1) + 1 ) + 1;  // 0 < num < randomNum
                
				long randomNum = (rand.nextLong() % (num - 1)) + 1;
                //long r = (long)(Math.pow(randomNum, (num - 1) / 2) % num);       //Binary modular exponentiation required eg : 37

				long r = modularExponent(randomNum, (num - 1)/2, num);

                if((r != 1) && (r != num - 1))
                {
                    flag = false;
//                    System.out.println("100% not prime");
                }
                else
                {
                    flag = true;
  //                  System.out.println("Probability : " + (1 - 1/(Math.pow(2,t))));
                }

                t++;
            }

        }

        return flag;
    }




	public String convertToBinary(long exponent)
	{
		return new String(Long.toBinaryString(exponent));
	}
}
