package program;

import model.ArvoreSolucao;
import model.Estado;
import utils.Constantes;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class IASimulador {

    private Queue<Estado> abertos;
    private List<Estado> fechados;

    public Boolean isEstadoFinal(Estado estado){
        return Objects.equals(estado.getTabuleiro(),Constantes.estadoFinal);
    }

    public void buscaGulosa() {

        abertos = new LinkedBlockingQueue<>();
        fechados = new ArrayList<>();
        ArvoreSolucao arvoreSolucao = new ArvoreSolucao();

        Estado estadoAtual = new Estado(Constantes.estadoInicial, null,0);

        arvoreSolucao.adicionarEstado(estadoAtual);

        while (estadoAtual != null) {
            if(isEstadoFinal(estadoAtual)) {
                construirSolucao(estadoAtual,arvoreSolucao);
                return;
            } else {
                List<Estado> estadosFilhos = gerarFilhos(estadoAtual);
                abertos.addAll(estadosFilhos);
                abertos.stream().sorted(Comparator.comparingInt(Estado::getValorHeuristica));
                arvoreSolucao.adicionarTodos(estadosFilhos,estadoAtual,0);
                abertos.remove(estadoAtual);
                fechados.add(estadoAtual);
                estadoAtual = abertos.poll();
            }
        }
        throw  new RuntimeException("Problema não possui solução");
    }

    private void construirSolucao(Estado estadoAtual, ArvoreSolucao arvoreSolucao) {
        Estado estadoPai = estadoAtual.getEstadoPai();
        Integer custoReal = 0;
        Stack<Estado> pilha = new Stack<>();
        pilha.add(estadoAtual);
        while (estadoPai != null) {
            pilha.add(estadoPai);
            custoReal+= arvoreSolucao.getTransicoesByOrigemByDestino(estadoPai,estadoAtual).stream()
                    .findFirst().orElse(null).getPeso();
            estadoAtual = estadoPai;
            estadoPai = estadoAtual.getEstadoPai();
        }
        imprimirSolucao(pilha,custoReal);
    }

    private void imprimirSolucao(Stack<Estado> pilha, Integer custoReal) {
        System.out.println("Custo real: "+custoReal);
        System.out.println("\nOrdem na solução:\n");
        while (!pilha.isEmpty()) {
            Estado estado = pilha.pop();
            System.out.println(estado);
            System.out.println(estado.getValorHeuristica());
        }
    }

    private List<Estado> gerarFilhos(Estado estadoAtual) {
        return Constantes.regras.stream()
                .map(regra -> regra.executarMovimento(estadoAtual.getTabuleiro()))
                .filter(Objects::nonNull)
                .map(tabuleiro -> new Estado(tabuleiro, estadoAtual, estadoAtual.getNivel() + 1))
                .sorted(Comparator.comparingInt(Estado::getValorHeuristica))
                .collect(Collectors.toList());
    }
}
