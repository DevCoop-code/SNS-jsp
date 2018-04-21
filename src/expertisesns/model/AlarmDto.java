package expertisesns.model;

public class AlarmDto 
{
	private int seq;
	private int sender_seq;
	private String type = null;
	
	public AlarmDto(){}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getSender_seq() {
		return sender_seq;
	}

	public void setSender_seq(int sender_seq) {
		this.sender_seq = sender_seq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
