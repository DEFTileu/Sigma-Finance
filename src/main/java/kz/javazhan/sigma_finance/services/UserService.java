package kz.javazhan.sigma_finance.services;

import kz.javazhan.sigma_finance.domain.DTOS.UserDTO;
import kz.javazhan.sigma_finance.domain.entities.User;

 public interface UserService {
     User getCurrentUser();

     User findUserByPhone(String phone);

     User changeProfile(UserDTO userDTO);
 }
