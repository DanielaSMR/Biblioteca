import java.io.IOException;
import java.util.Scanner;

public class IO{

    public static Scanner sc = new Scanner(System.in);

    static Integer pedirEntero() throws Exception{
        Integer numero = sc.nextInt();
        return numero;
    }

    static String pedirTexto() throws Exception{
        String texto = sc.nextLine();
        return texto;
    }

    static Boolean pedirBoolean() throws Exception{
        Boolean eleccion = sc.nextBoolean();
        return eleccion;
    }

    static Double pedirDouble() throws Exception{
        Double nums = sc.nextDouble();
        return nums;
    }

    



}