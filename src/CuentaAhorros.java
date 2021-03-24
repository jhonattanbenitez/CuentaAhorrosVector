import javax.swing.*;
import java.text.DecimalFormat;

public class CuentaAhorros {
    //Atributos

    private String titular;
    private final String numCta;
    private double saldo;
    private DecimalFormat df;

    // constructor:las
    public CuentaAhorros(String titular, String numCta, double saldo) {
        this.titular = titular.trim().toUpperCase();
        this.numCta = numCta.trim();
        this.saldo = saldo;

        df = new DecimalFormat("0.00");
    }
    // Getters

    public String getTitular() {
        return titular;
    }

    public String getNumCta() {
        return numCta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    //MÉTDO PARA DEPOSITAR DINERO
    public void consignar(double consignacion) {
        if (consignacion > 0) {
            saldo += consignacion;
        } else {
            JOptionPane.showMessageDialog(null, "el valor "
                    + "consignación es <=0", "ERROR CONSIGNACION", 0);
        }
    }

    // Método para retirar el dinero
    public void retirar(double valorRetiro) {
        if (valorRetiro > 0 && saldo - valorRetiro >= 0) {
            saldo -= valorRetiro;
        } else {
            JOptionPane.showMessageDialog(null, "el valor "
                            + "retiro es <=0 o no tiene saldo suficiente",
                    "ERROR CONSIGNACION", 0);
        }
    }

    @Override
    public String toString() {
        return "CuentaDeAhorros{" + "titular=" + titular + ", numCta="
                + numCta + ", saldo=" + df.format(saldo) + '}';
    }



}
