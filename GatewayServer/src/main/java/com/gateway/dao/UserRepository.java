package com.gateway.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gateway.pojos.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	
	public Users findByMobileNo(String userName);
	public Users findByMobileNoAndPassword(String userName,String password);

}
