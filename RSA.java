public class RSA
{
	public static final long MINPRIME = 10000;
	public static final long MAXPRIME = 100000;

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

		//Generate random odd e values from 1 < e < phi
		publicE = this.getPublicKey();
		
		privateD = this.gcdExtended(/*To be continued */);
	}

}
