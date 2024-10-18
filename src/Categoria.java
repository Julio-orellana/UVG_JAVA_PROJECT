class Categoria
{
    private int id;
    private String nombre;

    public Categoria(int id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    public void SetNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public int GetId()
    {
        return this.id;
    }

    public String GetNombre()
    {
        return this.nombre;
    }
}