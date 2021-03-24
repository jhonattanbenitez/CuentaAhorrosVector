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

}
