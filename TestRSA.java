import java.util.*;

public class TestRSA
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(Sytem.in);
		System.out.println("Enter text to encrypt : ");
		String line = sc.nextLine();

		RSA rsa = new RSA(line);

		System.out.println("Encrypted : " + rsa.getEncrypted());
		System.out.println("Decrypted : " + rsa.getDecrpyted());
	}
}		
