package module08;

import java.time.LocalDate;
import java.util.*;

public class MenuGerente {
    private static final String NOME_BANCO = "Mentorama";
    protected static List<Cliente> clientes;
    protected static List<ContaCorrente> listContaCorrente;
    protected static Hashtable<Conta, Cliente> contaClienteHashtable;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        clientes = new ArrayList();
        contaClienteHashtable = new Hashtable<>();

        int opcao;
        int numeroDaConta = 1000, agencia, quantidadeDeSaques = 3;
        double saldo, chequeEspecial;
        String banco = NOME_BANCO, dataAniversario;

        do {
            System.out.println("--------------------------------------------" +
                    "\n*** Banco Mentorama ***" +
                    "\nMenu de opções:" +
                    "\n[1] Criar Cliente" +
                    "\n[2] Criar Conta" +
                    "\n[3] Depositar" +
                    "\n[4] Sacar" +
                    "\n[5] Transferir Valores" +
                    "\n[6] Mostrar montante disponível nas contas" +
                    "\n[7] Sair" +
                    "\nEscolha a opção desejada:" +
                    "\n--------------------------------------------");

            opcao = scanner.nextInt();
            switch (opcao) {
                case 1:
                    scanner.nextLine();
                    System.out.println("Digite o nome do cliente:");
                    String nome = scanner.nextLine();
                    System.out.println("Digite o CPF do cliente:");
                    long cpf = scanner.nextLong();
                    System.out.println("Cliente cadastrado com sucesso!\n" +
                            ">>> Cliente: " + nome +
                            ", CPF = " + cpf +
                            ".");
                    clientes.add(new Cliente(nome, cpf));
                    break;

                case 2:
                    do {
                        System.out.println("--------------------------------------------" +
                                "\nEscolha o tipo de conta a ser criada:" +
                                "\n[1] Conta Corrente" +
                                "\n[2] Conta Poupança" +
                                "\n[3] Conta Salário" +
                                "\n[4] Voltar ao Menu principal" +
                                "\n--------------------------------------------");
                        opcao = scanner.nextInt();

                        switch (opcao) {

                            case 1:
                                Cliente clienteValidado = clienteValido();
                                if (clienteValidado != null) {
                                    System.out.println("Digite o número da agência:");
                                    agencia = scanner.nextInt();
                                    System.out.println("Digite o saldo inicial:");
                                    saldo = scanner.nextDouble();
                                    System.out.println("Digite o valor do cheque especial:");
                                    chequeEspecial = scanner.nextDouble();
                                    contaClienteHashtable.put(new ContaCorrente(banco, numeroDaConta, agencia, saldo, chequeEspecial), clienteValidado);
                                    for (Map.Entry<Conta, Cliente> contaClienteEntry : contaClienteHashtable.entrySet()) {
                                        if (contaClienteEntry.getKey().getNumeroDaConta() == numeroDaConta) {
                                            System.out.println("Conta criada com sucesso!\n" +
                                                    contaClienteEntry.getValue() + "\n" +
                                                    contaClienteEntry.getKey());
                                        }
                                    }
                                    numeroDaConta += 1;
                                }
                                break;

                            case 2:
                                clienteValidado = clienteValido();
                                if (clienteValidado != null) {
                                    dataAniversario = String.valueOf(LocalDate.now());
                                    System.out.println("Digite o numero da agencia:");
                                    agencia = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.println("Digite o saldo inicial:");
                                    saldo = scanner.nextDouble();
                                    contaClienteHashtable.put(new ContaPoupanca(banco, numeroDaConta, agencia, saldo, dataAniversario), clienteValidado);
                                    for (Map.Entry<Conta, Cliente> contaClienteEntry : contaClienteHashtable.entrySet()) {
                                        if (contaClienteEntry.getKey().getNumeroDaConta() == numeroDaConta) {
                                            System.out.println("Conta criada com sucesso!\n" +
                                                    contaClienteEntry.getValue() + "\n" +
                                                    contaClienteEntry.getKey());
                                        }
                                    }
                                    numeroDaConta += 1;
                                }
                                break;

                            case 3:
                                clienteValidado = clienteValido();
                                if (clienteValidado != null) {
                                    System.out.println("Digite o numero da agencia:");
                                    agencia = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.println("Digite o saldo inicial:");
                                    saldo = scanner.nextDouble();
                                    contaClienteHashtable.put(new ContaSalario(banco, numeroDaConta, agencia, saldo, quantidadeDeSaques), clienteValidado);
                                    for (Map.Entry<Conta, Cliente> contaClienteEntry : contaClienteHashtable.entrySet()) {
                                        if (contaClienteEntry.getKey().getNumeroDaConta() == numeroDaConta) {
                                            System.out.println("Conta criada com sucesso!\n" +
                                                    contaClienteEntry.getValue() + "\n" +
                                                    contaClienteEntry.getKey());
                                        }
                                    }
                                    numeroDaConta += 1;
                                }
                                break;

                            case 4:
                                break;

                            default:
                                System.out.println("Opção Inválida.");
                                break;
                        }
                    } while (opcao != 4);
                    break;

                case 3:
                    System.out.println("Operação depósito selecionada.");
                    Conta conta = contaValida();
                    if (conta != null) {
                        System.out.println("Digite o valor a ser retirado:");
                        int valor = scanner.nextInt();
                        conta.getDeposito(valor);
                    }
                    break;

                case 4:
                    conta = contaValida();
                    if (conta != null) {
                        System.out.println("Operação saque selecionada.");
                        System.out.println("Digite o valor a ser retirado:");
                        int valor = scanner.nextInt();
                        conta.getSaque(valor);
                    }
                    break;

                case 5:
                    System.out.println("Operação transferência selecionada");
                    System.out.println("Digite as informações da conta emissora");
                    conta = contaValida();
                    if (conta != null) {
                        System.out.println("Digite o valor a ser transferido:");
                        int valor = scanner.nextInt();
                        System.out.println("Digite as informações da conta de destino");
                        Conta destino = contaValida();
                        conta.getTransferencia(destino, valor);
                    }
                    break;
                case 6:
                    System.out.println("O montante disponível no banco é R$" + MontanteTotal());
                    System.out.println("Lista de contas: ");
                    for (Map.Entry<Conta, Cliente> contaClienteEntry : contaClienteHashtable.entrySet()) {
                        System.out.println(contaClienteEntry.getValue() + "\n" + contaClienteEntry.getKey());
                    }
                    break;

                case 7:
                    System.out.println("Programa encerrado!");
                    scanner.close();
                    break;

                default:
                    System.out.println("Opção Inválida.");
                    break;
            }
        } while (opcao != 7);
    }

    public static Cliente clienteValido() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o CPF de um cliente cadastrado:");
        long cpfCadastrado = scanner.nextLong();
        for (Cliente cliente : clientes) {
            if (Objects.equals(cliente.getCpf(), cpfCadastrado)) {
                System.out.println(cliente + " Selecionado.");
                return cliente;
            }
        }
        System.out.println("Cliente não cadastrado.");
        return null;
    }

    public static Conta contaValida() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o número da conta:");
        int numeroConta = scanner.nextInt();
        System.out.println("Digite o número da agencia:");
        int agencia = scanner.nextInt();
        for (Map.Entry<Conta, Cliente> contaClienteEntry : contaClienteHashtable.entrySet()) {
            if (contaClienteEntry.getKey().getAgencia() == agencia &&
                    contaClienteEntry.getKey().getNumeroDaConta() == numeroConta) {
                System.out.println(contaClienteEntry.getValue() + "Selecionado\n" + contaClienteEntry.getKey());
                return contaClienteEntry.getKey();
            }
        }
        System.out.println("Conta não encontrada.");
        return null;
    }

    public static Double MontanteTotal() {
        listContaCorrente = new ArrayList<>();
        double saldoTotal = 0;
        double creditoChequeEspecial = 0;
        for (Map.Entry<Conta, Cliente> contaClienteEntry : contaClienteHashtable.entrySet()) {
            saldoTotal = contaClienteHashtable
                    .keySet()
                    .stream()
                    .mapToDouble(Conta::getSaldo)
                    .sum();
            if (contaClienteEntry.getKey().getClass().equals(ContaCorrente.class)) {
                listContaCorrente.add((ContaCorrente) contaClienteEntry.getKey());
            }
            creditoChequeEspecial = listContaCorrente
                    .stream()
                    .mapToDouble(ContaCorrente::getChequeEspecial)
                    .sum();
        }
        return saldoTotal - creditoChequeEspecial;
    }
}
