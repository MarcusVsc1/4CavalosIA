package program;

import model.ArvoreSolucao;
import model.Estado;
import utils.Constantes;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IASimulador {

    private Queue<Estado> abertos;
    private List<Estado> fechados;

    private ArvoreSolucao arvoreSolucao;

    private Integer limite;

    public Boolean isEstadoFinal(Estado estado){
        return Objects.equals(estado.getTabuleiro(),Constantes.estadoFinal);
    }

    public void buscaGulosa() {
        iniciarBusca(Estado::getValorHeuristica);
    }

    public void buscaAEstrela() {
        iniciarBusca(this::getFuncaoAvalicao);
    }

    public void buscaOrdenada() {
        iniciarBusca(this::getPesoOrdenada);
    }

    //serve para a busca ordenada, gulosa e a A*, alterando apenas a função avaliação
    public void iniciarBusca(Function<Estado, Integer> funcaoAvaliacao) {
        abertos = new LinkedBlockingQueue<>();
        fechados = new ArrayList<>();
        arvoreSolucao = new ArvoreSolucao();

        Estado estadoAtual = new Estado(Constantes.estadoInicial, null,0);

        arvoreSolucao.adicionarEstado(estadoAtual);

        while (estadoAtual != null) {
            if(isEstadoFinal(estadoAtual)) {
                construirSolucao(estadoAtual,arvoreSolucao);
                return;
            } else {
                List<Estado> estadosFilhos = gerarFilhos(estadoAtual);
                abertos.addAll(estadosFilhos);
                arvoreSolucao.adicionarTodos(estadosFilhos,estadoAtual,1);
                abertos = ordenarRemoverDuplicatas(funcaoAvaliacao, abertos);
                fechados.add(estadoAtual);
                estadoAtual = abertos.poll();
            }
        }
        throw  new RuntimeException("Problema não possui solução");
    }

    public void buscaIdaEstrela() {
        Integer iteracao = 0;
        abertos = new LinkedBlockingQueue<>();
        fechados = new ArrayList<>();
        LinkedBlockingQueue<Estado> descartados = new LinkedBlockingQueue<>();
        arvoreSolucao = new ArvoreSolucao();
        System.out.println("Iteração: "+iteracao);
        Estado estadoAtual = new Estado(Constantes.estadoInicial, null,0);

        arvoreSolucao.adicionarEstado(estadoAtual);
        limite = getFuncaoAvalicao(estadoAtual);

        while (estadoAtual != null) {
            if(isEstadoFinal(estadoAtual)) {
                construirSolucao(estadoAtual,arvoreSolucao);
                return;
            } else {
                List<Estado> estadosFilhos = gerarFilhos(estadoAtual);
                arvoreSolucao.adicionarTodos(estadosFilhos,estadoAtual,1);
                Stream<Estado> filhosIn = estadosFilhos.stream()
                        .filter(estado -> getFuncaoAvalicao(estado) <= limite)
                        .sorted(Comparator.comparingInt(this::getFuncaoAvalicao));
                abertos = Stream.concat(filhosIn, abertos.stream())
                        .collect(Collectors.toCollection(LinkedBlockingQueue::new));
                descartados.addAll(estadosFilhos.stream()
                        .filter(estado -> getFuncaoAvalicao(estado) > limite)
                        .collect(Collectors.toList()));
                fechados.add(estadoAtual);
                estadoAtual = abertos.poll();
                if(estadoAtual == null && descartados.size()>0) {
                    descartados = ordenarRemoverDuplicatas(this::getFuncaoAvalicao, descartados);
                    estadoAtual = descartados.poll();
                    limite = getFuncaoAvalicao(estadoAtual);
                    iteracao++;
                    System.out.println("Iteração: "+iteracao);
                }
            }
        }
        throw  new RuntimeException("Problema não possui solução");
    }

    private LinkedBlockingQueue<Estado> ordenarRemoverDuplicatas(Function<Estado, Integer> funcaoAvaliacao, Collection<Estado> collection) {
        return collection.stream()
                .sorted(Comparator.comparingInt(funcaoAvaliacao::apply)) // ordena
                .distinct() // deixa somente o estado de menor função avaliação dentre os de tabuleiro igual
                .collect(Collectors.toCollection(LinkedBlockingQueue::new));
    }

    private void construirSolucao(Estado estadoAtual, ArvoreSolucao arvoreSolucao) {
        Estado estadoPai = estadoAtual.getEstadoPai();
        Integer custoReal = 0;
        Stack<Estado> pilha = new Stack<>();
        pilha.add(estadoAtual);
        while (estadoPai != null) {
            pilha.add(estadoPai);
            custoReal+= arvoreSolucao.getPesoDaTransicaoByOrigemByDestino(estadoPai,estadoAtual);
            estadoAtual = estadoPai;
            estadoPai = estadoAtual.getEstadoPai();
        }
        imprimirSolucao(pilha,custoReal);
    }

    private void imprimirSolucao(Stack<Estado> pilha, Integer custoReal) {
        System.out.println("Custo real: "+custoReal);
        System.out.println("Ordem na solução:");
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
                .collect(Collectors.toList());
    }


    private Integer getFuncaoAvalicao(Estado estado) {
        return estado.getValorHeuristica() +
                arvoreSolucao.calcularPesoCaminho(estado);
    }

    private Integer getPesoOrdenada(Estado estado) {
        return arvoreSolucao.calcularPesoCaminho(estado);
    }
}
