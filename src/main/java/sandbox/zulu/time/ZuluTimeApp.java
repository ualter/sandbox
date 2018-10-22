package sandbox.zulu.time;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * Hello world!
 *
 */
public class ZuluTimeApp 
{
    public static void main( String[] args )
    {
    	
        testTimeZone();
        
    }

	private static void testTimeZone() {
		String pattern = "dd/MM/yyyy HH:mm:ss XXX Z";
		
		ZonedDateTime now = ZonedDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		String result = formatter.format(now);
		String msg    = String.format("%s:%s", StringUtils.rightPad(TimeZone.getDefault().getID(),15,"."), result);
		System.out.println(msg);
		
		// Java 7
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        // UTC / Zulu Time
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        result = sdf.format(Calendar.getInstance().getTime());
        msg    = String.format("%s:%s", StringUtils.rightPad(TimeZone.getTimeZone("UTC").getID(),15,"."), result);
        System.out.println(msg);
        // Local Time
        sdf.setTimeZone(TimeZone.getDefault());
        result = sdf.format(Calendar.getInstance().getTime());
        msg    = String.format("%s:%s", StringUtils.rightPad(TimeZone.getDefault().getID() ,15,"."), result);
        System.out.println(msg);
	}
}
