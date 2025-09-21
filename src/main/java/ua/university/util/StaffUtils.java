package ua.university.util;

public class StaffUtils {

    private StaffUtils(){
    }

    public static boolean isValidRanking(float ranking){
        return !(ranking < 0) && !(ranking > 5);
    }
}
