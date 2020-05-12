import java.util.*;

public class Lehmann
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number : ");
        int num = sc.nextInt();

        boolean flag = checkifPrime(num);

        if(flag)
        {
            System.out.println(num + " is a prime number ");
        }
        else 
        {
            System.out.println(num + " is not a prime number ");
        }
    }

    public static boolean checkifPrime(int num)
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
                int randomNum = rand.nextInt(((num - 1) - 1) + 1 ) + 1;  // 0 < randomNum < num
                
                long r = (long)(Math.pow(randomNum, (num - 1) / 2) % num);       //Binary modular exponentiation required eg : 37

                if((r != 1) && (r != num - 1))
                {
                    flag = false;
                    System.out.println("100% not prime");
                }
                else
                {
                    flag = true;
                    System.out.println("Probability : " + (1 - 1/(Math.pow(2,t))));
                }

                t++;
            }

        }

        return flag;
    }
}
