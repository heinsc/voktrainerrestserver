package de.heinsc.vokabeltraineronline.postgresrestadapter.vokabeltraineronline.postgresrestadapter.appuser;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class AppUserController {

    @Autowired
    private AppUserRepository repository;

    // Find
    @GetMapping("/appUsers")
    List<AppUser> findAll() {
        List<AppUser> findAll = repository.findAll();
//		findAll.forEach(currentUser -> currentUser.setPasswordDecoded("hidden".getBytes()));
		return findAll;
    }

    // Save new
    @PostMapping("/appUsers")
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    AppUser newAppUser(@RequestBody AppUser newAppUser) {
    	if (//
    			null == newAppUser.getPasswordDecodedScnd()//
    			|| null == newAppUser.getPasswordDecoded() //
    			|| !Arrays.equals(newAppUser.getPasswordDecodedScnd(),newAppUser.getPasswordDecoded())//
    	) {
    		return handleError("Both password and second password must be filled with the same value.");
    	}
    	if (Strings.isEmpty(newAppUser.geteMailAddress())) {
    		return handleError("eMail address must not be empty.");
    	}
    	if (repository.findByeMailAddress(newAppUser.geteMailAddress()).isPresent()){
    		return handleError("user with eMail address already exists");
    	}
    	newAppUser.setLastLogin(new Date());
    	newAppUser.setResultMessage("New user has been added.");
        return repository.save(newAppUser);
    }

    private AppUser handleError(String message) {
		AppUser error = new AppUser();
		error.setResultMessage(message);
		return error;
	}

	// Find unique by id
    @GetMapping("/appUsers/{id}")
    AppUser findOne(@PathVariable Long id) throws Exception {
        return repository.findById(id)
        		.map(result -> {
        			result.setResultMessage("user found");
        			return result;
        		}).orElseGet(() -> {
        			return handleError("no user found with id="+id);
        		});
    }

    // update new password or eMail address
    @PutMapping("/appUsers/{id}")
    AppUser update(//
    		@RequestBody AppUser newAppUser//
    		, @PathVariable Long id//
    ) {
    	if (//
    			(//
    					Strings.isEmpty(newAppUser.geteMailAddress()) //
    					&& ( //
    							null == newAppUser.getPasswordDecodedScnd() //
    							|| 0 ==  newAppUser.getPasswordDecodedScnd().length//
    					)
    			) || (//
    					Strings.isNotEmpty(newAppUser.geteMailAddress()) //
    					&& null != newAppUser.getPasswordDecodedScnd() //
    					&& newAppUser.getPasswordDecodedScnd().length > 0
    					
    			)
    	){
    		return handleError("Exactly one of new password or eMail address must be filled");
    	}
    	return repository.findByIdAndPasswordDecoded(id, newAppUser.getPasswordDecoded())
    			.map( x -> {
    		    	// find out weather email or password has to be updated
    		    	if (Strings.isNotEmpty(newAppUser.geteMailAddress())){
    		        	if (repository.findByeMailAddress(newAppUser.geteMailAddress()).isPresent()){
    		        		return handleError("user with eMail address already exists");
    		        	}
                        x.seteMailAddress(newAppUser.geteMailAddress());
                        x.setResultMessage("email address updated");
    		    	} else { // only the case that password is set is remaining
    		    		x.setPasswordDecoded(newAppUser.getPasswordDecodedScnd());
    		    		x.setResultMessage("password updated");
    		        }
                    x.setLastLogin(new Date());
                    return repository.save(x);
    			}).orElseGet(() -> {
    				return handleError("no user found with id="+id+" and password");
                });
     }
    //check eMail address andpassword for login
    @PutMapping("/appUsers")
    AppUser authentificate(//
    		@RequestBody AppUser anAppUser//
    ) {
    	return repository.findByeMailAddressAndPasswordDecoded(//
    			anAppUser.geteMailAddress()//
    			, anAppUser.getPasswordDecoded()//
    	).map(
    			x -> {
    				x.setLastLogin(new Date());
    				x.setResultMessage("auth ok");
    				return repository.save(x);
    			}
    	).orElseGet(//
    		() ->{ return handleError("wrong password");}
    	);
    }

    @DeleteMapping("/appUsers/{id}")
    AppUser deleteAppUser(//
    		@PathVariable Long id//
    		, @RequestBody AppUser appuser//
    ) {
    	return repository.findByIdAndPasswordDecoded(id, appuser.getPasswordDecoded())
    			.map( x -> {
		    		x.setResultMessage("user deleted");
                    repository.deleteById(id);
                    return x;
    			}).orElseGet(() -> {
    				return handleError("no user found with id="+id+" and password");
                });
    	
    }

}
