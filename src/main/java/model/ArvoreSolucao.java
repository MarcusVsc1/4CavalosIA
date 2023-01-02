package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ArvoreSolucao {
    private List<Estado> estados = new ArrayList<>();
    private List<Transicao> transicoes = new ArrayList<>();

    public List<Estado> getDestinosByOrigem(Estado origem) {
        return transicoes.stream()
                .filter(transicao -> {return origem.getTabuleiro().equals(transicao.getOrigem().getTabuleiro());})
                .map(Transicao::getDestino)
                .collect(Collectors.toList());
    }

    public List <Transicao> getTransicoesByOrigemByDestino(Estado origem, Estado destino){
        return transicoes.stream().filter(transicao -> {
            return transicao.getOrigem().equals(origem) && transicao.getDestino().equals(destino);})
                .collect(Collectors.toList());
    }

    public List <Transicao> getTransicoesByOrigem(Estado origem){
        return transicoes.stream().filter(transicao -> {
                    return transicao.getOrigem().equals(origem);})
                .collect(Collectors.toList());
    }

    public List<Estado> getOrigensByDestino(Estado destino) {
        return transicoes.stream()
                .filter(transicao -> {return destino.getTabuleiro().equals(transicao.getDestino().getTabuleiro());})
                .map(Transicao::getOrigem)
                .collect(Collectors.toList());
    }

    public void adicionarTransicao(Transicao transicao) {
        this.transicoes.add(transicao);
    }

    public void removerTransicao(Transicao transicao) {
        this.transicoes.remove(transicao);
    }

    public void adicionarEstado(Estado estado) {
        this.estados.add(estado);
    }

    public void removerEstado(Estado estado) {
        this.estados.remove(estado);
    }

    // adiciona todos os estados da lista para a árvore
    public void adicionarTodos(List<Estado> estados, Estado origem) {
        estados.forEach(estado -> {
            adicionarEstado(estado);
            adicionarTransicao(new Transicao(1,origem,estado));
        });
    }

    public Integer getPesoDaTransicaoByOrigemByDestino(Estado origem, Estado destino) {
        return getTransicoesByOrigemByDestino(origem,destino).stream()
                .findFirst().orElse(null).getPeso();
    }

    // soma o valor das transições até a raiz
    public Integer calcularPesoCaminho(Estado estado) {
        Estado estadoPai = estado.getEstadoPai();
        Integer custo = 0;
        while (estadoPai != null) {
            custo+= getPesoDaTransicaoByOrigemByDestino(estadoPai,estado);
            estado = estadoPai;
            estadoPai = estado.getEstadoPai();
        }
        return custo;
    }
}
