package com.app.ecom.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.app.ecom.dto.AnschriftDTO;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.dto.UserResquest;
import com.app.ecom.model.Anschrift;
import com.app.ecom.model.User;
import com.app.ecom.model.UserRole;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService
{
  private final UserRepository userRepository;

  public List<UserResponse> fetchAllUsers()
  {
    return userRepository.findAll()
                         .stream()
                         .map(this::mapUserToUserResponse)
                         .collect(Collectors.toList());
  }

  public void addUser(UserResquest userResquest)
  {
    userRepository.save(mapUserResquestToUser(userResquest));
  }

  public Optional<UserResponse> fetchUser(Long id)
  {
    return userRepository.findById(id).map(this::mapUserToUserResponse);
  }

  public boolean updatedUser(Long id, UserResquest userUpdated)
  {
    return userRepository.findById(id)
                         .map(user -> {mapUserResquestToUser(userUpdated);
                         userRepository.save(user);
                         return true;})
            .orElse(false);

  }

  private UserResponse mapUserToUserResponse(User user){
    UserResponse userResponse = new UserResponse();
    userResponse.setId(String.valueOf(user.getId()));
    userResponse.setVorname(user.getVorname());
    userResponse.setNachname(user.getNachname());
    userResponse.setEmail(user.getEmail());
    userResponse.setTelefon(user.getTelefon());
    userResponse.setUserRole(UserRole.CUSTOMER);

    if(user.getAnschrift() != null){
      AnschriftDTO anschriftDTO = new AnschriftDTO();
      anschriftDTO.setStrasse(user.getAnschrift().getStrasse());
      anschriftDTO.setOrt(user.getAnschrift().getOrt());
      anschriftDTO.setPlz(user.getAnschrift().getPlz());
      anschriftDTO.setHausnummer(user.getAnschrift().getHausnummer());
      anschriftDTO.setLand(user.getAnschrift().getLand());
      userResponse.setAnschrift(anschriftDTO);
    }
    return userResponse;
  }

  private User mapUserResquestToUser(UserResquest userResquest)
  {
    User user = new User();
    user.setEmail(userResquest.getEmail());
    user.setNachname(userResquest.getNachname());
    user.setVorname(userResquest.getVorname());
    user.setTelefon(userResquest.getTelefon());
    if(userResquest.getAnschrift() != null){
      Anschrift anschrift = new Anschrift();
      anschrift.setStrasse(userResquest.getAnschrift().getStrasse());
      anschrift.setOrt(userResquest.getAnschrift().getOrt());
      anschrift.setPlz(userResquest.getAnschrift().getPlz());
      anschrift.setHausnummer(userResquest.getAnschrift().getHausnummer());
      anschrift.setLand(userResquest.getAnschrift().getLand());
      user.setAnschrift(anschrift);
    };
    return user;
  }

}
