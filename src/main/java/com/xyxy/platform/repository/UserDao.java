
package com.xyxy.platform.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.xyxy.platform.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	User findByLoginName(String loginName);

	User findByLoginNameAndStatus(String loginName, int status);
}
