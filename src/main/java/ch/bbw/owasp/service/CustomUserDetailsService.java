package ch.bbw.owasp.service;

import ch.bbw.owasp.model.AppUser;
import ch.bbw.owasp.repository.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User nicht gefunden"));
        return new CustomUserDetails(user);
    }

    public static class CustomUserDetails extends User {
        private final Long employeeId;

        public CustomUserDetails(AppUser appUser) {
            super(appUser.getUsername(), appUser.getPassword(), List.of(new SimpleGrantedAuthority(appUser.getRole())));
            this.employeeId = appUser.getEmployeeId();
        }

        public Long getEmployeeId() {
            return employeeId;
        }
    }
}
