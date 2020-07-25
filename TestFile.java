/* Author : Mahmudul Hossain (19303235)
 * Purpose : Perfom des encryption on the input file 
 * 			 and output the encrypted file along with
 * 			 the decrypted file which is then compared 
 * 			 with the input file
 * Last modified : 17/04/2020
 */



public class TestFile
{
	public static void main(String[] args)
	{
		String filename = "testfile-DES.txt";


		RSA rsa = new RSA(filename);
		
		//Notify user what files are created
		System.out.println("Encrypted text stored in encrypted.txt");
		System.out.println("Deccrypted text stored in decrypted.txt");

	}

}	
