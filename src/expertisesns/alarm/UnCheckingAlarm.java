package expertisesns.alarm;

import expertisesns.model.AlarmDao;
import expertisesns.model.ProfileDao;

public class UnCheckingAlarm 
{
	public static int uncheckingAlarmNum(String id)
	{
		ProfileDao profiledao = ProfileDao.getInstance();
		AlarmDao alarmdao = AlarmDao.getInstance();
		
		int receiver_seq = profiledao.getSeq(id);
		
		System.out.println("error : " + alarmdao.getNotCheckedAlarmNum(receiver_seq));
		return alarmdao.getNotCheckedAlarmNum(receiver_seq);
	}
}