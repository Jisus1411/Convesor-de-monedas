package ConversorDeMoneda;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 1;

        System.out.println("CONVERSOR DE MONEDA\n");

        while (opcion == 1) {
            try {
                // Mostrar las monedas disponibles
                System.out.println("Monedas disponibles:");
                for (Map.Entry<String, String> entry : CambiadorDeMoneda.NOMBRES_MONEDAS.entrySet()) {
                    System.out.printf("%s - %s%n", entry.getKey(), entry.getValue());
                }

                System.out.println("\n--------------------------------");

                // Solicitar moneda de origen
                String monedaOrigen;
                while (true) {
                    System.out.print("Ingrese la moneda de origen (ej: USD, EUR, COP): ");
                    monedaOrigen = scanner.nextLine().trim().toUpperCase();
                    if (CambiadorDeMoneda.NOMBRES_MONEDAS.containsKey(monedaOrigen)) break;
                    System.out.println("❌ Código no válido. Intente de nuevo.\n");
                }

                // Solicitar moneda destino
                String monedaDestino;
                while (true) {
                    System.out.print("Ingrese la moneda destino (ej: USD, EUR, COP): ");
                    monedaDestino = scanner.nextLine().trim().toUpperCase();
                    if (CambiadorDeMoneda.NOMBRES_MONEDAS.containsKey(monedaDestino)) break;
                    System.out.println("❌ Código no válido. Intente de nuevo.\n");
                }

                // Solicitar monto
                double monto = 0;
                while (true) {
                    try {
                        System.out.print("Ingrese el valor a convertir: ");
                        monto = Double.parseDouble(scanner.nextLine().trim());
                        if (monto <= 0) {
                            System.out.println("❌ El valor debe ser mayor que 0.\n");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Valor no válido. Ingrese un número.\n");
                    }
                }

                // Calcular resultado
                double tasa = CambiadorDeMoneda.obtenerTasa(monedaOrigen, monedaDestino);
                double resultado = monto * tasa;

                System.out.println("--------------------------------");
                System.out.printf("Resultado: %.2f %s = %.2f %s%n",
                        monto, monedaOrigen, resultado, monedaDestino);
                System.out.println("--------------------------------");

            } catch (IOException | InterruptedException e) {
                System.out.println("⚠️ Error al conectar con la API. Intente de nuevo.");
                continue; // Reinicia el bucle sin cerrar el programa
            } catch (Exception e) {
                System.out.println("⚠️ Ocurrió un error inesperado: " + e.getMessage());
                continue;
            }

            // Menú para continuar o salir
            System.out.println("\n¿Desea realizar otra conversión?");
            System.out.println("1. Sí, continuar");
            System.out.println("2. No, salir");
            System.out.print("Elija una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion == 2) {
                    System.out.println("Gracias por usar el conversor. ¡Hasta pronto!");
                } else if (opcion != 1) {
                    System.out.println("Opción no válida. Saliendo del programa...");
                    opcion = 2;
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida. Cerrando el programa...");
                opcion = 2;
            }

            System.out.println();
        }

        scanner.close();
    }
}
