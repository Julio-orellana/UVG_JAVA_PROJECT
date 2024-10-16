import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseConnector
{
    // Variables de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/prueba";
    private static final String USER = "root";
    private static final String PASSWORD = "@A57321418y";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    private Connection connect()
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } 
        catch (SQLException e)
        {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return connection;
    }

    private void close(Connection connection)
    {
        try
        {
            if (connection != null && !connection.isClosed())
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public List<Map<String, Object>> executeQuery(String query) {
        List<Map<String, Object>> results = new ArrayList<>();
        Connection connection = connect();
        
        if (connection != null)
        {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query))
            {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();        
                    
                while (resultSet.next())
                {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++)
                    {
                        row.put(metaData.getColumnName(i), resultSet.getObject(i));
                    }
                    
                    results.add(row);
                }
            }
            
            catch (SQLException e)
            {
                System.out.println("Error al ejecutar consulta: " + e.getMessage());
            }
            
            finally
            {
                close(connection);
            }
        }
        return results;
    }

    // Método para ejecutar actualizaciones (INSERT, UPDATE, DELETE)
    public void executeUpdate(String sql)
    {
        int rowsAffected = 0;
        Connection connection = connect();
        
        if (connection != null)
        {
            try
            {
                Statement statement = connection.createStatement();
                rowsAffected = statement.executeUpdate(sql);
            }
            
            catch (SQLException e)
            {
                System.out.println("Error al ejecutar actualización: " + e.getMessage());
            }
            
            finally
            {
                close(connection);
            }
        }
    }
}

