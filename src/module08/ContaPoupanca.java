package module08;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ContaPoupanca extends Conta implements Tributavel{

    public static final double TAXA_RENDIMENTO_MENSAL = 0.05;
    private String dataAniversario;
    private String dataDia = String.valueOf(LocalDate.now());

    public ContaPoupanca(String banco, int numeroDaConta, int agencia, double saldo, String dataAniversario) {
        super(banco, numeroDaConta, agencia, saldo);
        this.dataAniversario = dataAniversario;
    }

    public double getSaldo() {
        LocalDate dateAniversario = LocalDate.parse(dataAniversario, DateTimeFormatter.ISO_DATE);
        LocalDate dateDia = LocalDate.parse(dataDia, DateTimeFormatter.ISO_DATE);
        long diferencaTempo = ChronoUnit.MONTHS.between(dateAniversario.withDayOfMonth(1), dateDia.withDayOfMonth(1));
        if (dateDia.isBefore(dateAniversario.plusMonths(1))){
            return this.saldo;
        } else {
            double saldoJuros = this.saldo * Math.pow(TAXA_RENDIMENTO_MENSAL, diferencaTempo);
            return Math.round(saldoJuros*100.00)/100.00;
        }
    }

    @Override
    public boolean getSaque(double valor) {
        if (valor > getSaldo()) {
            System.out.println("Saldo insuficiente para realizar a transação.");
            return false;
        } else {
            this.saldo -= valor;
            System.out.println("Saque de R$ " + valor + " realizado com sucesso.");
            System.out.println("Saldo atualizado: R$ " + getSaldo());
            return true;
        }
    }

    @Override
    public boolean getTransferencia(Conta destino, double valor) {
        if (getSaldo() < valor + getValorImposto()) {
            System.out.println("Saldo insuficiente para realizar o saque.");
            return false;
        } else {
            this.saldo = saldo - valor - getValorImposto();
            destino.saldo += valor;
            System.out.println("Transferência de R$ " + valor + " realizado com sucesso.");
            System.out.println("Taxa fixa de transferência: R$ " + getValorImposto());
            System.out.println("Saldo atualizado: R$ " + getSaldo());
            return true;
        }
    }

    @Override
    public String toString() {
        return "Conta Poupanca {" +
                super.toString() +
                "Dia do rendimento mensal = " + dataAniversario +
                '}';
    }

    @Override
    public double getValorImposto() {
        return - 1;
    }
}
