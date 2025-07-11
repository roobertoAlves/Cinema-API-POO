package Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class RoomsDAO 
{

    private Statement dbLink = null;

    public RoomsDAO() 
    {
        try 
        {
            this.dbLink = new DBConnection().getConnection().createStatement();
        } 
        catch ( SQLException e ) 
        {
            e.printStackTrace();
        }

    }

    public int insert( Rooms rooms ) 
    {
        try 
        {
            String cmd = "INSERT INTO bdcinema.tbsalas (numeroSala, capacidadeMaxima," + 
                     "capacidadeAtual, statusSala, tipoSala, poltronas) VALUES (" 
            + rooms.getRoomNumber() + ", " 
            + rooms.getRoomMaxCapacity() + ", " 
            + rooms.getRoomCurrentCapacity() + ", '" 
            + rooms.getRoomStatus() + "', '" 
            + rooms.getRoomType() + "', " 
            + rooms.getSeats() + ")";
               
            int linesAfected = dbLink.executeUpdate( cmd );

            return linesAfected;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
        
    }

    public int update( Rooms rooms ) 
    {
        try 
        {
            int linesAfected = 0;

            if ( rooms.getRoomId() > 0 ) 
            {
                String cmd = "UPDATE bdcinema.tbsalas SET" + 
                             "numeroSala = " + rooms.getRoomNumber() + 
                             ", capacidadeMaxima = " + rooms.getRoomMaxCapacity() + 
                             ", capacidadeAtual = " + rooms.getRoomCurrentCapacity() + 
                             ", statusSala = '" + rooms.getRoomStatus() + 
                             "', tipoSala = '" + rooms.getRoomType() + 
                             "', poltronas = " + rooms.getSeats() +
                             " WHERE id_sala = " + rooms.getRoomId();

                linesAfected = dbLink.executeUpdate( cmd );

                return linesAfected;
            }
            else
            {
                return 0;
            }
        } 
        catch ( SQLException e ) 
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete( Rooms rooms ) 
    {
        try 
        {
            int linesAfected = 0;

            if (rooms.getRoomId() > 0) 
            {
                String cmd =  " DELETE FROM bdcinema.tbsalas";
                       cmd += " WHERE id_sala = " + rooms.getRoomId();

                
                linesAfected = dbLink.executeUpdate( cmd );

                return linesAfected;

            }
            else
            {
                return 0;
            }
        } 
        catch ( SQLException e ) 
        {
            e.printStackTrace();
            return 0;
        }		
    }

    public ResultSet list( String where ) 
    {
        String cmd = "SELECT id_sala, numeroSala, capacidadeMaxima, capacidadeAtual," + 
                     "statusSala, tipoSala, poltronas FROM bdcinema.tbsalas";
        
        if ( !where.isEmpty() ) 
        {
            cmd += " WHERE " + where;
        }

        ResultSet rs = null;

        try 
        {
            rs = dbLink.executeQuery( cmd );
        } 
        catch ( SQLException e ) 
        {
            e.printStackTrace();
        }
        
        return rs;
    }

    public int updateCurrentCapacity(int roomId, int newCurrentCapacity) 
    {
        try 
        {
            String cmd = "UPDATE bdcinema.tbsalas SET capacidadeAtual = " + newCurrentCapacity + 
                         " WHERE id_sala = " + roomId;

            int linesAfected = dbLink.executeUpdate(cmd);
            return linesAfected;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int resetAllRoomsCapacity() 
    {
        try 
        {
            String cmd = "UPDATE bdcinema.tbsalas SET capacidadeAtual = 0";
            int linesAffected = dbLink.executeUpdate(cmd);
            return linesAffected;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    public boolean hasAvailableSeats(int roomId) 
    {
        try 
        {
            ResultSet rs = list("id_sala = " + roomId);
            if (rs.next()) 
            {
                int maxCapacity = rs.getInt("capacidadeMaxima");
                int occupiedSeats = rs.getInt("capacidadeAtual");
                rs.close();
                return occupiedSeats < maxCapacity;
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return false;
    }
    
    public int getAvailableSeats(int roomId) 
    {
        try 
        {
            ResultSet rs = list("id_sala = " + roomId);
            if (rs.next()) 
            {
                int maxCapacity = rs.getInt("capacidadeMaxima");
                int occupiedSeats = rs.getInt("capacidadeAtual");
                rs.close();
                return Math.max(0, maxCapacity - occupiedSeats);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return 0;
    }

}
