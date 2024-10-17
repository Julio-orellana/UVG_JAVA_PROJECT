# PROYECTO FINAL POO

## Integrantes:
- **Ricardo Rodriguez 18907**
- **Julio Orellana - 24207**
- **Sofia Batz - 241281**
- **Ginebra Estrada - 24079**

## Estructuracion del repositorio:
- La rama [main](https://github.com/Julio-orellana/UVG_JAVA_PROJECT/tree/main) contiene uno de los prototipos del proyecto
- La rama [development](https://github.com/Julio-orellana/UVG_JAVA_PROJECT/tree/development) se usa para solucionar problemas de la rama main y development2
- La rama [development2](https://github.com/Julio-orellana/UVG_JAVA_PROJECT/tree/development2) contiene un segundo prototipo del proyecto
- **src/main/java** contiene el codigo fuente del proyecto
- **docs/** contiene la documentacion del proyecto en formato javaDoc y documentos de las entregas del proyecto
- **.env.example** contiene un ejemplo de las variables de entorno del proyecto

### Ejecucion sin plugin de ejecucion:
Para correr el programa:
1. Clonar el repositorio.
2. Moverse a la carpeta del proyecto 
3. Crear un archivo `.env` con las variables de entorno del proyecto (ver archivo `.env.example` para referencia)
4. Ejecutar el comando `mvn package` para instalar todas ls dependencias del sistema 
5. Ejecutar el comando `mvn exec:java` para correr el programa

### Ejecucion con plugin de ejecucion:
Para correr el programa:
1. Clonar el repositorio 
2. Moverse a la carpeta del proyecto 
3. Crear un archivo `.env` con las variables de entorno del proyecto (ver archivo `.env.example` para referencia)
4. Ejecutar el comando `mvn package` para instalar todas ls dependencias del sistema 
5. Correr el programa en el boton play de la IDE desde el archivo `App.java`