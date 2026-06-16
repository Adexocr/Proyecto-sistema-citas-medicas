package tecnicotec.salud_cr.entity;

public enum Rol {
    PACIENTE("Paciente"),
    DOCTOR("Doctor"),
    ADMIN("Administrador");

    private final String descripcion;

    Rol(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
