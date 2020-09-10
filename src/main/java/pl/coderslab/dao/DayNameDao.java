package pl.coderslab.dao;

import pl.coderslab.model.DayName;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DayNameDao {
    private static final String ALL_DAYS_NAME_QUERY = "SELECT * FROM day_name";
    private static final String ID_DAY_NAME_QUERY = "SELECT id FROM day_name WHERE name = ?";

    public List<DayName> allDaysName (){
        List<DayName> dayNames = new ArrayList<>();
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(ALL_DAYS_NAME_QUERY);
            ResultSet rs = pr.executeQuery();
        ){
            while(rs.next()){
                DayName dayName = new DayName();
                dayName.setId(rs.getInt("id"));
                dayName.setName(rs.getString("name"));
                dayName.setDisplayOrder(rs.getInt("display_order"));
                dayNames.add(dayName);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dayNames;
    }
}
