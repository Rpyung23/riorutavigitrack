package com.vigitrackecuador.riorutavigitrack.POO;


public class cBuses
{
    private String code_bus;
    private String linea_bus;
    private String hora_inicio_bus;
    private String hora_final_bus;
    private String frecuencia;
    public cBuses(){}

    public cBuses(String code_bus, String linea_bus, String hora_inicio_bus, String hora_final_bus, String frecuencia) {
        this.code_bus = code_bus;
        this.linea_bus = linea_bus;
        this.hora_inicio_bus = hora_inicio_bus;
        this.hora_final_bus = hora_final_bus;
        this.frecuencia = frecuencia;
    }

    public String getCode_bus() {
        return code_bus;
    }

    public void setCode_bus(String code_bus) {
        this.code_bus = code_bus;
    }

    public String getLinea_bus() {
        return linea_bus;
    }

    public void setLinea_bus(String linea_bus) {
        this.linea_bus = linea_bus;
    }

    public String getHora_inicio_bus() {
        return hora_inicio_bus;
    }

    public void setHora_inicio_bus(String hora_inicio_bus) {
        this.hora_inicio_bus = hora_inicio_bus;
    }

    public String getHora_final_bus() {
        return hora_final_bus;
    }

    public void setHora_final_bus(String hora_final_bus) {
        this.hora_final_bus = hora_final_bus;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }
}
