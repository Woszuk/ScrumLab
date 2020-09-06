package pl.coderslab.dao;

import pl.coderslab.model.DayName;
import pl.coderslab.model.MealName;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealNameDao {
    private static final String ALL_MEAL_NAME_QUERY = "SELECT * FROM meal_name";
    private static final String ID_MEAL_NAME_QUERY = "SELECT id FROM meal_name WHERE name = ?";

    public List<MealName> allMealName (){
        List<MealName> mealNames = new ArrayList<>();
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(ALL_MEAL_NAME_QUERY);
            ResultSet rs = pr.executeQuery();
        ){
            while(rs.next()){
                MealName mealName = new MealName();
                mealName.setId(rs.getInt("id"));
                mealName.setName(rs.getString("name"));
                mealName.setDisplayOrder(rs.getInt("display_order"));
                mealNames.add(mealName);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return mealNames;
    }

    public Integer mealNameId(String mealName){
        Integer mealNameId = 0;
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(ID_MEAL_NAME_QUERY)
        ){
            pr.setString(1, mealName);
            try(ResultSet rs = pr.executeQuery()){
                if (rs.next()){
                    mealNameId = rs.getInt("id");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return mealNameId;
    }
}