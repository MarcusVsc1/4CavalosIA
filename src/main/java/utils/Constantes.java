package utils;

import enums.Cavalo;
import enums.Regra;

import java.util.Arrays;
import java.util.List;

// constantes que vão ser usadas em todas as buscas
public class Constantes {

    public static final ListaCircular estadoInicial = new ListaCircular(Arrays.
            asList(Cavalo.P1,null,Cavalo.B1,null,Cavalo.B2,null,Cavalo.P2,null));

    public static final List<Cavalo> estadoFinal = Arrays.
            asList(Cavalo.B2,null,Cavalo.P2,null,Cavalo.P1,null,Cavalo.B1,null);

    public static final List<String> posicoes = Arrays.asList(
            "1A","2C","3A","1B","3C","2A","1C","3B"
    );

    public static final List<Regra> regras = Arrays.asList(Regra.R1,Regra.R2,Regra.R3,Regra.R4);
}
