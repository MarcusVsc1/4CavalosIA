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

import enums.Cavalo;
import lombok.Getter;
import lombok.Setter;
import utils.Constantes;
import utils.ListaCircular;

@Getter
@Setter
public class Estado {
    
    private ListaCircular<Cavalo> tabuleiro;
    private Estado estadoPai;

    private Integer valorHeuristica;

    private Integer nivel;

    public Estado(ListaCircular<Cavalo> tabuleiro, Estado estadoPai, Integer nivel) {
        this.tabuleiro = tabuleiro;
        this.estadoPai = estadoPai;
        this.valorHeuristica = calcularHeuristica();
        this.nivel = nivel;
    }

    private Integer calcularHeuristica() {

        return Math.min(Math.abs(this.tabuleiro.indexOf(Cavalo.B1) - Constantes.estadoFinal.indexOf(Cavalo.B1)),
                        Math.abs(this.tabuleiro.indexOf(Cavalo.B1) - Constantes.estadoFinal.indexOf(Cavalo.B1) - this.tabuleiro.size())) +
                Math.min(Math.abs(this.tabuleiro.indexOf(Cavalo.B2) - Constantes.estadoFinal.indexOf(Cavalo.B2)),
                        Math.abs(this.tabuleiro.indexOf(Cavalo.B2) - Constantes.estadoFinal.indexOf(Cavalo.B2) - this.tabuleiro.size())) +
                Math.min(Math.abs(this.tabuleiro.indexOf(Cavalo.P1) - Constantes.estadoFinal.indexOf(Cavalo.P1)),
                        Math.abs(this.tabuleiro.indexOf(Cavalo.P1) - Constantes.estadoFinal.indexOf(Cavalo.P1) - this.tabuleiro.size())) +
                Math.min(Math.abs(this.tabuleiro.indexOf(Cavalo.P2) - Constantes.estadoFinal.indexOf(Cavalo.P2)),
                        Math.abs(this.tabuleiro.indexOf(Cavalo.P2) - Constantes.estadoFinal.indexOf(Cavalo.P2) - this.tabuleiro.size()));

    }

    @Override
    public boolean equals(Object obj){
        if(obj == this){return true;}
        if (!(obj instanceof Estado)) {
            return false;
        }
        Estado e = (Estado) obj;
        return e.getTabuleiro().equals(this.tabuleiro);
    }

    @Override
    public  String toString(){
        return String.format("[%s]",this.tabuleiro);
    }

    @Override
    public int hashCode() {
        return tabuleiro.hashCode();
    }

}
