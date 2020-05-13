import java.util.Random;

public class Number
{

	
	public static long modularExponent(long base, long exponent, long modulus)
	{
/*
		long res = 1; // Initialize result 
         
        // Update x if it is more   
        // than or equal to p 
        base = base % modulus;  
  
       if (base == 0) return 0; // In case x is divisible by p;    
  
        while (exponent > 0) 
        { 
            // If y is odd, multiply x 
            // with result 
            if((exponent & 1) == 1) 
                res = (res * base) % modulus; 
      
            // y must be even now 
            // y = y / 2 
            exponent = exponent >> 1;  
            base = (base * base) % modulus;  
        } 
        return res; 
	*/

//		System.out.println(base + "^" + exponent + " mod " + modulus); 
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
		
//		System.out.println("F value : " + f);
		return f;
			
		/*
	 // Convert the exponent into an array of bits
          long[] bits = longToBinary(exponent);
  
          long c = 0, f = 1;
  
          for (int i = 0; i < bits.length; i++)
          {
              c = 2*c;
              f = (f * f) % modulus;
  
              if (bits[i] == 1)
             {
                 c++;
                 f = (f * base) % modulus;
             }
         }
 
         return f;
	*/

	}	


	//Finds the greatest common divisor among num1 and num2 
	//Code adapted from the pseudocode found in lecture 6 slide 19
	//Similar program used in assignment_1
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
	public static long[] gcdExtended(long a, long n)
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
		

		/*
		//Faraz
		 long[] array = new long[2];
		 long temp = 0;
         long quotient; // The quotient is stored each stage
         long x0 = 1, y0 = 0; // Initial values of x and y
         long x1 = 0, y1 = 1; // Secondary values of x and y
         long tempX, tempY; // Used as intermediaries to swap x0,y0 with x1,y1
 
         // We also have to store the original value of n in case
          // x0 turns out to be a negative number
          long origN = n;
 
          // When n = 0, it means we have found the gcd
          while (n != 0)
          {
              // Steps for the normal Euclidean algorithm
              temp = n;
              quotient = a / n;
              n = a % n;
              a = temp;
  
             // Extended Euclidean Algorithm requires us to update the x and y
              // values with each step as well
              // Refer to the textbook to understand why each step does what
              // it does
              tempX = x1;
              tempY = y1;
              x1 = x0 - quotient*x1;
              y1 = y0 - quotient*y1;
              x0 = tempX;
              y0 = tempY;
          }
		  if (x0 < 0) x0 = origN + x0;
		array[0] = a;
		array[1] = x0;

		return array;
		*/

	}

   public static boolean checkifPrime(long num)
    {
        boolean flag = false;
        
        int t = 1;

        if( num % 2 == 0)
        {
            flag = false;
        }
        else
        {
            while(t < 100)
            {
                Random rand = new Random();
  
  				long randomNum = (long)rand.nextInt(((int) num - 2) + 1) + 2;				
//				long randomNum = (rand.nextLong() % (num - 1)) + 1;

				long r = modularExponent(randomNum, (num - 1)/2, num);

                if((r != 1) && (r != num - 1))
                {
                    flag = false;
                }
                else
                {
                    flag = true;
                }

                t++;
            }

        }

        return flag;
    }

	public static String convertToBinary(long exponent)
	{
		return new String(Long.toBinaryString(exponent));
	}

	private static long[] longToBinary(long x)
	{
         String binaryString = Long.toBinaryString(x);
         long[] bits = new long[binaryString.length()];
 
         for (int i = 0; i < binaryString.length(); i++)
         {
             bits[i] = Character.getNumericValue(binaryString.charAt(i));
         }

         return bits;
     }



}
