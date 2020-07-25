/* Author : Mahmudul Hossain (19303235)
 * Purpose : Calculates greatest common divisor between two
 * 		     numbers using extended euclidean algorithm
 * Date Modified : 13/05/2020
 */

public class CalcGCD
{
	public static void main(String[] args)
	{
		//Numbers referring to Q.1
		long a = 2543, b = 1672;
		System.out.println("gcd(" + a + ", " + b + ") = " + Number.gcdExtended(a,b)[0]);

	}
}
