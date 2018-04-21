package expertisesns.model;

public class SearchingPeopleDto 
{
	private int seq;
	private String name;
	private String highestschooling;
	private String expertise;
	private String lastcompany;
	private String profileurl;
	
	public SearchingPeopleDto(){}

	
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHighestschooling() {
		return highestschooling;
	}

	public void setHighestschooling(String highestschooling) {
		this.highestschooling = highestschooling;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public String getLastcompany() {
		return lastcompany;
	}

	public void setLastcompany(String lastcompany) {
		this.lastcompany = lastcompany;
	}

	public String getProfileurl() {
		return profileurl;
	}

	public void setProfileurl(String profileurl) {
		this.profileurl = profileurl;
	}
	
}
