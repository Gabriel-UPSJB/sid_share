package com.froggame.sid_share.recursos;

public class Guia
{
    public int ID;
    public int Estado;
    public String Nombre_cliente;
    public int DNI_cliente;
    public String Articulo;
    public String Modelo;
    public String NroSerie;
    public String Averia;
    public String Solucion;
    public String Nota;
    public String Tecnico;
    public String Telefono;
    public int ID_tecnico;
    public String tratado;
    public String a_cuenta;
    public String saldo;
    public String FECHA;
    public String HORA;

    public Guia(int ID, int estado, String nombre_cliente, int DNI_cliente, String articulo, String modelo, String nroSerie, String averia, String solucion, String nota, String tecnico, String telefono, int ID_tecnico, String tratado, String a_cuenta, String saldo, String FECHA, String HORA) {
        this.ID = ID;
        Estado = estado;
        Nombre_cliente = nombre_cliente;
        this.DNI_cliente = DNI_cliente;
        Articulo = articulo;
        Modelo = modelo;
        NroSerie = nroSerie;
        Averia = averia;
        Solucion = solucion;
        Nota = nota;
        Tecnico = tecnico;
        Telefono = telefono;
        this.ID_tecnico = ID_tecnico;
        this.tratado = tratado;
        this.a_cuenta = a_cuenta;
        this.saldo = saldo;
        this.FECHA = FECHA;
        this.HORA = HORA;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getTratado() {
        return tratado;
    }

    public void setTratado(String tratado) {
        this.tratado = tratado;
    }

    public String getA_cuenta() {
        return a_cuenta;
    }

    public void setA_cuenta(String a_cuenta) {
        this.a_cuenta = a_cuenta;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }

    public String getNombre_cliente() {
        return Nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        Nombre_cliente = nombre_cliente;
    }

    public int getDNI_cliente() {
        return DNI_cliente;
    }

    public void setDNI_cliente(int DNI_cliente) {
        this.DNI_cliente = DNI_cliente;
    }

    public String getArticulo() {
        return Articulo;
    }

    public void setArticulo(String articulo) {
        Articulo = articulo;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getNroSerie() {
        return NroSerie;
    }

    public void setNroSerie(String nroSerie) {
        NroSerie = nroSerie;
    }

    public String getAveria() {
        return Averia;
    }

    public void setAveria(String averia) {
        Averia = averia;
    }

    public String getSolucion() {
        return Solucion;
    }

    public void setSolucion(String solucion) {
        Solucion = solucion;
    }

    public String getNota() {
        return Nota;
    }

    public void setNota(String nota) {
        Nota = nota;
    }

    public String getHORA() {
        return HORA;
    }

    public void setHORA(String HORA) {
        this.HORA = HORA;
    }

    public String getTecnico() {
        return Tecnico;
    }

    public void setTecnico(String tecnico) {
        Tecnico = tecnico;
    }

    public int getID_tecnico() {
        return ID_tecnico;
    }

    public void setID_tecnico(int ID_tecnico) {
        this.ID_tecnico = ID_tecnico;
    }
}
