package fr.esgi.jee.api.partner.domain;
import fr.esgi.jee.api.authentication.login.Role;
import fr.esgi.jee.api.authentication.login.RoleRepository;
import fr.esgi.jee.api.authentication.security.TokenProvider;
import fr.esgi.jee.api.users.domain.User;
import fr.esgi.jee.api.users.domain.UserRepository;
import fr.esgi.jee.api.users.domain.UserServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PartnerServiceImpl implements PartnerService {

    private final UserServiceImpl userService;
    private final RoleRepository roleRepository;
    private final PartnerRepository partnerRepository;

    /**
     * Constructor Injection
     * better than @Autowired
     */
    public PartnerServiceImpl(PartnerRepository partnerRepository, RoleRepository roleRepository, UserServiceImpl userService) {
        this.partnerRepository = partnerRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Override
    public Partner addPartner(Partner partner, User user) {

        Partner createdPartner = partnerRepository.save(
                Partner.builder()
                        .name(partner.getName())
                        .phoneNumber(partner.getPhoneNumber())
                        .address(partner.getAddress())
                        .createDate(new Date())
                        .closeDate(null)
                        .build()
        );

        var partners = user.getPartners() == null ? new HashSet<Partner>() : user.getPartners();
        partners.add(createdPartner);
        user.setPartners(partners);
        userService.updateUser(user);

        return createdPartner;
    }



    @Override
    public List<Partner> getPartners() {
        return partnerRepository.findAll();
    }

    public List<Partner> findAll(){
        return partnerRepository.findAll();
    }

    public List<Partner> findById(String id){
        return new ArrayList<>(userService.findUserById(id).getPartners());
    }

}
