public class CalcGCD
{
	public static void main(String[] args)
	{
		long a = 2543, b = 1672;
//		System.out.println("gcd(" + a + ", " + b + ") = " + Number.gcd(a,b));
//		RSA rsa = new RSA();

		System.out.println("gcd(" + a + ", " + b + ") = " + Number.gcdExtended(a,b)[0]);

	}
}
