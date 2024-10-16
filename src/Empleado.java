class Empleado
{
    private int id;
    private String nombre;

    public Empleado(int id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    public int GetId()
    {
        return this.id;
    }
}