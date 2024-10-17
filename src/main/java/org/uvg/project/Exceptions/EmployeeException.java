package org.uvg.project.Exceptions;

public class EmployeeException extends Exception {

    private String description;

    /**
     * Constructor que inicializa la excepción con una descripción personalizada.
     *
     * @param description La descripción del error o excepción.
     */
    public EmployeeException(String description) {

        this.description = description;
    }

    /**
     * Retorna el mensaje de error, que en este caso es la descripción.
     *
     * @return La descripción del error.
     */
    @Override
    public String getMessage() {

        return this.getDescription();
    }

    /**
     * Retorna la descripción del error.
     *
     * @return La descripción de la excepción.
     */
    public String getDescription() {

        return this.description;
    }

}
