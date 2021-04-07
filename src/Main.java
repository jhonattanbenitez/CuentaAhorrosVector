import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.text.DecimalFormat;

/**
 * En una entidad financiera, una cuenta de ahorros se caracteriza por el nombre
 * completo del titular de la cuenta, un número único de cuenta (que, aunque es
 * número, se recomienda manejar como texto) y el saldo de la cuenta. La entidad
 * financiera se caracteriza por su razón social y por su NIT (Número de
 * Identificación Tributaria). La entidad requiere un programa que le permita:
 * (1) Crear y guardar las cuentas de ahorro de sus clientes; (2) Obtener un
 * reporte con los datos de cada cuenta de ahorros; (3) Buscar los datos de una
 * cuenta de ahorros a partir de su número de cuenta; (4)A partir del número de
 * una cuenta de ahorros, buscar la posición (o índice) de la cuenta en el
 * contenedor que las guarda; (5) Eliminar una cuenta de ahorros a partir de su
 * número de cuenta; (6) Determinar el mayor saldo; (7) Calcular el saldo
 * promedio de las cuentas; (8) Obtener un reporte especial, con los datos de
 * las cuentas con saldos mayores o iguales que un saldo de referencia dado;
 * (9) Ejecutar un abono en todas las cuentas de ahorro, debido a un Superávit
 * financiero; (10) Ordenar las cuentas de ahorro por los apellidos de los
 * titulares; y (11) Ordenar las cuentas de ahorro por los saldos, del mayor al
 * menor.
 */

// texto, un número real REDONDEADO.
// desde el teclado.


//**********************************************************************************************************************

// Clase CLIENTE:
public class Main {

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("0.00");
        String strHayCuentas;
        char hayCuentas, continuar;

        Banco banco = crearBanco();

        //ciclo infinito
        while (true) {
            strHayCuentas = JOptionPane.showInputDialog("¿Hay cuentas de ahorros" +
                    " para abrir en el banco [S/N]?").trim().toUpperCase();
            hayCuentas = strHayCuentas.charAt(0);

            if (hayCuentas == 'N') System.exit(0); //se finaliza el programa

            //Ciclo para crear las cuentas del banco:
            CuentaAhorros cta;
            while (hayCuentas == 'S') {
                cta = crearCuentaAhorros();
                banco.agregarCuentadeAhorros(cta);
                strHayCuentas = JOptionPane.showInputDialog("¿Hay MÁs cuentas de ahorro" +
                        "para abrir en el banco?").trim().toUpperCase();
                hayCuentas = strHayCuentas.charAt(0);

            }
            // Obtener reporte con los datos del banco y de cada cuenta de ahorros
            System.out.println(banco.toString());

            //Buscar los datos de una cuenta de ahorros a partir de su número de cuenta

            String numCta = JOptionPane.showInputDialog("Digite el número de la cuenta de " +
                    "ahorros a buscar").trim();
            System.out.println(banco.buscarDatosDeUnaCuenta(numCta));
            int posicion = banco.buscarPosicionDeUnaCuenta(numCta);
            if (posicion == -1) {
                System.out.println("No existe una cuenta con número " + numCta);

            }else{
                System.out.println("La cuenta con No. " + numCta + "Existe y está en la posisción" + (posicion+1));
            }
            // Eliminar una cuenta de ahorros a partir del número de la cuenta:
            numCta = JOptionPane.showInputDialog("Digite el número de la cuenta que desea eliminar: ").trim();

            System.out.println(banco.eliminarCuentaDeAhorros(numCta));
            // Determinar el mayor saldo:
            double mayorSaldo = banco.obtenerMayorSaldo();
            System.out.println("El mayor sald ode la cuenta es : $" + df.format(mayorSaldo));

            //Calcular el saldo promedio de las cuentas
            double saldoPromedio = banco.calcularSaldoPromedio();
            System.out.println("El saldo promedio de las cuentas es: $" + df.format(saldoPromedio));

            //Obtener reporte especial, con los datos de las cuentas con saldos mayores o iguales que un saldo de
            // referencia dado:

            double saldoReferencia = Double.parseDouble(JOptionPane.showInputDialog("Digite el saldo de" +
                    "referencia").trim());
            System.out.println(banco.reporteEspecial(saldoReferencia));

            //Ejecutar el abono en todas las cuentas de ahorro debido a un superavit financiero
            double abono = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la cantidad de dinero que " +
                    "se abonará a las cuentas de ahorros").trim());
            banco.abonarPorSuperavit(abono);

            System.out.println("\nDatos de las cuentas, DESPUÉS DEL ORDENAMIENTO "
                    + "POR LOS APELLIDOS DE LOS TITULARES: ");
            System.out.println(banco.toString());
            // (11) Ordenar las cuentas de ahorro por los saldos, del mayor al menor:
            banco.ordenarPorSaldo();
            System.out.println("\nDatos de las cuentas, DESPUÉS DEL ORDENAMIENTO "
                    + "POR LOS SALDOS, DEL MAYOR AL MENOR: ");
            System.out.println(banco.toString());

            continuar = JOptionPane.showInputDialog("¿Desea CONTINUAR la"
                    + " ejecución del programa? [S/N]: ").trim().toUpperCase().charAt(0);
            if(continuar == 'N') break; // ABORTE el ciclo infinito Y TERMINE.
        }

    } // main

    private static Banco crearBanco() {
        String razonSocial, NIT;

        razonSocial = JOptionPane.showInputDialog("Digite la razon social del banco").trim().toUpperCase();
        NIT = JOptionPane.showInputDialog("Digite el NIT del cliente").trim();

        //Instancia la calse Banco; es decir, crea un objeto de esa clase, invocando el constructor de la clase
        // enviando los argumentos esperados y retorna una referencia al objeto:

        return new Banco(razonSocial, NIT);


    }

    private static CuentaAhorros crearCuentaAhorros() {
        // Declaración de variables locales para los atributos de la clase CuentaAhorros:
        String titular, numCta;
        double saldo;
        //lectura de las variables locales
        titular = JOptionPane.showInputDialog("Digite apellidos y nombres del " +
                "titular de la cuenta").trim().toUpperCase();
        numCta = JOptionPane.showInputDialog("Digite el número de la cuenta").trim();
        saldo = Double.parseDouble(JOptionPane.showInputDialog("Digite el saldo inicial: $").trim());

        //Instancia la clase CuentaAhorros; es decir, crea un objeto de esa clase invocando el constructor de la clase
        //enviándole los argumentos esperados y retorna una referencia al objeto:

        return new CuentaAhorros(titular, numCta, saldo);
    }

}   // public class Main