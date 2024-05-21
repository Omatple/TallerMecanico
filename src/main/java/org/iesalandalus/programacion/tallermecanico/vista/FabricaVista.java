package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.tallermecanico.vista.texto.VistaTexto;

public enum FabricaVista {
    TEXTO {
        @Override
        public Vista crear() {
            return new VistaTexto();
        }
    },

    GRAFICA {
        @Override
        public Vista crear() {
            return VistaGrafica.getInstancia();
        }
    };

    public abstract Vista crear();
}
