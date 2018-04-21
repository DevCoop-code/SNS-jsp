package expertisesns.eachfollow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import expertisesns.data.DataInfo;
import expertisesns.model.FollowDao;
import expertisesns.model.FollowDto;
import expertisesns.model.MemberDao;
import expertisesns.model.ProfileDao;

public class SearchEachFollow 
{
	public static List<FollowDto> searchFollow(String id)
	{
		ProfileDao profiledao = ProfileDao.getInstance();
		FollowDao followdao = FollowDao.getInstance();
		MemberDao memberdao = MemberDao.getInstance();
		
		FollowDto followdto = null;
		
		List<Integer> Follower_seq_list = null;
		List<Integer> Following_seq_list = null;
		
		List<FollowDto> Follower_data_list = new ArrayList<FollowDto>();
		List<FollowDto> Following_data_list = new ArrayList<FollowDto>();
		List<FollowDto> Follow_data_list = new ArrayList<FollowDto>();
		
		int receiver_seq = profiledao.getSeq(id);
		
		//나를 팔로우 하는 사람들의 리스트
		Follower_seq_list = followdao.getFollowerSeq(receiver_seq);
		//내가 팔로우 하는 사람들의 리스트 
		Following_seq_list = followdao.getFollowingSeq(receiver_seq);
		
		Iterator<Integer> follower_iter = Follower_seq_list.iterator();
		Iterator<Integer> following_iter = Following_seq_list.iterator();
		
		//나의 팔로워들 가져오기
		while(follower_iter.hasNext())
		{
			int follower_seq_value = follower_iter.next();
			
			followdto = new FollowDto();
			
			String follower_name = memberdao.getNameFromSeq(follower_seq_value);
			String follower_id = memberdao.getIdFromSeq(follower_seq_value);
			
			String profile_folder = DataInfo.getIDFolderName(follower_id);
			String profileimage_name = memberdao.getProfile(follower_id);
			
			String profile_path = DataInfo.getProfileURL(follower_id);
			
			followdto.setSeq(follower_seq_value);
			followdto.setName(follower_name);
			followdto.setId(follower_id);
			followdto.setProfile_path(profile_path);
			
			Follower_data_list.add(followdto);
		}
		//내가 팔로잉 하는 사람들 데리고오기
		while(following_iter.hasNext())
		{
			int following_seq_value = following_iter.next();
			
			followdto = new FollowDto();
			
			String following_name = memberdao.getNameFromSeq(following_seq_value);
			String following_id = memberdao.getIdFromSeq(following_seq_value);
			
			followdto.setSeq(following_seq_value);
			followdto.setName(following_name);
			followdto.setId(following_id);
			
			Following_data_list.add(followdto);	
		}

		FollowDto follower_array[] = Follower_data_list.toArray(new FollowDto[Follower_data_list.size()]);
		FollowDto following_array[] = Following_data_list.toArray(new FollowDto[Following_data_list.size()]);

		//맞팔 찾기
		for(int i=0; i<follower_array.length; i++)
		{
			for(int j=0; j<following_array.length; j++)
			{
				if(follower_array[i].getSeq() == following_array[j].getSeq())
				{
					Follow_data_list.add(follower_array[i]);
				}
				if(follower_array[i].getSeq() < following_array[j].getSeq())
				{
					break;
				}
			}
		}
		
		return Follow_data_list;
	}
}
