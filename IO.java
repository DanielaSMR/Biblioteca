
/**
* IO
* Llamaremos a esta clase para pedir los datos introducidos por teclado que necesitemos
* @author Daniela Perez / Empresa
* @version 0.1, 
*/
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IO{ 
        private static final Scanner sc = new Scanner(System.in);
    
        public static String pedirTexto() {
    
            
            String inputString = null;
    
            try {
                inputString = sc.nextLine();
                if (inputString == null || inputString.isEmpty()) {
                    throw new Exception("String vacio o nulo.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                inputString = pedirTexto();
            }
    
            return inputString;
        }
    
        public static int pedirEntero() {
            int readInt = 0;
    
            try {
                String inputString = sc.nextLine();
    
                if (inputString == null || inputString.isEmpty()) {
                    throw new Exception("String vacio o nulo.");
                }
    
                readInt = Integer.parseInt(inputString);
    
            } catch (NumberFormatException e) {
                System.out.println("Error: El argumento introducido no es del tipo int. Detalles: " + e.getMessage());
                readInt = pedirEntero();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                readInt = pedirEntero();
            }
    
            return readInt;
        }
    
        public static char pedirChar() {
            char readChar = ' ';
    
            try {
                String inputString = sc.nextLine();
    
                if (inputString == null || inputString.isEmpty()) {
                    throw new Exception("String vacio o nulo.");
                }
    
                if (inputString.length() > 1) {
                    throw new Exception("Introduce solo un caracter.");
                }
    
                readChar = inputString.charAt(0);
    
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                readChar = pedirChar();
            }
    
            return readChar;
        }
    
        public static double pedirDouble() {
            double readDouble = 0.0;
            
            try {
                String inputString = sc.nextLine();
    
                if (inputString == null || inputString.isEmpty()) {
                    throw new Exception("Introduce solo un caracter.");
                }
    
                readDouble = Double.parseDouble(inputString);
    
            } catch (NumberFormatException e) {
                System.out.println("Error: Valor no valido para el tipo double. Detalles: " + e.getMessage());
                readDouble = pedirDouble();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                readDouble = pedirDouble();
            }
    
            return readDouble;
        }
    
        public static boolean pedirBoolean() {
            boolean readBoolean = false;
        
            try {
                String inputString = sc.nextLine();
    
                Map<String, Boolean> booleanMap = new HashMap<>();
    
                booleanMap.put("true", true);
                booleanMap.put("false", false);
                booleanMap.put("t", true);
                booleanMap.put("f", false);
                booleanMap.put("si", true);
                booleanMap.put("no", false);
                booleanMap.put("s", true);
                booleanMap.put("n", false);
        
                if (!booleanMap.containsKey(inputString.toLowerCase())) {
                    throw new Exception("Introduce un valor valido para el tipo boolean. Valores validos: true, false, t, f, si, no, s, n.");
                }
        
                readBoolean = booleanMap.get(inputString.toLowerCase());
        
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                readBoolean = pedirBoolean();
            }
        
            return readBoolean;
        }
    
        public static int pedirRango(int min, int max) throws Exception {
            int readInt = 0;
    
                try {
                    readInt = pedirEntero();
                    if (readInt < min || readInt > max) {
                        throw new Exception("Numero fuera del rango");
                    }
                }catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    readInt = pedirRango(min, max);
                }
                
            return readInt;
        }
    }
    
    