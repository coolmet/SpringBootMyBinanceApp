package com.my.binance.model.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import com.my.binance.model.UserDetailsModel;

public interface UserDetailsModelImpl extends JpaRepository<UserDetailsModel,Long>
{
	UserDetailsModel findByUsername(String username);
}
