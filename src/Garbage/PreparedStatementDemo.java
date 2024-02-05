package Garbage;

import java.sql.*;

public class PreparedStatementDemo
{
    public static void main(String[] args) throws SQLException {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","Phani@7989");

//            System.out.println("Enter Id");
//            int emp_id = MenuOptions.input.nextInt();
//            MenuOptions.input.nextLine();
//            System.out.println("Enter name");
//            String emp_name = MenuOptions.input.nextLine();
//            System.out.println("Enter dept");
//            String dept = MenuOptions.input.nextLine();
//            selectUsingPS(emp_id, connection);
//            insertUsingPS(connection, emp_id, emp_name, dept);
//            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE emp_details SET emp_id = 5 WHERE emp_id = 7");
//            preparedStatement.executeUpdate();
//            System.out.println("Successfully updated ");

//            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM emp_details WHERE emp_id = 5");
//            preparedStatement.executeUpdate();



        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }

    private static void insertUsingPS(Connection connection, int emp_id, String emp_name, String dept) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO emp_details VALUES (?,?,?)");
        preparedStatement.setInt(1, emp_id);
        preparedStatement.setString(2, emp_name);
        preparedStatement.setString(3, dept);
        int count = preparedStatement.executeUpdate();
        System.out.println("Successfully updated "+count);
    }

    private static void selectUsingPS(int emp_id, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM emp_details WHERE emp_id  ?");
        preparedStatement.setInt(1, emp_id);
        ResultSet result = preparedStatement.executeQuery();
        ResultSetMetaData metaData = result.getMetaData();
        int columns = metaData.getColumnCount();
        for(int i = 1; i <= columns; i++)
        {
            System.out.print(metaData.getColumnName(i)+"\t");
        }
        System.out.println();
        while (result.next())
        {
            System.out.println(result.getString(1)+"\t\t"+result.getString(2)+"\t\t"+result.getString(3));
        }
    }
}
