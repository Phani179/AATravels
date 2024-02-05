package Garbage;

import java.time.LocalDate;

public class DayOfWeek
{
    public static void main(String[] args)
    {
        LocalDate day = LocalDate.parse("2022-11-19");
        System.out.println(day.getDayOfWeek());

        String s = "09";
        char start = 0,end = s.charAt(1);
        int a = start,b = end;
        System.out.print(start+" "+a+" "+b);
    }
}
