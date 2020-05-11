public class Number
{
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



}
