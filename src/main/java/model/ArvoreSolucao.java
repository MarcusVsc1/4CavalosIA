/*
 * Trabalho Prático: DCC146 - Aspectos Teóricos da Computação
 * Autores:
 * 
 * João Pedro Sequeto Nascimento - 201776022
 * Beatriz Cunha Rodrigues - 201776038
 * Marcus Vinícius V. A. Cunha - 201776013
 * Milles Joseph M e Silva - 201776026
 */

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

    public void adicionarTodos(List<Estado> estados, Estado origem, Integer peso) {
        estados.forEach(estado -> {
            adicionarEstado(estado);
            adicionarTransicao(new Transicao(1,origem,estado));
        });
    }

    public Integer getPesoDaTransicaoByOrigemByDestino(Estado origem, Estado destino) {
        return getTransicoesByOrigemByDestino(origem,destino).stream()
                .findFirst().orElse(null).getPeso();
    }

}
