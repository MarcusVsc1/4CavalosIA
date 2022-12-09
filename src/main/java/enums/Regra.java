package enums;

import utils.Constantes;
import utils.ListaCircular;

public enum Regra {
    R1(1,Cavalo.P1),

    R2(1,Cavalo.B1),

    R3(1,Cavalo.B2),

    R4(1,Cavalo.P2);


    private Integer offset;
    private Cavalo cavalo;

    Regra(Integer offset, Cavalo cavalo) {
        this.offset = offset;
        this.cavalo = cavalo;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Cavalo getCavalo() {
        return cavalo;
    }

    public void setCavalo(Cavalo cavalo) {
        this.cavalo = cavalo;
    }

    public ListaCircular<Cavalo> executarMovimento(ListaCircular<Cavalo> tabuleiro){

        Integer index = tabuleiro.indexOf(this.cavalo);
        ListaCircular<Cavalo> tabuleiroClone = (ListaCircular<Cavalo>) tabuleiro.clone();

        if(tabuleiro.getCircular(index,offset)==null &&
            index != Constantes.estadoFinal.indexOf(this.cavalo)){

            tabuleiroClone.setCircular(index,offset,this.cavalo);
            tabuleiroClone.set(index,null);
            return tabuleiroClone;

        }

        return null;
    }

}
