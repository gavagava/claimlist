package claimlist;

import java.util.List;

import claimlist.Claim;

public interface ClaimService {
	
	Claim findById(long id);
	
	void saveClaim(Claim claim);
	
	void updateClaim(Claim claim);
	
	void deleteClaimById(long id);

	List<Claim> findAllClaims(); 
	
	void deleteAllClaims();
	
	public boolean isClaimExist(Claim claim);
	
}
