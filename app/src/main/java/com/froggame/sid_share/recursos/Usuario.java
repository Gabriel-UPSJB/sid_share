package com.froggame.sid_share.recursos;

public class Usuario {
    public int ID;
    public String Nombre;
    public String Lista_IDS;
    public String DNI;
    public int user_type;


    public Usuario(int ID, String nombre, String lista_IDS, String DNI, int user_type) {
        this.ID = ID;
        Nombre = nombre;
        Lista_IDS = lista_IDS;
        this.DNI = DNI;
        this.user_type = user_type;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getLista_IDS() {
        return Lista_IDS;
    }

    public void setLista_IDS(String lista_IDS) {
        Lista_IDS = lista_IDS;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
}
