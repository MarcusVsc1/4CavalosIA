package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transicao {
    private Integer peso;
    private Estado origem;
    private Estado destino;

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Transicao) {
            return ((Transicao) obj).getPeso().equals(this.peso) &&
                    ((Transicao) obj).getDestino().equals(this.destino) &&
                    ((Transicao) obj).getOrigem().equals(this.origem);
        }
        return false;
    }
}
