package org.acme.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Nivel {

    ENTRADA(1, "Entrada"),
    MID_END(2, "Mid-End"),
    HIGH_END(3, "High-End");

    private int id;
    private String label;

    Nivel(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Nivel valueOf(Integer id) throws IllegalArgumentException {
        if (id == null) {
            return null;
        }

        for (Nivel nivel : Nivel.values()) {
            if (id.equals(nivel.getId())) {
                return nivel;
            }
        }

        throw new IllegalArgumentException("Id inválido:" + id);
    }
}