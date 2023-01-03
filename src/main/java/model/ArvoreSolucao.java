package model;

import enums.Cavalo;

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
            adicionarTransicao(new Transicao(calcularPesoAdicional(origem, estado) + 1 ,origem,estado));
        });
    }

    private Integer calcularPesoAdicional(Estado origem, Estado filho) {
        Cavalo cavaloDiferente = verificarCavaloDiferente(origem,filho);

        if(cavaloDiferente.equals(Cavalo.B1) &&
            Cavalo.B2.equals(filho.getTabuleiro().getCircular(filho.getTabuleiro().indexOf(Cavalo.B1), 1))){
            return 2;
        }

        if(cavaloDiferente.equals(Cavalo.B2) &&
                Cavalo.P2.equals(filho.getTabuleiro().getCircular(filho.getTabuleiro().indexOf(Cavalo.B2), 1))){
            return 1;
        }

        if(cavaloDiferente.equals(Cavalo.P1) &&
                Cavalo.B1.equals(filho.getTabuleiro().getCircular(filho.getTabuleiro().indexOf(Cavalo.P1), 1))){
            return 1;
        }

        if(cavaloDiferente.equals(Cavalo.P2) &&
                Cavalo.P1.equals(filho.getTabuleiro().getCircular(filho.getTabuleiro().indexOf(Cavalo.P2), 1))){
            return 2;
        }

        return 0;
    }

    private Cavalo verificarCavaloDiferente(Estado origem, Estado filho) {
        if (origem.getTabuleiro().indexOf(Cavalo.B1) != filho.getTabuleiro().indexOf(Cavalo.B1)) return Cavalo.B1;
        if (origem.getTabuleiro().indexOf(Cavalo.B2) != filho.getTabuleiro().indexOf(Cavalo.B2)) return Cavalo.B2;
        if (origem.getTabuleiro().indexOf(Cavalo.P1) != filho.getTabuleiro().indexOf(Cavalo.P1)) return Cavalo.P1;
        if (origem.getTabuleiro().indexOf(Cavalo.P2) != filho.getTabuleiro().indexOf(Cavalo.P2)) return Cavalo.P2;
        return  null;
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

    public Integer getTotalDeNos() {
        return this.estados.size();
    }
}
