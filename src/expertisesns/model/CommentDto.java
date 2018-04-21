package expertisesns.model;

public class CommentDto 
{
	private int comment_seq;
	private int content_seq;
	private String commerter_id = null;
	private String today_date = null;
	private String comments = null;
	
	public CommentDto(){}

	
	public int getComment_seq() {
		return comment_seq;
	}


	public void setComment_seq(int comment_seq) {
		this.comment_seq = comment_seq;
	}

	public int getContent_seq() {
		return content_seq;
	}

	public void setContent_seq(int content_seq) {
		this.content_seq = content_seq;
	}

	public String getCommerter_id() {
		return commerter_id;
	}

	public void setCommerter_id(String commerter_id) {
		this.commerter_id = commerter_id;
	}

	public String getToday_date() {
		return today_date;
	}

	public void setToday_date(String today_date) {
		this.today_date = today_date;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
