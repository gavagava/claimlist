package claimlist;

enum ClaimStatus { PROPOSED, READY, RESOLVED, CLOSED };

public class Claim {

	private long id;
	
	private String from, to;
	
	private ClaimStatus status;
	
	public Claim(){
		id=0;
	}
	
	public Claim(long id, String from, String to, ClaimStatus status){
		this.id = id;
		this.from = from;
		this.to = to;
		this.status = status;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public ClaimStatus getStatus() {
		return status;
	}

	public void setStatus(ClaimStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Claim other = (Claim) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Claim [id=" + id + ", from=" + from + ", to=" + to
				+ ", status=" + status + "]";
	}


}
