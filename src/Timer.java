public class Timer 
{
	private long hours;
	private long minutes;
	private long seconds;
	private long timestart;
	private long timeend;
	private long totaltime = 0;
	
	@SuppressWarnings("unqualified-field-access")
	public Timer()
	{
		//Get Initial time
		timestart = System.currentTimeMillis();
	}
	
	
	@SuppressWarnings({ "unqualified-field-access", "nls" })
	public String getTime()
	{
		timeend = System.currentTimeMillis();
		totaltime += (timeend - timestart) / 1000;
		String time = new String();
		hours = totaltime / 3600;
		minutes = (totaltime % 3600) / 60;
		seconds = ((totaltime % 3600 ) % 60 ) % 60;
		String h = String.valueOf(hours);
		String m = String.valueOf(minutes);
		String s = String.valueOf(seconds);
		if (hours < 10)
			h = "0" + h;
		if (minutes < 10)
			m = "0" + m;
		if (seconds < 10)
			s = "0" + s;
		time = h + ":" + m + ":" + s;
		
		return time;
	}
	
	public void setTotalTime(long t)
	{
		totaltime = t;
	}
	
	public void restartTimer()
	{
		timestart = System.currentTimeMillis();
		totaltime = 0;
	}
	
}

