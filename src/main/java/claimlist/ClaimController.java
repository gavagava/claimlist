package claimlist;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import claimlist.Claim;
import claimlist.ClaimService;

@RestController
public class ClaimController {

	@Autowired
	ClaimService claimService;  //Service which will do all data retrieval/manipulation work

	
	//-------------------Retrieve All Claims--------------------------------------------------------
	
	@RequestMapping(value = "/claims/", method = RequestMethod.GET)
	public ResponseEntity<List<Claim>> listAllClaims() {
		List<Claim> claims = claimService.findAllClaims();
		if(claims.isEmpty()){
			return new ResponseEntity<List<Claim>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Claim>>(claims, HttpStatus.OK);
	}


	//-------------------Retrieve Single Claim--------------------------------------------------------
	
	@RequestMapping(value = "/claims/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Claim> getClaim(@PathVariable("id") long id) {
		System.out.println("Fetching Claim with id " + id);
		Claim claim = claimService.findById(id);
		if (claim == null) {
			System.out.println("Claim with id " + id + " not found");
			return new ResponseEntity<Claim>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Claim>(claim, HttpStatus.OK);
	}

	
	
	//-------------------Create a Claim--------------------------------------------------------
	
	@RequestMapping(value = "/claims/", method = RequestMethod.POST)
	public ResponseEntity<Void> createClaim(@RequestBody Claim claim, 	UriComponentsBuilder ucBuilder) {
		System.out.println("Creating Claim " + claim.getId());

		claimService.saveClaim(claim);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/claims/{id}").buildAndExpand(claim.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	
	//------------------- Update a Claim --------------------------------------------------------
	
	@RequestMapping(value = "/claims/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Claim> updateClaim(@PathVariable("id") long id, @RequestBody Claim claim) {
		System.out.println("Updating Claim " + id);
		
		Claim currentClaim = claimService.findById(id);
		
		if (currentClaim==null) {
			System.out.println("Claim with id " + id + " not found");
			return new ResponseEntity<Claim>(HttpStatus.NOT_FOUND);
		}

		currentClaim.setFrom(claim.getFrom());
		currentClaim.setTo(claim.getTo());
		currentClaim.setStatus(claim.getStatus());
		
		claimService.updateClaim(currentClaim);
		return new ResponseEntity<Claim>(currentClaim, HttpStatus.OK);
	}

	//------------------- Delete a Claim --------------------------------------------------------
	
	@RequestMapping(value = "/claims/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Claim> deleteClaim(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting Claim with id " + id);

		Claim claim = claimService.findById(id);
		if (claim == null) {
			System.out.println("Unable to delete. Claim with id " + id + " not found");
			return new ResponseEntity<Claim>(HttpStatus.NOT_FOUND);
		}

		claimService.deleteClaimById(id);
		return new ResponseEntity<Claim>(HttpStatus.NO_CONTENT);
	}

	
	//------------------- Delete All Claim --------------------------------------------------------
	
	@RequestMapping(value = "/claims/", method = RequestMethod.DELETE)
	public ResponseEntity<Claim> deleteAllClaims() {
		System.out.println("Deleting All Claims");

		claimService.deleteAllClaims();
		return new ResponseEntity<Claim>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/statuses", method = RequestMethod.GET)
	public List<ClaimStatus> getStatuses ()
	{
		return Arrays.asList(ClaimStatus.values());
	}
	
	@RequestMapping(value = "/from", method = RequestMethod.GET)
	public List<String> getFrom ()
	{
		String[] res = {
				"user1@users.com", 
				"user2@users.com", 
				"user3@users.com",
				"user4@users.com",
				"user5@users.com",
				"user6@users.com",
				"user7@users.com",
				"user8@users.com"
				};
		return Arrays.asList(res);
	}
	
	@RequestMapping(value = "/to", method = RequestMethod.GET)
	public List<String> getTo ()
	{
		String[] res = {
				"support1@supports.com", 
				"support2@supports.com", 
				"support3@supports.com",
				"support4@supports.com",
				"support5@supports.com",
				"support6@supports.com",
				"support7@supports.com",
				"support8@supports.com"
				};
		return Arrays.asList(res);
	}

}
