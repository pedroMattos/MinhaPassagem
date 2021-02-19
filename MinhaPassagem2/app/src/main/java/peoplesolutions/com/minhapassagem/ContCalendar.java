package peoplesolutions.com.minhapassagem;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ContCalendar {
    GregorianCalendar calendar = new GregorianCalendar();
    //pega os dias uteis restantes do mes atual
    int daDia = calendar.getActualMaximum(Calendar.DATE);
    int weDia = calendar.get(Calendar.DAY_OF_WEEK);
    int month = calendar.get(Calendar.MONTH) + 1;
    int datDia = calendar.get(Calendar.DAY_OF_MONTH);
    //int datDia = 16;
//    int weDia = 5;
    int rDia = daDia - datDia;
    int weekend = rDia/7;
    int wWeekend = weekend*2;
    public ContCalendar(){
        if (this.daDia == 31 && this.rDia < 14){
            this.wWeekend = this.wWeekend + 2;
            this.weekend = this.weekend + 2;
        }
        if ( this.rDia < 7 && this.weDia == 4){
            this.wWeekend = this.wWeekend + 2;
            this.weekend = this.weekend + 2;
        }
    }
}



