package utils;

import enums.Cavalo;

import java.util.ArrayList;
import java.util.Collection;

public class ListaCircular<T> extends ArrayList<Cavalo> implements Cloneable {

    public ListaCircular(Collection<? extends Cavalo> c) {
        super(c);
    }

    public Cavalo getCircular(int index, int offset) {
        index = (index + offset) % this.size();
        if (index < 0)
            index += this.size();
        return this.get(index);
    }

    public void setCircular(int index, int offset, Cavalo cavalo) {
        index = (index + offset) % this.size();
        if (index < 0)
            index += this.size();
        this.set(index, cavalo);
    }

    @Override
    public String toString() {
        if(this.isEmpty()) return "[]";
        StringBuilder toString = new StringBuilder("[");
        for (Cavalo cavalo : this) {
            if(cavalo != null){
                toString.append(cavalo);
            } else {
                toString.append("<>");
            }
            toString.append(", ");
        }
        return toString.substring(0,toString.length()-2) + "]";
    }

}
