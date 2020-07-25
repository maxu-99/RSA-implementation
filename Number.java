/* Author : Mahmudul Hossain (19303235)
 * Purpose : Store necessary functions to carry out 
 * 			 RSA
 * Date Modified : 16/05/2020
 */

import java.util.Random;
import java.math.BigInteger;

public class Number
{
	//Implementation adapted from the book : 
	//Cryptography and Network Security : Principles and Practice (6th edition)
	//Page 269 Figure : 9.8
	//Calculates   (base^exponent) mod mudulus 
	//Return as BigInteger datatype since the calculated value might
	//be very large 
	//modularExponent acts as a replacement of Math.pow() % modulus  
	//where this function is able to store very large numbers during the intermediate stages 
	//of calculation ensuring good accuracy of calculated value for the encryption and 
	//decryption process of RSA, avoiding chances of number overflow
	public static BigInteger modularExponent(long base, long exponent, long modulus)
	{
		//Convert exponent to string of binary bitstring 
		String expBits = convertToBinary(exponent);

		//BigInteger used to store very large intermediate values
		BigInteger c = BigInteger.valueOf(0), f = BigInteger.valueOf(1);

		for(int i = 0; i < expBits.length(); i++)
		{
			c = c.multiply(new BigInteger("2")); // c = c * 2

			//f = (f * f) % modulus
			f = f.multiply(f); 
			f = f.mod(BigInteger.valueOf(modulus));

			if(expBits.charAt(i) == '1')
			{
				c = c.add(new BigInteger("1")); // c = c + 1

				//f = (f * base) % modulus
				f = f.multiply(BigInteger.valueOf(base));  
				f = f.mod(BigInteger.valueOf(modulus));
			}

		}

		//f = (base ^ exponent) % modulus
		return f;		
	}



	//Finds the greatest common divisor among num1 and num2 
	//Code adapted from the pseudocode found in lecture 6 slide 19
	//Same program used in assignment_1
	public static long gcd(long num1, long num2)
	{
		long temp;

		while(num2 != 0)
		{
			temp = num2;
			num2 = num1 % num2;
			num1 = temp;
		}

		return num1;
	}


	//Extended Euclidean algorithm
	//Returns the greatest common divisor (d) between a and n stored in index 0
	//Returns the Multiplicative Modular Inverse of a (x) with modulo n in index 1
	//Generate x and y such that a*x + n*y + d = gcd(a, n)
	//Code adapted from : https://www.sanfoundry.com/java-program-extended-euclid-algorithm/
	public static long[] gcdExtended(long a, long n)
	{		
		//0th index stores the gcd value
		//1st index stores the value of x
		long[] array = new long[2];

		//Initial values of 
		long x = 0, y = 1;
		long finalX = 1, finalY = 0;

		//The quotient and remainder to store in each iteration
		//Acts as an intermediate variable to swap between x, finalX and 
		//y, finalY.
		long quotient, remainder, temp;	

		//If finalX is negative, use initial
		//to make finalX positive
		long initial = n;

		//If n == 0, gcd has been obtained
		while(n != 0)
		{

			//Steps described indepth in the book : 
			//Cryptography and Network Security : Principles and Practice (6th edition)
			//Page 98 and 
			//Page 99 Table 4.4
	

			//Determines the greatest common divisor
			quotient = a / n;
			remainder = a % n;
			a = n;
			n = remainder;

			//Updates value of finalX and finalY in each iteration
			temp = x;
			x = finalX - (quotient * x);
			finalX = temp;

			temp = y;
			y = finalY - (quotient * y);
			finalY = temp;
		}

		//Here finalX and finalY have opposite signs to one another
		//finalX stores the actual multiplicative inverse so we 
		//dont care about finalY
		//If finalX is positive then, finalX is the multiplicate inverse
		//else if it is negative, finalX must be turned into positive by 
		//adding 
		//finalX is such that (finalX * a) mod n == 1
		if(finalX < 0)
		{
			finalX = finalX + initial;
		}
		array[0] = a; //Greatest common divisor value
		array[1] = finalX; //Multiplicative inverse value

		return array;

	}

	//Generate a random prime number within range using Lehmann Algorithm
	public static long generateRandomPrime(long minPrime, long maxPrime)
	{
		int min = (int)minPrime;
		int max = (int)maxPrime;
		Random rand = new Random();
		boolean surePrime = false;
		long prime = 0;

		//Loop until a prime number is found 
		while(!(surePrime))
		{
			//Generate a random number within the specified range
			prime = (long)rand.nextInt((max - min) + 1) + min;

			//Use Lehmann Algorithm to determine if the randomly generate
			//number is a prime
			if(checkifPrime(prime))
			{
				surePrime = true;
			}
		}

		return prime;
	}
		

   	//Use Lehmann algorithm to check if the imported number num is a prime
	//Code adjusted from FCC prac 2
	//Step 1 : Generate a random number randomNum such that 0 < randomNum < num
	//Step 2 : Calculate r in the form : r = randomNum^((num - 1) / 2) mod num
	//Step 3 : Check to see if r satisfies certain conditions to determine if 
	//		   num is actually prime 
	//Step 4 : Repeat Steps 1 to 3 100 times to gain sufficient confidence 
	//		   that num is a prime number
   	public static boolean checkifPrime(long num)
    {
        boolean flag = false;
        
        int t = 1;

		//Even numbers are not prime 
        if( num % 2 == 0)
        {
            flag = false;
        }
        else
        {
			//Run the iteration 100 times to make a sufficient conclusion
            while(t < 100)
            {
                Random rand = new Random();
  
				//Step 1
				long randomNum = (rand.nextLong() + 1) % (num - 1) ;

				//Step 2
				//modularExponent is used to prevent any number overflow
				//which might result a prime number to be falsefully justified to be
				//not prime 
				//If Math.pow() was used,the primality test failed for numbers 
				//eg : 37, 41 etc at random runs resulting them to be not prime but infact
				//they are prime numbers
				long r = (modularExponent(randomNum, (num - 1)/2, num)).longValue();

				//Step 3 
                if((r != 1) && (r != num - 1))
                {
					//Here num is 100% not prime 
                    flag = false;
                }
                else
                {
					//num is a prime number with probability 1 - (1/(2^t)) 
                    flag = true;
                }

                t++;
            }

        }

        return flag;
    }

	//Convert exponent number to a binary bit string
	public static String convertToBinary(long exponent)
	{
		return new String(Long.toBinaryString(exponent));
	}

}
