package expertisesns.model;

//회원정보 seq 가져오기(seq, 이름, 경력)
public class ContentNeededMemberInfo 
{
	private int seq = -1;
	private String member_name = null;
	private String member_career = null;
	
	public ContentNeededMemberInfo(){ }
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_career() {
		return member_career;
	}
	public void setMember_career(String member_career) {
		this.member_career = member_career;
	}
}
