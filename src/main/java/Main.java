import program.IASimulador;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        IASimulador iaSimulador = new IASimulador();
        System.out.println("Digite qual a busca deseja realizar:");
        System.out.println("1 - Busca Ordenada");
        System.out.println("2 - Busca Gulosa");
        System.out.println("3 - Busca A*");
        System.out.println("4 - Busca IDA*");
        System.out.println("Qualquer outro número para sair.");
        while (true) {
            try {
                switch (Integer.parseInt(new Scanner(System.in).nextLine())){
                    case 1 -> iaSimulador.buscaOrdenada();
                    case 2 -> iaSimulador.buscaGulosa();
                    case 3 -> iaSimulador.buscaAEstrela();
                    case 4 -> iaSimulador.buscaIdaEstrela();
                }
                break;

            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Digite novamente.");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        System.out.println("Programa encerrado.");
    }

}
