import javax.swing.*;
import java.util.Arrays;

public class Banco {
    private static final int TAMANIO_INICIAL = 10;
    //atributos
    private String razonSocial;
    private String NIT;
    private CuentaAhorros[] cuentasDeAhorros;
    private int cantCtasDeAhorro;
    private int indice;

    public Banco(String razonSocial, String NIT) {
        this.razonSocial = razonSocial.trim().toUpperCase();
        this.NIT = NIT.trim();
        // crea y dimensiona el vector de cuentas de ahooro:

        cuentasDeAhorros = new CuentaAhorros[TAMANIO_INICIAL];
        this.cantCtasDeAhorro = 0;
        this.indice = 0;
    } // fin del constructor

    // métodos de consulta
    public String getRazonSocial() {
        return razonSocial;
    }

    public String getNIT() {
        return NIT;
    }

    public int getCantCtasDeAhorro() {
        return cantCtasDeAhorro;
    }
    //Setters

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    //Metodo que construye y retorna el estado del objeto

    @Override
    public String toString() {
        String estado = "Entidad financiera" + razonSocial + ", NIT:" + NIT + "\n  ****Cuentas de Ahorro \n";

        for(int i=0; i < cantCtasDeAhorro; i ++){
            estado+= cuentasDeAhorros[i].toString() + "\n";
        }

        return estado;
    }

    //métodos
    public void agregarCuentadeAhorros(CuentaAhorros cta){
        if(indice < cuentasDeAhorros.length){
            cuentasDeAhorros[indice] = cta; //agrega la cuenta en el vector
            indice ++; // el indice actual se incrementa en 1
            cantCtasDeAhorro ++;
        }else{
            //el vector está lleno.
            //declara, crea y dimensiona un vector locar con un tamaño igual al anterior
            //doble del tammaño del vector cuentasDeAhorro
            CuentaAhorros[] vectorAuxiliar = new CuentaAhorros[2*cuentasDeAhorros.length];

            //Copiar el vector de cuentas en el vector Local de tamaño doble:

            for(int i = 0; i < cuentasDeAhorros.length; i++){
                vectorAuxiliar[i] = cuentasDeAhorros[i];
            }
            // Que el atributo referencie al vector al auxiliar:
            cuentasDeAhorros = vectorAuxiliar;
            // Revursividad: el método se invoca a si mismo para guardar la cuenta:
            agregarCuentadeAhorros(cta);

        }

        }
     public String buscarDatosDeUnaCuenta(String numCta){
        for(int i =0; i< cantCtasDeAhorro; i++){
            if(cuentasDeAhorros[i].getNumCta().equals(numCta)){
                //salga del método y retorne la posición de la cuenta buscada:
                return cuentasDeAhorros[i].toString();
            }
        }
        return "no existe cuenta de ahorros con el numero de cuenta";

    }

    public int buscarPosicionDeUnaCuenta(String numCta){
        for( int i = 0; i < cantCtasDeAhorro; i++){
            if(cuentasDeAhorros[i].getNumCta().equals(numCta)){
                //salga del método y retorne la posicion de la cuenta buscada

                return i;
            } //if

        } //for
        return  -1; // significa que no existe la cuenta con el número dado
    }
    // Elimina una Cuenta de Ahorros a partir de su número de cuenta.
    public void eliminarCuentaDeAhorros(String numCta) {
        int posicionDeLaCuenta = this.buscarPosicionDeUnaCuenta(numCta);
        String msj;
        if(posicionDeLaCuenta == -1) {
            msj = "No existe una cuenta con el número " + numCta;
            JOptionPane.showMessageDialog(null, msj, "Error Eliminación", 0);
            System.out.println(msj);
            return; // SALGA del método.
        }   // if
        // La encontró y se procede a su eliminación:
        for(int i = posicionDeLaCuenta; i < cantCtasDeAhorro - 1; i ++) {
           cuentasDeAhorros[i] = cuentasDeAhorros[i + 1];
        }   // for
        cantCtasDeAhorro --;
        indice = cantCtasDeAhorro;
        msj = "Se ha eliminado la cuenta con el número " + numCta;
        JOptionPane.showMessageDialog(null, msj, "Eliminación Exitosa", 1);
        System.out.println(msj);
    }   // void eliminarCuentaDeAhorros(String numCta)

    // Método que determina y retorna el mayor saldo:
    public double obtenerMayorSaldo(){
        // Suponga que el mayor saldo lo tiene la primer cuenta:
        double mayorSaldo = cuentasDeAhorros[0].getSaldo();
        // Recorra el vector y compare el saldo actual con lo que
        // hay en mayorSaldo:
        for(int i = 1; i < cantCtasDeAhorro; i ++) {
            if(cuentasDeAhorros[i].getSaldo() > mayorSaldo) {
                // Actualice el mayor saldo:
                mayorSaldo = cuentasDeAhorros[i].getSaldo();
            }   // if
        }   // for
        return mayorSaldo;
    }   // double obtenerMayorSaldo()

    // Método que calcula y retorna el saldo promedio de las cuentas:
    public double calcularSaldoPromedio() {
        double suma = 0, saldoPromedio;
        for(int i = 0; i < cantCtasDeAhorro; i ++) {
            suma += cuentasDeAhorros[i].getSaldo();
        }   // for
        saldoPromedio = suma / cantCtasDeAhorro;
        return saldoPromedio;
    }   // double calcularSaldoPromedio()

    // Método que construye y retorna un reporte especial, con los datos de las
    // cuentas con saldos mayores o iguales que un saldo de referencia:
    public String reporteEspecial(double saldoReferencia) {
        String reporte = "Cuentas con Saldos mayores o iguales que $ " +
                saldoReferencia + ":\n";
        for(int i = 0; i < cantCtasDeAhorro; i ++) {
            if(cuentasDeAhorros[i].getSaldo() >= saldoReferencia) {
                // Concatene en el reporte los datos de esta cuenta:
                reporte += cuentasDeAhorros[i].toString() + "\n";
            }   // if
        }   // for
        return reporte;
    }   // String reporteEspecial(double saldoReferencia)

    // Abonar por Superávit
    public void abonarPorSuperavit(double abono) {
        if(abono <= 0) return;
        double saldoActual;
        for(int i = 0; i < cantCtasDeAhorro; i ++) {
            saldoActual = cuentasDeAhorros[i].getSaldo();
            cuentasDeAhorros[i].setSaldo(saldoActual + abono);
        }   // for
    }   // void abonarPorSuperAavit(double abono)

    // Método de ordenamiento BURBUJA para ordenar las cuentas por los titulares:
    public void ordenarPorTitular() {
        String titular1, titular2;
        CuentaAhorros auxi;
        for(int i = 0; i < cantCtasDeAhorro - 1; i ++) {
            for(int j = 0; j < cantCtasDeAhorro - 1 - i; j ++) {
                titular1 = cuentasDeAhorros[j].getTitular();
                titular2 = cuentasDeAhorros[j + 1].getTitular();
                if(titular1.compareTo(titular2) > 0) {
                    // Intercambie de posición las cuentas:
                    auxi = cuentasDeAhorros[j];
                    cuentasDeAhorros[j] = cuentasDeAhorros[j + 1];
                    cuentasDeAhorros[j + 1] = auxi;
                }   // if
            }   // for j
        }   // for i
    }   // void ordenarPorTitular()

    // Método de ordenamiento BURBUJA para ordenar las cuentas por los
    // saldos del mayor al menor:
    public void ordenarPorSaldo() {
        double saldo1, saldo2;
        CuentaAhorros auxi;
        for(int i = 0; i < cantCtasDeAhorro - 1; i ++) {
            for(int j = 0; j < cantCtasDeAhorro - 1 - i; j ++) {
                saldo1 = cuentasDeAhorros[j].getSaldo();
                saldo2 = cuentasDeAhorros[j + 1].getSaldo();
                if(saldo1 < saldo2) {
                    // Intercambie de posición las cuentas:
                    auxi = cuentasDeAhorros[j];
                    cuentasDeAhorros[j] = cuentasDeAhorros[j + 1];
                    cuentasDeAhorros[j + 1] = auxi;
                }   // if
            }   // for j
        }   // for i
    }   // void ordenarPorSaldo()

}
