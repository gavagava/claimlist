package claimlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import claimlist.Claim;

@Service("claimService")
@Transactional
public class ClaimServiceImpl implements ClaimService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<Claim> claims;
	
	static{
		claims= populateDummyClaims();
	}

	public List<Claim> findAllClaims() {
		return claims;
	}
	
	public Claim findById(long id) {
		for(Claim claim : claims){
			if(claim.getId() == id){
				return claim;
			}
		}
		return null;
	}
	
	public void saveClaim(Claim claim) {
		claim.setId(counter.incrementAndGet());
		claims.add(claim);
	}

	public void updateClaim(Claim claim) {
		int index = claims.indexOf(claim);
		claims.set(index, claim);
	}

	public void deleteClaimById(long id) {
		
		for (Iterator<Claim> iterator = claims.iterator(); iterator.hasNext(); ) {
		    Claim claim = iterator.next();
		    if (claim.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isClaimExist(Claim claim) {
		return findById(claim.getId())!=null;
	}

	private static List<Claim> populateDummyClaims(){
		List<Claim> claims = new ArrayList<Claim>();
		claims.add(new Claim(counter.incrementAndGet(),"user1", "Support", ClaimStatus.PROPOSED));
		claims.add(new Claim(counter.incrementAndGet(),"user2", "Support", ClaimStatus.PROPOSED));
		claims.add(new Claim(counter.incrementAndGet(),"user3", "Support", ClaimStatus.PROPOSED));
		claims.add(new Claim(counter.incrementAndGet(),"user4", "Support", ClaimStatus.PROPOSED));		
		return claims;
	}

	public void deleteAllClaims() {
		claims.clear();
	}

}
