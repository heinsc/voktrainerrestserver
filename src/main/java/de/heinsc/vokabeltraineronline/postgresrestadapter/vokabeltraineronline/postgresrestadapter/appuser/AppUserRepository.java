package de.heinsc.vokabeltraineronline.postgresrestadapter.vokabeltraineronline.postgresrestadapter.appuser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	Optional<AppUser> findByeMailAddress(String eMailAddress);

	Optional<AppUser> findByeMailAddressAndPasswordDecoded(String eMailAddress, byte[] passwordDecoded);
	
	Optional<AppUser> findByIdAndPasswordDecoded(Long id, byte[] passwordDecoded);
}
